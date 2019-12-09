package com.einyun.app.pms.mine.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivitySettingViewModuleBinding;
import com.einyun.app.pms.mine.databinding.ActivityUserInfoViewModuleBinding;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.UserInfoViewModel;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_USER_INFO)
public class UserInfoViewModuleActivity extends BaseHeadViewModelActivity<ActivityUserInfoViewModuleBinding, UserInfoViewModel> {

    @Override
    protected UserInfoViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(UserInfoViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_user_info));
        binding.setCallBack(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 跳转设置签名
     */
    public void EnterSignName(){
        ARouter.getInstance().build(RouterUtils.ACTIVITY_SIGN_SET).withString("edit",binding.etSignName.getText().toString()).navigation();
    }
    /**
     *跳转 设置头像
     */

    public void HeadShotOnClick(){
        ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_HEAD_SHOT).navigation();
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
