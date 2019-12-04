package com.einyun.app.pms.main.core.ui.fragment;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.MineViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.FragmentMineBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * 我的page
 */
public class MineViewModelFragment extends BaseViewModelFragment<FragmentMineBinding, MineViewModel> {
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
            viewModel.getWorkState().observe(this, status -> {
                binding.ivWorkStatus.setVisibility(View.VISIBLE);
                upWorkStatus(status);
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
