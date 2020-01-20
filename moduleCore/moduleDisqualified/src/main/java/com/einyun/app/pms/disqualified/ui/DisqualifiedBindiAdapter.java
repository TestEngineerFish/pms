package com.einyun.app.pms.disqualified.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.constants.DisqualifiedDataKey;


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
public class DisqualifiedBindiAdapter {



//    @BindingAdapter("isSignIn")
//    public static void isSignIn(TextView view,int signInResult){
//        if(SignCheckResult.SIGN_IN_SUCCESS==signInResult){
//            view.setText(R.string.text_sign_in_ed);
//            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
//        }else{
//            view.setText(R.string.text_un_sign_in);
//            view.setTextColor(view.getContext().getResources().getColor(R.color.blueTextColor));
//        }
//    }

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
    public static void status(TextView view,String  value){
        if(DisqualifiedDataKey.STATUS_CREATE_STEP.equals(value)){
            view.setText(R.string.text_state_new);
            view.setBackgroundResource(R.mipmap.icon_new);
        }else if(DisqualifiedDataKey.STATUS_PROCESSING_STEP.equals(value)){
            view.setText(R.string.text_state_processing);
            view.setBackgroundResource(R.mipmap.icon_processing);
        }else if(DisqualifiedDataKey.STATUS_COMPLETED_STEP.equals(value)){//已完成
            view.setText(R.string.text_finished);
            view.setBackgroundResource(R.mipmap.icon_state_closed);
        }
    }
    @BindingAdapter("line")
    public static void line(TextView view,String  value){
        if (value==null) {
            return;
        }
        switch (value) {
            case DisqualifiedDataKey.LINE_ENV://环境
                view.setText("环境");
                break;
            case DisqualifiedDataKey.LINE_ENG://工程
                view.setText("工程");
                break;
            case DisqualifiedDataKey.LINE_ORDER://秩序
                view.setText("秩序");
                break;
            case DisqualifiedDataKey.LINE_CUSTOMER://客服
                view.setText("客服");
                break;
        }
    }
    @BindingAdapter("severity")
    public static void severity(TextView view,String  value){
        if (value==null) {
            return;
        }
            switch (value) {
                case DisqualifiedDataKey.SEVERITY_HIGHT_LEVEL:
                    view.setText("高");
                    break;
                case DisqualifiedDataKey.SEVERITY_MIDDLE_LEVEL:
                    view.setText("中");
                    break;
                case DisqualifiedDataKey.SEVERITY_LOW_LEVEL:
                    view.setText("低");
                    break;
            }
    }
    @BindingAdapter("status_detial")
    public static void status_detial(TextView view,String value){
        if("createStep".equals(value)){
            view.setText(R.string.text_state_new);
            view.setTextColor(view.getContext().getResources().getColor(R.color.repair_detail_evaluate_color));
        }else if("processingStep".equals(value)){
            view.setText(R.string.text_state_processing);
            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
        }else if("completedStep".equals(value)){
            view.setText(R.string.text_finished);
            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
        }
    }


    @BindingAdapter("status")
    public static void status(ImageView view,String  value){
        if(value.equals("createStep")){
            view.setImageResource(R.mipmap.icon_new);
        }else if(value.equals("processingStep")){
            view.setImageResource(R.mipmap.icon_processing);
        }else if(value.equals("completedStep")){
            view.setImageResource(R.mipmap.icon_state_closed);
        }
    }

    @BindingAdapter("isCached")
    public static void isCached(ImageView view,boolean value){
        if(value){
            view.setImageResource(R.drawable.icon_cached);
        }else{
            view.setImageResource(R.drawable.icon_no_cache);
        }
    }

//    @BindingAdapter("duration")
//    public static void duration(TextView view,int duration){
//        String text= String.format(view.getResources().getString(R.string.text_duration),duration);
//        view.setText(text);
//    }

}
