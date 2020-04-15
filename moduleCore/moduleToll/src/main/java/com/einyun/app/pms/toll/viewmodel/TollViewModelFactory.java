package com.einyun.app.pms.toll.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TollViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TollViewModel.class)) {
            return (T) new TollViewModel();
        }
//        if (modelClass.isAssignableFrom(DisqualifiedFragmentViewModel.class)) {
//            return (T) new DisqualifiedFragmentViewModel();
//        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
