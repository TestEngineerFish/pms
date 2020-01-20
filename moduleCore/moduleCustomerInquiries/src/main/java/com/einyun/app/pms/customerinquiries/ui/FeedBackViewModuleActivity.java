package com.einyun.app.pms.customerinquiries.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.pms.customerinquiries.R;
import com.einyun.app.pms.customerinquiries.constants.Constants;
import com.einyun.app.pms.customerinquiries.databinding.ActivityFeedbackViewModuleBinding;
import com.einyun.app.pms.customerinquiries.model.FeedBackModule;
import com.einyun.app.pms.customerinquiries.model.FeedBackRequest;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.viewmodule.FeedBackViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.List;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
public class FeedBackViewModuleActivity extends BaseHeadViewModelActivity<ActivityFeedbackViewModuleBinding, FeedBackViewModel> {
@Autowired(name=RouteKey.KEY_TASK_ID)
String taskID;
    private FeedBackModule feedBackModule;

    @Override
    protected FeedBackViewModel initViewModel() {
        return new ViewModelProvider(this, new CustomerInquiriesViewModelFactory()).get(FeedBackViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_feed_back);
        binding.setCallBack(this);
    }

    @Override
    protected void initData() {
        super.initData();
        /**
         * 获取反馈
         */
        viewModel.queryFeedbackInfo(taskID).observe(this,module->{
            Log.e("model", "initData: "+module );
            feedBackModule = module;
            UpdateUI(module);
        });
    }

    private void UpdateUI(FeedBackModule module) {
        if (module == null) {
            return;
        }
        binding.setModule(module);
        List<FeedBackModule.CommuReceiversBean> commuReceivers = module.getCommuReceivers();
        if (commuReceivers!=null&&commuReceivers.size()>0) {
            long expectTime = commuReceivers.get(0).getExpectTime();
            if (expectTime!=0) {
                binding.tvBackTime.setText(TimeUtil.getAllTime(expectTime));
            }
        }
    }

    /**
     * 提交
     * */
    public  void onSubmitClick() {
//        "account":"ceshi001",
//                "taskId":1302601,
//                "opinion":"反馈内容",
//                "notifyType":"inner,app_push",
//                "actionName":"commu"
        if (IsFastClick.isFastDoubleClick()) {
        if (feedBackModule.getTaskCommu()==null) {
            return;
        }
        String option = binding.etLimitInput.getString();
        if (option.isEmpty()) {
            ToastUtil.show(this, getString(R.string.tv_empty_content));
            return;
        }
        FeedBackRequest feedBackRequest = new FeedBackRequest();
        feedBackRequest.setAccount((String) SPUtils.get(this, SPKey.KEY_ACCOUNT,""));
        feedBackRequest.setTaskId(taskID);
        feedBackRequest.setOpinion(option);
        feedBackRequest.setNotifyType("inner");
        feedBackRequest.setActionName("commu");
        viewModel.feedBack(feedBackRequest).observe(this,module->{
            Log.e("model", "initData: "+module );
            if (module) {
                LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                finish();
            }else {
                ToastUtil.show(this, "此任务已被处理或不存在");
            }
        });
    }
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
