package com.einyun.app.pms.main.core.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.pms.main.R;


/**
 * @ProjectName: pms
 * @Package: com.einyun.app.pms.pointcheck.ui
 * @ClassName: CheckPointBindiAdapter
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/22 0022 下午 18:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/22 0022 下午 18:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainBindingAdapter {

    @BindingAdapter("setHeadImage")
    public static void setHeadImage(ImageView iv, String imagePath) {
        Glide.with(BasicApplication.getInstance()).load(EinyunHttpService.getInstance().getBaseUrl() + "/media/" + imagePath)
                .apply(RequestOptions
                        .bitmapTransform(new CircleCrop()))
                .error(Glide.with(BasicApplication.getInstance()).load(R.mipmap.img_user_default_head).apply(RequestOptions
                        .bitmapTransform(new CircleCrop())))
                .into(iv);
    }

}
