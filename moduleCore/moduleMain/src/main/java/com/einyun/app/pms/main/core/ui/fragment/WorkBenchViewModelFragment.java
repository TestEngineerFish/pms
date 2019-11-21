package com.einyun.app.pms.main.core.ui.fragment;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.FragmentWorkBenchBinding;
import com.orhanobut.logger.Logger;

import static android.app.Activity.RESULT_OK;

/**
 * 工作台Fragment
 */
public class WorkBenchViewModelFragment extends BaseViewModelFragment<FragmentWorkBenchBinding, WorkBenchViewModel> {
    public static WorkBenchViewModelFragment newInstance() {
        return new WorkBenchViewModelFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_bench;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        binding.setCallBack(this);
    }

    @Override
    protected WorkBenchViewModel initViewModel() {
        return  new ViewModelProvider(this, new ViewModelFactory()).get(WorkBenchViewModel.class);
    }

    public void onPagingButtonClick(){
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
                .navigation();
    }

    public void onX5WebViewButtonClick(){
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                .withString(RouteKey.KEY_WEB_URL,"http://soft.imtt.qq.com/browser/tes/feedback.html")
                .navigation();
    }

    public void onWorkButtonClick(){
        ARouter.getInstance().build(RouterUtils.ACTIVITY_BLOCK_CHOOSE)
                .withString(RouteKey.KEY_USER_ID,userModuleService.getUserId())
                .navigation(getActivity(),RouterUtils.ACTIVITY_REQUEST_BLOCK_CHOOSE);
    }

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==RouterUtils.ACTIVITY_REQUEST_BLOCK_CHOOSE){
                String blockId=data.getStringExtra(DataConstants.KEY_BLOCK_ID);
                String blockName=data.getStringExtra(DataConstants.KEY_BLOCK_NAME);
                String blockCode=data.getStringExtra(DataConstants.KEY_BLOCK_CODE);
                Logger.d(blockId+":"+blockName+":"+blockCode);
            }
        }
    }
}
