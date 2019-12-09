package com.einyun.app.pms.approval.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.QueryBuilder;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.approval.BR;
import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.constants.ApprovalDataKey;
import com.einyun.app.pms.approval.constants.RouteKey;
import com.einyun.app.pms.approval.databinding.FragmentApprovalBinding;
import com.einyun.app.pms.approval.databinding.ItemApprovalListBinding;
import com.einyun.app.pms.approval.module.ApprovalBean;
import com.einyun.app.pms.approval.module.ApprovalItemmodule;
import com.einyun.app.pms.approval.module.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.module.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.ui.widget.CustomPopWindow;
import com.einyun.app.pms.approval.utils.IsFastClick;
import com.einyun.app.pms.approval.viewmodule.ApprovalFragmentViewModel;
import com.einyun.app.pms.approval.viewmodule.ApprovalViewModelFactory;


import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批page
 */
public class ApprovalViewModelFragment extends BaseViewModelFragment<FragmentApprovalBinding, ApprovalFragmentViewModel> implements CustomPopWindow.OnItemClickListener , ItemClickListener<ApprovalItemmodule> ,PeriodizationView.OnPeriodSelectListener{


    private int tabId;
    RVPageListAdapter<ItemApprovalListBinding, ApprovalItemmodule> adapter;
    private ApprovalBean approvalBean;
    private List<GetByTypeKeyInnerAuditStatusModule> approvalAuditStateModule=new ArrayList<>();
    private List<GetByTypeKeyForComBoModule> approvalAuditTypeModule=new ArrayList<>();
    private String divideName = "";
    private String divideId = "";
    private String auditType = "";
    private String auditSubType = "";
    private String auditStatus = "";
    private String typeValue;
    CustomPopWindow customPopWindow;
    public static ApprovalViewModelFragment newInstance(Bundle bundle) {
        ApprovalViewModelFragment approvalViewModelFragment = new ApprovalViewModelFragment();
        approvalViewModelFragment.setArguments(bundle);
        return approvalViewModelFragment;
    }
    @Override
    protected ApprovalFragmentViewModel initViewModel() {
        return new ViewModelProvider(this, new ApprovalViewModelFactory()).get(ApprovalFragmentViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_approval;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void setUpView() {
        tabId = getArguments().getInt("tabId");
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            viewModel.refresh();
        });
        binding.approvalList.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));
        binding.approvalList.setAdapter(adapter);

        LiveEventBus
                .get(ApprovalDataKey.APPROVAL_FRAGMENT_REFRESH, Boolean.class)
                .observe(this, new Observer<Boolean>() {

                    @Override
                    public void onChanged(Boolean aBoolean) {

                        Log.e("onChanged", "onChanged: "+aBoolean);
                    }
                });
    }

    @Override
    protected void setUpData() {
     binding.setCallBack(this);
     /*
     * 审批状态数据
     * */
        viewModel.queryAduitState("").observe(this, model -> {
            approvalAuditStateModule = model;

        });
        /*
         * 审批类型数据
         * */
        viewModel.queryAduitType().observe(this, model -> {
            approvalAuditTypeModule= model;

        });
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemApprovalListBinding, ApprovalItemmodule>(getActivity(), BR.approvallist,mDiffCallback){
                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemApprovalListBinding binding, ApprovalItemmodule checkPointModel) {
                    binding.tvApprovalerName.setText(getString(R.string.tv_applicat)+checkPointModel.getApply_user());
                    binding.rlApprovalTime.setVisibility(View.VISIBLE);
//                    Log.e(TAG, "1onBindItem: auditType:"+checkPointModel.getAudit_type()+"---auditSubType :"+checkPointModel.getAudit_sub_type());
                    String auditType = getTypeStringByCode(checkPointModel.getAudit_type());
                    String auditSubType = getSubTypeStringByCode(checkPointModel.getAudit_sub_type());
//                    Log.e(TAG, "2onBindItem: auditType:"+auditType+"---auditSubType :"+auditSubType);
                    typeValue = (auditType.length() > 0 ? (auditType + "-") : "") + (auditSubType.length() > 0 ? auditSubType : "");
                    checkPointModel.getUserAuditStatus();
                    if (checkPointModel.getStatus().equals("submit")) {//待审批
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_wait_approval);
                        binding.tvApprovalState.setText(getString(R.string.tv_wait_approval));
                        binding.rlApprovalTime.setVisibility(View.GONE);//隐藏审批时间
                    } else if (checkPointModel.getStatus().equals("approve")) {//通过
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_pass);
                        binding.tvApprovalState.setText(getString(R.string.tv_had_approval));
                    } else if (checkPointModel.getStatus().equals("reject")) {//驳回
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_unpass);
                        binding.tvApprovalState.setText(getString(R.string.tv_had_not_approval));
                    } else if (checkPointModel.getStatus().equals("in_approval")) {//审批中
                        binding.rlApprovalTime.setVisibility(View.GONE);//隐藏审批时间
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approvaling);
                        binding.tvApprovalState.setText(getString(R.string.tv_approvaling));
                    }
                    binding.tvApprovalNum.setText(checkPointModel.getAudit_code());//审批单号
                    binding.tvApprovalType.setText(typeValue);
                    binding.tvIntallment.setText(checkPointModel.getDivide_name());
                    binding.tvApplyTime.setText(TimeUtil.getAllTimeNoSecond(checkPointModel.getApply_date()));
                    binding.tvApprovalTime.setText(TimeUtil.getAllTimeNoSecond(checkPointModel.getAudit_date()));




                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_approval_list;
                }
            };
        }
        binding.approvalList.setAdapter(adapter);
        adapter.setOnItemClick(this);
        loadPagingData(viewModel.getData(1,100),tabId);
    }
    private void loadPagingData(ApprovalBean approvalBean,int tabId){
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(approvalBean,tabId).observe(this, dataBeans -> {
            adapter.submitList(dataBeans)

            ;});

    }
    /*
    * 筛选按钮点击
    * */
    public void onInstallmentClick(){
        boolean flag=false;
        if (IsFastClick.isFastDoubleClick()) {
        if (approvalAuditStateModule.size()==0||approvalAuditTypeModule.size()==0) {
            /*
             * 审批状态数据
             * */
            viewModel.queryAduitState("").observe(this, model -> {
                approvalAuditStateModule = model;
                viewModel.queryAduitType().observe(this, model2 -> {
                    approvalAuditTypeModule= model2;

                    if (approvalAuditStateModule.size()!=0&&approvalAuditTypeModule.size()!=0) {
                        if (customPopWindow==null) {
                            customPopWindow=new CustomPopWindow(getActivity(),tabId,approvalAuditTypeModule,approvalAuditStateModule);
                            if (!customPopWindow.isShowing()) {
                                customPopWindow.showAsDropDown(binding.installment);
                                customPopWindow.setOnItemClickListener(this);
                            }
                        }

                    }

                });
            });

            /*
             * 审批类型数据
             * */


        }else {
            CustomPopWindow customPopWindow = new CustomPopWindow(getActivity(),tabId,approvalAuditTypeModule,approvalAuditStateModule);
            if (!customPopWindow.isShowing()) {
                customPopWindow.showAsDropDown(binding.installment);
                customPopWindow.setOnItemClickListener(this);
            }
        }
        }
    }
    /*
     * 分期按钮点击
     * */
    public void onPlotClick(){
//        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL).navigation();
//        QueryBuilder queryBuilder = new QueryBuilder();
//        queryBuilder.addQueryItem("a","s").build();
//        Logger.d(queryBuilder.toString());
//        Logger.d("table"+tabId);
        //弹出分期view
        PeriodizationView periodizationView=new PeriodizationView();
        periodizationView.setPeriodListener(ApprovalViewModelFragment.this::onPeriodSelectListener);
        periodizationView.show(getActivity().getSupportFragmentManager(),"");
    }


    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<ApprovalItemmodule> mDiffCallback = new DiffUtil.ItemCallback<ApprovalItemmodule>() {

        @Override
        public boolean areItemsTheSame(@NonNull ApprovalItemmodule oldItem, @NonNull ApprovalItemmodule newItem) {
            return oldItem.getTaskId().equals(newItem.getTaskId()) ;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ApprovalItemmodule oldItem, @NonNull ApprovalItemmodule newItem) {
            return oldItem==newItem;
        }
    };

    /*
    * 筛选数据bean
    * */
    @Override
    public void setOnItemClick(View v, ApprovalBean data) {
//        Logger.d(new Gson().toJson(data));
//        approvalBean = data;
//        loadPagingData(approvalBean,tabId);
    }
    /*
    * 筛选各类型字段
    *结合分期 根据个字段生成请求bean
    * */
    @Override
    public void onData(String auditType, String auditSubType, String auditStatus) {
        this.auditType=auditType;
        this.auditSubType=auditSubType;
        this.auditStatus=auditStatus;
        ApprovalBean data = viewModel.getData(1, 10, divideId, divideName, auditType, auditSubType, auditStatus);

        loadPagingData(data,tabId);
//        viewModel.refresh();
    }
    /*
     * 分期参数
     * */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {

       divideId=orgModel.getId();
       divideName=orgModel.getName();
        ApprovalBean data = viewModel.getData(1, 10, divideId, divideName, auditType, auditSubType, auditStatus);
        loadPagingData(data,tabId);
//        viewModel.refresh();
    }
    /*
    * 列表条目点击事件
    * */
    @Override
    public void onItemClicked(View veiw, ApprovalItemmodule data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                .withString(RouteKey.APPROVAL_DETAIL_TYPE_VALUE,typeValue)
                .withSerializable(RouteKey.APPROVAL_ITEM_DATA,data).navigation();
    }

    private String getTypeStringByCode(String typeCode) {

        //process
        if (approvalAuditTypeModule != null) {
            for (GetByTypeKeyForComBoModule bean : approvalAuditTypeModule) {
                if (bean.getKey().equalsIgnoreCase(typeCode)) {
                    return bean.getName();
                }
            }
        }
        return "";
    }

    private String getSubTypeStringByCode(String typeCode) {

        //process
        if (approvalAuditTypeModule != null) {
            for (GetByTypeKeyForComBoModule bean : approvalAuditTypeModule) {
                List<GetByTypeKeyForComBoModule.ChildrenBean> childs = bean.getChildren();
                for (GetByTypeKeyForComBoModule.ChildrenBean child : childs) {
                    if (child.getKey().equalsIgnoreCase(typeCode)) {
                        return child.getName();
                    }
                }

            }
        }

        return "";
    }
}
