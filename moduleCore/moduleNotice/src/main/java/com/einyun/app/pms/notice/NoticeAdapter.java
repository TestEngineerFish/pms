package com.einyun.app.pms.notice;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class NoticeAdapter {
    @BindingAdapter("noteState")
    public static void noteState(ImageView view, String state) {
        switch (state){
//            case "notice":
//                view.setImageResource(com.einyun.app.common.R.drawable.notify_tag);
//                break;
//            case "urgent":
//                view.setImageResource(com.einyun.app.common.R.drawable.emergency_tag);
//                break;
//            case "tips":
//                view.setImageResource(com.einyun.app.common.R.drawable.tips_tag);
//                break;
//            case "important":
//                view.setImageResource(com.einyun.app.common.R.drawable.important_tag);
//                break;
//            case "Briefing":
//                view.setImageResource(com.einyun.app.common.R.drawable.report_tag);
//                break;
//            case "other":
//                view.setImageResource(com.einyun.app.common.R.drawable.other_tag);
//                break;
//                default:break;
        }
    }
}
