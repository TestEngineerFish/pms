package com.einyun.app.pms.notice.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModelFactory
 */
public class ViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NoticeViewModel.class)) {
            return (T) new NoticeViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
