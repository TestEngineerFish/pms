package com.einyun.app.pms.sendorder;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.library.resource.workorder.model.OrderState;

public class SendOrderBindAdapter {

    /**
     * 列表状态
     * @param view
     * @param value
     */
    @BindingAdapter("status")
    public static void status(TextView view, int value){
        if(value== OrderState.NEW.getState()){
            view.setText(R.string.text_state_new);
        }else if(value==OrderState.HANDING.getState()||value==OrderState.PROCESSING.getState()){
            view.setText(R.string.text_state_processing);
        }else if(value==OrderState.CLOSED.getState()){
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
        } else if(state==OrderState.PROCESSING.getState()){
            view.setText(R.string.text_approval_wait);
        }
        else if(state==OrderState.CLOSED.getState()){
            view.setText(R.string.text_state_closed);
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
        }else if(state==OrderState.PROCESSING.getState()){
            view.setText(R.string.text_approval);
        }
        else if(state==OrderState.CLOSED.getState()){
            view.setVisibility(View.GONE);
        }
    }


    @BindingAdapter("status")
    public static void status(ImageView view, int value){
        if(value== OrderState.NEW.getState()){
            view.setImageResource(R.mipmap.icon_new);
        }else if(value==OrderState.HANDING.getState()||value==OrderState.PROCESSING.getState()){
            view.setImageResource(R.mipmap.icon_processing);
        }else if(value==OrderState.CLOSED.getState()){
            view.setImageResource(R.mipmap.icon_state_closed);
        }
    }
}
