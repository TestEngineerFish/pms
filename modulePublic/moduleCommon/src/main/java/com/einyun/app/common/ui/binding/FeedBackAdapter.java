package com.einyun.app.common.ui.binding;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.common.R;

public class FeedBackAdapter {
    @BindingAdapter("feedStatus")
    public static void feedStatus(ImageView view, int value) {
    if (value==1){
        view.setImageResource(R.drawable.wait_handle);
        return;
    }
    if (value==2){
        view.setImageResource(R.drawable.handler);
        return;
    }
    }

    @BindingAdapter("feedStatusIn")
    public static void feedStatusIn(TextView view, int value) {
        if (value==1){
            view.setText("待处理");
            return;
        }
        if (value==2){
            view.setText("已处理");
            return;
        }
    }
}
