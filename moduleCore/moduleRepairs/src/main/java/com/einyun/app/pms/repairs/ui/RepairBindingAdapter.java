package com.einyun.app.pms.repairs.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.pms.repairs.R;

public class RepairBindingAdapter {
    @BindingAdapter("status")
    public static void status(TextView view, String value) {
        if (value==null){
            view.setText(R.string.text_state_closed);
            return;
        }
        if (value.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            view.setText(R.string.text_wait_response);
            return;
        } else if (value.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            view.setText(R.string.text_handling);
            return;
        } else if (value.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            view.setText(R.string.text_wait_evaluate);
            return;
        } else if (value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            view.setText(R.string.text_wait_send);
            return;
        }

    }


    @BindingAdapter("status")
    public static void status(ImageView view, String value) {
        if (value==null){
            view.setImageResource(R.mipmap.icon_state_closed);
            return;
        }
        if (value.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            view.setImageResource(R.mipmap.icon_state_wait_response);
            return;
        } else if (value.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            view.setImageResource(R.mipmap.icon_state_handling);
            return;
        } else if (value.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            view.setImageResource(R.mipmap.icon_state_wait_evaluate);
            return;
        } else if (value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            view.setImageResource(R.mipmap.icon_state_wait_send);
            return;
        }
    }
}
