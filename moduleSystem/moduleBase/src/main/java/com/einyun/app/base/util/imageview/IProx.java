package com.einyun.app.base.util.imageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.util.imageview
 * @ClassName: IProx
 * @Description: 图片处理代理高级抽象接口
 * @Author: chumingjun
 * @CreateDate: 2019/09/27 10:15
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/27 10:15
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface IProx {
    //默认加载
    void loadImageView(Context mContext, String path, ImageView mImageView);

    //加载指定大小
    void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView);

    //设置加载中以及加载失败图片
    void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView);

    //设置加载中以及加载失败图片并且指定大小
     void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView);

    //设置跳过内存缓存
    void loadImageViewCache(Context mContext, String path, ImageView mImageView);

    //设置下载优先级
    void loadImageViewPriority(Context mContext, String path, ImageView mImageView) ;

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
    void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView);

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
     void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView);

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView);

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    void loadImageViewCrop(Context mContext, String path, ImageView mImageView);

    //设置动态GIF加载方式
    void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView);

    //设置静态GIF加载方式
    void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView);

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<Drawable> requstlistener);

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    void loadImageViewContent(Context mContext, String path, SimpleTarget<Drawable> simpleTarget);

    //清理磁盘缓存
    void GuideClearDiskCache(Context mContext);

    //清理内存缓存
    void GuideClearMemory(Context mContext);
}
