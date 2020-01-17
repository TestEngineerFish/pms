package com.einyun.app.pms.disqualified.ui.fragment;

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
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.searchhistory.PageSearchFragment;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.constants.DisqualifiedDataKey;
import com.einyun.app.pms.disqualified.databinding.FragmentDisqualifiedOrderListBinding;
import com.einyun.app.pms.disqualified.databinding.FragmentDisqualifiedViewModuleBinding;
import com.einyun.app.pms.disqualified.databinding.ItemDisqualifiedListBinding;
import com.einyun.app.pms.disqualified.model.DisqualifiedItemModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
import com.einyun.app.pms.disqualified.net.request.DisqualifiedListRequest;
import com.einyun.app.pms.disqualified.ui.DisqualifiedViewModuleActivity;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedFragmentViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.einyun.app.pms.disqualified.widget.DisqualifiedTypeSelectPopWindow;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_ORDER_LIST;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;


/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DisqualifiedOrderListFragment extends BaseViewModelFragment<FragmentDisqualifiedOrderListBinding, DisqualifiedFragmentViewModel>  implements ItemClickListener<DisqualifiedItemModel>,PeriodizationView.OnPeriodSelectListener, DisqualifiedTypeSelectPopWindow.OnItemClickListener {
    RVPageListAdapter<ItemDisqualifiedListBinding, DisqualifiedItemModel> adapter;
    private String divideId="";
    private String divideName="";
    private int mPosition=-1;
    private int mPositionState=-1;
    private String mLine="";
    private String mState="";
    private String blockName;
    private DisqualifiedTypeSelectPopWindow inquiriesTypeSelectPopWindow;
    private PeriodizationView periodizationView;
    private PageSearchFragment searchFragment;
    private List<DisqualifiedTypesBean> model1;
    private List<DisqualifiedTypesBean> model2;

    public static DisqualifiedOrderListFragment newInstance(Bundle bundle) {
        DisqualifiedOrderListFragment fragment = new DisqualifiedOrderListFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->"+bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_disqualified_order_list;
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
            loadPagingData(viewModel.getRequestBean(1,10,mLine,mState,divideId),getFragmentTag());
           });
        binding.list.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.list.setAdapter(adapter);
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Log.e("onChanged", "onChanged: "+aBoolean);
                    }
                });
//        blockName = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
//        divideId = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, "");
//        if (!blockName.isEmpty()) {
//            binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
//            binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
//            binding.tvDivide.setText(blockName);
//        }

        binding.llSearch.setVisibility(View.GONE);
    }
    private void loadPagingData(DisqualifiedListRequest requestBean, String  tag){
//        初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(requestBean,tag).observe(this, dataBeans ->
                adapter.submitList(dataBeans)
        );

    }
    @Override
    protected void setUpData() {
        binding.setCallBack(this);
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemDisqualifiedListBinding, DisqualifiedItemModel>(getActivity(), com.einyun.app.pms.disqualified.BR.callBack,mDiffCallback){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemDisqualifiedListBinding binding, DisqualifiedItemModel inquiriesItemModule) {
                    binding.setModel(inquiriesItemModule);
//                    switch (getFragmentTag()) {
//                        case FRAGMENT_DISQUALIFIED_WAIT_FOLLOW://待跟进
//                            binding.itemCache.setVisibility(View.VISIBLE);
//                            break;
//                        case FRAGMENT_DISQUALIFIED_HAD_FOLLOW://已跟进
//                        case FRAGMENT_DISQUALIFIED_ORDER_LIST://liebiao
//                            binding.itemCache.setVisibility(View.GONE);
//                            break;
//                    }
                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_disqualified_list;
                }
            };
        }
        binding.list.setAdapter(adapter);
        adapter.setOnItemClick(this);
        switch (getFragmentTag()) {
            case FRAGMENT_DISQUALIFIED_WAIT_FOLLOW://待跟进
                viewModel.queryAduitType(DisqualifiedDataKey.LINE_TYPE_LIST).observe(this, modelLine->{model1 = modelLine; });
                viewModel.queryAduitType(DisqualifiedDataKey.ORDER_STATE_TYPE_LIST).observe(this, modelState->{ model2 = modelState; });
            case FRAGMENT_DISQUALIFIED_HAD_FOLLOW://已跟进
                viewModel.queryAduitType(DisqualifiedDataKey.LINE_TYPE_LIST).observe(this, model->{ model1 = model; });
                viewModel.queryAduitType(DisqualifiedDataKey.ORDER_STATE_TYPE_LIST).observe(this, model->{ model2 = model; });
//                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_DISQUALIFIED_HAD_FOLLOW);
                break;
            case FRAGMENT_DISQUALIFIED_ORDER_LIST://工单列表
                viewModel.queryAduitType(DisqualifiedDataKey.LINE_TYPE_LIST).observe(this, model->{ model1 = model; });
                viewModel.queryAduitType(DisqualifiedDataKey.ORDER_STATE_TYPE_LIST).observe(this, model->{ model2 = model; });
//                loadPagingData(viewModel.getRequestBean(1,10,"","",""),FRAGMENT_DISQUALIFIED_HAD_FOLLOW);
                break;
        }
        loadPagingData(viewModel.getRequestBean(1,10,mLine,mState,divideId),getFragmentTag());
    }

    @Override
    protected DisqualifiedFragmentViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new DisqualifiedViewModelFactory()).get(DisqualifiedFragmentViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<DisqualifiedItemModel> mDiffCallback = new DiffUtil.ItemCallback<DisqualifiedItemModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull DisqualifiedItemModel oldItem, @NonNull DisqualifiedItemModel newItem) {
            return oldItem==newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DisqualifiedItemModel oldItem, @NonNull DisqualifiedItemModel newItem) {
            return oldItem==newItem;
        }
    };
    /*
     * 搜索按钮点击
     * */
    public void onSearchClick(){

    }
