package com.einyun.app.pms.patrol.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.databinding.ActivityApplyLateBinding;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseApplyPostPoneActivity;
import com.einyun.app.library.resource.workorder.model.ApplyState;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.net.request.ExtenDetialRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_PATROL_POSTPONE)
public class PatrolPostPoneActivity extends BaseApplyPostPoneActivity<PatrolViewModel> {
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String orderId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PARAMS)
    int applyDate;
    @Autowired(name = RouteKey.KEY_PARENT_ID)
    int applyNum;

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        getExtDays(binding);
    }

    protected void getExtDays(ActivityApplyLateBinding binding) {
        binding.applyDate.setText(applyDate + "天");
        binding.applyNum.setText(applyNum + "次");
    }

    @Override
    public void submitForm(List<PicUrl> data) {
        ExtenDetialRequest request = new ExtenDetialRequest();
        request.setID_(orderId);
        request.setInstId(proInsId);
        request.setApplyFiles(new ImageUploadManager().toJosnString(data));
        request.setExtension_days(binding.delayDate.getText().toString());
        request.setApplication_description(binding.delayInfo.getString());
        viewModel.postpone(request).observe(this, flag -> {
            if (flag) {
                ToastUtil.show(this, R.string.apply_late_success);
                setResult(flag);//返回提交强制申请成功
            }
        });
    }
}
