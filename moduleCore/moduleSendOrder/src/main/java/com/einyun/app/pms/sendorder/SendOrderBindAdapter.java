package com.einyun.app.pms.sendorder;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.library.resource.workorder.model.OrderState;

public class SendOrderBindAdapter {

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
