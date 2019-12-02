package com.einyun.app.pms.viewmodule;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ApprovalViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ApprovalViewModel.class)) {
            return (T) new ApprovalViewModel();
        }
        if (modelClass.isAssignableFrom(ApprovalFragmentViewModel.class)) {
            return (T) new ApprovalFragmentViewModel();
        }
        if (modelClass.isAssignableFrom(ApprovalDetailViewModel.class)) {
            return (T) new ApprovalDetailViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
