package com.einyun.app.pms.extra.core.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * UserViewModelFactory
 */
public class ExtraModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
