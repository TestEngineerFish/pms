package com.einyun.app.common.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import com.einyun.app.base.BaseActivity;

public abstract class BaseSkinActivity extends BaseActivity {
    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }
}
