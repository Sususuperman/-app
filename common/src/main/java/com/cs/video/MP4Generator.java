package com.cs.video;

public class MP4Generator
{
	/* 压缩数据生成mp4视频
	 *
	 * outfilename:		最终生成的视频文件,注意扩展名为mp4
	 * videofilename:	单独录制的yuv420sp格式的视频
	 * video_size:		视频的桢数量
	 * source_width:	原始视频的宽度
	 * source_height:	原始视频的高度
	 * rotated:			是否需要将原始视频顺时针旋转90度
	 * width:			目标视频的宽度
	 * height:			目标视频的高度
	 *
	 *
	 * audiofilename:	单独录制的格式为pcm的音频
	 * audio_size:		音频文件的大小
	 * sample_rate:		音频的采样率,注意因为采用faac,所以最小为8000
	 * channels:		音频的声道数目.
	 *
	 *
	 * */
	public static native int compress(String outfilename,
			String videofilename, int video_size, int source_width, int source_height,
			int rotated, int width, int height,
			String audiofilename, int audio_size, int sample_rate, int channels);

	/* 已经完成的百分比 */
	public static native double ratio();

	/*
	 * 给原始yuv420sp生成缩略图
	 * outfilename:		最终生成的jpeg文件
	 * image_width:		生成的jpeg的宽度
	 * image_height:	生成的jpeg的高度
	 * quality:				生成的图片的质量,0-100. 0 文件大小比较小, 100 图片质量比较高.
	 * index:					取第几桢生成缩略图,0代表第一桢

	 * yuvfilename:		原始yuv420sp文件
	 * rotated:				是否需要将原始视频顺时针旋转90度
	 * source_width:	目标视频的宽度
	 * source_height:	目标视频的高度

	 */
	public static native int compressToJpeg(String outfilename,int image_width, int image_height, int quality, int index,
			String yuvfilename, int rotated, int source_width, int source_height);
	
	static 
	{
		System.loadLibrary("mp4Generator"); 
	} 
}
