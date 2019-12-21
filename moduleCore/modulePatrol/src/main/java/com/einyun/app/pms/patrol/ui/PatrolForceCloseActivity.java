package com.einyun.app.pms.patrol.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseApplyForceCloseActivity;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.google.gson.Gson;

import java.util.List;

@Route(path = RouterUtils.ACTIVITY_PATROL_FORCE_CLOSE)
public class PatrolForceCloseActivity extends BaseApplyForceCloseActivity<PatrolViewModel> {
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String orderId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public void submitForm(List<PicUrl> data) {
        ApplyCloseRequest request = new ApplyCloseRequest();
        request.setID(orderId);
        request.setTaskId(taskId);
        request.setInstId(proInsId);
        request.setApplyFiles(new ImageUploadManager().toJosnString(data));
        request.setMessageType("1");
        request.setEndReason(binding.applyCloseReason.getString());
        viewModel.forceClose(request).observe(this, flag -> {
            if (flag) {
                ToastUtil.show(this, R.string.apply_close_success);
                setResult(flag);//返回提交强制申请成功
            }
        });
    }
}
