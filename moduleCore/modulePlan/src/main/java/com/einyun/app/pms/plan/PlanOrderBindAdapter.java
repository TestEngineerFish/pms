package com.einyun.app.pms.plan;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.library.resource.workorder.model.ApplyState;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;

public class PlanOrderBindAdapter {
    /**
     * 列表状态
     *
     * @param view
     * @param value
     */
    @BindingAdapter("status")
    public static void status(TextView view, String value) {
        if (!StringUtil.isNullStr(value)) {
            return;
        }
        int state = Integer.parseInt(value);
        if (state == OrderState.NEW.getState()) {
            view.setText(R.string.text_state_new);
        } else if (state == OrderState.HANDING.getState()) {
            view.setText(R.string.text_state_processing);
        } else if (state == OrderState.APPLY.getState()) {
            view.setText(R.string.text_apply);
        } else if (state == OrderState.CLOSED.getState()) {
            view.setText(R.string.text_state_closed);
        } else if (state == OrderState.PENDING.getState()) {
            view.setText(R.string.text_state_wait_receive);
        } else if (state == OrderState.OVER_DUE.getState()) {
            view.setText(R.string.text_state_wait_send);
        }

    }

    /**
     * xiangqing状态
     *
     * @param view
     * @param value
     */
    @BindingAdapter("status_detail")
    public static void status_detail(TextView view, String value) {
        if (!StringUtil.isNullStr(value)) {
            return;
        }
        int state = Integer.parseInt(value);
        if (state == OrderState.NEW.getState()) {
            view.setText(R.string.text_state_new);
        } else if (state == OrderState.HANDING.getState()) {
            view.setText(R.string.text_state_processing);
        } else if (state == OrderState.APPLY.getState()) {
            view.setText(R.string.text_apply);
        } else if (state == OrderState.CLOSED.getState()) {
            view.setText(R.string.text_finished);
            view.setTextColor(view.getContext().getResources().getColor(R.color.greenTextColor));
        } else if (state == OrderState.PENDING.getState()) {
            view.setText(R.string.text_state_wait_receive);
        } else if (state == OrderState.OVER_DUE.getState()) {
            view.setText(R.string.text_state_wait_send);
        }

    }

    /**
     * 派工单详情显示状态
     *
     * @param view
     * @param value
     */
    @BindingAdapter("order_status")
    public static void order_status(TextView view, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        int state = Integer.parseInt(value);
        if (state == OrderState.NEW.getState()) {
            view.setText(R.string.text_state_new);
        } else if (state == OrderState.HANDING.getState()) {
            view.setText(R.string.text_state_processing);
        } else if (state == OrderState.APPLY.getState()) {
            view.setText(R.string.text_approval_wait);
        } else if (state == OrderState.CLOSED.getState()) {
            view.setText(R.string.text_state_closed);
        }

    }

    /**
     * 计划工单延期
     *
     * @param view
     * @param state
     */
    @BindingAdapter("apply_status")
    public static void apply_status(TextView view, int state) {
        if (state == ApplyState.APPLYING.getState()) {
            view.setText(R.string.text_applying);
        } else if (state == ApplyState.PASS.getState()) {
            view.setText(R.string.text_state_pass);
        } else if (state == ApplyState.REJECT.getState()) {
            view.setText(R.string.text_state_reject);
        }

    }

    /**
     * 派工单详情处理按钮状态
     *
     * @param view
     * @param value
     */
    @BindingAdapter("button_status")
    public static void button_status(Button view, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        int state = Integer.parseInt(value);
        if (state == OrderState.NEW.getState()) {
            view.setText(R.string.text_take_order);
        } else if (state == OrderState.HANDING.getState()) {
            view.setText(R.string.text_commit);
        } else if (state == OrderState.APPLY.getState()) {
            view.setText(R.string.text_work_order_apply);
        } else if (state == OrderState.CLOSED.getState()) {
            view.setVisibility(View.GONE);
        }
    }


    @BindingAdapter("status")
    public static void status(ImageView view, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        int state = Integer.parseInt(value);
        if (state == OrderState.NEW.getState()) {
            view.setImageResource(R.mipmap.icon_new);
        } else if (state == OrderState.HANDING.getState()) {
            view.setImageResource(R.mipmap.icon_processing);
        } else if (state == OrderState.APPLY.getState()) {
            view.setImageResource(R.mipmap.icon_work_order_apply);
        } else if (state == OrderState.CLOSED.getState()) {
            view.setImageResource(R.mipmap.icon_state_closed);
        } else if (state == OrderState.PENDING.getState()) {
            view.setImageResource(R.mipmap.icon_new);
        } else if (state == OrderState.OVER_DUE.getState()) {
            view.setImageResource(R.mipmap.icon_new);
        }
    }

    @BindingAdapter("setTime")
    public static void setTime(TextView view, Long value) {
        view.setText(FormatUtil.formatDate(value));
    }
//    @BindingAdapter("setUserName")
//    public static void status(TextView view, GetMappingByUserIdsResponse value) {
//        view.setText(value.getFullname() + "(" + value.getAccount() + ")");
//    }

    /**
     * 是否强制扫码
     *
     * @param view
     * @param state
     */
    @BindingAdapter("isScan")
    public static void is_force_scan(TextView view, int state) {
        if (state == 0) {
            view.setText("否");
            view.setTextColor(view.getContext().getResources().getColor(R.color.blackTextColor));
        } else if (state == 1) {
            view.setText("是");
            view.setTextColor(view.getContext().getResources().getColor(R.color.redTextColor));
        }
    }

    /**
     * 是否强制扫码
     *
     * @param view
     * @param state
     */
    @BindingAdapter("isOverTime")
    public static void isOverTime(TextView view, int state) {
        if (state == 0) {
            view.setText("否");
//            view.setTextColor(view.getContext().getResources().getColor(R.color.blackTextColor));
        } else if (state == 1) {
            view.setText("是");
//            view.setTextColor(view.getContext().getResources().getColor(R.color.redTextColor));
        }
    }

    /**
     * 是否强制扫码
     *
     * @param view
     * @param state
     */
    @BindingAdapter("scanReasult")
    public static void scan_reasult(TextView view, int state) {
        if (state == 2) {
            view.setText("失败");
            view.setTextColor(view.getContext().getResources().getColor(R.color.redTextColor));
        } else if (state == 1) {
            view.setText("成功");
            view.setTextColor(view.getContext().getResources().getColor(R.color.blackTextColor));
        } else {
            view.setText("");
            view.setTextColor(view.getContext().getResources().getColor(R.color.blackTextColor));
        }
    }
    @BindingAdapter("isCached")
    public static void isCached(ImageView view,boolean value){
        if(value){
            view.setImageResource(R.drawable.icon_cached);
        }else{
            view.setImageResource(R.drawable.icon_no_cache);
        }
    }
    @BindingAdapter("isCached")
    public static void isCached(TextView view,boolean value){
        if(value){
            view.setText("已缓存");
            view.setTextColor(view.getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
        }else{
            view.setText("未缓存");
            view.setTextColor(view.getContext().getResources().getColor(R.color.normal_main_text_icon_color));
        }
    }
}
