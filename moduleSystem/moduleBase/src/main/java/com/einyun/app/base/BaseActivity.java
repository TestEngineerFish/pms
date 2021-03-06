package com.einyun.app.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ActivityUtil;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        ARouter.getInstance().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (fullWindowFlag()) {
                //需要设置这个flag contentView才能延伸到状态栏
                View decorView = getWindow().getDecorView();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }else{
                //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
                getWindow().setStatusBarColor(getColorPrimary());
            }
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        initViews(savedInstanceState);
        initListener();
        initData();
        ActivityUtil.addActivity(this);
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
    public void finish() {
        super.finish();
        ActivityUtil.removeActivity(this.getClass());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadingDialog!=null){
            if(loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    }

    /**
     * 是否设置全屏模式
     * @return
     */
    protected boolean fullWindowFlag(){
        return false;
    }
}
