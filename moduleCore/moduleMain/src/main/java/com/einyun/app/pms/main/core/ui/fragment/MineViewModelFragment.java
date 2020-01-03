package com.einyun.app.pms.main.core.ui.fragment;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.model.UserStarsBean;
import com.einyun.app.pms.main.core.viewmodel.MineViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.FragmentMineBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * 我的page
 */
public class MineViewModelFragment extends BaseViewModelFragment<FragmentMineBinding, MineViewModel> {

    private UserInfoModel userInfoModel1;

    public static MineViewModelFragment newInstance() {
        return new MineViewModelFragment();
    }

    private String workStatus;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void setUpView() {
        LiveEventBus
                .get(LiveDataBusKey.MINE_FRESH, String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        initData();
                    }
                });
    }

    @Override
    protected void setUpData() {
        binding.setCallBack(this);
        initData();
    }

    private void initData() {
        viewModel.getUserInfoByUserId().observe(this, userInfoModel -> {
            binding.setUserInfo(userInfoModel);
            userInfoModel1 = userInfoModel;
            viewModel.getWorkState().observe(this, status -> {
                if(status!=null){
                    binding.ivWorkStatus.setVisibility(View.VISIBLE);
                    upWorkStatus(status);
                }
            });
            /**
             * 设置星级
             * */
            viewModel.getStars(new UserStarsBean("",userInfoModel.getId())).observe(this, model -> {
                float stars = (float) model.getStars();
                binding.ratingBar.setStar(5f);

            });
        });

    }

    /**
     * 更新工作状态组件
     *
     * @param status
     */
    private void upWorkStatus(String status) {
        this.workStatus = status;
        if (status.equalsIgnoreCase("1")) {
            binding.ivWorkStatus.setImageResource(R.mipmap.img_mine_go_work);
        } else {
            binding.ivWorkStatus.setImageResource(R.mipmap.img_mine_working);
        }
    }

    /**
     * 跳转设置等页面
     *
     * @param routerName
     */
    public void jumpUserSetting(String routerName) {
        ARouter.getInstance()
                .build(routerName)
                .navigation();
    }
    /**
    * 跳转设置界面
    * */
    public void Setting(){
        jumpUserSetting(RouterUtils.ACTIVITY_MINE_SETTING);
    }
    /**
    * 跳转审批界面
    * */
    public void approvalOnClick(){
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_APPROVAL)
                .withString(RouteKey.APPROVAL_LIST_FROM,RouteKey.APPROVAL_LIST_FROM)
                .navigation();
    }
    /**
    * 跳转意见反馈
    * */
    public void adviceFeedBack(){
        if (userInfoModel1!=null) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_ADVICE_FEED_BACK)
                    .withString(RouteKey.ACCOUNT,userInfoModel1.getAccount())
                    .withString(RouteKey.NAME,userInfoModel1.getFullname())
                    .withString(RouteKey.PHONE,userInfoModel1.getMobile())
                    .withString(RouteKey.ID,userInfoModel1.getId())
                    .navigation();
        }

    }
    /**
     *跳转个人信息
     */
    public void userInfoOnClick(){
        if (userInfoModel1!=null) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_USER_INFO)
                    .withString(RouteKey.ACCOUNT,userInfoModel1.getAccount())
                    .withString(RouteKey.ID,userInfoModel1.getId())
                    .navigation();
        }
    }

    /**
     * 改变工作状态
     */
    public void changeWorkStatus() {
        if (workStatus == null) {
            return;
        }
        new AlertDialog(getActivity()).builder().setTitle(getResources().getString(R.string.tip))
                .setMsg(getResources().getString(
                        "0".equals(workStatus) ? R.string.ad_change_work_status : R.string.ad_change_working_status))
                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.updateWorkState("0".equals(workStatus) ? "1" : "0").observe(getActivity(), workStatus -> {
                            viewModel.getWorkState().observe(getActivity(), status -> {
                                upWorkStatus(status);
                            });
                        });
                    }
                }).show();
    }

    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(MineViewModel.class);
    }

}