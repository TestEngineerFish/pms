package com.einyun.app.pms.create;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.ComplainOrderState;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.CreateSendOrderRequest;

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
public class CreateOrderBindiAdapter {
    @BindingAdapter("setTime")
    public static void setTime(TextView view, Long value) {
        view.setText(FormatUtil.formatDate(value));
    }
    @BindingAdapter("setSelectTxt")
    public static void setSelectTxt(TextView view, String value) {
        if (StringUtil.isNullStr(value)) {
            view.setText(value);
        } else {
            view.setText("请选择");
//            view.setTextColor(view.getContext().getResources().getColor(R.color.normal_main_text_icon_color));
        }
    }

    @BindingAdapter("setWorkTypeTxt")
    public static void setWorkTypeTxt(TextView view, CreateSendOrderRequest request) {
        if (request == null) {
            view.setText("请选择");
            return;
        }
        if (StringUtil.isNullStr(request.getEnvType3Name())) {
            view.setText(request.getEnvType3Name());
        } else if (StringUtil.isNullStr(request.getTypeName())) {
            view.setText(request.getTypeName());
        } else {
            view.setText("请选择");
//            view.setTextColor(view.getContext().getResources().getColor(R.color.normal_main_text_icon_color));
        }
    }

    @BindingAdapter("setRg")
    public static void setRg(RadioGroup view, String position) {
        if (StringUtil.isNullStr(position)) {
            view.check(Integer.valueOf(position) - 1);
        } else {
            view.clearCheck();
        }
    }

    @BindingAdapter("status")
    public static void status(TextView view, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (value == ComplainOrderState.ADD.getState()) {
            view.setText("新生成");
        } else if (value == ComplainOrderState.CLOSED.getState()) {
            view.setText("已关闭");
        }else if (value == ComplainOrderState.DEALING.getState()) {
            view.setText("处理中");
        }else if (value == ComplainOrderState.RESPONSE.getState()) {
            view.setText("待响应");
        }else if (value == ComplainOrderState.RETURN_VISIT.getState()) {
            view.setText("待评价");
        }

    }
}
