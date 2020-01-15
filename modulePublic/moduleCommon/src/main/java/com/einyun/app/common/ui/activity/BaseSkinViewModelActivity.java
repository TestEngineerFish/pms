package com.einyun.app.common.ui.activity;

import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;
import androidx.databinding.ViewDataBinding;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.BaseViewModelActivity;
import com.einyun.app.common.R;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseSkinViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseViewModelActivity<V,VM>{
    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
