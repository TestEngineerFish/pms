package com.einyun.app.pms.operatepercent.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.einyun.app.pms.operatepercent.OperatePercentViewModel;

public class OperatePercentModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OperatePercentViewModel.class)){
            return (T) new OperatePercentViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
