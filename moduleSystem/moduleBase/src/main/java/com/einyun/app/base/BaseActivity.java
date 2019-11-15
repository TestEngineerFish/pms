package com.einyun.app.base;

import android.os.Bundle;
import android.util.TypedValue;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.widget.LoadingDialog;
import com.githang.statusbar.StatusBarCompat;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/04 12:24
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/04 12:24
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, getColorPrimary());
        initViews(savedInstanceState);
        initListener();
        initData();
        BasicApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    protected void initData(){}

    /**
     * 设置资源布局
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    public void initViews(Bundle savedInstanceState) {

    }

    protected void initListener() {
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
