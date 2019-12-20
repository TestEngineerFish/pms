package com.einyun.app.pms.customerinquiries.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Distribute;

import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.RouteKey;

import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.customerinquiries.BR;
import com.einyun.app.pms.customerinquiries.R;


import com.einyun.app.pms.customerinquiries.constants.Constants;
import com.einyun.app.pms.customerinquiries.databinding.FragmentCustomerInquiriesViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ItemInquiriesListBinding;
import com.einyun.app.pms.customerinquiries.module.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;
import com.einyun.app.pms.customerinquiries.ui.CustomerInquiriesViewModuleActivity;
import com.einyun.app.pms.customerinquiries.viewmodule.CusInquiriesFragmentViewModel;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.widget.InquiriesTypeSelectPopWindow;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class CustomerInquiriesViewModuleFragment extends BaseViewModelFragment<FragmentCustomerInquiriesViewModuleBinding, CusInquiriesFragmentViewModel>  implements ItemClickListener<InquiriesItemModule>,PeriodizationView.OnPeriodSelectListener, InquiriesTypeSelectPopWindow.OnItemClickListener {
    RVPageListAdapter<ItemInquiriesListBinding, InquiriesItemModule> adapter;
    private CustomerInquiriesViewModuleActivity activity;
    private String divideId="";
    private String divideName="";
    private int mPosition=-1;
    private String cate="";

    public static CustomerInquiriesViewModuleFragment newInstance(Bundle bundle) {
        CustomerInquiriesViewModuleFragment fragment = new CustomerInquiriesViewModuleFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->"+bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_customer_inquiries_view_module;
    }


    @Override
    protected void init() {
        super.init();

    }

    protected String getFragmentTag(){
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }



    @Override
    protected void setUpView() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag()); });
        binding.inquiriesList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.inquiriesList.setAdapter(adapter);

    }

    @Override
    protected void setUpData() {
        binding.setCallBack(this);
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemInquiriesListBinding, InquiriesItemModule>(getActivity(), BR.InquiriesItemModule,mDiffCallback){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemInquiriesListBinding binding, InquiriesItemModule inquiriesItemModule) {
                    switch (inquiriesItemModule.taskNodeId) {
                        case Constants.INQUIRIES_STATE_DEALING:
                            binding.tvApprovalState.setText(getString(R.string.tv_dealing));
                            binding.tvApprovalState.setBackgroundResource(R.mipmap.icon_processing);
                            break;

                        case Constants.INQUIRIES_STATE_RETURN_VISIT:
                            binding.tvApprovalState.setText(getString(R.string.tv_for_respone));
                            binding.tvApprovalState.setBackgroundResource(R.mipmap.icon_new);
                            break;
                        default:
                            binding.tvApprovalState.setText(getString(R.string.tv_closed));
                            binding.tvApprovalState.setBackgroundResource(R.mipmap.icon_state_closed);
                            break;
                    }
                    switch (getFragmentTag()) {
                        case FRAGMENT_TO_FOLLOW_UP://待跟进
                           binding.llTalkOrTurnSingle.setVisibility(View.VISIBLE);
                           binding.rlFeedBack.setVisibility(View.VISIBLE);
                            break;
                        case FRAGMENT_TO_FEED_BACK://待反馈
                            binding.llTalkOrTurnSingle.setVisibility(View.GONE);
                            binding.rlFeedBack.setVisibility(View.VISIBLE);
                            break;
                        case FRAGMENT_HAVE_TO_FOLLOW_UP://已跟进
                        case FRAGMENT_TRANSFERRED_TO://已办结
                        case FRAGMENT_COPY_ME://抄送我
                            binding.llTalkOrTurnSingle.setVisibility(View.GONE);
                            binding.rlFeedBack.setVisibility(View.GONE);
                            break;
                    }
                    binding.tvInquiriesType.setText(inquiriesItemModule.subject);
                    binding.tvPropertyNum.setText(inquiriesItemModule.wx_house);
                    binding.tvAskingPeople.setText(inquiriesItemModule.wx_user);
                    binding.tvWorkOrderNum.setText(inquiriesItemModule.wx_code);
                    binding.tvCreateTime.setText(TimeUtil.getAllTime(inquiriesItemModule.wx_time));
                    binding.tvTurnOrder.setOnClickListener(view -> {
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                .withString(RouteKey.KEY_TASK_ID,inquiriesItemModule.getTaskId())
                                .withString(RouteKey.KEY_ORDER_ID,inquiriesItemModule.getID_())
                                .withString(RouteKey.KEY_DIVIDE_ID,inquiriesItemModule.wx_dk_id)
                                .withString(RouteKey.KEY_PROJECT_ID,inquiriesItemModule.getU_project_id())
                                .navigation();

                    });
                    binding.tvTalk.setOnClickListener(view -> {});
                    binding.rlFeedBack.setOnClickListener(view -> {
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID,inquiriesItemModule.getTaskId())
                                .withString(RouteKey.KEY_ORDER_ID,inquiriesItemModule.getID_())
                                .withString(RouteKey.KEY_DIVIDE_ID,inquiriesItemModule.wx_dk_id)
                                .withString(RouteKey.KEY_PROJECT_ID,inquiriesItemModule.getU_project_id())
                                .navigation();

                    });


                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_inquiries_list;
                }
            };
        }
        binding.inquiriesList.setAdapter(adapter);
        adapter.setOnItemClick(this);
        activity = (CustomerInquiriesViewModuleActivity) getActivity();
        switch (getFragmentTag()) {
            case FRAGMENT_TO_FOLLOW_UP://待跟进
//                viewModel.getTiaoXian();
//                viewModel.getOrderType();
                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_TO_FOLLOW_UP);
                break;
            case FRAGMENT_TO_FEED_BACK://待反馈
                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_TO_FEED_BACK);
                break;
            case FRAGMENT_HAVE_TO_FOLLOW_UP://已跟进
                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_HAVE_TO_FOLLOW_UP);
                break;
            case FRAGMENT_TRANSFERRED_TO://已办结
                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_TRANSFERRED_TO);
                break;
            case FRAGMENT_COPY_ME://抄送我
                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_COPY_ME);
                break;
        }

    }

    private void loadPagingData(InquiriesRequestBean requestBean, String  tag){
//        初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(requestBean,tag).observe(this, dataBeans -> adapter.submitList(dataBeans));

    }

    @Override
    protected CusInquiriesFragmentViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new CustomerInquiriesViewModelFactory()).get(CusInquiriesFragmentViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<InquiriesItemModule> mDiffCallback = new DiffUtil.ItemCallback<InquiriesItemModule>() {


        @Override
        public boolean areItemsTheSame(@NonNull InquiriesItemModule oldItem, @NonNull InquiriesItemModule newItem) {
            return oldItem==newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull InquiriesItemModule oldItem, @NonNull InquiriesItemModule newItem) {
            return oldItem==newItem;
        }
    };
    /*
     * 筛选按钮点击
     * */
    public void onInstallmentClick(){
        CustomerInquiriesViewModuleActivity activity = (CustomerInquiriesViewModuleActivity) getActivity();
        if (activity.mInquiriesTypesModule==null||activity.mInquiriesTypesModule.size()==0) {

            return;
        }
        InquiriesTypeSelectPopWindow inquiriesTypeSelectPopWindow = new InquiriesTypeSelectPopWindow(getActivity(), activity.mInquiriesTypesModule,mPosition);
        inquiriesTypeSelectPopWindow.setOnItemClickListener(this);
        inquiriesTypeSelectPopWindow.showAsDropDown(binding.llTableLine);
    }
    /*
     * 分期按钮点击
     * */
    public void onPlotClick(){
        //弹出分期view
        PeriodizationView periodizationView=new PeriodizationView();
        periodizationView.setPeriodListener(CustomerInquiriesViewModuleFragment.this::onPeriodSelectListener);
        periodizationView.show(getActivity().getSupportFragmentManager(),"");
    }
    /**
     *分期返回
     */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        divideId = orgModel.getId();
        divideName = orgModel.getName();
        binding.tvDivide.setText(divideName);
        binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
        loadPagingData(viewModel.getRequestBean(1,10,"","", divideId),getFragmentTag());
    }
    /**
     * item点击
     */
    @Override
    public void onItemClicked(View veiw, InquiriesItemModule data) {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_INQUIRIES_DETAIL)
                .withSerializable(Constants.INQUIRIES_BEAN,data)
                .navigation();
    }

    /**
     * 筛选过后的position
     */
    @Override
    public void onData(String dataKey,int position) {
        Log.e("onData", "onData:dataKey=== "+dataKey );
        cate = dataKey;
        mPosition = position;
        loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
