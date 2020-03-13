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
    public static void status_read(TextView view,boolean  value){
        if (value) {
            view.setTextColor(view.getContext().getResources().getColor(R.color.greyTextColor));
        }else {
            view.setTextColor(view.getContext().getResources().getColor(R.color.txt_black_order));
        }

    }

    @BindingAdapter("status_image")
    public static void status_image(ImageView view,String value){
        MsgExtendVars msgExtendVars = new Gson().fromJson(value, MsgExtendVars.class);
        if (msgExtendVars!=null) {
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
