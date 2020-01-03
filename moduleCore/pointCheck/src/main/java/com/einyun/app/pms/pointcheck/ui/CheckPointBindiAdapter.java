package com.einyun.app.pms.pointcheck.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.pms.pointcheck.R;

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
public class CheckPointBindiAdapter {

    @BindingAdapter("isPic")
    public static void setIsPic(TextView view,int value){
        if(value>0){
           view.setText(R.string.yes);
        }else{
            view.setText(R.string.no);
        }
    }

    @BindingAdapter("isUnusual")
    public static void setIsUnusual(TextView view,int value){
        if(value>0){
            view.setText(R.string.state_error);
            view.setTextColor(view.getContext().getResources().getColor(R.color.check_error));
        }else{
            view.setText(R.string.state_ok);
            view.setTextColor(view.getContext().getResources().getColor(R.color.check_pass));
        }
    }

    @BindingAdapter("isUnusualPic")
    public static void setIsUnusualPic(ImageView view, int value){
        if(value>0){
            view.setImageResource(R.mipmap.icon_error);
        }else{
            view.setImageResource(R.mipmap.icon_ok);
        }
    }
}
