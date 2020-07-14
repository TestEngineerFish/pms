package com.einyun.app.common.utils;


import android.app.Activity;
import android.widget.Toast;

import com.einyun.app.common.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareUtil {
    public static void share(Activity activity, String title,String content, String url) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage image = new UMImage(activity, R.mipmap.ico_app);
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
        new ShareAction(activity).withMedia(web).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(activity, "分享成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(activity, "分享失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Toast.makeText(activity, "分享取消", Toast.LENGTH_LONG).show();
                    }
                }).open();
    }


}
