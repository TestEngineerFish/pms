package com.einyun.app.base.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */
public class FileUtil {
    public static final String PMS_CACHE_PATH = "/pms";

    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取app cache路径（部分手机 该目录下新建文件夹中不能存放文件）
     *
     * @param context
     * @return
     */
    @Deprecated
    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    /**
     * 获取PMS缓存路径
     *
     * @return
     */
    public static String getPmsCachePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sd卡可用
            //sd卡根目录
            return Environment.getExternalStorageDirectory() + PMS_CACHE_PATH;
        } else {
            //当前sd卡不可用
            //获取手机本身存储根目录
            return Environment.getExternalStoragePublicDirectory("") + PMS_CACHE_PATH;
        }
    }

    /**
     * 获取文件缓存路径
     *
     * @return
     */
    public static String getDiskCacheFilePath() {
        return isFileExistAndCreated(getPmsCachePath() + "/fileCache");
    }

    /**
     * 获取图片缓存路径
     *
     * @return
     */
    public static String getDiskCacheImagePath() {
        return isFileExistAndCreated(getPmsCachePath() + "/imageCache");
    }

    /**
     * 获取视频缓存路径
     *
     * @return
     */
    public static String getDiskCacheVideoPath() {
        return isFileExistAndCreated(getPmsCachePath() + "/videoCache");
    }

    /**
     * 获取录音缓存路径
     *
     * @return
     */
    public static String getDiskCacheVoicePath() {
        return isFileExistAndCreated(getPmsCachePath() + "/voiceCache");
    }

    /**
     * 获取日志缓存路径
     *
     * @return
     */
    public static String getDiskCacheLogPath() {
        return isFileExistAndCreated(getPmsCachePath() + "/logCache");
    }

    /**
     * 获取保存图片的路径
     *
     * @return
     */
    public static String getDiskDownloadImagePath() {
        return isFileExistAndCreated(getPmsCachePath() + "/imageDownload");
    }

    /**
     * 判断文件夹是否存在  不存在会创建
     *
     * @param path
     */
    private static String isFileExistAndCreated(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取文件后缀名
     *
     * @param filePath
     * @return
     */
    public static String getFileSuffixName(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * 传入文件下载链接判断文件是否已经在本地存在
     *
     * @return
     */
    public static boolean isExsite(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        //判断本地是否存在该文件
        if (fileIsExists(getDiskCacheFilePath() + "/" + fileName)) {
            return true;
        }
        return false;
    }

    /**
     * 根據网络路径获取本地已下载的文件地址
     *
     * @return
     */
    public static String getFileLocalUrl(String fileUrl) {
        if (isExsite(fileUrl)) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
            return getDiskCacheFilePath() + "/" + fileName;
        }
        return "";
    }

    /**
     * 根据文件路径 获取文件名称
     *
     * @param fileUrl
     * @return
     */
    public static String getFileNmae(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    }

    /**
     * 获取文件名称
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "";
        } else {
            return f.getName();
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static String getFileSize(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return "0 MB";
        } else {
            long size = f.length();
            return formatFileSize(size);
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long fileSize(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return 0;
        } else {
            long size = f.length();
            return size;
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS) {
//        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df = new DecimalFormat("#.0");//保留一位小数
        String fileSizeString;
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 打开文件的方法
     *
     * @param filePath
     * @return
     */
    public static Intent openFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getVideoFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        } else if (end.equals("ppt") || end.equals("pptx")) {
            return getPptFileIntent(filePath);
        } else if (end.equals("xls") || end.equals("xlsx")) {
            return getExcelFileIntent(filePath);
        } else if (end.equals("doc") || end.equals("docx")) {
            return getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        } else {
            return getAllIntent(filePath);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param strFile
     * @return
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap, String parentPath) {
        String savePath;
        File filePic;
        try {
            filePic = new File(parentPath + "/" + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return filePic.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * Android获取一个用于打开VIDEO文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * Android获取一个用于打开AUDIO文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * Android获取一个用于打开Html文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * Android获取一个用于打开图片文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getImageFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
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
     * Android获取一个用于打开CHM文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
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

    public static void compress(String filepath, CallBack<File> callBack){
        Luban.with(BasicApplication.getInstance())
                .load(filepath)
                .ignoreBy(100)
                .setTargetDir(getCachePath(BasicApplication.getInstance()))
                .setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                Logger.d("Luban compress start");
            }

            @Override
            public void onSuccess(File file) {
                callBack.call(file);
            }

            @Override
            public void onError(Throwable e) {
                callBack.onFaild(e);
            }
        }).launch();
    }

    public static final String ROOT_DIR		= "Android/data/";
    public static final String DOWNLOAD_DIR	= "download";
    public static final String CACHE_DIR		= "cache";
    public static final String ICON_DIR		= "icon";

    public static final String APP_STORAGE_ROOT = "maybeStorageRoot";

    /** 判断SD卡是否挂载 */
    public static boolean isSDCardAvailable()
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /** 获取下载目录 */
    public static String getDownloadDir(Context context)
    {
        return getDir(DOWNLOAD_DIR,context);
    }

    /** 获取缓存目录 */
    public static String getCacheDir(Context context)
    {
        return getDir(CACHE_DIR,context);
    }

    /** 获取icon目录 */
    public static String getIconDir(Context context)
    {
        return getDir(ICON_DIR,context);
    }

    /** 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录 */
    public static String getDir(String name, Context context)
    {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable())
        {
            sb.append(getAppExternalStoragePath());
        }
        else
        {
            sb.append(getCachePath(context));
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path))
        {
            return path;
        }
        else
        {
            return null;
        }
    }

    /** 获取SD下的应用目录 */
    public static String getExternalStoragePath(Context context)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR+context.getPackageName());
        sb.append(File.separator);
        return sb.toString();
    }

    /** 获取SD下当前APP的目录 */
    public static String getAppExternalStoragePath()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(APP_STORAGE_ROOT);
        sb.append(File.separator);
        return sb.toString();
    }

    /** 获取应用的cache目录 */
    public static String getCachePath(Context context)
    {
        File f =context.getCacheDir();
        if (null == f)
        {
            return null;
        }
        else
        {
            return f.getAbsolutePath() + "/";
        }
    }

    /** 创建文件夹 */
    public static boolean createDirs(String dirPath)
    {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) { return  file.mkdirs(); }
        return true;
    }

    /**产生图片的路径，这里是在缓存目录下*/
    public static String generateImgePathInStoragePath(Context context){
        return getDir(ICON_DIR,context) + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    /**
     * 发起剪裁图片的请求
     * @param activity 上下文
     * @param srcFile 原文件的File
     * @param output 输出文件的File
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(AppCompatActivity activity, File srcFile, File output, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(activity,srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 480);
        // intent.putExtra("return-data", false);

        //        intent.putExtra(MediaStore.EXTRA_OUTPUT,
        //                Uri.fromFile(new File(FileUtils.picPath)));

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection

        activity.startActivityForResult(intent, requestCode);
    }

    /**安卓7.0裁剪根据文件路径获取uri*/
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 复制bm
     * @param bm
     * @return
     */
    public static String saveBitmap(Bitmap bm, Context context) {
        String croppath="";
        try {
            File f = new File(generateImgePathInStoragePath(context));
            //得到相机图片存到本地的图片
            croppath=f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }

    /**
     * 按质量压缩bm
     * @param bm
     * @param quality 压缩率
     * @return
     */
    public static String saveBitmapByQuality(Bitmap bm, int quality, Context context) {
        String croppath="";
        try {
            File f = new File(generateImgePathInStoragePath(context));
            //得到相机图片存到本地的图片
            croppath=f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG,quality, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }

}