//    private void search() {
//        try {
//            DistributePageRequest request = (DistributePageRequest) viewModel.request.clone();
//            if (searchFragment == null) {
//                searchFragment = new PageSearchFragment<ItemDisqualifiedSearchListBinding, PlanWorkOrder>(getActivity(), com.einyun.app.pms.plan.BR.planModel, new PageSearchListener<PlanWorkOrder>() {
//                    @Override
//                    public LiveData<PagedList<PlanWorkOrder>> search(String search) {
//                        request.setSearchValue(search);
//                        if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
//                            return viewModel.loadPadingNetData(request, getFragmentTag());
//                        } else {
//                            return viewModel.loadDonePagingNetData(request, getFragmentTag());
//                        }
//                    }
//
//                    @Override
//                    public void onItemClick(PlanWorkOrder model) {
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
//                                .withString(RouteKey.KEY_ORDER_ID, model.getID_())
//                                .withString(RouteKey.KEY_PRO_INS_ID, model.getProInsId())
//                                .withString(RouteKey.KEY_TASK_ID, model.getTaskId())
//                                .withString(RouteKey.KEY_TASK_NODE_ID, model.getTaskNodeId())
//                                .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
//                                .navigation();
//                    }
//
//                    @Override
//                    public int getLayoutId() {
//                        return R.layout.item_disqualified_search_list;
//                    }
//                });
//                searchFragment.setHint("请搜索工单编号或计划名称");
//            }
//            searchFragment.show(getActivity().getSupportFragmentManager(), "");
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//    }

    /*
     * 筛选按钮点击
     * */
    public void onInstallmentClick(){
//        DisqualifiedViewModuleActivity activity = (DisqualifiedViewModuleActivity) getActivity();
//        if (activity.mInquiriesTypesModule==null||activity.mInquiriesTypesModule.size()==0) {
//
//            return;
//        }
        if (model1==null||model1.size()==0) {

            return;
        }
        if (model2==null||model2.size()==0) {

            return;
        }

//        inquiriesTypeSelectPopWindow = new InquiriesTypeSelectPopWindow(getActivity(), activity.mInquiriesTypesModule,mPosition);
//        inquiriesTypeSelectPopWindow.setOnItemClickListener(this);
//        if (!inquiriesTypeSelectPopWindow.isShowing()) {
//            inquiriesTypeSelectPopWindow.showAsDropDown(binding.llTableLine);
//        }
        //TODO 数据源
        inquiriesTypeSelectPopWindow = new DisqualifiedTypeSelectPopWindow(getActivity(), model1,model2,mPosition,mPositionState);
        inquiriesTypeSelectPopWindow.setOnItemClickListener(this);
        if (!inquiriesTypeSelectPopWindow.isShowing()) {
            inquiriesTypeSelectPopWindow.showAsDropDown(binding.llTableLine);
        }

    }
    /*
     * 分期按钮点击
     * */
    public void onPlotClick(){
        //弹出分期view
        PeriodizationView periodizationView=new PeriodizationView();
        periodizationView.setPeriodListener(DisqualifiedOrderListFragment.this::onPeriodSelectListener);
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
//        binding.ivTriangleDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
        loadPagingData(viewModel.getRequestBean(1,10,mLine,mState,divideId),getFragmentTag());
    }
    /**
     * item点击
     */
    @Override
    public void onItemClicked(View veiw, DisqualifiedItemModel data) {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
                .withString(RouteKey.KEY_TASK_ID,data.getTaskId())
                .withString(RouteKey.KEY_PRO_INS_ID,data.getProInsId())
                .withString(RouteKey.FRAGMENT_TAG,getFragmentTag())
                .navigation();
    }

    private static final String TAG = "CustomerInquiriesViewMo";
    /**
     * 筛选过后的position
     */
    @Override
    public void onData(String line,String state,int position,int positionState) {
        Log.e("onData", "onData:state=== "+state );
        Log.e("onData", "onData:position=== "+position );
        Log.e("onData", "onData:positionState=== "+positionState );
        mLine = line;
        mState = state;
        mPosition = position;
        mPositionState = positionState;
        if (mPosition==-1&&mPositionState==-1) {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.greyTextColor));
            binding.ivTriangleSelect.setImageResource(R.drawable.iv_approval_sel_type);

        }else {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.ivTriangleSelect.setImageResource(R.drawable.iv_approval_sel_type_blue);

        }
        loadPagingData(viewModel.getRequestBean(1,10,mLine,mState,divideId),getFragmentTag());
    }

}
