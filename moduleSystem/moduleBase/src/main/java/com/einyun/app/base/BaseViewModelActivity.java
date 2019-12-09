package com.einyun.app.base;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.widget.LoadingDialog;

public abstract class BaseViewModelActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected V binding;
    protected VM viewModel;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        binding = DataBindingUtil.setContentView(this, getLayoutId());

        ActivityUtil.addActivity(this);
        viewModel = initViewModel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (fullWindowFlag()){
                //需要设置这个flag contentView才能延伸到状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
            getWindow().setStatusBarColor(getColorPrimary());
        }
        initViews(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * 是否设置全屏模式
     * @return
     */
    protected boolean fullWindowFlag(){
        return false;
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
            viewModel.getLiveEvent().observe(this, status -> {
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(loadingDialog!=null){
            if(loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this.getClass());
    }
}
