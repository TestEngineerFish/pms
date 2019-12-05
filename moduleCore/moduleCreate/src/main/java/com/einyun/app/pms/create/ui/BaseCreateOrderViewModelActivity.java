package com.einyun.app.pms.create.ui;

import androidx.databinding.ViewDataBinding;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;

public abstract class BaseCreateOrderViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseHeadViewModelActivity<V,VM> {

    public abstract void pleaseSelect(String type);
}
