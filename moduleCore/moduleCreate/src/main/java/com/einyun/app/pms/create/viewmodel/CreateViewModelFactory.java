package com.einyun.app.pms.create.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * UserViewModelFactory
 */
public class CreateViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateViewModel.class)) {
            return (T) new CreateViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
