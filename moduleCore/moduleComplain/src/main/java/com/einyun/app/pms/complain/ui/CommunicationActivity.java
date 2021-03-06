package com.einyun.app.pms.complain.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.util.DateConverter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.model.BottomPickerModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.net.request.PostCommunicationRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ActivityCommunicationBinding;
import com.einyun.app.pms.complain.viewmodel.DetailViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_COMMUNICATION)
public class CommunicationActivity extends BaseHeadViewModelActivity<ActivityCommunicationBinding, DetailViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideID;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectID;
    @Autowired(name = RouteKey.KEY_CUSTOM_TYPE)
    String customType;

    @Override
    protected DetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(DetailViewModel.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_communication;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_initiate_communication);
        binding.setCallBack(this);
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.tvPerson.setText(model.getFullname());
            request.setUserId(model.getId());
        });
    }

    PostCommunicationRequest request = new PostCommunicationRequest();


    public void selectTime() {
        //???????????????
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = dft.format(date);
                request.setExpectTime(format);
                binding.tvTime.setText(format);
            }
        }).setType(new boolean[]{true, true, true, true, true, true})// ??????????????????
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .build();
        pvTime.show();
    }

    public void selectPerson() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, divideID)
                .withString(RouteKey.KEY_PROJECT_ID, projectID)
                .navigation();
    }

    /**
     * ??????
     */
    public void submit() {
        if (!StringUtil.isNullStr(request.getUserId())) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (!StringUtil.isNullStr(binding.delayInfo.getString())) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        request.setTaskId(taskId);
        request.setOpinion(binding.delayInfo.getString());
        viewModel.postCommunication(request).observe(this, b -> {
            if (customType!=null) {
                if (CustomEventTypeEnum.COMPLAIN_COMMUN.getTypeName().equals(customType)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("user_name", UserUtil.getUserName());
                    MobclickAgent.onEvent(CommunicationActivity.this, CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName(),map);
                }else if (CustomEventTypeEnum.INQUIRIES_COMMUN.getTypeName().equals(customType)){

                    HashMap<String, String> map = new HashMap<>();
                    map.put("user_name", UserUtil.getUserName());
                    MobclickAgent.onEvent(CommunicationActivity.this, CustomEventTypeEnum.INQUIRIES_TURN_ORDER.getTypeName(),map);

                }else if (CustomEventTypeEnum.REPAIR_COMMUN.getTypeName().equals(customType)){

                    HashMap<String, String> map = new HashMap<>();
                    map.put("user_name", UserUtil.getUserName());
                    MobclickAgent.onEvent(CommunicationActivity.this, CustomEventTypeEnum.REPAIR_TURN_ORDER.getTypeName(),map);
                }

            }
            finish();
        });
    }
}
