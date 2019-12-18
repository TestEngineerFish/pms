package com.einyun.app.pms.customerinquiries.viewmodule;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CustomerInquiriesViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CustomerInquiriesViewModel.class)) {
            return (T) new CustomerInquiriesViewModel();
        }
        if (modelClass.isAssignableFrom(CusInquiriesFragmentViewModel.class)) {
            return (T) new CusInquiriesFragmentViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
