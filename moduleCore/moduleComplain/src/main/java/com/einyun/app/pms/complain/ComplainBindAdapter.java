package com.einyun.app.pms.complain;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.library.resource.workorder.model.ApplyState;
import com.einyun.app.library.resource.workorder.model.ComplainOrderState;
import com.einyun.app.library.resource.workorder.model.OrderState;

public class ComplainBindAdapter {
//    /**
//     * 派工单详情显示状态
//     *
//     * @param view
//     * @param value
//     */
//    @BindingAdapter("order_status")
//    public static void order_status(TextView view, String value) {
//        if (TextUtils.isEmpty(value)) {
//            return;
//        }
//        int state = Integer.parseInt(value);
//        if (state == OrderState.NEW.getState()) {
//            view.setText(R.string.text_state_new);
//        } else if (state == OrderState.HANDING.getState()) {
//            view.setText(R.string.text_state_processing);
//        } else if (state == OrderState.APPLY.getState()) {
//            view.setText(R.string.text_approval_wait);
//        } else if (state == OrderState.CLOSED.getState()) {
//            view.setText(R.string.text_state_closed);
//        }
//
//    }

    @BindingAdapter("status")
    public static void status(ImageView view, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (value.equals(ComplainOrderState.ADD.getState())) {
            view.setImageResource(R.mipmap.icon_new);
        } else if (value.equals(ComplainOrderState.CLOSED.getState())) {
            view.setImageResource(R.mipmap.icon_state_closed);
        } else if (value.equals(ComplainOrderState.DEALING.getState())) {
            view.setImageResource(R.mipmap.icon_processing);
        } else if (value.equals(ComplainOrderState.RESPONSE.getState())) {
            view.setImageResource(R.mipmap.icon_work_order_apply);
        } else if (value.equals(ComplainOrderState.RETURN_VISIT.getState())) {
            view.setImageResource(R.mipmap.icon_evaluate);
        }
    }

    @BindingAdapter("setTime")
    public static void setTime(TextView view, Long value) {
        view.setText(FormatUtil.formatDate(value));
    }

    @BindingAdapter("status")
    public static void status(TextView view, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (value.equals(ComplainOrderState.ADD.getState())) {
            view.setText("新生成");
        } else if (value.equals(ComplainOrderState.CLOSED.getState())) {
            view.setText("已关闭");
        } else if (value.equals(ComplainOrderState.DEALING.getState())) {
            view.setText("处理中");
        } else if (value.equals(ComplainOrderState.RESPONSE.getState())) {
            view.setText("待响应");
        } else if (value.equals(ComplainOrderState.RETURN_VISIT.getState())) {
            view.setText("待评价");
        }

    }
}
