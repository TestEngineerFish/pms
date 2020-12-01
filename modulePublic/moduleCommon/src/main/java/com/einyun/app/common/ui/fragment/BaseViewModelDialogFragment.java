//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.einyun.app.common.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.R.attr;

import com.einyun.app.common.ui.widget.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseViewModelDialogFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends com.einyun.app.base.BaseViewModelFragment<V, VM> {
    // Fragment页面onResume函数重载
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getLocalClassName()); //统计页面("MainScreen"为页面名称，可自定义)

    }
    protected LoadingDialog loadingDialog;
    protected void showLoading(Context context) {
        if (this.loadingDialog == null) {
            this.loadingDialog = (new LoadingDialog.Builder(context)).create();
        }

        if (!loadingDialog.isShowing()) {

            this.loadingDialog.show();
        }
    }

    protected void hideLoading() {
        if (this.loadingDialog != null && this.loadingDialog.isShowing()) {
            this.loadingDialog.dismiss();
        }

    }
    // Fragment页面onResume函数重载
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getLocalClassName());
    }
}
