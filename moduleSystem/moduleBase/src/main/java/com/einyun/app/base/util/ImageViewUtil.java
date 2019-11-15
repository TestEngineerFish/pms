package com.einyun.app.base.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.einyun.app.base.util.imageview.GlideProxy;
import com.einyun.app.base.util.imageview.IProx;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.util
 * @ClassName: ImageViewUtil
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/27 09:28
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/27 09:28
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ImageViewUtil {

    private static IProx prox;

    static {
        prox=new GlideProxy();//此处替换其他图片框架
    }

    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        prox.loadImageView(mContext,path,mImageView);
    }

    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        prox.loadImageViewSize(mContext,path,width,height,mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        prox.loadImageViewLoding(mContext,path,mImageView,lodingImage,errorImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        prox.loadImageViewLodingSize(mContext,path,width,height,mImageView,lodingImage,errorImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        prox.loadImageViewCache(mContext,path,mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        prox.loadImageViewPriority(mContext,path,mImageView);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        prox.loadImageViewDiskCache(mContext,path,mImageView);
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        prox.loadImageViewAnim(mContext,path,anim,mImageView);
    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        prox.loadImageViewThumbnail(mContext,path,mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        prox.loadImageViewCrop(mContext,path,mImageView);
    }

    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        prox.loadImageViewDynamicGif(mContext,path,mImageView);
    }

    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
       prox.loadImageViewStaticGif(mContext,path,mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<Drawable> requstlistener) {
        prox.loadImageViewListener(mContext,path,mImageView,requstlistener);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<Drawable> simpleTarget) {
        prox.loadImageViewContent(mContext,path,simpleTarget);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        prox.GuideClearDiskCache(mContext);
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        prox.GuideClearMemory(mContext);
    }
}
