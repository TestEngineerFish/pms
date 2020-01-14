package com.einyun.app.pms.orderlist.viewmodel;

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
        if (modelClass.isAssignableFrom(OrderListViewModel.class)) {
            return (T) new OrderListViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
