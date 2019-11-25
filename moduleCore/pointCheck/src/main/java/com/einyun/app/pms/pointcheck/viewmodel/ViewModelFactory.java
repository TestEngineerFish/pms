package com.einyun.app.pms.pointcheck.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * UserViewModelFactory
 */
public class UserViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PointCheckListViewModel.class)) {
            return (T) new PointCheckListViewModel();
        }
        if (modelClass.isAssignableFrom(PointCheckDetialViewModel.class)) {
            return (T) new PointCheckDetialViewModel();
        }
        if (modelClass.isAssignableFrom(CreateCheckViewModel.class)) {
            return (T) new CreateCheckViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
