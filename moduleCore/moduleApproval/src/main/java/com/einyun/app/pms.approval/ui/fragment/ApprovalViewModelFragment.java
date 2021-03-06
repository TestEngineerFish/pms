package com.einyun.app.pms.approval.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.approval.BR;
import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.constants.ApprovalDataKey;
import com.einyun.app.pms.approval.databinding.FragmentApprovalBinding;
import com.einyun.app.pms.approval.databinding.ItemApprovalListBinding;
import com.einyun.app.pms.approval.model.ApprovalBean;
import com.einyun.app.pms.approval.model.ApprovalItemmodule;
import com.einyun.app.pms.approval.model.GetByTypeKeyForComBoModule;
import com.einyun.app.pms.approval.model.GetByTypeKeyInnerAuditStatusModule;
import com.einyun.app.pms.approval.repository.ApprovalkListRepository;
import com.einyun.app.pms.approval.ui.widget.CustomPopWindow;
import com.einyun.app.pms.approval.utils.IsFastClick;
import com.einyun.app.pms.approval.viewmodule.ApprovalFragmentViewModel;
import com.einyun.app.pms.approval.viewmodule.ApprovalViewModelFactory;


import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批page
 */
public class ApprovalViewModelFragment extends BaseViewModelFragment<FragmentApprovalBinding, ApprovalFragmentViewModel> implements CustomPopWindow.OnItemClickListener , ItemClickListener<ApprovalItemmodule> ,PeriodizationView.OnPeriodSelectListener{

    public  int mApprovalTypePosition=-1;
    public  int mApprovalChildTypePosition=-1;
    public  int mApprovalStatusPosition=-1;
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
//    private String blockName;

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
        binding.swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        binding.swipeRefresh.setOnRefreshListener(() -> {
            initPage();
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
                         viewModel.refresh();
                        Log.e("onChanged", "onChanged: "+aBoolean);
                    }
                });
//        blockName = (String) SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
//        if (!blockName.isEmpty()) {
//            binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
//            binding.ivDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
//            binding.tvDivide.setText(blockName);
//        }
    }

    private void initPage() {
        ApprovalkListRepository.mPage2=0;//解决快速刷新导致列表数据不显示问题
        ApprovalkListRepository.mPage3=0;
        ApprovalkListRepository.mPage1=0;
    }

    @Override
    protected void setUpData() {
     binding.setCallBack(this);
        viewModel.queryAduitType().observe(this, model -> {
            approvalAuditTypeModule= viewModel.sortAuditTypelist(model);
//            approvalAuditTypeModule= model;

        });
     /*
     * 审批状态数据
     * */
        viewModel.queryAduitState().observe(this, model -> {
            approvalAuditStateModule = model;

        });
        /*
         * 审批类型数据
         * */

        if(adapter==null){
            adapter=new RVPageListAdapter<ItemApprovalListBinding, ApprovalItemmodule>(getActivity(), BR.approvallist,mDiffCallback){
//                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemApprovalListBinding binding, ApprovalItemmodule checkPointModel) {
                    binding.tvApprovalerName.setText(getString(R.string.tv_applicat)+checkPointModel.getApply_user());
                    binding.rlApprovalTime.setVisibility(View.VISIBLE);
//                    Log.e(TAG, "1onBindItem: auditType:"+checkPointModel.getAudit_type()+"---auditSubType :"+checkPointModel.getAudit_sub_type());
//                    String auditType = getTypeStringByCode(checkPointModel.getAudit_type());
//                    String auditSubType = getSubTypeStringByCode(checkPointModel.getAudit_sub_type());
//                    auditType=checkPointModel.getAudit_type();
//                    auditSubType=checkPointModel.getAudit_sub_type();
//                    Log.e(TAG, "2onBindItem: auditType:"+auditType+"---auditSubType :"+auditSubType);
//                    typeValue = (auditType.length() > 0 ? (auditType + "-") : "") + (auditSubType.length() > 0 ? auditSubType : "");
                    checkPointModel.getUserAuditStatus();
                    if (checkPointModel.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_PENDING)) {//待审批
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_wait_approval);
                        binding.tvApprovalState.setText(getString(R.string.tv_wait_approval));
                        binding.rlApprovalTime.setVisibility(View.GONE);//隐藏审批时间
                    } else if (checkPointModel.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_HAD_PASS)) {//通过
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_pass);
                        binding.tvApprovalState.setText(getString(R.string.tv_had_pass));
                    } else if (checkPointModel.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_HAD_UNPASS)) {//驳回
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_unpass);
                        binding.tvApprovalState.setText(getString(R.string.tv_had_not_approval));
                    } else if (checkPointModel.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_IN_APPROVAL)) {//审批中
                        binding.rlApprovalTime.setVisibility(View.GONE);//隐藏审批时间
                        binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approvaling);
                        binding.tvApprovalState.setText(getString(R.string.tv_approvaling));
                    }
                    binding.tvApprovalNum.setText(checkPointModel.getAudit_code());//审批单号
                    binding.tvApprovalType.setText(getTypeValue(checkPointModel.getAudit_type(),checkPointModel.getAudit_sub_type()));
                    binding.tvIntallment.setText(checkPointModel.getDivide_name());
                    binding.tvApplyTime.setText(TimeUtil.getAllTime(checkPointModel.getApply_date()));
                    binding.tvApprovalTime.setText(TimeUtil.getAllTime(checkPointModel.getAudit_date()));
                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_approval_list;
                }
            };
        }
        binding.approvalList.setAdapter(adapter);
        adapter.setOnItemClick(this);
