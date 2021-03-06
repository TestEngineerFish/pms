package com.einyun.app.pms.patrol.viewmodel;

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
        if(modelClass.isAssignableFrom(PatrolSignInViewModel.class)){
            return (T) new PatrolSignInViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
