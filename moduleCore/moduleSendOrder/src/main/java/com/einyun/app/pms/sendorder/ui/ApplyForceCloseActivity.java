package com.einyun.app.pms.sendorder.ui;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseApplyForceCloseActivity;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.viewmodel.ApplyCloseViewModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CLOSE)
public class ApplyForceCloseActivity extends BaseApplyForceCloseActivity<ApplyCloseViewModel> {
    protected ApplyCloseRequest request;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String id;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_CLOSE_ID)
    String keyId;

    @Override
    protected ApplyCloseViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(ApplyCloseViewModel.class);
    }

    @Override
    protected void initData() {
        super.initData();
        request = new ApplyCloseRequest();
        if (StringUtil.isNullStr(keyId) && RouteKey.KEY_PLAN.equals(keyId)){
            request.setID(id);
            request.setTaskId(taskId);
            request.setInstId(proInsId);
            request.setMessageType("1");
        }else{
            request.setId(id);
            request.setApplyTaskId(taskId);
            request.setInstId(proInsId);
        }

    }

    @Override
    public void submitForm(List<PicUrl> data) {
        request.setEndReason(binding.applyCloseReason.getString());
        if (StringUtil.isNullStr(keyId)) {
            if (RouteKey.KEY_PLAN.equals(keyId)){
                viewModel.applyClosePlan(request, data).observe(this, model -> {
                    if (model.getCode().equals("0")) {
                        ToastUtil.show(this, R.string.apply_close_success);
                        this.finish();
                    } else {
                        ToastUtil.show(this, model.getMsg());
                    }
                });
            }
        } else {
            viewModel.applyClose(request, data).observe(this, model -> {
                if (model.getCode().equals("0")) {
                    ToastUtil.show(this, R.string.apply_close_success);
                    this.finish();
                } else {
                    ToastUtil.show(this, model.getMsg());
                }
            });
        }
    }

}
