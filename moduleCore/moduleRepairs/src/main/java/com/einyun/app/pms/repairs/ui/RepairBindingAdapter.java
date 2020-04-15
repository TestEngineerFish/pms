package com.einyun.app.pms.repairs.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.pms.repairs.R;

public class RepairBindingAdapter {
    @BindingAdapter("statusDetail")
    public static void statusDetail(TextView view, String value) {
        Context context = CommonApplication.getInstance();
        if (value == null) {
            view.setText(R.string.text_state_closed);
            view.setTextColor(context.getResources().getColor(R.color.repair_detail_close_color));
            return;
        }
        if (value.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            view.setText(R.string.text_wait_response);
            view.setTextColor(context.getResources().getColor(R.color.repair_detail_response_color));
        } else if (value.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            view.setText(R.string.text_handling);
            view.setTextColor(context.getResources().getColor(R.color.repair_detail_handle_color));
        } else if (value.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            view.setText(R.string.text_wait_evaluate);
            view.setTextColor(context.getResources().getColor(R.color.repair_detail_evaluate_color));
        } else if (value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            view.setText(R.string.text_wait_send);
            view.setTextColor(context.getResources().getColor(R.color.repair_detail_send_color));
        } else if (value.equals(RouteKey.REPAIR_STATUS_WAIT_GRAB) || value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER_LATE)) {
            view.setText(R.string.text_wait_grab);
            view.setTextColor(context.getResources().getColor(R.color.repair_detail_grab_color));
        } else {

        }

    }

    @BindingAdapter("status")
    public static void status(TextView view, String value) {
        if (value == null) {
            view.setText(R.string.text_state_closed);
            return;
        }
        if (value.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            view.setText(R.string.text_wait_response);
        } else if (value.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            view.setText(R.string.text_handling);
        } else if (value.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            view.setText(R.string.text_wait_evaluate);
        } else if (value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            view.setText(R.string.text_wait_send);
        } else if (value.equals(RouteKey.REPAIR_STATUS_WAIT_GRAB) || value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER_LATE)) {
            view.setText(R.string.text_wait_grab);
        } else {

        }

    }


    @BindingAdapter("status")
    public static void status(ImageView view, String value) {
        if (value == null) {
            view.setImageResource(R.mipmap.icon_state_closed);
            return;
        }
        if (value.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            view.setImageResource(R.mipmap.icon_state_wait_response);
        } else if (value.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            view.setImageResource(R.mipmap.icon_state_handling);
        } else if (value.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            view.setImageResource(R.mipmap.icon_state_wait_evaluate);
        } else if (value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            view.setImageResource(R.mipmap.icon_state_wait_send);
        } else if (value.equals(RouteKey.REPAIR_STATUS_WAIT_GRAB)|| value.equals(RouteKey.REPAIR_STATUS_SEND_ORDER_LATE)) {
            view.setImageResource(R.mipmap.icon_state_wait_grab);
        }
    }


    @BindingAdapter("ifpay")
    public static void ifpay(TextView view, String value) {
        if (value == null) {
            view.setText(R.string.no);
            return;
        }
        if (value.equals(RouteKey.KEY_PAID)) {
            view.setText(R.string.yes);
            return;
        }
        if (value.equals(RouteKey.KEY_NOT_PAID)) {
            view.setText(R.string.no);
        }

    }
    @BindingAdapter("asses")
    public static void asses(TextView view, String value) {
        if (value == null) {
            return;
        }
        if ("normal".equals(value)) {
            view.setText("一般");
        }else if ("general".equals(value)){
            view.setText("轻微");
        }else {
            view.setText("严重");
        }

    }

    @BindingAdapter("setText")
    public static void setText(TextView view, String value) {
        if (TextUtils.isEmpty(value) || value.equals("null")) {
            view.setText("--");
            return;
        }
        view.setText(value);

    }

    @BindingAdapter("isSolve")
    public static void isSolve(TextView view, Integer value) {
        if (value == null) {
            return;
        }
        if (value == (RouteKey.KEY_IS_SOLVED)) {
            view.setText(R.string.tv_had_solve);
            return;
        }
        if (value == (RouteKey.KEY_NO_SOLVED)) {
            view.setText(R.string.tv_un_solve);
            return;
        }
    }

    @BindingAdapter("isSolve")
    public static void isSolve(ImageView view, Integer value) {
        if (value == null) {
            return;
        }
        if (value.equals(RouteKey.KEY_IS_SOLVED)) {
            view.setImageResource(R.drawable.iv_solve);
            return;
        }
        if (value.equals(RouteKey.KEY_NO_SOLVED)) {
            view.setImageResource(R.drawable.iv_un_solve);
            return;
        }
    }
}
