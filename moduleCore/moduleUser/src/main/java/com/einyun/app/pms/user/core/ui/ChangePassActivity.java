package com.einyun.app.pms.user.core.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.ToastUtil;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityChangePassBinding;
import com.einyun.app.pms.user.databinding.ActivityEnterAccountBinding;

import java.util.regex.Pattern;

@Route(path = RouterUtils.ACTIVITY_CHANGE_PASS)
public class ChangePassActivity extends BaseHeadViewModelActivity<ActivityChangePassBinding, UserViewModel> {
    @Autowired(name = "phone")
    String phone;
    @Autowired(name = "account")
    String account;
    @Autowired(name = "code")
    String code;
    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pass;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle("密码修改");
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
    }

    /**
     * 跳转到修改密码界面
     */
    public void changePass() {
        if (TextUtils.isEmpty(binding.etAccount.getText().toString().trim())) {
            ToastUtil.show(this, "请输入新密码");
        }else if (!checkPass(binding.etAccount.getText().toString().trim())){
            ToastUtil.show(this, "密码必须包含数字字母和特殊符号，长度不小于6位！");
        } else {
            viewModel.changePass(phone,code,account,binding.etAccount.getText().toString().trim()).observe(this, data -> {
                if (data!=null&&data.isState()){
                    ToastUtil.show(this,"密码修改成功!");
                    ActivityUtil.finishLastThreeActivity();
                }else {
                    ToastUtil.show(this,data.getMsg());
                }
            });
        }
    }

    /**
     * 校验密码格式
     * */
    private boolean checkPass(String pass){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$\\%\\^\\&\\*\\(\\)])[0-9a-zA-Z!@#$\\%\\^\\&\\*\\(\\)]{6,16}$");
        return pattern.matcher(pass).find();
    }
}