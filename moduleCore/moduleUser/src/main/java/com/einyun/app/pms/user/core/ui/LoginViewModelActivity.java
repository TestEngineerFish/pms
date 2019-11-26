package com.einyun.app.pms.user.core.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityLoginBinding;
import com.einyun.app.pms.user.databinding.ItemBlockTextDeleteBinding;


/***
 * 登录页面
 *
 * 需要部分整改   没有将数据整改在一起
 */
@Route(path = RouterUtils.ACTIVITY_USER_LOGIN)
public class LoginViewModelActivity extends BaseSkinViewModelActivity<ActivityLoginBinding, UserViewModel> {
    RVBindingAdapter<ItemBlockTextDeleteBinding, UserModel> adapter;

    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        //隐私页展示
        viewModel.showPrivacy(this);
        binding.setUserModel(new UserModel("", "", "", ""));
        //本地用户信息展示
        viewModel.getLastUser().observe(this,
                user -> binding.setUserModel(user));
        binding.setCallBack(this);
        binding.etOrgCode.setText(SPUtils.get(this, "tenantCode", "").toString());
        setUserList();
        initEvent();
    }

    @Override
    protected int getColorPrimary() {
        return Color.TRANSPARENT;
    }

    ListPopupWindow listPopupWindow;

    /**
     * 设置用户下拉框
     */
    private void setUserList() {
        viewModel.loadAllUserName().observe(this, list -> {
            Log.e("list", "" + list.size());
            if (list == null || list.size() == 0) {
                return;
            }
            binding.etUser.setVisibility(View.VISIBLE);
            listPopupWindow = new ListPopupWindow(this);
            ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(this, R.layout.item_block_text_delete, list);
            listPopupWindow.setAdapter(arr_adapter);
            listPopupWindow.setAnchorView(binding.etUser);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    binding.etUser.setText(list.get(i));
                    listPopupWindow.dismiss();
                }
            });
        });

    }

    /**
     * 设置时间监听
     */
    private void initEvent() {
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.ivOption.setVisibility(View.VISIBLE);
                } else {
                    binding.ivOption.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binding.ivDelete.setVisibility(View.VISIBLE);
                } else {
                    binding.ivDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 设置显示隐藏密码
     */
    boolean showPwd;

    public void showEye() {
        if (showPwd) {
            binding.ivOption.setImageResource(R.mipmap.img_login_password_show);
            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPwd = false;
        } else {
            binding.ivOption.setImageResource(R.mipmap.img_login_password_hide);
            binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPwd = true;
        }
    }


    /**
     * 登陆事件
     */
    public void onLoginClick() {
        viewModel.getTenantId(binding.etOrgCode.getText().toString()).observe(this,
                tenantModel -> {
                    ToastUtil.show(this, "tentantId:" + tenantModel.getId());
                    UserModel model = binding.getUserModel();
                    //判断用户名是否为空
                    if (!StringUtil.isNullStr(binding.etUser.getText().toString())) {
                        ToastUtil.show(this, R.string.login_username_null_tip);
                        return;
                    }
                    //判断密码是否为空
                    if (!StringUtil.isNullStr(model.getPassword())) {
                        ToastUtil.show(this, R.string.login_password_null_tip);
                        return;
                    }
                    viewModel.login(binding.etUser.getText().toString(), model.getPassword())
                            .observe(LoginViewModelActivity.this,
                                    user -> {
                                        ARouter.getInstance()
                                                .build(RouterUtils.ACTIVITY_MAIN_HOME)
                                                .navigation();
                                        finish();
                                    });
                });

    }

    /**
     * 清除用户信息
     */
    public void deleteUserName() {
        binding.etUser.setText("");
    }

    public void spinnerUser() {
        if (listPopupWindow.isShowing()) {
            listPopupWindow.dismiss();
        } else {
            listPopupWindow.show();
        }
    }
}
