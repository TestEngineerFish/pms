package com.einyun.app.pms.mine.ui;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.model.MsgExtendVars;
import com.google.gson.Gson;


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
public class MsgBindAdapter {

    @BindingAdapter("status_read")
    public static void status_read(TextView view, boolean value) {
        if (value) {
            view.setTextColor(view.getContext().getResources().getColor(R.color.greyTextColor));
        } else {
            view.setTextColor(view.getContext().getResources().getColor(R.color.txt_black_order));
        }

    }

    @BindingAdapter("status_time")
    public static void status_time(TextView view, String value) {

        if (value == null) {
            return;
        }
        if (value.isEmpty()) {
            return;
        }
        long timeModel = TimeUtil.getTime(value, "yyyy-MM-dd HH:mm:ss");
        long timeCurrent = System.currentTimeMillis();
        long time = timeCurrent - timeModel;//时间差
        if (time < 60*1000) {

            view.setText("刚刚");
        } else if (time >= 60*1000&& time < 60 * 60*1000) {
            String format = String.format("%S分钟前",time/60/1000);
//            view.setText(time/60+"分钟前");
            view.setText(format);

        } else if (time >= 60 * 60*1000 && time < 60 * 60 * 24*1000) {
            String format = String.format("%S小时前",time/60/60/1000);
//            view.setText(time/60+"分钟前");
            view.setText(format);

        } else if (time >= 60 * 60 * 24*1000 && time < 60 * 60 * 24 * 2*1000) {
            String format = String.format("%S天前",time/60/60/24/1000);
//            view.setText(time/60+"分钟前");
            view.setText("昨天");
        } else if (time >= 2*60 * 60 * 24*1000 && time < 60 * 60 * 24 * 3*1000) {
            String format = String.format("%S天前",time/60/60/24/1000);
//            view.setText(time/60+"分钟前");
            view.setText("前天");
        }  else if (time >= 3*60 * 60 * 24*1000 && time < 60 * 60 * 24 * 7*1000) {
            String format = String.format("%S天前",time/60/60/24/1000);
//            view.setText(time/60+"分钟前");
            view.setText(format);
        }else {

            view.setText(value);
        }
    }

    @BindingAdapter("status_image")
    public static void status_image(ImageView view, String value) {
        MsgExtendVars msgExtendVars = new Gson().fromJson(value, MsgExtendVars.class);
        if (msgExtendVars != null) {
            switch (msgExtendVars.getType()) {
                case "grab"://抢单
                    view.setImageResource(R.drawable.iv_grab);
                    break;
                case "reminder"://新待处理工单提醒
                    view.setImageResource(R.drawable.iv_reminder);
                    break;
            }
        }
    }


}
