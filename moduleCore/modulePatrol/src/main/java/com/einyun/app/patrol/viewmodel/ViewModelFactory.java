package com.einyun.app.patrol.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.einyun.app.base.BaseViewModelFactory;

public class ViewModelFactory extends BaseViewModelFactory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PatrolListViewModel.class)) {
            return (T) new PatrolListViewModel();
        }
        if (modelClass.isAssignableFrom(PatrolViewModel.class)) {
            return (T) new PatrolViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
