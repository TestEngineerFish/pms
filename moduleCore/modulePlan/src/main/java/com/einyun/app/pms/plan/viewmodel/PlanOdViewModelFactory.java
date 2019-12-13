package com.einyun.app.pms.plan.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class PlanOdViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlanOrderViewModel.class)) {
            return (T) new PlanOrderViewModel();
        }
        if(modelClass.isAssignableFrom(PlanOrderDetailViewModel.class)){
            return (T)new PlanOrderDetailViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
