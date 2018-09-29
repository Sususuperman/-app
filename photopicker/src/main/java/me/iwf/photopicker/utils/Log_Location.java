package me.iwf.photopicker.utils;

import android.content.Context;
import android.location.Location;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author zhuhui
 *
 */
public class Log_Location {
	
	/**
	 * @param name
	 *            需要保存文件的文件名,如log.txt（无需添加路径，只传入文件名即可）
	 * @param sendtime
	 *            位置点上传到服务器的时间（为0则表明是存点log保存，不为0则为上传点位或普通log保存，
	 *            目前只在PolygonRecordUserTask中上传成功的时候此值不为0，其他各处均为0）
	 * @param location
	 *            为GPSLocation或android.location.Location或String，在保存log的时候会做相应的处理
	 * @param context
	 *            上下文对象（可以传入null，为null则加载app中的全局context对象）
	 * @throws IOException
	 *             抛出读写异常
	 */

	public static String logPath = "/log/";
	public static void logToSDcard(String name, long sendtime,
			Object location,Context context)  {
		try {
				writeLog(name, sendtime, location);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @param path
	 *            需要保存文件的文件名
	 * @param sendtime
	 *            位置点上传到服务器的时间（为0则表明是存点log保存，不为0则为上传点位log保存，
	 *            目前只在PolygonRecordUserTask中上传成功的时候此值不为0，其他各处均为0）
	 * @param location
	 *            为GPSLocation或android.location.Location，在保存log的时候会做相应的处理
	 * @throws IOException
	 *             抛出读写异常
	 */
	private static void writeLog(String path, long sendtime, Object location)
			throws IOException 
	{

		String SDPath = Environment.getExternalStorageDirectory()
				+ logPath + DateUtils.transforMillToDate(System.currentTimeMillis());// /log/当天日期/
		String ROOTPath =Environment.getExternalStorageDirectory()
				+ logPath ;// 日志保存的根目录
		File rootFile = new File(ROOTPath);
		File file = new File(SDPath);
		if (!file.exists())// 当天的log日志文件夹不存在
		{
			dirfiles(rootFile);
			file.mkdirs();// 创建目录
		}
		saveLoaction( path, sendtime, location);
		
	}

	/**存储位置的主方法
	 * @param path 文件名
	 * @param sendtime 点位上传时间
	 * @param location   为GPSLocation或android.location.Location，在保存log的时候会做相应的处理
	 * @throws IOException 抛出的读写异常
	 */
	private static void saveLoaction(String path, long sendtime, Object location) throws IOException
	{
		
		String SDPath = Environment.getExternalStorageDirectory()
				+ logPath + DateUtils.transforMillToDate(System.currentTimeMillis());// /log/当天日期/
		File F = new File(SDPath + File.separator + path);
		// 如果文件不存在,就动态创建文件
		if (!F.exists()) 
		{
			F.createNewFile();
		}
		
		
		FileWriter fw = null;
		String writeContent = getLocationmsg(sendtime, location);
		try 
		{
			// 设置为:True,表示写入的时候追加数据
			fw = new FileWriter(F, true);
			// 回车并换行
			fw.write(writeContent + "\r\n");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (fw != null) 
			{
				fw.close();
			}
		}
	}

	/**
	 * 将location组织成需要输出的log信息
	 * 
	 * @param location
	 * @return
	 */
	private static String getLocationmsg(long sendtime, Object location)
	{
		String locmsg = "";
		String content = "";
//		if (location instanceof GPSLocation) 
//		{
//			
//			// 由于上传点位是将数据库中保存的点位上传到服务器，而不是系统的loction对象是项目中的GPSLocation对象，对于一些没有的字段如：精度写成0，是否含精度写成false
//			GPSLocation gpsLocation = (GPSLocation) location;//强制转换成GPSLocation
//			
//			locmsg = gpsLocation.getTruelat() + "\t" + gpsLocation.getTruelng()
//					+ "\t" + gpsLocation.getProvider() + "\t" + "0" + "\t"
//					+ "false" + "\t" + gpsLocation.getDateline();
//			
//			content = "'" + DateUtils.transforMillToDateInfo(sendtime / 1000) + "\t"
//					+ sendtime + "\t" + "'"
//					+ DateUtils.transforMillToDateInfo(gpsLocation.getDateline() / 1000)
//					+ "\t" + locmsg;
//
//		} 
		if (location instanceof Location) 
		{
			Location location2 = (Location) location;//强制转换成Location
			
			locmsg = location2.getLatitude() + "\t" + location2.getLongitude()
					+ "\t" + location2.getProvider() + "\t"
					+ location2.getAccuracy() + "\t" + location2.hasAccuracy()
					+ "\t" + location2.getTime();
			
			content = "'" + DateUtils.transforMillToDateInfo(location2.getTime() / 1000)
					+ "\t" + locmsg;
		}
        else if(location instanceof  String){
            content = "'" + DateUtils.transforMillToDateInfo(System.currentTimeMillis() / 1000)	+ "\t"  + (String)location;
        }

		return content;
	}
	
	/**
     * 遍历log文件夹并且打包最近4天的log
     */
    private static void dirfiles(File file) 
    {
        File[] files = file.listFiles(); 
        if (files == null) 
            return ; 
        int len = files.length;
        
        if(len>=5)//该目录下有多于5个的文件或目录，取出最近4天的文件进行判定是否打包
        {
        	selectRecentFile(file);//选出最近4天的文件或目录
        }
        
		for (int i = 0; i < len; i++) 
		{
			if (files[i].isDirectory())// 是目录则打包
			{
				FileUtils.rarfile(files[i]); // 打包文件
				
				FileUtils.DeleteFolder(files[i].getAbsolutePath());//删除对应文件
			}
		}
        return ;
	}


	/**
	 * 选出最近4天的文件 过期的文件删除
	 * @param file
	 */
	private static void selectRecentFile(File file) 
	{
		String SDPath = Environment.getExternalStorageDirectory()
				+ logPath;
		File[] files = file.listFiles();
		List<Long> list = new ArrayList<Long>();
		if (files == null)
			return;
		for(int i=0;i<files.length;i++)
		{
			long time = DateUtils.transforDateToMill(files[i].getName());
			list.add(time);
		}
		Collections.sort(list);//将list中的元素进行升序
		int len =  list.size();
		int size = len - 4;
		if(len>=5)
		{
			for(int i = 0;i<size;i++)
			{
				String path = DateUtils.transforMillToDate(list.get(i));//4天之前的文件
				File file2 = new File(SDPath+path);
			    if(file2.exists())
			    {
			    	FileUtils.DeleteFolder(SDPath+path);//删除对应文件
			    }
			    else
			    {
			    	FileUtils.DeleteFolder(SDPath+path+".zip");
			    }
			}
		}
	}
}
