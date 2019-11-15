package com.einyun.app.pms.main.viewmodel;

import androidx.databinding.ObservableField;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.model.HomeModel;

public class HomeTabViewModel extends BaseViewModel {

    public ObservableField<HomeModel> homeModel = new ObservableField<>();

    public HomeTabViewModel() {
        homeModel.set(new HomeModel(CommonApplication.getInstance().getResources().getString(R.string.tv_tab_work_bench),
                CommonApplication.getInstance().getResources().getString(R.string.tv_tab_mine)));
    }

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    /**
     * 获取用户Id
     *
     * @return
     */
    public String getUserId() {
        return userModuleService.getUserId();
    }
}