//        loadPagingData(viewModel.getData(1,10,"",blockName,"","",""),tabId);
        loadPagingData(viewModel.getData(1,10,"","","","",""),tabId);
//        LiveEventBus.get(LiveDataBusKey.APPROVAL_EMPTY,Boolean.class).observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if (aBoolean) {
//                    binding.empty.getRoot().setVisibility(View.VISIBLE);
//                }else {
//                    binding.empty.getRoot().setVisibility(View.GONE);
//
//                }
//            }
//        });
        LiveDataBusUtils.getLiveBusData(binding.empty.getRoot(),LiveDataBusKey.APPROVAL_EMPTY+tabId,this);
    }

    private String  getTypeValue(String auditType ,String auditSubType) {
        switch (auditType) {
            case "INNER_AUDIT_CREATE_PLAN"://创建计划
                switch (auditSubType) {
                    case "CREATE_WORK_PLAN"://创建工作计划
                        typeValue="创建计划-创建工作计划";
                        break;
                    case "CREATE_PATROL_PLAN"://创建巡查计划
                        typeValue="创建计划-创建巡查计划";
                        break;
                }
                break;
            case "INNER_AUDIT_FORCE_CLOSE"://强制闭单
                switch (auditSubType) {
                    case "FORCE_CLOSE_COMPLAIN"://客户投诉工单
                        typeValue="强制闭单-客户投诉工单";
                        break;
                    case "FORCE_CLOSE_ENQUIRY"://客户问询工单
                        typeValue="强制闭单-客户问询工单";
                        break;
                    case "FORCE_CLOSE_REPAIR"://客户报修工单
                        typeValue="强制闭单-客户报修工单";
                        break;
                    case "FORCE_CLOSE_PATROL"://巡查工单
                        typeValue="强制闭单-巡查工单";
                        break;
                    case "FORCE_CLOSE_PLAN"://计划工单
                        typeValue="强制闭单-计划工单";
                        break;
                    case "FORCE_CLOSE_ALLOCATE"://派工单
                        typeValue="强制闭单-派工单";
                        break;
                }
                break;
            case "INNER_AUDIT_UPDATE_PLAN"://修改计划
                switch (auditSubType) {
                    case "UPDATE_PATROL_PLAN"://修改巡查计划
                        typeValue="修改计划-修改巡查计划";
                        break;
                    case "UPDATE_WORK_PLAN"://修改工作计划
                        typeValue="修改计划-修改工作计划";
                        break;
                }
                break;
            case "INNER_AUDIT_POSTPONED": //工单延期
                switch (auditSubType) {
                    case "POSTPONED_REPAIR"://客户报修工单
                        typeValue="工单延期-客户报修工单";
                        break;
                    case "POSTPONED_COMPLAIN"://客户投诉工单
                        typeValue="工单延期-客户投诉工单";
                        break;
                    case "POSTPONED_PATROL"://巡查工单
                        typeValue="工单延期-巡查工单";
                        break;
                    case "POSTPONED_PLAN"://计划工单
                        typeValue="工单延期-计划工单";
                        break;
                    case "POSTPONED_ALLOCATE"://派工单
                        typeValue="工单延期-派工单";
                        break;
                }
                break;
        }
        return typeValue;
    }

    private static final String TAG = "ApprovalViewModelFragme";
    private void loadPagingData(ApprovalBean approvalBean,int tabId){
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(approvalBean,tabId).observe(this, dataBeans ->{
            adapter.submitList(dataBeans);
        });

    }
    /*
    * 筛选按钮点击
    * */
    public void onInstallmentClick(){
        viewModel.isFirst=true;
        if (IsFastClick.isFastDoubleClick()) {
        if (approvalAuditStateModule.size()==0||approvalAuditTypeModule.size()==0) {
            /*
             * 审批状态数据
             * */
            viewModel.queryAduitState().observe(this, model -> {
                approvalAuditStateModule = model;
                viewModel.queryAduitType().observe(this, model2 -> {
//                    approvalAuditTypeModule= viewModel.sortAuditTypelist(model2);

                    if (approvalAuditStateModule.size()!=0&&approvalAuditTypeModule.size()!=0) {
                        if (customPopWindow==null) {
                            customPopWindow=new CustomPopWindow(getActivity(),tabId,approvalAuditTypeModule,approvalAuditStateModule, mApprovalTypePosition, mApprovalChildTypePosition, mApprovalStatusPosition, auditType, auditSubType, auditStatus);
                            if (!customPopWindow.isShowing()) {
                                customPopWindow.showAsDropDown(binding.llTableLine);
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

                customPopWindow = new CustomPopWindow(getActivity(),tabId,approvalAuditTypeModule,approvalAuditStateModule,mApprovalTypePosition,mApprovalChildTypePosition,mApprovalStatusPosition,auditType,auditSubType,auditStatus);

            if (!customPopWindow.isShowing()) {
                customPopWindow.showAsDropDown(binding.llTableLine);
                customPopWindow.setOnItemClickListener(this);
            }
        }
        }
    }
    /*
     * 园区按钮点击
     * */
    public void onPlotClick(){
        //弹出园区view
        PeriodizationView periodizationView=new PeriodizationView();
        periodizationView.setPeriodListener(ApprovalViewModelFragment.this::onPeriodSelectListener);
        periodizationView.show(getActivity().getSupportFragmentManager(),"");
    }


    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<ApprovalItemmodule> mDiffCallback = new DiffUtil.ItemCallback<ApprovalItemmodule>() {

        @Override
        public boolean areItemsTheSame(@NonNull ApprovalItemmodule oldItem, @NonNull ApprovalItemmodule newItem) {
            return oldItem.getID_()==newItem.getID_();
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
    *结合园区 根据个字段生成请求bean
    * */
    @Override
    public void onData(String auditType, String auditSubType, String auditStatus, int mApprovalTypePosition, int mApprovalChildTypePosition, int mApprovalStatusPosition) {
        this.auditType=auditType;
        this.auditSubType=auditSubType;
        this.auditStatus=auditStatus;
        this.mApprovalTypePosition=mApprovalTypePosition;
        this.mApprovalChildTypePosition=mApprovalChildTypePosition;
        this.mApprovalStatusPosition=mApprovalStatusPosition;
        initPage();
        if (auditType.equals("")&&auditStatus.equals("")&&auditStatus.equals("")) {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.greyTextColor));
            binding.ivSelect.setImageResource(R.drawable.iv_approval_sel_type);
        }else {
            binding.tvSelect.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.ivSelect.setImageResource(R.drawable.iv_approval_sel_type_blue);

        }
        ApprovalBean data = viewModel.getData(1, 10, divideId, divideName, auditType, auditSubType, auditStatus);

        loadPagingData(data,tabId);
//        viewModel.refresh();
    }
    /*
     * 园区参数
     * */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
       initPage();
       divideId=orgModel.getId();
       divideName=orgModel.getName();
       binding.tvDivide.setText(divideName);
       binding.ivDivide.setImageResource(R.drawable.iv_approval_sel_type_blue);
       binding.tvDivide.setTextColor(getResources().getColor(R.color.blueTextColor));
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
                .withString(RouteKey.KEY_PRO_INS_ID,data.getProInsId())
                .withString(RouteKey.KEY_TASK_ID,data.getTaskId())
                .withInt(RouteKey.KEY_TAB_ID,tabId)
                .withString(RouteKey.KEY_APPROVAL_USER_STATE,data.getUserAuditStatus())
                .navigation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initPage();
        viewModel.isFirst=true;
    }
}
