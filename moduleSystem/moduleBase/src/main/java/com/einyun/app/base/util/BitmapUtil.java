package com.einyun.app.base.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class BitmapUtil {


    private static String PHOTO_FILE_NAME = "PMSManagerPhoto";

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    //转换为圆形状的bitmap
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }


    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImage(String filePath,int quality) {

        //原文件
        File oldFile = new File(filePath);


        //压缩文件路径 照片路径/
//        String targetPath =filePath;
        String targetPath =  String.format("%d.jpg", new Date().getTime());

        // int quality = 80;//压缩比例0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
               outputFile.getParentFile().mkdirs();
               // outputFile.createNewFile();
            } else {
                //outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * bitmap变成圆角
     *
     * @param bitmap
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        // 前面同上，绘制图像分别需要bitmap，canvas，paint对象
        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
        Bitmap bm = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 这里需要先画出一个圆
        canvas.drawCircle(200, 200, 200, paint);
        // 圆画好之后将画笔重置一下
        paint.reset();
        // 设置图像合成模式，该模式为只在源图像和目标图像相交的地方绘制源图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bm;
    }

    /**
     * bitmap变成圆角
     *
     * @param bitmap
     * @return
     */
    public Bitmap toRoundBitmap2(Bitmap bitmap) {
        // 这里可能需要调整一下图片的大小来让你的图片能在圆里面充分显示
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        // 构建一个位图对象，画布绘制出来的图片将会绘制到此bitmap对象上
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        // 构建一个画布,
        Canvas canvas = new Canvas(bm);
        // 获得一个画笔对象，并设置为抗锯齿
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 获得一种渲染方式对象
        // BitmapShader的作用是使用一张位图作为纹理来对某一区域进行填充。
        // 可以想象成在一块区域内铺瓷砖，只是这里的瓷砖是一张张位图而已。
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 设置画笔的渲染方式
        paint.setShader(shader);
        // 通过画布的画圆方法将渲染后的图片绘制出来
        canvas.drawCircle(100, 100, 100, paint);
        // 返回的就是一个圆形的bitmap对象
        return bm;
    }

    /**
     * 合成群组头像
     *
     * @param mSize
     * @param data
     * @return
     */
    public Bitmap generateGroupAvator(int mSize, List<Bitmap> data) {
        switch (data == null ? 0 : data.size()) {
            case 1: {
                return data.get(0);
            }
            case 2: {
                int widthSize = mSize / 2;
                int heightSize = mSize / 2;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    canvas.drawBitmap(newBit, i % 2 * widthSize, (mSize - heightSize) / 2 + i / 2 * heightSize, null);
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 3: {
                int widthSize = mSize / 2;
                int heightSize = mSize / 2;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    if (i == 0) {
                        canvas.drawBitmap(newBit, (mSize - widthSize) / 2, 0, null);
                    } else {
                        canvas.drawBitmap(newBit, i / 2 * widthSize, heightSize, null);
                    }
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 4: {
                int widthSize = mSize / 2;
                int heightSize = mSize / 2;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    canvas.drawBitmap(newBit, i % 2 * widthSize, i / 2 * heightSize, null);
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 5: {
                int widthSize = mSize / 3;
                int heightSize = mSize / 3;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    if (i < 2) {
                        canvas.drawBitmap(newBit, widthSize / 2 + i * widthSize, heightSize / 2, null);
                    } else {
                        canvas.drawBitmap(newBit, i % 3 * widthSize, heightSize / 2 + heightSize, null);
                    }
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 6: {
                int widthSize = mSize / 3;
                int heightSize = mSize / 3;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    canvas.drawBitmap(newBit, i % 3 * widthSize, heightSize / 2 + i / 3 * heightSize, null);
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 7: {
                int widthSize = mSize / 3;
                int heightSize = mSize / 3;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    if (i == 0) {
                        canvas.drawBitmap(newBit, widthSize, 0, null);
                    } else {
                        canvas.drawBitmap(newBit, (i + 2) % 3 * widthSize, (i + 2) / 3 * heightSize, null);
                    }
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 8: {
                int widthSize = mSize / 3;
                int heightSize = mSize / 3;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                canvas.drawColor(0xFFe1e2e3);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    if (i < 2) {
                        canvas.drawBitmap(newBit, widthSize / 2 + i * widthSize, 0, null);
                    } else {
                        canvas.drawBitmap(newBit, (i + 1) % 3 * widthSize, (i + 1) / 3 * heightSize, null);
                    }
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            case 9: {
                int widthSize = mSize / 3;
                int heightSize = mSize / 3;
                Bitmap groupAvator = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(groupAvator);
                for (int i = 0; i < data.size(); i++) {
                    Bitmap bitmap = data.get(i);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(widthSize * 1.0f / width, heightSize * 1.0f / height);
                    Bitmap newBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    canvas.drawBitmap(newBit, i % 3 * widthSize, i / 3 * heightSize, null);
                }
                canvas.save();
                canvas.restore();
                return groupAvator;
            }
            default:
                break;
        }
        return null;
    }
}
