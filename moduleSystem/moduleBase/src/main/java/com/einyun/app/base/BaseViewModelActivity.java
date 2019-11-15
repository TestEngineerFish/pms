package com.einyun.app.base;

import android.os.Bundle;
import android.util.TypedValue;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.widget.LoadingDialog;
import com.githang.statusbar.StatusBarCompat;

public abstract class BaseViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected V binding;
    protected VM viewModel;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        StatusBarCompat.setStatusBarColor(this, getColorPrimary());
        BasicApplication.getInstance().addActivity(this);
        viewModel = initViewModel();
        initViews(savedInstanceState);
        initData();
        initListener();
    }


    /**
     * 获取主题颜色
     * @return
     */
    protected int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    /**
     * 创建ViewModel
     *
     * @return
     */
    protected abstract VM initViewModel();


    /**
     * 设置资源布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    public void initViews(Bundle savedInstanceState) {
        if(viewModel!=null){
            viewModel.getSingleLiveEvent().observe(this, status -> {
                if (status.isLoadingShow()) {
                    showLoading();
                }else if(!status.isLoadingShow()){
                    hideLoading();
                }
            });
        }

    }

    protected void showLoading(){
        if(loadingDialog==null){
            loadingDialog = new LoadingDialog.Builder(this).create();
        }
        loadingDialog.show();
    }

    protected void hideLoading(){
        if(loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BasicApplication.getInstance().removeActivity(this);
        if(loadingDialog!=null){
            if(loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    }
}
