package com.cs.video;

public class RecorderParam
{
/**************************************************************/
	//最后输出的视频的尺寸
	public int width = 224;
	public int height = 288;
	
	//视频分辨率,注意视频的高宽是翻转90度的
	public int videoWidth = 0;//游览时视频宽度
	public int videoHeight = 0;//游览时视频高度
	
	//视频桢数量
	public int videoSize = 0;
	
/**************************************************************/
	//音频采样率
	public int sampleRate = 8000;
	
	//声道数,是单声道
	public int channels = 1;
	
	//音频数据采样数量
	public int audioSize = 0;
	
	
/**************************************************************/
	//保存临时视频和音频文件的路径,这两个参数需要在真是项目和机器中通过代码改变
	public String videoFile = "/mnt/sdcard/temp.yuv";
	public String audioFile = "/mnt/sdcard/temp.pcm";
}
