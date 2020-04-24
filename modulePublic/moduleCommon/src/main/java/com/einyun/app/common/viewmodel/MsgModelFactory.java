package com.einyun.app.common.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MsgModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BaseWorkOrderHandelViewModel.class)) {
            return (T) new BaseWorkOrderHandelViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
