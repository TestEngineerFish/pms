package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.common.ui.widget.OgSelectView;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.ui.fragment.SendWorkOrderFragment;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

import java.util.ArrayList;

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
    private String[] mTitles = new String[]{"待跟进", "已跟进"};//tab标题
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
        setHeadTitle(R.string.text_send_order);
        setTxtColor(R.color.blackTextColor);
        setRightOption(R.drawable.scan);
        setBackIcon1(R.drawable.back);
        final ArrayList<SendWorkOrderFragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("tabId", i);
            fragments.add(SendWorkOrderFragment.newInstance(bundle));
        }

        binding.vpSendWork.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public SendWorkOrderFragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        binding.tabSendOrder.setupWithViewPager(binding.vpSendWork);
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.distributeWaitPage("", "","","","","","")
                .observe(SendOrderActivity.this,
                        user -> {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_MAIN_HOME)
                                    .navigation();
                            finish();
                        });

    }

    @Override
    protected int getColorPrimary() {
        return Color.WHITE;
    }
}
