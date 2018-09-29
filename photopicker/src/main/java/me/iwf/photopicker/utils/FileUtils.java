package me.iwf.photopicker.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import me.iwf.photopicker.ImagePagerActivity;

import static me.iwf.photopicker.utils.Log_Location.logPath;

public abstract class FileUtils {
    /**
     * 递归创建目录
     *
     * @param path
     * @return
     */
    public static boolean mkDir(String path) {
        boolean isFinished = false;

        try {
            File f = new File(path);
            if (f.isDirectory()) {
                return true;
            }

            File parent = f.getParentFile();
            if (!parent.exists()) {
                mkDir(parent.getAbsolutePath());
            }

            isFinished = f.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFinished;
    }

    private static final int BUFFER_SIZE = 4096;

    /**
     * @param src     要拷贝的输入流
     * @param size    要拷贝的输入流的大小
     * @param dest    目标文件路径
     * @param handler 拷贝文件过程的消息处理器
     * @param message 拷贝过程中已经拷贝字节数的消息编号，消息的arg1参数是当前已经拷贝的大小，arg2参数是要拷贝的大小
     * @return
     */
    public static boolean copy(InputStream src, long size, String dest, Handler handler, int message) {
        File f = new File(dest);
        mkDir(f.getParentFile().getAbsolutePath());

        InputStream in = src;
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(dest));

            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;

                if (handler != null && message != 0) {
                    Message msg = new Message();
                    msg.arg1 = byteCount;
                    msg.arg2 = (int) size;
                    msg.what = message;
                    handler.sendMessage(msg);
                }
            }
            out.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * @param src  要拷贝的输入流
     * @param dest 目标文件路径
     * @return
     */
    public static boolean copy(InputStream src, String dest) {
        return copy(src, 0, dest, null, 0);
    }

