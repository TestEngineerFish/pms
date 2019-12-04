package com.einyun.app.pms.patrol.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.pms.patrol.R;


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
public class PatrolBindiAdapter {

    @BindingAdapter("isWarn")
    public static void isWarn(ImageView view,boolean value){
        if(value){
          view.setVisibility(View.VISIBLE);
        }else{
           view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("isCached")
    public static void isCached(TextView view,boolean value){
        if(value){
           view.setText(R.string.text_cached);
           view.setTextColor(view.getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
        }else{
            view.setText(R.string.text_no_cached);
            view.setTextColor(view.getContext().getResources().getColor(R.color.normal_main_text_icon_color));
        }
    }

    @BindingAdapter("time")
    public static void time(TextView view,long value){
       String dateStr= TimeUtil.getAllTime(value);
       view.setText(dateStr);
    }

    @BindingAdapter("status")
    public static void status(TextView view,int value){
        if(value== OrderState.NEW.getState()){
            view.setText(R.string.text_state_new);
        }else if(value==OrderState.HANDING.getState()||value==OrderState.PROCESSING.getState()){
            view.setText(R.string.text_state_processing);
        }else if(value==OrderState.CLOSED.getState()){
            view.setText(R.string.text_state_closed);
        }
    }


    @BindingAdapter("status")
    public static void status(ImageView view,int value){
        if(value== OrderState.NEW.getState()){
            view.setImageResource(R.mipmap.icon_new);
        }else if(value==OrderState.HANDING.getState()||value==OrderState.PROCESSING.getState()){
            view.setImageResource(R.mipmap.icon_processing);
        }else if(value==OrderState.CLOSED.getState()){
            view.setImageResource(R.mipmap.icon_state_closed);
        }
    }

    @BindingAdapter("isCached")
    public static void isCached(ImageView view,boolean value){
        if(value){
            view.setImageResource(R.mipmap.icon_cached);
        }else{
            view.setImageResource(R.mipmap.icon_no_cache);
        }
    }


}
