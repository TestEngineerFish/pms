package com.einyun.app.pms.mine.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityAdviceFeedBackViewModuleBinding;
import com.einyun.app.pms.mine.model.FeedBackBean;
import com.einyun.app.pms.mine.viewmodule.AdviceFeedBackViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;

import java.util.ArrayList;
import java.util.List;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_ADVICE_FEED_BACK)
public class AdviceFeedBackViewModuleActivity extends BaseHeadViewModelActivity<ActivityAdviceFeedBackViewModuleBinding, AdviceFeedBackViewModel> {
    @Autowired(name= RouteKey.ACCOUNT)
    String account;
    @Autowired(name= RouteKey.NAME)
    String name;
    @Autowired(name= RouteKey.ID)
    String userId;
    @Autowired(name= RouteKey.PHONE)
    String mobile;
    int typeDefaultPos=-1;
    List<String> typeList = new ArrayList<>();
    @Override
    protected AdviceFeedBackViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(AdviceFeedBackViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_advice_feed_back_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_advice_feed_back));
        binding.setCallBack(this);
    }

    /**
     * 问题类型选择
     */
    public void EnterSignName(){
        typeList.clear();
        typeList.add(getString(R.string.tv_optimization_suggestions));
        typeList.add(getString(R.string.tv_function_error));
        typeList.add(getString(R.string.tv_other));
        BottomPicker.buildBottomPicker(this, typeList, 0, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                typeDefaultPos = position;
                binding.tvSelectType.setText(typeList.get(position));
            }
        });
    }
    /**
     * 确认提交
     */
    public void btnConfirm(){
//        if(typeDefaultPos<0){
//            ToastUtil.show(this, R.string.tv_select_ques_type);
//            return;
//        }
        if (binding.etLimitInput.getString().isEmpty()) {
            ToastUtil.show(this, R.string.tv_write_your_advice);
            return;
        }
        FeedBackBean feedBackBean = viewModel.getJsonObject(binding.etLimitInput.getString(), account, name, mobile, userId, (typeDefaultPos + 1));
        viewModel.sumitApproval(feedBackBean).observe(this, model -> {
            if (model) {
                finish();
            }

        });
    }



    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