    /**
     * @param src     要拷贝的文件路径
     * @param dest    目标文件路径
     * @param handler 拷贝文件过程的消息处理器
     * @param message 拷贝过程中已经拷贝字节数的消息编号，消息的arg1参数是当前已经拷贝的大小，arg2参数是要拷贝的大小
     * @return
     */
    public static boolean copy(String src, String dest, Handler handler, int message) {
        try {
            File s = new File(src);
            InputStream in = new BufferedInputStream(new FileInputStream(src));

            return copy(in, s.length(), dest, handler, message);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    /**
     * @param src  要拷贝的文件路径
     * @param dest 目标文件路径
     * @return
     */
    public static boolean copy(String src, String dest) {
        return copy(src, dest, null, 0);
    }

    /**
     * 压缩图片
     *
     * @param BitmapOrg 源图
     * @param destFile  输出文件
     *                  压缩图片：如果压缩失败，拷贝原图片到目标图片。<p>
     *                  Andriod2.2以后有系统压缩方法。
     * @return
     */
    public static boolean compressImage(Bitmap BitmapOrg, String destFile) {
        try {
            // 判断源文件是否存在
            File fdest = new File(destFile);
            mkDir(fdest.getParentFile().getAbsolutePath());
//			BitmapFactory.Options options = new BitmapFactory.Options();
            int weight = BitmapOrg.getWidth();
            int height = BitmapOrg.getHeight();
            float scaleWidth = ((float) 540) / weight;
            float scaleHeight = ((float) 960) / height;
            scaleHeight = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the Bitmap
            matrix.postScale(scaleWidth, scaleHeight);
            // if you want to rotate the Bitmap
            // matrix.postRotate(45);
            // recreate the new Bitmap
            Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, weight,
                    height, matrix, true);

            FileOutputStream fos = new FileOutputStream(fdest);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 遍历文件下的所有zip文件
     *
     * @param path 文件的路径
     * @return
     */
    public static List<String> zipFile(String path) {
        List<String> fileList = new ArrayList<String>();
        File rootFile = new File(path);
        if (rootFile != null && rootFile.exists()) {
            File[] files = rootFile.listFiles();
            if (files == null)
                return fileList;
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(".zip")) {
                    fileList.add(files[i].getAbsolutePath());
                }
            }
        }
        return fileList;
    }

    /**
     * 打包文件夹
     *
     * @param file
     */
    public static void rarfile(File file) {
        String jarPath = Environment.getExternalStorageDirectory()
                + logPath + file.getName() + ".zip";// 打包文件的文件名
        File[] files = file.listFiles();
        if (files == null)
            return;
        try {
            byte[] buffer = new byte[100 * 1024];
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    jarPath));
            for (int i = 0; i < files.length; i++) {
                FileInputStream fis = new FileInputStream(files[i]);
                out.putNextEntry(new ZipEntry(files[i].getName()));
                int len;
                // 读入log文件，打包到zip文件
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                fis.close();
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或目录
     *
     * @param sPath 需要删除的文件路径
     * @return
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除文件
     *
     * @param sPath 需要删除文件的路径
     * @return
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录
     *
     * @param sPath 需要删除的文件的路径
     * @return
     */
    public static boolean deleteDirectory(String sPath) {
        boolean flag = false;

        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }

        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }


        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile() && !files[i].getName().endsWith(".zip")) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }


        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param destFile
     * @param angle
     * @return
     * @throws IOException
     */
    public static boolean compressImage(Bitmap bitmap, String destFile, int angle, int widthP, int heightP, int quality) throws IOException {
        Bitmap bitmap_scale = scale(bitmap, angle, widthP, heightP);
        System.gc();

        Bitmap bitmap_dest = null;
        if (angle != 0) {
            bitmap_dest = rotate(bitmap_scale, angle);
            System.gc();
        } else {
            bitmap_dest = bitmap_scale;
        }

        File fdest = new File(destFile);
        mkDir(fdest.getParentFile().getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(fdest);
        bitmap_dest.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        fos.close();
        if (!bitmap.isRecycled())
            bitmap.recycle();
        if (!bitmap_scale.isRecycled())
            bitmap_scale.recycle();
        if (!bitmap_dest.isRecycled())
            bitmap_dest.recycle();
        System.gc();
        return true;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param angle
     * @return
     * @throws IOException
     */
    private static Bitmap scale(Bitmap bitmap, int angle, int widthP, int heightP) throws IOException {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // 定义缩放的高和宽的比例
        float sw = 0;
        float sh = 0;
        if (w > h) {
            sw = ((float) heightP) / w;
            sh = ((float) widthP) / h;
        } else {
            sw = ((float) widthP) / w;
            sh = ((float) heightP) / h;
        }
        sh = sw > sh ? sh : sw;
        sw = sh;
        // 创建操作图片的用的Matrix对象
        Matrix matrix = new Matrix();

        matrix.postScale(sw, sh);

        Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return b;
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param angle
     * @return
     * @throws IOException
     */
    private static Bitmap rotate(Bitmap bitmap, int angle) throws IOException {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // 创建操作图片的用的Matrix对象
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);

        Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return b;
    }

    /**
     * 根据图片路径获取bmp对象
     * 此方法能有效的避免 bmp对象内存溢出
     *
     * @param path           附件路径
     * @param maxNumOfPixels 当前视频的分辨率 width*height
     * @return Bitmap 对象
     * @throws OutOfMemoryError 内存溢出异常
     */
    public static Bitmap loadBitmap(String path, int maxNumOfPixels) throws OutOfMemoryError {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true; //true 内存溢出返回图片宽高
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        //如果bmp为空 则按照opts返回的图片宽高 计算出图片的压缩最好基位
        if (bitmap == null) {
            //这里设置-1 是图片压缩的最小基位
            opts.inSampleSize = computeSampleSize(opts, -1, maxNumOfPixels);
            opts.inJustDecodeBounds = false;//false
            //重新加载bmp对象
            bitmap = BitmapFactory.decodeFile(path, opts);
        }
        return bitmap;
    }

    /**
     * 计算图片缩放 最好基位
     *
     * @param options        bmp内部类对象
     * @param minSideLength  压缩的最小基位
     * @param maxNumOfPixels 屏幕分辨率
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        //根据设备分辨率获取最小压缩基位
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        //计算图片的最好压缩基位
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     * 获取当前设备分辨率的图片压缩最小基位
     *
     * @param options        bmp内部类对象
     * @param minSideLength
     * @param maxNumOfPixels 屏幕分辨率 width*height
     * @return 最小压缩基位
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        //获取图片的大小
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 获取文件大小
     *
     * @param path 文件路径
     * @return Long 文件大小
     * @throws IOException
     */
    public static int getFileSize(String path) throws IOException {
        File f = new File(path);
        //file.length方法有时候返回的是0 这里是用文件流的形式确定文件大小
        FileInputStream fi = new FileInputStream(f);
        int size = fi.available();
        fi.close();
        return size;
    }

    /**
     * 获取指定图片的旋转角度值
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static int getOrientation_rotate(String path) throws IOException {
        //获取旋转角度
        int angle = 0;
        ExifInterface exifInterface = new ExifInterface(path);
        int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
        if (tag == ExifInterface.ORIENTATION_ROTATE_90) {//如果是旋转地图片则先旋转
            angle = 90;
        }
        if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
            angle = 180;
        }
        if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
            angle = 270;
        }
        return angle;
    }

    /**
     * 获取设备分辨率
     *
     * @return
     */
    public static List<Integer> getPixels(Activity activity) {
        List<Integer> list = new ArrayList<Integer>();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthP = displayMetrics.widthPixels;
        int heightP = displayMetrics.heightPixels;
        list.add(widthP);
        list.add(heightP);
        return list;
    }

    /**
     * 获得处理后的文件路径
     *
     * @param path    原始文件路径
     * @param temp    处理后的文件路径
     * @param quality 图片质量压缩比
     * @return
     * @throws IOException
     */
    public static String getAttachPath(String path, String temp, int quality, Activity activity) {

        try {
            Bitmap bitmap = null;
            // 获取旋转角度
            int angle = 0;
            angle = FileUtils.getOrientation_rotate(path);// 获取图片的旋转角度
            // 获取设备分辨率
            List<Integer> pixels = FileUtils.getPixels(activity);
            int widthP = pixels.get(0);
            int heightP = pixels.get(1);

            // 根据Path读取资源图片
            bitmap = FileUtils.loadBitmap(path, widthP * heightP);

            // 调用压缩、旋转
            FileUtils.compressImage(bitmap, temp, angle, widthP, heightP, quality);
        } catch (Throwable e) {
            temp = path;
        }
        return temp;
    }


    /**
     * 打开office附件
     * @param filePath
     * @return
     */
    public static void openFile(Context context, String filePath)
    {

        File file = new File(filePath);
        if (!file.exists()){
            Toast.makeText(context, "文件不存在!", Toast.LENGTH_SHORT).show();
            return ;
        }

        /* 取得扩展名 */
        String end = StringUtils.getFilenameExtension(filePath);
        Intent intent = null;
        /* 依扩展名的类型决定MimeType */
        if (end.equals("ppt"))
        {
            intent = getPptFileIntent(filePath);
        }
        else if (end.equals("xls") || end.equals("xlsx"))
        {
            intent =  getExcelFileIntent(filePath);
        }
        else if (end.equals("doc") || end.equals("docx"))
        {
            intent =  getWordFileIntent(filePath);
        }
        else if (end.equals("pdf"))
        {
            intent =  getPdfFileIntent(filePath);
        }
        else if (end.equals("txt"))
        {
            intent =  getTextFileIntent(filePath, false);
        }else if(end.equals("jpg") || end.equals("jpeg") || end.equals("png") || end.equals("gif")){
            intent =  getImgFileIntent(context,filePath);
        }

        try {
            if (intent != null) {
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "未发现可打开程序！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Android获取一个用于打开PPT文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * Android获取一个用于打开Excel文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * Android获取一个用于打开Word文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }


    /**
     * Android获取一个用于打开文本文件的intent
     *
     * @param param
     * @param paramBoolean
     * @return
     */
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getPdfFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     * @param param
     * @return
     */
    public static Intent getImgFileIntent(Context context, String param ){
        Intent intent = new Intent(context,ImagePagerActivity.class);
        ArrayList<String> urls = new ArrayList<>();
        urls.add(param);
        intent.putExtra("position", 0);
        intent.putExtra("urls", urls);
        return intent;
    }

    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
