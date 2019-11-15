package com.einyun.app.common.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BaseViewModelActivity;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.IncludeLayoutActivityHeadBinding;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.activity
 * @ClassName: BaseHeadViewModelActivity
 * @Description: with Head
 * @Author: chumingjun
 * @CreateDate: 2019/10/14 19:02
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/14 19:02
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class BaseHeadViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseViewModelActivity<V,VM>{
    /**
     * 创建ViewModel
     *
     * @return
     */
    protected abstract VM initViewModel();
    protected View headBar;
    protected IncludeLayoutActivityHeadBinding headBinding;
    @Override
    public int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        headBar=binding.getRoot().findViewById(R.id.head_bar);
        headBar.setBackgroundColor(getColorPrimary());
        headBinding= DataBindingUtil.getBinding(headBar);
    }


    protected void setHeadTitle(String headTitle){
        headBinding.tvHeaderTitle.setText(headTitle);
    }

    protected void setHeadTitle(int resId){
       headBinding.tvHeaderTitle.setText(resId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        headBinding.back.setOnClickListener(v -> onBackOnClick(v));
        headBinding.ivRightOption.setOnClickListener(v->onOptionClick(v));
    }

    /**
     * 返回按钮
     */
    public void onBackOnClick(View view){
        finish();
    }

    /**
     * 右侧功能按钮
     */
    public void onOptionClick(View view){

    }
}