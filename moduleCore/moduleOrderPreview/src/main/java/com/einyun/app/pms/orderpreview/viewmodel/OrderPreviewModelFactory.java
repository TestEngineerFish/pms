package com.einyun.app.pms.orderpreview.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OrderPreviewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OrderPreviewViewModel.class)){
            return (T) new OrderPreviewViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }}
