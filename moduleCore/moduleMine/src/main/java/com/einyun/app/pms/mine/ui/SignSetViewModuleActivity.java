package com.einyun.app.pms.mine.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivitySignSetViewModuleBinding;
import com.einyun.app.pms.mine.databinding.ActivityUserInfoViewModuleBinding;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.SignSetViewModel;
import com.einyun.app.pms.mine.viewmodule.UserInfoViewModel;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_SIGN_SET)
public class SignSetViewModuleActivity extends BaseHeadViewModelActivity<ActivitySignSetViewModuleBinding, SignSetViewModel> {
    @Autowired(name = Constants.SIGN_NAME_EDIT)
    String oldText;
    @Override
    protected SignSetViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SignSetViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_set_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_sign_set));
        binding.setCallBack(this);
        setRightTxt(R.string.tv_finish);
        headBinding.tvRightTitle.setTextColor(getResources().getColor(R.color.blueTextColor));
    }

    @Override
    protected void initData() {
        super.initData();
        binding.editEtSlogan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int detailLength = s.length();
                binding.editTvSloganNum.setText(detailLength + "/30");
            }
        });

        binding.editEtSlogan.setText(oldText);

    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 右侧功能按钮
     */
    public void onOptionClick(View view){

    }
}
