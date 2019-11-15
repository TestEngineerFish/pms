package com.einyun.app.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: BaseViewModelFactory
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/02 16:35
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/02 16:35
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseViewModelFactory implements ViewModelProvider.Factory {
    protected Application application;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass,@NonNull Application application) {
        this.application=application;
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
