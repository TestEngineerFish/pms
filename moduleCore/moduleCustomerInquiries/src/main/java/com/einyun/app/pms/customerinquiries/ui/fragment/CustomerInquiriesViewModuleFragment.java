package com.einyun.app.pms.customerinquiries.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;

import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;

import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.customerinquiries.BR;
import com.einyun.app.pms.customerinquiries.R;


import com.einyun.app.pms.customerinquiries.constants.Constants;
import com.einyun.app.pms.customerinquiries.databinding.FragmentCustomerInquiriesViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ItemInquiriesListBinding;
import com.einyun.app.pms.customerinquiries.model.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.model.InquiriesRequestBean;
import com.einyun.app.pms.customerinquiries.respository.CustomerInquiriesRepository;
import com.einyun.app.pms.customerinquiries.ui.CustomerInquiriesViewModuleActivity;
import com.einyun.app.pms.customerinquiries.viewmodule.CusInquiriesFragmentViewModel;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.widget.InquiriesTypeSelectPopWindow;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

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
    private String blockName;

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

    private void initPage() {
        CustomerInquiriesRepository.mPage2=0;//解决快速刷新导致列表数据不显示问题
        CustomerInquiriesRepository.mPage3=0;
        CustomerInquiriesRepository.mPage1=0;
        CustomerInquiriesRepository.mPage4=0;
        CustomerInquiriesRepository.mPage5=0;
    }


    @Override
    protected void setUpView() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            initPage();
            loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag()); });
        binding.inquiriesList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.inquiriesList.setAdapter(adapter);
        LiveEventBus.get(Constants.INQUIRIES_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

                    @Override
                    public void onChanged(Boolean aBoolean) {
                        loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag());
                        Log.e("onChanged", "onChanged: "+aBoolean);
                    }
                });
        blockName = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
        divideId = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, "");
        if (!blockName.isEmpty()) {
            binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
            binding.tvDivide.setText(blockName);
        }
    }

    @Override
    protected void setUpData() {
        binding.setCallBack(this);
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemInquiriesListBinding, InquiriesItemModule>(getActivity(), BR.InquiriesItemModule,mDiffCallback){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemInquiriesListBinding binding, InquiriesItemModule inquiriesItemModule) {
                    switch (inquiriesItemModule.getTaskNodeId()) {
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
                           binding.rlFeedBack.setVisibility(View.GONE);
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
                                .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER,RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                .navigation();

                    });
                    binding.tvTalk.setOnClickListener(view -> {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_COMMUNICATION)
                                .withString(RouteKey.KEY_TASK_ID,inquiriesItemModule.getTaskId())
                                .withString(RouteKey.KEY_DIVIDE_ID,inquiriesItemModule.wx_dk_id)
                                .withString(RouteKey.KEY_PROJECT_ID,inquiriesItemModule.getU_project_id())
                                .navigation();
                    });
                    binding.rlFeedBack.setOnClickListener(view -> {
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID,inquiriesItemModule.getTaskId())
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
                loadPagingData(viewModel.getRequestBean(1,10,"","",divideId),FRAGMENT_TO_FOLLOW_UP);
                break;
            case FRAGMENT_TO_FEED_BACK://待反馈
                loadPagingData(viewModel.getRequestBean(1,10,"","",divideId),FRAGMENT_TO_FEED_BACK);
                break;
            case FRAGMENT_HAVE_TO_FOLLOW_UP://已跟进
                loadPagingData(viewModel.getRequestBean(1,10,"","",divideId),FRAGMENT_HAVE_TO_FOLLOW_UP);
                break;
            case FRAGMENT_TRANSFERRED_TO://已办结
                loadPagingData(viewModel.getRequestBean(1,10,"","",divideId),FRAGMENT_TRANSFERRED_TO);
                break;
            case FRAGMENT_COPY_ME://抄送我
                loadPagingData(viewModel.getRequestBean(1,10,"","",divideId),FRAGMENT_COPY_ME);
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
        initPage();
        divideId = orgModel.getId();
        divideName = orgModel.getName();
        binding.tvDivide.setText(divideName);
        binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
        binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
        loadPagingData(viewModel.getRequestBean(1,10,cate,"", divideId),getFragmentTag());
    }
    /**
     * item点击
     */
    @Override
    public void onItemClicked(View veiw, InquiriesItemModule data) {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_INQUIRIES_DETAIL)
                .withString(RouteKey.FRAGMENT_TAG,getFragmentTag())
                .withSerializable(Constants.INQUIRIES_BEAN,data)
                .navigation();
        String taskId = data.getTaskId();
        String proInsId = data.getProInsId();
        Log.e(TAG, "onItemClicked: "+"taskId  "+taskId);
    }

    private static final String TAG = "CustomerInquiriesViewMo";
    /**
     * 筛选过后的position
     */
    @Override
    public void onData(String dataKey,int position) {
        Log.e("onData", "onData:dataKey=== "+dataKey );
        initPage();

        cate = dataKey;
        mPosition = position;
        if (mPosition==-1) {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.greyTextColor));
            binding.ivTriangleSelect.setImageResource(R.drawable.iv_approval_sel_type);

        }else {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.ivTriangleSelect.setImageResource(R.drawable.iv_approval_sel_type_blue);

        }
        loadPagingData(viewModel.getRequestBean(1,10,cate,"",divideId),getFragmentTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initPage();
    }
}
