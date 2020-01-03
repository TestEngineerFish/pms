package com.einyun.app.pms.main.core.viewmodel.contract;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;

import java.util.List;


public interface MineViewModelContract {
    //获取工作状态
    LiveData<String> getWorkState();
    //更新工作状态
    LiveData<String> updateWorkState(String status);
    //获取个人信息
    LiveData<UserInfoModel> getUserInfoByUserId();
    /**
     * 获取用户Id
     *
     * @return
     */
    public String getUserId();
}
