package com.einyun.app.pms.user.core.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.net.CommonHttpService;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.common.utils.HttpUrlUtil;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.common.utils.PicEvUtils;
import com.einyun.app.library.EinyunSDK;
import com.einyun.app.library.core.net.EinyunHttpService;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.Constants;
import com.einyun.app.pms.user.core.UserServiceManager;
import com.einyun.app.pms.user.core.ui.adapter.UserListPopupAdapter;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityLoginBinding;


/***
 * ????????????
 *
 * ??????????????????   ??????????????????????????????
 */
@Route(path = RouterUtils.ACTIVITY_USER_LOGIN)
public class LoginViewModelActivity extends BaseSkinViewModelActivity<ActivityLoginBinding, UserViewModel> {

    private String uuid;

    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    String path;

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        path = extras.getString(RouteKey.KEY_PATH);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setLoginClass(this.getClass());
        ActivityUtil.removeExceptCurrentActivity(LoginViewModelActivity.class);
    }

    @Override
    protected void initData() {
        super.initData();
        //??????????????????????????????????????????
//        showPicEv();
        //???????????????
//        viewModel.showPrivacy(this);
        binding.setUserModel(new UserModel("", "", "", ""));
        //????????????????????????
        viewModel.getLastUser().observe(this,
                user -> {
                    user.setPassword("");
                    binding.setUserModel(user);
                });
        binding.setCallBack(this);
        binding.etOrgCode.setText(SPUtils.get(this, Constants.SP_KEY_TENANT_CODE, "").toString());
        setUserList();
        CommonApplication.getInstance().unbindAccount();
        initEvent();
        getImgVerify();
    }

    @Override
    protected int getColorPrimary() {
        return Color.TRANSPARENT;
    }


    ListPopupWindow userListPopupWindow;

    /**
     * ?????????????????????
     */
    private void setUserList() {
        viewModel.loadAllUserName().observe(this, list -> {
//            Log.e("list", "" + list.size());
            if (list == null || list.size() == 0) {
                return;
            }
            binding.ivSpinner.setVisibility(View.VISIBLE);
            userListPopupWindow = new ListPopupWindow(this);
            UserListPopupAdapter arr_adapter = new UserListPopupAdapter(this, list, new UserListPopupAdapter.DrawableDeleteClickListener() {
                @Override
                public void click(Integer position, Object user) {
                    //?????????????????????
                    viewModel.deleteUser(user.toString());
                    //???????????????1??????????????????????????????????????????
                    if (list.size() == 1) {
                        userListPopupWindow.dismiss();
                        binding.ivSpinner.setVisibility(View.GONE);
                    }
                }
            });
            userListPopupWindow.setAdapter(arr_adapter);
            userListPopupWindow.setAnchorView(binding.etUser);
            userListPopupWindow.setModal(true);
            userListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    binding.etUser.setText(list.get(i));
                    binding.etPassword.setText("");
                    userListPopupWindow.dismiss();
                }
            });
        });
    }

    /**
     * ??????????????????
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
                checkData();
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
                checkData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkData() {
        if (StringUtil.isNullStr(binding.etUser.getText().toString())
                && StringUtil.isNullStr(binding.etPassword.getText().toString())&&StringUtil.isNullStr(binding.etVerify.getText().toString())) {
            binding.btLogin.setEnabled(true);
        } else {
            binding.btLogin.setEnabled(false);
        }
    }

    /**
     * ?????????????????????
     */
    public void getImgVerify() {
        CommonHttpService.getInstance().tenantId("-1");
        viewModel.getImgVerify().observe(this, data -> {
            if (data != null && data.getImg() != null) {
                uuid=data.getUuid();
                byte[] decodedString = Base64.decode(data.getImg(), Base64.DEFAULT);
                binding.ivVerify.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
            } else {
                ToastUtil.show(this, "?????????????????????");
            }
        });
    }

    /**
     * ????????????????????????
     */
    boolean showPwd;

    /**
     * ????????????????????????????????????edit??????
     */
    public void showEye() {
        if (showPwd) {
            binding.ivOption.setImageResource(R.mipmap.img_login_password_hide);
            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPwd = false;
        } else {
            binding.ivOption.setImageResource(R.mipmap.img_login_password_show);
            binding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPwd = true;
        }
    }

    /**
     * ??????????????????
     */
    public void enterAccount() {
        CommonHttpService.getInstance().tenantId("-1");
        ARouter.getInstance().build(RouterUtils.ACTIVITY_ENTER_ACCOUNT).navigation();
    }

    /**
     * ????????????
     */
    public void onLoginClick() {
        if (!IsFastClick.isFastDoubleClick()) {
            return;
        }
     /*   viewModel.getTenantId(binding.etOrgCode.getText().toString().trim(), false).observe(this,
                tenantModel -> {*/
//                    ToastUtil.show(this, "tentantId" + tenantModel.getId());
        CommonHttpService.getInstance().tenantId("-1");
        SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TENANT_CODE, "-1");
        SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TENANT_ID, "-1");
        UserModel model = binding.getUserModel();
        model.setProp(new UserModel.Prop(binding.etVerify.getText().toString().trim(),uuid));
                   /* //???????????????????????????
                    if (!StringUtil.isNullStr(binding.etOrgCode.getText().toString().trim())) {
                        ToastUtil.show(this, "?????????????????????");
                        return;
                    }*/
        //???????????????????????????
        if (!StringUtil.isNullStr(binding.etUser.getText().toString().trim())) {
            ToastUtil.show(this, R.string.login_username_null_tip);
            return;
        }
        //????????????????????????
        if (!StringUtil.isNullStr(model.getPassword())) {
            ToastUtil.show(this, R.string.login_password_null_tip);
            return;
        }
        viewModel.login(binding.etUser.getText().toString().trim(), model.getPassword(),model.getProp().getCode(),uuid, true)
                .observe(LoginViewModelActivity.this,
                        user -> {
                            getPersonInfo(user.getAccount());
                            CommonApplication.getInstance().bindAccount(user.getUserId().replace("-", ""));
                            SPUtils.put(BasicApplication.getInstance(), "SIGN_LOGIN", "SIGN_LOGIN");
                            SPUtils.put(BasicApplication.getInstance(), SPKey.KEY_ACCOUNT, binding.etUser.getText().toString());
                            SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_TENANT_CODE, "-1");
                            if (StringUtil.isNullStr(path)) {
                                ARouter.getInstance()
                                        .build(path).with(getIntent().getExtras())
                                        .navigation();
                            } else {
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_MAIN_HOME)
                                        .navigation();
                            }
                            finish();
                        });
