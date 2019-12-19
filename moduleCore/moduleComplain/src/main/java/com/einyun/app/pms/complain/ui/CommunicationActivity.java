package com.einyun.app.pms.complain.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.BottomPickerModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.net.request.PostCommunicationRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ActivityCommunicationBinding;
import com.einyun.app.pms.complain.viewmodel.DetailViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_COMMUNICATION)
public class CommunicationActivity extends BaseHeadViewModelActivity<ActivityCommunicationBinding, DetailViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideID;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectID;
    private List<DictDataModel> dictTimeList = new ArrayList<>();

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
        //获取预约上门时间
        viewModel.getByTypeKey(Constants.REPAIR_TIME).observe(this, dictDataModels -> {
            dictTimeList = dictDataModels;
        });
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.tvPerson.setText(model.getFullname());
            request.setUserId(model.getId());
        });
    }

    PostCommunicationRequest request = new PostCommunicationRequest();

    int rtTimeDefaultPos = 0;
    int rtDateDefaultPos = 0;

    public void selectTime() {
        if (dictTimeList == null || dictTimeList.size() == 0) {
            ToastUtil.show(this, "暂无预约上门时间");
            return;
        }
        List<BottomPickerModel> models = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            BottomPickerModel model = new BottomPickerModel();
            model.setData(getOldDate(i));
            List<String> dataList = new ArrayList<>();
            for (DictDataModel data : dictTimeList) {
                dataList.add(data.getName());
            }
            model.setDataList(dataList);
            models.add(model);
        }

        BottomPicker.buildBottomPicker(this, models, rtDateDefaultPos, rtTimeDefaultPos, new BottomPicker.OnItemDoublePickListener() {
            @Override
            public void onPick(int position1, int position2) {
                rtDateDefaultPos = position1;
                rtTimeDefaultPos = position2;
                BottomPickerModel model = models.get(position1);
                binding.tvTime.setText(model.getData() + " " + model.getDataList().get(position2));
                request.setExpectTime(model.getData() + " " + model.getDataList().get(position2));
            }
        });
    }
    public void selectPerson() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, divideID)
                .withString(RouteKey.KEY_PROJECT_ID, projectID)
                .navigation();
    }

    /**
     * 提交
     */
    public void submit() {
        if (!StringUtil.isNullStr(request.getUserId())) {
            ToastUtil.show(this, "请选择沟通人员");
            return;
        }
        if (!StringUtil.isNullStr(binding.delayInfo.getString())) {
            ToastUtil.show(this, "请输入沟通内容");
            return;
        }
        request.setTaskId(taskId);
        request.setOpinion(binding.delayInfo.getString());
        viewModel.postCommunication(request).observe(this, b -> {
            finish();
        });
    }

    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }
}
