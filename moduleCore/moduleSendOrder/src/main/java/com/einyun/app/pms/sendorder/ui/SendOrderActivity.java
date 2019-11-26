package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendOrderActivity
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/25 16:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/25 16:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_SEND_ORDER)
public class SendOrderActivity extends BaseHeadViewModelActivity<ActivitySendOrderBinding, SendOrderViewModel> {

    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_order;
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTitleBarColor(R.color.white);
        setBackIcon(R.drawable.back);
        setHeadTitle(R.string.text_send_order);
        setTxtColor(R.color.blackTextColor);
        setRightOption(R.drawable.scan);
    }

    @Override
    protected void initData() {
        super.initData();
//        binding.test.setText("hahahahahhah");
    }
}
