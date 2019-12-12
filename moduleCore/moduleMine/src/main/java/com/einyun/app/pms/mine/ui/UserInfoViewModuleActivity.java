package com.einyun.app.pms.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.HttpUrlUtil;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivitySettingViewModuleBinding;
import com.einyun.app.pms.mine.databinding.ActivityUserInfoViewModuleBinding;
import com.einyun.app.pms.mine.module.GetUserByccountBean;
import com.einyun.app.pms.mine.module.UCUserDetailsBean;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.UserInfoViewModel;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_USER_INFO)
public class UserInfoViewModuleActivity extends BaseHeadViewModelActivity<ActivityUserInfoViewModuleBinding, UserInfoViewModel> {
    @Autowired(name= RouteKey.ACCOUNT)
    String account;
    @Autowired(name= RouteKey.ID)
    String userID;
    private GetUserByccountBean getUserByccountBean;

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
        LiveEventBus
                .get(LiveDataBusKey.MINE_FRESH, String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        initInfo();
                    }
                });
        initInfo();
        viewModel.getStars(viewModel.getJsonObject(userID)).observe(this, model -> {
            float stars = (float) model.getStars();
            binding.ratingBar.setStar(stars);

        });


    }

    private void initInfo() {
        viewModel.getUserByccountBeanLiveData(account).observe(this, model -> {
            getUserByccountBean = model;
            binding.tvAccount.setText(model.account);
            binding.tvName.setText(model.fullname);
            binding.tvPhoneNum.setText(model.getMobile());
            String imageUrl= HttpUrlUtil.getImageServerUrl(model.getPhoto());
            Glide.with(this)
                    .load(imageUrl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
//                    .centerCrop()
                    .into(binding.ivHeadShot);
        });
        /**
         *有问提
         */
        viewModel.getSignText(userID).observe(this, model -> {
            Logger.d("sssssss"+model);
        });
    }

    /**
     * 跳转设置签名
     */
    public void EnterSignName(){
        if (getUserByccountBean==null) {
            return;
        }
        ARouter.getInstance().build(RouterUtils.ACTIVITY_SIGN_SET)
                .withString(Constants.SIGN_USER_ID,getUserByccountBean.getId())
                .withString("edit",binding.etSignName.getText().toString()).navigation();
    }
    /**
     *跳转 设置头像
     */

    public void HeadShotOnClick(){
        if (getUserByccountBean==null) {
            return;
        }
        ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_HEAD_SHOT)
                .withSerializable("bean",getUserByccountBean)
                .navigation();
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