//                });

    }

    /**
     * ??????????????????
     */
    public void deleteUserName() {
        binding.etUser.setText("");
    }

    /**
     * ??????????????????
     */
    public void spinnerUser() {
        if (userListPopupWindow.isShowing()) {
            userListPopupWindow.dismiss();
        } else {
            userListPopupWindow.show();
        }
    }

    @Override
    protected boolean fullWindowFlag() {
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    /**
     * ??????????????????
     */
    private void getPersonInfo(String account) {
        viewModel.getUserByccountBeanLiveData(account).observe(this, model -> {
            if (model != null) {
                if (model.getMobile() != null) {
                    UserServiceManager.getInstance().getCurrentUserModel().setPhone(model.getMobile());
                }
            }
        });
    }

    //??????????????????????????????????????????
    private void showPicEv() {
        if (com.einyun.app.common.BuildConfig.BUILD_TYPE.equals("debug")) {
            binding.picEv.setVisibility(View.VISIBLE);
            SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "debug");
            binding.testEv.setChecked(true);
            binding.picEv.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.test_ev) {
                        SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "debug");
                    }
                    if (checkedId == R.id.uat_ev) {
                        SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "uat");
                    }
                    if (checkedId == R.id.release_ev) {
                        SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "release");
                    }
                    EinyunSDK.Companion.init(CommonApplication.getInstance(), PicEvUtils.getBaseUrl((String) SPUtils.get(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "")));
                }
            });
        } else if (com.einyun.app.common.BuildConfig.BUILD_TYPE.equals("uat")) {
            SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "uat");
        } else {
            SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_BUILD_TYPE, "release");
        }
    }

}
