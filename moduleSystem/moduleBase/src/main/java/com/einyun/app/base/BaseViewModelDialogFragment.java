package com.einyun.app.base;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Description:BaseViewModelFragment
 *  Fragment基类
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/09/02
 * */
public abstract class BaseViewModelDialogFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends DialogFragment {
    protected V binding;
    protected VM viewModel;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mContext = getContext();
        ARouter.getInstance().inject(this);
        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=initViewModel();
        setUpView();
        setUpData();
        setUpListener();
    }

    /**
     * 获取主题颜色
     * @return
     */
    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public abstract int getLayoutId();
    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();

    protected  void setUpListener(){

    }

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据 * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {
    }

    /**
     * 创建ViewModel
     * @return
     */
    protected abstract VM initViewModel();
}
