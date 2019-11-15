package com.einyun.app.pms.main.viewmodel;

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
        if (modelClass.isAssignableFrom(HomeTabViewModel.class)) {
            return (T) new HomeTabViewModel();
        }
        if (modelClass.isAssignableFrom(WorkBenchViewModel.class)) {
            return (T) new WorkBenchViewModel();
        }
        if (modelClass.isAssignableFrom(MineViewModel.class)) {
            return (T) new MineViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
