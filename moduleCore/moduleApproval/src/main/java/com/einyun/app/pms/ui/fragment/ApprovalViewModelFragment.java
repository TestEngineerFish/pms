package com.einyun.app.pms.ui.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.R;
import com.einyun.app.pms.databinding.FragmentApprovalBinding;

import com.einyun.app.pms.ui.widget.CustomPopWindow;
import com.einyun.app.pms.viewmodule.ApprovalFragmentViewModel;
import com.einyun.app.pms.viewmodule.ApprovalViewModel;
import com.einyun.app.pms.viewmodule.ApprovalViewModelFactory;
import com.orhanobut.logger.Logger;

/**
 * 我的page
 */
public class ApprovalViewModelFragment extends BaseViewModelFragment<FragmentApprovalBinding, ApprovalFragmentViewModel> implements CustomPopWindow.OnItemClickListener{


    public static ApprovalViewModelFragment newInstance() {
        return new ApprovalViewModelFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_approval;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
     binding.setCallBack(this);
    }
    /*
    * 筛选按钮点击
    * */
    public void onInstallmentClick(){
        CustomPopWindow customPopWindow = new CustomPopWindow(getActivity());
        customPopWindow.showAsDropDown(binding.installment);
        customPopWindow.setOnItemClickListener(this);
    }
    /*
     * 分期按钮点击
     * */
    public void onPlotClick(){
        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL).navigation();
    }


    @Override
    protected ApprovalFragmentViewModel initViewModel() {
        return new ViewModelProvider(this, new ApprovalViewModelFactory()).get(ApprovalFragmentViewModel.class);
    }

    @Override
    public void setOnItemClick(View v, String data) {
        Logger.d(data);
    }
}
