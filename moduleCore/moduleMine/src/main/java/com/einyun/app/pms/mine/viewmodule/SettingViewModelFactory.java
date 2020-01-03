package com.einyun.app.pms.mine.viewmodule;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SettingViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel();
        }
        if (modelClass.isAssignableFrom(UserInfoViewModel.class)) {
            return (T) new UserInfoViewModel();
        }
        if (modelClass.isAssignableFrom(SignSetViewModel.class)) {
            return (T) new SignSetViewModel();
        }
        if (modelClass.isAssignableFrom(UserHeadShotViewModel.class)) {
            return (T) new UserHeadShotViewModel();
        }
        if (modelClass.isAssignableFrom(AdviceFeedBackViewModel.class)) {
            return (T) new AdviceFeedBackViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
