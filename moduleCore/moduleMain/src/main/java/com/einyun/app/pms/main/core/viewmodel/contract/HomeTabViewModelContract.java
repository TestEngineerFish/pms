package com.einyun.app.pms.main.core.viewmodel.contract;

import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.model.HomeModel;

public interface HomeTabViewModelContract {

    /**
     * 获取用户Id
     *
     * @return
     */
    public String getUserId();
}
