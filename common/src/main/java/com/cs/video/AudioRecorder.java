package com.cs.video;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;

import com.cs.common.utils.Logger;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AudioRecorder implements Runnable
{
	private static final String TAG = "AudioRecorder";
	
	private static final int READY = 1;
	private static final int RECORDING = 2;
//	private static final int ERROR = 3;
	private static final int STOPPED = 4;
	
	private RecorderParam param;
	
  private AudioRecord audioRecord;
  private RandomAccessFile file;
  private int bufferSize;
  private byte[] buffer;
  private int length;
  private Thread thread;
  private int state = STOPPED;
  
  public AudioRecorder(RecorderParam param)
	{
		super();
		this.param = param;
	}

	private int initPcmFile()
  {
  	try
		{
			file = new RandomAccessFile(param.audioFile, "rw");
			file.setLength(0L);
//			file.writeBytes("RIFF");
//			file.writeInt(0);//文件长度
//			file.writeBytes("WAVE");
//			file.writeBytes("fmt ");
//			file.writeInt(Integer.reverseBytes(16));//文件内部格式信息数据的大小
//			file.writeShort(Short.reverseBytes((short)1));//音频数据的编码方式。1 表示是 PCM 编码
//			file.writeShort(Short.reverseBytes((short)1));//通道数，单声道为1，双声道为2 
//			file.writeInt(Integer.reverseBytes(8000));//采样率（每秒样本数），表示每个通道的播放速度
//			file.writeInt(Integer.reverseBytes(16000));//波形音频数据传送速率，其值为通道数×每秒数据位数×每样本的数据位数／8
//			file.writeShort(Short.reverseBytes((short)2));//每次采样的大小，其值为通道数×每样本的数据位值／8
//			file.writeShort(Short.reverseBytes((short)16));//每样本的数据位数，表示每个声道中各个样本的数据位数。如果有多个声道，对每个声道而言，样本大小都一样
//			file.writeBytes("data");
//			file.writeInt(0);//语音数据的长度 
			return 0;
		} 
  	catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
  }
  
  public boolean init()//初始化记录器
  {
  	if(state != STOPPED)
  		return false;

  	length = 0;
  	
  	bufferSize = AudioRecord.getMinBufferSize(param.sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
  	if(bufferSize <= 0)
  		return false;
  	
  	Logger.d(TAG, "getMinBufferSize:" + bufferSize);
  	buffer = new byte[4 * bufferSize];
  	
  	audioRecord = new AudioRecord(AudioSource.MIC, param.sampleRate, 
  			AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 4 * bufferSize);
  	
  	if(audioRecord.getState() != AudioRecord.STATE_INITIALIZED)
  		return false;

  	if(initPcmFile() < 0)
  	{
  		audioRecord.release();
  		return false;
  	}

  	thread = new Thread(this);
  	state = READY;
  	
  	return true;
  }
  
  public void start()
  {
  	if(state != READY)
  		return;

  	param.audioSize = 0;
  	state = RECORDING;
  	thread.start();
  }
  
  public void run ()
  {
  	try
		{
			audioRecord.startRecording();
			audioRecord.read(buffer, 0, buffer.length / 2);
			
			while(state == RECORDING)
			{
				int size = audioRecord.read(buffer, 0, buffer.length / 2);
				length += size;
				file.write(buffer, 0, size);
			}
		}
  	catch (IOException e)
		{
			e.printStackTrace();
			stop();
		}
  	audioRecord.stop();
  	
  	try
		{
//			file.seek(4L);
//			file.writeInt(Integer.reverseBytes(36 + length));
//			file.seek(40L);
//			file.writeInt(Integer.reverseBytes(length));
  		
  		param.audioSize = length;
			file.close();
		} 
  	catch (IOException e)
		{
			e.printStackTrace();
		}
  	
  	release();
  }
  
  public void stop()
  {
  	if(state != RECORDING)
  		return;
  	
		state = STOPPED;
  	try
		{
			thread.join();
		}
  	catch (InterruptedException e)
		{
			e.printStackTrace();
		}
  }
  
  public void release()
  {
  	if(state == RECORDING || audioRecord != null)
  		audioRecord.stop();
  	
  	if(audioRecord != null)
  	{
  		audioRecord.release();
  		audioRecord = null;
  	}
  	
  	try
		{
			if(file != null)
			{
				file.close();
			}
		} 
  	catch (IOException e)
		{
			e.printStackTrace();
		}
  	state = STOPPED;
  }
}
