package com.einyun.app.pms.customerinquiries.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.customerinquiries.R;
import com.einyun.app.pms.customerinquiries.databinding.ActivityCustomerInquiriesViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ActivityFeedbackViewModuleBinding;
import com.einyun.app.pms.customerinquiries.module.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.module.FeedBackModule;
import com.einyun.app.pms.customerinquiries.module.FeedBackRequest;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.ui.fragment.CustomerInquiriesViewModuleFragment;
import com.einyun.app.pms.customerinquiries.viewmodule.CusInquiriesFragmentViewModel;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.viewmodule.FeedBackViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

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
            Log.e("module", "initData: "+module );
            feedBackModule = module;
        });
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
        if (feedBackModule.getTaskCommu()==null) {
            return;
        }
        FeedBackRequest feedBackRequest = new FeedBackRequest();
        feedBackRequest.setAccount(feedBackModule.getTaskCommu().getSender());
        feedBackRequest.setTaskId(taskID);
        feedBackRequest.setOpinion("content");
        feedBackRequest.setNotifyType("inner");
        feedBackRequest.setActionName("commu");
        viewModel.feedBack(feedBackRequest).observe(this,module->{
            Log.e("module", "initData: "+module );
        });
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
