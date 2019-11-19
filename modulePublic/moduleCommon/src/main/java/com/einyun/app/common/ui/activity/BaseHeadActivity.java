package com.einyun.app.common.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.einyun.app.base.BaseActivity;
import com.einyun.app.common.R;


/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.activity
 * @ClassName: BaseHeadActivity
 * @Description: with Head
 * @Author: chumingjun
 * @CreateDate: 2019/10/14 18:52
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/14 18:52
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseHeadActivity extends BaseSkinActivity {

    protected ImageView btnBack;
    protected TextView tvTitle;
    protected TextView tvRightTitle;
    protected ImageView ivRightOption;
    protected View root;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        root=findViewById(R.id.main_header_bar);
        btnBack=findViewById(R.id.back);
        tvTitle=findViewById(R.id.tv_header_title);
        tvRightTitle=findViewById(R.id.tv_right_title);
        ivRightOption.findViewById(R.id.iv_right_option);

        root.setBackgroundColor(getColorPrimary());
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setHeadTitle(String title){
        tvTitle.setText(title);
    }

    @Override
    protected void initListener() {
        super.initListener();
        //返回按钮
        btnBack.setOnClickListener(v -> {
            finish();
        });
        //右侧功能按钮
        ivRightOption.setOnClickListener(v -> onOptionClick(v));
    }

    protected void onOptionClick(View ivOption){

    }
}
