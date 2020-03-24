package com.einyun.app.pms.complain.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.WorkOrder;
import com.einyun.app.common.databinding.ItemFeedbackHistoryLayoutBinding;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.WorkOrderType;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.ClickProxy;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.ComplainNodeIdState;
import com.einyun.app.library.resource.workorder.model.ComplainOrderState;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.library.workorder.model.ComplainAppendBean;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.CustomerComplainModelBean;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.library.workorder.net.request.ComplainDetailCompleteRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.complain.BR;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ActivityComplainDetailBinding;
import com.einyun.app.pms.complain.viewmodel.DetailViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;

@Route(path = RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
public class ComplainDetailActivity extends BaseHeadViewModelActivity<ActivityComplainDetailBinding, DetailViewModel> implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.ID)
    String id;
    @Autowired(name = RouteKey.KEY_FRAGEMNT_TAG)
    String fragmentKey;
    CustomerComplainModelBean detail;
    RepairsDetailModel.DelayInfoBean applyExtApplication;
    RepairsDetailModel.ForceCloseInfoBean closeExtApplication;
    List<ComplainAppendBean> complainAppendList;
    List<RepairsDetailModel.HandleListBean> handleList;
    private String createTime;
    ComplainDetailCompleteRequest request = new ComplainDetailCompleteRequest();
    private List<DictDataModel> dictComplainNatureList = new ArrayList<>();
    private List<TypeAndLine> lines = new ArrayList<>();
    private RVBindingAdapter<ItemFeedbackHistoryLayoutBinding, RepairsDetailModel.HandleListBean> adapter;
    private RVBindingAdapter<ItemFeedbackHistoryLayoutBinding, ComplainAppendBean> addAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_complain_detail;
    }

    @Override
    protected DetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(DetailViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_complain);
        setRightOption(R.drawable.histroy);
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
        binding.setCallBack(this);
        binding.submit.setOnClickListener(new ClickProxy(this));
        binding.save.setOnClickListener(new ClickProxy(this));
        binding.layoutApplyLateInfo.sendOrderPostponePicList.addItemDecoration(new SpacesItemDecoration(18));
        binding.layoutReportComplainInfo.rvPhoto.addItemDecoration(new SpacesItemDecoration(18));
        binding.layoutApplyCloseInfo.sendOrderClosePicList.addItemDecoration(new SpacesItemDecoration(18));
        binding.layoutReportComplainInfo.llComplainType2.setOnClickListener(new ClickProxy(this));
        binding.layoutReportComplainInfo.llComplainNature2.setOnClickListener(new ClickProxy(this));
        viewModel.isClosedLiveData.observe(this, isClosedState -> {
            if (isClosedState.isClosed()) {
                if (isClosedState.getType().equals(WorkOrder.POSTPONED_COMPLAIN)) {
                    navigatApply(RouterUtils.ACTIVITY_LATE);//强制关闭
                } else if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_COMPLAIN)) {
                    this.isCloseClose = isClosedState.isClosed();
                }
            } else {
                if (isClosedState.getType().equals(WorkOrder.FORCE_CLOSE_COMPLAIN)) {
                    this.isCloseClose = isClosedState.isClosed();
                    binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                    binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                    binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
                    binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                    binding.save.setVisibility(View.GONE);
                    binding.submit.setVisibility(View.GONE);
                } else {
                    ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
                }

            }
        });
        binding.sendOrder.repairSelectPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPeple();
            }
        });
        //选择人员
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.sendOrder.repairSelectedPepple.setText(model.getFullname());
            request.getBizData().setF_pd_assignor(model.getFullname());
            request.getBizData().setF_pd_assignor_id(model.getId());
        });
        fresh();
    }

    //false为不可以闭单  true为可以闭单
    private boolean isCloseClose = false;

    private void navigatApply(String activityPatrolForceClose) {
        ARouter.getInstance().build(activityPatrolForceClose).withString(RouteKey.KEY_ORDER_ID, id)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_CUSTOMER_COMPLAIN)
                .withString(RouteKey.KEY_DIVIDE_ID, detail.getF_ts_dk_id())
                .withString(RouteKey.KEY_DIVIDE_NAME, detail.getF_ts_dk())
                .withString(RouteKey.KEY_MID_URL, RouteKey.KEY_MID_URL_COMPLAIN)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .navigation();
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    private void fresh() {
        viewModel.getComplainDetail(proInsId, taskId).observe(this, repairsDetailModel -> {
            updateUI(repairsDetailModel);
        });
    }
    String nodeId = "";
    /**
     * 刷新UI
     *
     * @param repairsDetailModel
     */
    private void updateUI(RepairsDetailModel repairsDetailModel) {
        if (repairsDetailModel == null) {
            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        this.detail = repairsDetailModel.getData().getCustomer_complain_model();
        nodeId = repairsDetailModel.getInfo() == null?"":repairsDetailModel.getInfo().getNodeId();
        updatePageUIState(PageUIState.FILLDATA.getState());
        updateElapsedTime();
        //延期
        updatePostponeUI(repairsDetailModel);
        //闭单
        upateForceCloseUI(repairsDetailModel);
        //处理历史
        handleList = repairsDetailModel.getHandleList();
        handleList();

        //追加投诉信息
        complainAppendList = repairsDetailModel.getData().getCustomer_complain_model().getSub_complain_append();
        complainAppendList();
        //隐藏展示
        String value = detail.getF_state();
        setStatus(value, nodeId);

        binding.layoutApplyCloseBtn.llApplyLate.setOnClickListener(this);
        binding.layoutApplyCloseBtn.llClose.setOnClickListener(new ClickProxy(this));
        binding.complainEvaluate.radiogroup.setOnCheckedChangeListener(this);
        binding.setComplain(detail);
        setImageList(binding.layoutReportComplainInfo.rvPhoto, detail.getF_ts_attachment());
        showDetailFollow();
        IsClosedRequest request = new IsClosedRequest();
        request.setId(id);
        request.setType(WorkOrder.FORCE_CLOSE_COMPLAIN);
        viewModel.isClosed(request);
    }

    private void upateForceCloseUI(RepairsDetailModel repairsDetailModel) {
        closeExtApplication = repairsDetailModel.getForceCloseInfo();
        if (closeExtApplication == null) {
            binding.layoutApplyCloseInfo.getRoot().setVisibility(View.GONE);
        } else {
            binding.layoutApplyCloseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.setCloseExtApplication(closeExtApplication);
            setImageList(binding.layoutApplyCloseInfo.sendOrderClosePicList, closeExtApplication.getAttachment());
        }
    }

    private void updatePostponeUI(RepairsDetailModel repairsDetailModel) {
        applyExtApplication = repairsDetailModel.getDelayInfo();
        if (applyExtApplication == null) {
            binding.layoutApplyLateInfo.getRoot().setVisibility(View.GONE);
        } else {
            binding.layoutApplyLateInfo.getRoot().setVisibility(View.VISIBLE);
            binding.setApplyExtApplication(applyExtApplication);
            setImageList(binding.layoutApplyLateInfo.sendOrderPostponePicList, applyExtApplication.getAttachment());
        }
    }

    private void updateElapsedTime() {
        //处理时长
        createTime = detail.getF_ts_time();
        if (ComplainOrderState.CLOSED.getState().equals(detail.getF_state())) {
            if (StringUtil.isNullStr(detail.getF_close_time()))
                binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detail.getF_ts_time(), detail.getF_close_time()));
        } else {
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detail.getF_ts_time()));
            runnable.run();
        }
    }

    /**
     * 选择指派人
     */
    private void selectPeple() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, detail.getF_ts_dk_id())
                .withString(RouteKey.KEY_PROJECT_ID, detail.getU_project_id())
                .navigation();
    }


    private void setStatus(String value, String nodeId) {
        //只有待跟进才能操作   其余无法操作
        if (FRAGMENT_REPAIR_WAIT_FOLLOW.equals(fragmentKey)) {
            if (ComplainNodeIdState.Confirm.getState().equals(nodeId)) {
                binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.VISIBLE);
                binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
                binding.sendOrderInfo.getRoot().setVisibility(View.GONE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.save.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
                //获取投诉性质
                viewModel.getByTypeKey(Constants.COMPLAIN_WAY).observe(this, dictDataModels -> {
                    dictComplainNatureList = dictDataModels;
                });
                viewModel.typeAndLineList().observe(this, lines -> {
                    this.lines = lines;
                });
                request.getBizData().setF_ts_property_id(detail.getF_ts_property_id());
                request.getBizData().setF_ts_property(detail.getF_ts_property());
                request.getBizData().setF_ts_cate(detail.getF_ts_cate());
                request.getBizData().setF_ts_cate_id(detail.getF_ts_cate_id());
                request.getBizData().setF_line_key(detail.getF_line_key());
                request.getBizData().setF_line_name(detail.getF_line_name());
                binding.layoutReportComplainInfo.setRequest(request);
            } else if (ComplainNodeIdState.Response.getState().equals(nodeId)) {
                binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.VISIBLE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.VISIBLE);
                showOrHide();
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.save.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
                //获取投诉性质
                viewModel.getByTypeKey(Constants.COMPLAIN_WAY).observe(this, dictDataModels -> {
                    dictComplainNatureList = dictDataModels;
                });
                viewModel.typeAndLineList().observe(this, lines -> {
                    this.lines = lines;
                });
                request.getBizData().setF_ts_property_id(detail.getF_ts_property_id());
                request.getBizData().setF_ts_property(detail.getF_ts_property());
                request.getBizData().setF_ts_cate(detail.getF_ts_cate());
                request.getBizData().setF_ts_cate_id(detail.getF_ts_cate_id());
                request.getBizData().setF_line_key(detail.getF_line_key());
                request.getBizData().setF_line_name(detail.getF_line_name());
                binding.layoutReportComplainInfo.setRequest(request);
            }  else if (value.equals(ComplainOrderState.CLOSED.getState())) {
                if (StringUtil.isNullStr(detail.getF_response_time())){
                    binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
                }else{
                    binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
                }
                if (StringUtil.isNullStr(detail.getF_return_time())){
                    binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.VISIBLE);
                }else{
                    binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                }
                showOrHide();
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.save.setVisibility(View.GONE);
                binding.submit.setVisibility(View.GONE);
                binding.layoutAlreadyComplainEvaluate.rbAttr.setStar(detail.getF_return_score() == null ? 0 : detail.getF_return_score());
                binding.layoutAlreadyComplainEvaluate.rbQuality.setStar(detail.getService_quality_score() == null ? 0 : detail.getService_quality_score());
            } else if (value.equals(ComplainOrderState.DEALING.getState())) {
                if (StringUtil.isNullStr(detail.getF_response_time())){
                    binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
                }else{
                    binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
                }
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                showOrHide();
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.VISIBLE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.VISIBLE);
                binding.save.setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.VISIBLE);
            } else if (value.equals(ComplainOrderState.RETURN_VISIT.getState())) {
                //待评价
                if (StringUtil.isNullStr(detail.getF_response_time())){
                    binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
                }else{
                    binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
                }
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.VISIBLE);
                showOrHide();
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.save.setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
            }
            if (ComplainNodeIdState.Confirm.getState().equals(nodeId)||ComplainNodeIdState.Response.getState().equals(nodeId)) {
                binding.layoutReportComplainInfo.llComplainType1.setVisibility(View.GONE);
                binding.layoutReportComplainInfo.llComplainNature1.setVisibility(View.GONE);
                binding.layoutReportComplainInfo.llComplainType2.setVisibility(View.VISIBLE);
                binding.layoutReportComplainInfo.llComplainNature2.setVisibility(View.VISIBLE);
            } else {
                binding.layoutReportComplainInfo.llComplainType1.setVisibility(View.VISIBLE);
                binding.layoutReportComplainInfo.llComplainNature1.setVisibility(View.VISIBLE);
                binding.layoutReportComplainInfo.llComplainType2.setVisibility(View.GONE);
                binding.layoutReportComplainInfo.llComplainNature2.setVisibility(View.GONE);
            }
        } else {
            binding.layoutReportComplainInfo.llComplainType1.setVisibility(View.VISIBLE);
            binding.layoutReportComplainInfo.llComplainNature1.setVisibility(View.VISIBLE);
            binding.layoutReportComplainInfo.llComplainType2.setVisibility(View.GONE);
            binding.layoutReportComplainInfo.llComplainNature2.setVisibility(View.GONE);
            if (StringUtil.isNullStr(detail.getF_response_time())){
                binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
            }else{
                binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
            }
            binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
            binding.complainEvaluate.getRoot().setVisibility(View.GONE);
            showOrHide();
            binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
            binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
            binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
            binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
            binding.save.setVisibility(View.GONE);
            binding.submit.setVisibility(View.GONE);
            binding.layoutAlreadyComplainEvaluate.rbAttr.setStar(detail.getF_return_score() == null ? 0 : detail.getF_return_score());
            binding.layoutAlreadyComplainEvaluate.rbQuality.setStar(detail.getService_quality_score() == null ? 0 : detail.getService_quality_score());
            if (value.equals(ComplainOrderState.CLOSED.getState())) {
                if (StringUtil.isNullStr(detail.getF_return_time())){
                    binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.VISIBLE);
                }else{
                    binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                }
            }
            if (value.equals(ComplainOrderState.ADD.getState()) || value.equals(ComplainOrderState.RESPONSE.getState())) {
                binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
            }
        }
    }

    private void showOrHide(){
        if (StringUtil.isNullStr(detail.getF_pd_time())){
            binding.sendOrder.getRoot().setVisibility(View.GONE);
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
        }else{
            binding.sendOrder.getRoot().setVisibility(View.GONE);
            binding.sendOrderInfo.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 处理历史
     */
    private void handleList() {
        if (handleList != null && handleList.size() != 0) {
            binding.layoutComplainHistory.getRoot().setVisibility(View.VISIBLE);
            initHistoryList();
        } else {
            binding.layoutComplainHistory.getRoot().setVisibility(View.GONE);
        }
    }

    private void initHistoryList() {
        //一级列表适配器
        adapter = new RVBindingAdapter<ItemFeedbackHistoryLayoutBinding, RepairsDetailModel.HandleListBean>(this, com.einyun.app.common.BR.history) {
            @Override
            public void onBindItem(ItemFeedbackHistoryLayoutBinding binding, RepairsDetailModel.HandleListBean model, int position) {
                if (position == 0) {
                    binding.ivFirst.setVisibility(View.INVISIBLE);
                } else {
                    binding.ivFirst.setVisibility(View.VISIBLE);
                }
                if (position == adapter.getDataList().size() - 1) {
                    binding.itemHistroyImg.setVisibility(View.INVISIBLE);
                } else {
                    binding.itemHistroyImg.setVisibility(View.VISIBLE);
                }
                binding.tvContent.setText(model.getHandle_result());
                binding.tvName.setText(model.getHandle_user());
                binding.tvTime.setText(TimeUtil.getAllTime(model.getHandle_time()));
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_feedback_history_layout;
            }

        };
        binding.layoutComplainHistory.rlLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutComplainHistory.rlLoadMore.setVisibility(View.GONE);
                adapter.setDataList(handleList);
            }
        });
        binding.layoutComplainHistory.listHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.layoutComplainHistory.listHistory.setAdapter(adapter);
        List<RepairsDetailModel.HandleListBean> handleListThreeData = new ArrayList<>();
        if (handleList.size() > 3) {//只展示三条
            binding.layoutComplainHistory.rlLoadMore.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                handleListThreeData.add(handleList.get(i));
            }
            adapter.setDataList(handleListThreeData);
        } else {
            binding.layoutComplainHistory.rlLoadMore.setVisibility(View.GONE);
            adapter.setDataList(handleList);
        }
        binding.layoutComplainHistory.rlLoadMore.setOnClickListener(view -> {
            adapter.setDataList(handleList);
            binding.layoutComplainHistory.rlLoadMore.setVisibility(View.GONE);
        });

    }


    private void complainAppendList() {
        if (complainAppendList != null && complainAppendList.size() != 0) {
            binding.layoutAddComplainInfo.getRoot().setVisibility(View.VISIBLE);
            initAddComplainInfo();
        } else {
            binding.layoutAddComplainInfo.getRoot().setVisibility(View.GONE);
        }
    }

    private void initAddComplainInfo() {
        //一级列表适配器
        addAdapter = new RVBindingAdapter<ItemFeedbackHistoryLayoutBinding, ComplainAppendBean>(this, com.einyun.app.common.BR.history) {
            @Override
            public void onBindItem(ItemFeedbackHistoryLayoutBinding binding, ComplainAppendBean model, int position) {
                if (position == 0) {
                    binding.ivFirst.setVisibility(View.INVISIBLE);
                } else {
                    binding.ivFirst.setVisibility(View.VISIBLE);
                }
                if (position == addAdapter.getDataList().size() - 1) {
                    binding.itemHistroyImg.setVisibility(View.INVISIBLE);
                } else {
                    binding.itemHistroyImg.setVisibility(View.VISIBLE);
                }
                binding.tvContent.setText(model.getF_ac_content());
                binding.tvName.setText(model.getF_ac_user());
                binding.tvTime.setText(model.getF_ac_time());
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_feedback_history_layout;
            }

        };
        binding.layoutAddComplainInfo.rlLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutAddComplainInfo.rlLoadMore.setVisibility(View.GONE);
                addAdapter.setDataList(complainAppendList);
            }
        });
        binding.layoutAddComplainInfo.listHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.layoutAddComplainInfo.listHistory.setAdapter(addAdapter);
        List<ComplainAppendBean> complainAppendListThreeData = new ArrayList<>();
        if (complainAppendList.size() > 3) {//只展示三条
            binding.layoutAddComplainInfo.rlLoadMore.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                complainAppendListThreeData.add(complainAppendList.get(i));
            }
            addAdapter.setDataList(complainAppendListThreeData);
        } else {
            binding.layoutAddComplainInfo.rlLoadMore.setVisibility(View.GONE);
            addAdapter.setDataList(complainAppendList);
        }
        binding.layoutAddComplainInfo.rlLoadMore.setOnClickListener(view -> {
            addAdapter.setDataList(complainAppendList);
            binding.layoutAddComplainInfo.rlLoadMore.setVisibility(View.GONE);
        });

    }

    private void setImageList(RecyclerView view, String files) {
        if (files != null) {
            PhotoListAdapter adapter = new PhotoListAdapter(this);
            view.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            view.setAdapter(adapter);
            PicUrlModelConvert convert = new PicUrlModelConvert();
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(files);
            adapter.updateList(modelList);
        }
    }

    /**
     * 只有待跟进才能操作   其余无法操作
     */
    private void showDetailFollow() {
        if (!FRAGMENT_REPAIR_WAIT_FOLLOW.equals(fragmentKey)) {
            binding.layoutReportComplainInfo.llComplainType1.setVisibility(View.VISIBLE);
            binding.layoutReportComplainInfo.llComplainNature1.setVisibility(View.VISIBLE);
            binding.layoutReportComplainInfo.llComplainType2.setVisibility(View.GONE);
            binding.layoutReportComplainInfo.llComplainNature2.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fresh();
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_ORDER_ID, detail.getId_())
                .withString(RouteKey.KEY_PRO_INS_ID, detail.getProc_inst_id_())
                .navigation();
    }

    //立即调用方法
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            //计算时间
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime));
        }
    };

    private void submit() {
        request.getDoNextParam().setTaskId(taskId);
        String value = detail.getF_state();
        if (nodeId.equals(ComplainNodeIdState.Confirm.getState())) {
            //判断指派人
            if (!StringUtil.isNullStr(request.getBizData().getF_pd_assignor_id())) {
                ToastUtil.show(this, "请选择指派人");
                return;
            }
            request.getBizData().setF_pd_remark(binding.sendOrder.repairSendReason.getString());
        } else if (nodeId.equals(ComplainNodeIdState.Response.getState())) {
            String reasonString = binding.layoutComplainResponse.ltResponseReason.getString();
            if (!StringUtil.isNullStr(reasonString)) {
                ToastUtil.show(this, "请输入沟通结果");
                return;
            }
            request.getBizData().setF_response_result(reasonString);
        } else if (value.equals(ComplainOrderState.DEALING.getState())) {
            String reasonString = binding.layoutComplainDeadline.ltResponseReason.getString();
            if (!StringUtil.isNullStr(reasonString)) {
                ToastUtil.show(this, "请输入处理结果");
                return;
            }
            request.getBizData().setF_handle_result(reasonString);
        } else if (value.equals(ComplainOrderState.RETURN_VISIT.getState())) {
            if (request.getBizData().getC_is_solve() == null) {
                request.getBizData().setC_is_solve(1);
            }
            if (request.getBizData().getC_is_solve() == 0) {
                if (!StringUtil.isNullStr(binding.complainEvaluate.ltExplain.getString())) {
                    ToastUtil.show(this, "请输入说明内容");
                    return;
                }
                request.getBizData().setF_return_result(binding.complainEvaluate.ltExplain.getString());
            }
            request.getBizData().setF_return_score((int) binding.complainEvaluate.rbFirst.getSelectedStarts());
            request.getBizData().setService_quality_score((int) binding.complainEvaluate.rbSecond.getSelectedStarts());
            request.getBizData().setService_attitude_content(binding.complainEvaluate.ltReasonAttitude.getString());
            request.getBizData().setService_quality_content(binding.complainEvaluate.ltNoReasonQuality.getString());
        }
        viewModel.complainDetailComplete(request).observe(this, aBoolean -> {
            if (aBoolean) {
                ToastUtil.show(this, "工单提交成功");
                finish();
            }
        });
    }

    private void save() {
        request.setID_(id);
        String reasonString = binding.layoutComplainDeadline.ltResponseReason.getString();
        if (!StringUtil.isNullStr(reasonString)) {
            ToastUtil.show(this, "请输入处理结果");
            return;
        }
        request.getBizData().setF_handle_result(reasonString);
        request.getBizData().setSub_complain_append(detail.getSub_complain_append());
        viewModel.complainDetailSave(request).observe(this, aBoolean -> {
            if (aBoolean) {
                binding.layoutComplainDeadline.ltResponseReason.setText("");
                fresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            submit();
        }
        if (v.getId() == R.id.save) {
            save();
        }
        if (v.getId() == R.id.ll_complain_type_2) {
            complainType();
        }
        if (v.getId() == R.id.ll_complain_nature_2) {
            complainNature();
        }
        if (v.getId() == R.id.ll_apply_late) {
            IsClosedRequest request = new IsClosedRequest();
            request.setId(id);
            request.setType(WorkOrder.POSTPONED_COMPLAIN);
            viewModel.isClosed(request, true);
        }
        if (v.getId() == R.id.ll_close) {
            if (isCloseClose) {
                navigatApply(RouterUtils.ACTIVITY_CLOSE);//强制关闭
            } else {
                ToastUtil.show(CommonApplication.getInstance(), R.string.text_applying_wait);
            }
        }
    }


    int cnDefaultPos = 0;

    /**
     * 投诉性质
     */
    private void complainNature() {
        if (dictComplainNatureList.size() == 0) {
            ToastUtil.show(this, "暂无投诉性质");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictComplainNatureList) {
            txStrList.add(data.getName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, cnDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                cnDefaultPos = position;
                //获取条线id
                for (DictDataModel childBean : dictComplainNatureList) {
                    if (childBean.getName().equals(txStrList.get(position))) {
                        request.getBizData().setF_ts_property(childBean.getName());
                        request.getBizData().setF_ts_property_id(childBean.getKey());
                    }
                }
                binding.layoutReportComplainInfo.setRequest(request);
            }
        });
    }

    int ctDefaultPos = 0;

    /**
     * 投诉类别
     */
    private void complainType() {
        if (lines.size() == 0) {
            ToastUtil.show(this, "暂无投诉类别");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (TypeAndLine line : lines) {
            txStrList.add(line.getDataName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, ctDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                ctDefaultPos = position;
                for (TypeAndLine line : lines) {
                    if (line.getDataName().equals(txStrList.get(position))) {
                        request.getBizData().setF_ts_cate_id(line.getDataKey());
                        request.getBizData().setF_ts_cate(line.getDataName());
                        request.getBizData().setF_line_key(line.getMajorLine().getKey());
                        request.getBizData().setF_line_name(line.getMajorLine().getName());
                    }
                }
                binding.layoutReportComplainInfo.setRequest(request);
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_solve || checkedId == R.id.tv_solve) {
            request.getBizData().setC_is_solve(1);
            binding.complainEvaluate.llExplain.setVisibility(View.GONE);
            binding.complainEvaluate.llExplainSecond.setVisibility(View.GONE);
            binding.complainEvaluate.vExplain.setVisibility(View.GONE);
        }
        if (checkedId == R.id.rb_un_solve || checkedId == R.id.tv_un_solve) {
            request.getBizData().setC_is_solve(0);
            binding.complainEvaluate.llExplain.setVisibility(View.VISIBLE);
            binding.complainEvaluate.vExplain.setVisibility(View.VISIBLE);
            binding.complainEvaluate.llExplainSecond.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
