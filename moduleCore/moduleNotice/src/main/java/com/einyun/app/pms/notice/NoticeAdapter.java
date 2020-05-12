package com.einyun.app.pms.notice;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class NoticeAdapter {
    @BindingAdapter("noteState")
    public static void noteState(ImageView view, String state) {
        switch (state){
            case "notice":
                view.setImageResource(com.einyun.app.common.R.drawable.notify_tag);
                break;
            case "urgent":
                view.setImageResource(com.einyun.app.common.R.drawable.emergency_tag);
                break;
            case "tips":
                view.setImageResource(com.einyun.app.common.R.drawable.tips_tag);
                break;
            case "important":
                view.setImageResource(com.einyun.app.common.R.drawable.important_tag);
                break;
            case "Briefing":
                view.setImageResource(com.einyun.app.common.R.drawable.report_tag);
                break;
            case "other":
                view.setImageResource(com.einyun.app.common.R.drawable.other_tag);
                break;
                default:break;
        }
    }

    @BindingAdapter("noteStateTxt")
    public static void noteStateTxt(TextView view, String state) {
        switch (state){
            case "notice":
                view.setText("通知");
                break;
            case "urgent":
                view.setText("紧急");
                break;
            case "tips":
                view.setText("提示");
                break;
            case "important":
                view.setText("重要");
                break;
            case "Briefing":
                view.setText("简报");
                break;
            case "other":
                view.setText("其他");
                break;
            default:break;
        }
    }
}
