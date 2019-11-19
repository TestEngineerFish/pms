package com.einyun.app.common.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;
import androidx.databinding.ViewDataBinding;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BaseViewModelActivity;

public abstract class BaseSkinViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseViewModelActivity<V,VM>{
    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }
}
