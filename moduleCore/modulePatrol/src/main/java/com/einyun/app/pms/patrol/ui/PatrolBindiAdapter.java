package com.einyun.app.pms.patrol.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.model.SignCheckResult;


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



    @BindingAdapter("isSignIn")
    public static void isSignIn(TextView view,int signInResult){
        if(SignCheckResult.SIGN_IN_SUCCESS==signInResult){
            view.setText(R.string.text_sign_in_ed);
            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
        }else{
            view.setText(R.string.text_un_sign_in);
            view.setTextColor(view.getContext().getResources().getColor(R.color.blueTextColor));
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
        }else if(value==OrderState.HANDING.getState()||value==OrderState.APPLY.getState()){
            view.setText(R.string.text_state_processing);
        }else if(value==OrderState.CLOSED.getState()){
            view.setText(R.string.text_state_closed);
        }else if(value==OrderState.PENDING.getState()){
            view.setText(R.string.text_state_wait_receive);
        }else if(value==OrderState.OVER_DUE.getState()){
            view.setText(R.string.text_state_wait_send);
        }
    }

    @BindingAdapter("status_detial")
    public static void status_detial(TextView view,int value){
        if(value== OrderState.NEW.getState()){
            view.setText(R.string.text_state_new);
            view.setTextColor(view.getContext().getResources().getColor(R.color.blueTextColor));
        }else if(value==OrderState.HANDING.getState()||value==OrderState.APPLY.getState()){
            view.setText(R.string.text_state_processing);
            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
        }else if(value==OrderState.CLOSED.getState()){
            view.setText(R.string.text_finished);
            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
        }else if(value==OrderState.PENDING.getState()){//待接单
            view.setText(R.string.text_state_wait_receive);
            view.setTextColor(view.getContext().getResources().getColor(R.color.blueTextColor));
        }else if(value==OrderState.OVER_DUE.getState()){//待派单
            view.setText(R.string.text_state_wait_send);
            view.setTextColor(view.getContext().getResources().getColor(R.color.blueTextColor));
        }
    }


    @BindingAdapter("status")
    public static void status(ImageView view,int value){
        if(value== OrderState.NEW.getState()){
            view.setImageResource(R.mipmap.icon_new);
        }else if(value==OrderState.HANDING.getState()||value==OrderState.APPLY.getState()){
            view.setImageResource(R.mipmap.icon_processing);
        }else if(value==OrderState.CLOSED.getState()){
            view.setImageResource(R.mipmap.icon_state_closed);
        }else if(value==OrderState.PENDING.getState()){//接单
            view.setImageResource(R.mipmap.icon_new);
        }else if(value==OrderState.OVER_DUE.getState()){//待派单
            view.setImageResource(R.mipmap.icon_new);
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

    @BindingAdapter("duration")
    public static void duration(TextView view,int duration){
        String text= String.format(view.getResources().getString(R.string.text_duration),duration);
        view.setText(text);
    }

}
