package com.einyun.app.pms.disqualified.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DisqualifiedViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DisqualifiedViewModel.class)) {
            return (T) new DisqualifiedViewModel();
        }
        if (modelClass.isAssignableFrom(DisqualifiedFragmentViewModel.class)) {
            return (T) new DisqualifiedFragmentViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
