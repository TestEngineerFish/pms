package com.einyun.app.pms.customerinquiries.viewmodule;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.pms.customerinquiries.R;


public class InquiriesBindingAdapter {

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
            view.setText("已解决");
            return;
        }
        if (value == (RouteKey.KEY_NO_SOLVED)) {
            view.setText("未解决");
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
