package com.einyun.app.pms.mine.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;

import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivitySignSetViewModuleBinding;

import com.einyun.app.pms.mine.module.SignSetModule;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.SignSetViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_SIGN_SET)
public class SignSetViewModuleActivity extends BaseHeadViewModelActivity<ActivitySignSetViewModuleBinding, SignSetViewModel> {
    @Autowired(name = "edit")
    String oldText;
    @Autowired(name = Constants.SIGN_USER_ID)
    String userID;
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
        binding.etText.setText(oldText);
        headBinding.tvRightTitle.setTextColor(getResources().getColor(R.color.blueTextColor));
    }

    @Override
    protected void initData() {
        super.initData();
        binding.etText.addTextChangedListener(new TextWatcher() {
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

        binding.etText.setText(oldText);

    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 完成按钮
     */
    public void onOptionClick(View view){
        if (binding.etText.getText().toString().trim().isEmpty()) {
            ToastUtil.show(this, getString(R.string.tv_pl_set_sign));
            return;
        }
        SignSetModule signSetModule = new SignSetModule();
        signSetModule.setUserId(userID);
        signSetModule.setSlogan(binding.etText.getText().toString().trim());
        viewModel.sumitSignText(signSetModule).observe(this, model -> {
            if (model) {
                //通知刷新界面
                LiveEventBus.get(LiveDataBusKey.MINE_FRESH, String.class).post("");
                finish();
            }
        });
    }
}
