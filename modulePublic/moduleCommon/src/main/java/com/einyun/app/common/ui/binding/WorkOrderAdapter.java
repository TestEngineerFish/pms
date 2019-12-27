package com.einyun.app.common.ui.binding;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.common.R;
import com.einyun.app.library.resource.workorder.model.ApplyState;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.model.PatrolWorkSate;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;

public class WorkOrderAdapter {
    /**
     * 列表状态
     * @param view
     * @param value
     */
    @BindingAdapter("status")
    public static void status(TextView view, int value){
        if(value== OrderState.NEW.getState()){
            view.setText(R.string.text_state_new);
        }else if(value==OrderState.HANDING.getState()){
            view.setText(R.string.text_state_processing);
        }else if(value==OrderState.APPLY.getState()){
            view.setText(R.string.text_apply);
        }
        else if(value==OrderState.CLOSED.getState()){
            view.setText(R.string.text_state_closed);
        }
    }


    /**
     * 筛选条件是否选择，分期是否选择
     * @param textView
     * @param state
     */
    @BindingAdapter("condition_select")
    public static void condition_select(TextView textView,boolean state){
        if(state){
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.blueConditionColor));
        }else{
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.normal_main_text_icon_color));
        }
    }

    /**
     * 筛选条件是否选择，分期是否选择
     * @param view
     * @param state
     */
    @BindingAdapter("condition_select")
    public static void condition_select(ImageView view,boolean state){
        if(state){
           view.setImageResource(R.mipmap.icon_down_selected);
        }else{
            view.setImageResource(R.drawable.down);
        }
    }

    @BindingAdapter("isComingTimeout")
    public static void isComingTimeout(ImageView view, int value){
        if(value>0){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 列表状态
     * @param view
     * @param value
     */
    @BindingAdapter("patrol_status")
    public static void patrol_status(TextView view, int value){
        if(value== PatrolWorkSate.NEW.getState()){
            view.setText(R.string.text_state_new);
        }else if(value==PatrolWorkSate.HANDING.getState()){
            view.setText(R.string.text_state_processing);
        }
        else if(value==PatrolWorkSate.CLOSED.getState()){
            view.setText(R.string.text_state_closed);
        }

    }

    /**
     * 派工单详情显示状态
     * @param view
     * @param value
     */
    @BindingAdapter("order_status")
    public static void order_status(TextView view, String value){
        if(TextUtils.isEmpty(value)){
            return;
        }
        int state=Integer.parseInt(value);
        if(state== OrderState.NEW.getState()){
            view.setText(R.string.text_state_new);
        }else if(state==OrderState.HANDING.getState()){
            view.setText(R.string.text_state_processing);
        } else if(state==OrderState.APPLY.getState()){
            view.setText(R.string.text_approval_wait);
        }
        else if(state==OrderState.CLOSED.getState()){
            view.setText(R.string.text_state_closed);
        }

    }

    /**
     * 派工单详情显示状态
     * @param view
     * @param state
     */
    @BindingAdapter("apply_status")
    public static void apply_status(TextView view, int state){
        if(state== ApplyState.APPLYING.getState()){
            view.setText(R.string.text_applying);
        }else if(state==ApplyState.PASS.getState()){
            view.setText(R.string.text_state_pass);
        } else if(state==ApplyState.REJECT.getState()){
            view.setText(R.string.text_state_reject);
        }

    }

    /**
     * 派工单详情处理按钮状态
     * @param view
     * @param value
     */
    @BindingAdapter("button_status")
    public static void button_status(Button view, String value){
        if(TextUtils.isEmpty(value)){
            return;
        }
        int state=Integer.parseInt(value);
        if(state== OrderState.NEW.getState()){
            view.setText(R.string.text_take_order);
        }else if(state==OrderState.HANDING.getState()){
            view.setText(R.string.text_commit);
        }else if(state==OrderState.APPLY.getState()){
            view.setText(R.string.text_work_order_apply);
        }
        else if(state==OrderState.CLOSED.getState()){
            view.setVisibility(View.GONE);
        }
    }


    @BindingAdapter("status")
    public static void status(ImageView view, int value){
        if(value== OrderState.NEW.getState()){
            view.setImageResource(R.mipmap.icon_new);
        }else if(value==OrderState.HANDING.getState()){
            view.setImageResource(R.mipmap.icon_processing);
        }else if(value==OrderState.APPLY.getState()){
            view.setImageResource(R.mipmap.icon_work_order_apply);
        }
        else if(value==OrderState.CLOSED.getState()){
            view.setImageResource(R.mipmap.icon_state_closed);
        }
    }

    @BindingAdapter("patrol_status")
    public static void patrol_status(ImageView view, int value){
        if(value== PatrolWorkSate.NEW.getState()){
            view.setImageResource(R.mipmap.icon_new);
        }else if(value==PatrolWorkSate.HANDING.getState()) {
            view.setImageResource(R.mipmap.icon_processing);
        }
        else if(value==PatrolWorkSate.CLOSED.getState()){
            view.setImageResource(R.mipmap.icon_state_closed);
        }
    }

    @BindingAdapter("setUserName")
    public static void status(TextView view, GetMappingByUserIdsResponse value) {
        view.setText(value.getFullname() + "(" + value.getAccount() + ")");
    }

    @BindingAdapter("is_overdure")
    public static void is_overdue(TextView view,int status){
        if(status>0){
            view.setText(R.string.yes);
        }else{
            view.setText(R.string.no);
        }
    }

    @BindingAdapter("ordered")
    public static void ordered(TextView view,int sort){
        if(sort==1){
            view.setText(R.string.text_ordered);
        }else{
            view.setText(R.string.text_unordered);
        }
    }
}
