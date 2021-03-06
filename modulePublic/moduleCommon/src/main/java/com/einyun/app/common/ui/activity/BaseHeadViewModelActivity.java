package com.einyun.app.common.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BaseViewModelActivity;
import com.einyun.app.common.R;
import com.einyun.app.common.databinding.IncludeLayoutActivityHeadBinding;
import com.einyun.app.common.utils.ClickProxy;

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
public abstract class BaseHeadViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseSkinViewModelActivity<V,VM>{
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
    protected void setIvBackground(int res){
        headBinding.ivBack.setImageResource(res);
    }
    protected void setHeadTitle(int resId){
       headBinding.tvHeaderTitle.setText(resId);
    }
    /**
     * 设置右侧按钮图标
     * */
    protected void setRightOption(int iconId){
        headBinding.ivRightOption.setImageResource(iconId);
        headBinding.ivRightOption.setVisibility(View.VISIBLE);
    }

    protected void hideRightOption(){
        headBinding.ivRightOption.setVisibility(View.GONE);
    }
    /**
     * 设置标题栏颜色
    * */
    protected void setTitleBarColor(int color){
        headBar.setBackgroundColor(color);
    }
    /**
     * 设置标题颜色
     * */
    protected void setTxtColor(int resId){
        headBinding.tvHeaderTitle.setTextColor(resId);
    }
    /**
     * 设置右侧文字颜色
     * */
    protected void setRightTxtColor(int resId){
        headBinding.tvRightTitle.setTextColor(getResources().getColor(resId));
    }

    /**
     * 设置最右侧文字
     * */
    protected void setRightTxt(int resId){
        headBinding.tvRightTitle.setVisibility(View.VISIBLE);
        headBinding.tvRightTitle.setText(resId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        headBinding.back.setOnClickListener(v -> onBackOnClick(v));
        headBinding.ivRightOption.setOnClickListener(new ClickProxy(v->onOptionClick(v)));
        headBinding.tvRightTitle.setOnClickListener(new ClickProxy(v->onRightOptionClick(v)));
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
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
    /**
     * 右侧文字按钮
     */
    public void onRightOptionClick(View view){

    }
}
