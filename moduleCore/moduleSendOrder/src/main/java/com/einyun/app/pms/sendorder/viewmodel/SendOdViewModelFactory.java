package com.einyun.app.pms.sendorder.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.einyun.app.pms.sendorder.model.SendOrderModel;

public class SendOdViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SendOrderViewModel.class)) {
            return (T) new SendOrderViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
