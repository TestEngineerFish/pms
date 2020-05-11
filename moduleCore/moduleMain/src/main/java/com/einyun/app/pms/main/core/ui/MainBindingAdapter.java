package com.einyun.app.pms.main.core.ui;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.library.resource.workorder.model.OrderState;
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

    @BindingAdapter("wordOrderHeight")
    public static void wordOrderHeight(LinearLayout ll, String path) {

    }
    @BindingAdapter("line")
    public static void line(TextView textView, int  value) {
        switch (value) {
            case 0:
                textView.setText("全部");
                break;
            case 1:
                textView.setText("环境");
                break;
            case 2:
                textView.setText("工程");
                break;
            case 3:
                textView.setText("秩序");
                break;
            case 4:
                textView.setText("客服");
                break;
        }


    }

    @BindingAdapter("signType")
    public static void signType(TextView textView, int  value) {
        switch (value) {
            case 1:
                textView.setText("二维码");
                break;
            case 2:
                textView.setText("NFC");
                break;
            case 3:
                textView.setText("不签到");
                break;
            case 4:
                textView.setText("蓝牙");
                break;
        }

    }
    @BindingAdapter("isPhoto")
    public static void isPhoto(TextView textView, int  value) {

        if (value==1) {
            textView.setText("是");
        }else {
            textView.setText("否");
        }

    }
    /**
     * 列表状态
     *
     * @param view
     * @param value
     */
    @BindingAdapter("status")
    public static void status(TextView view, String value) {
        int valueInt = Integer.parseInt(value);
        if (valueInt == OrderState.NEW.getState()) {
            view.setText(com.einyun.app.common.R.string.text_state_new);
        } else if (valueInt == OrderState.HANDING.getState()) {
            view.setText(com.einyun.app.common.R.string.text_state_processing);
        } else if (valueInt == OrderState.APPLY.getState()) {
            view.setText(com.einyun.app.common.R.string.text_apply);
        } else if (valueInt == OrderState.CLOSED.getState()) {
            view.setText(com.einyun.app.common.R.string.text_state_closed);
        }
    }
    @BindingAdapter("status")
    public static void status(ImageView view, String value) {
        int valueInt = Integer.parseInt(value);
        if (valueInt == OrderState.NEW.getState()) {
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_new);
        } else if (valueInt == OrderState.HANDING.getState()) {
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_processing);
        } else if (valueInt == OrderState.APPLY.getState()) {
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_work_order_apply);
        } else if (valueInt == OrderState.CLOSED.getState()) {
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_closed);
        }
    }
}
