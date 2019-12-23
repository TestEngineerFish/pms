package com.einyun.app.pms.sendorder.ui;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseApplyPostPoneActivity;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.net.request.ExtenDetialRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderDetialViewModel;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_LATE)
public class ApplyLateActivity extends BaseApplyPostPoneActivity<SendOrderDetialViewModel> {

    @Autowired(name = RouteKey.KEY_ORDER_DETAIL_EXTEN)
    public ArrayList<ExtensionApplication> extensionApplication;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String orderId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_LATER_ID)
    String keyId;
    ExtenDetialRequest request = new ExtenDetialRequest();
    @Override
    protected SendOrderDetialViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderDetialViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.setExtensionApplication(extensionApplication);
        super.initViews(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void submitForm(List<PicUrl> data) {
        request.setExtensionDays(binding.delayDate.getText().toString());
        request.setApplicationDescription(binding.delayInfo.getString());
        request.setId(orderId);
        request.setInstId(proInsId);
        if (StringUtil.isNullStr(keyId)) {
            if (RouteKey.KEY_PLAN.equals(keyId)) {
                viewModel.applyLatePlan(request, data).observe(this, o -> {
                    if ("0".equals(o.getCode())) {
                        ToastUtil.show(getApplicationContext(), R.string.apply_late_success);
                        finish();
                    }

                });
            }
        } else {
            viewModel.applyLate(request, data).observe(this, o -> {
                if ("0".equals(o.getCode())) {
                    ToastUtil.show(getApplicationContext(), R.string.apply_late_success);
                    finish();
                }
            });
        }
    }

}
