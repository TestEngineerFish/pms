package com.einyun.app.pms.complain.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.library.resource.workorder.model.ApplyType;
import com.einyun.app.library.resource.workorder.model.ComplainOrderState;
import com.einyun.app.library.resource.workorder.model.ExtensionApplication;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.CustomerComplainModelBean;
import com.einyun.app.library.workorder.model.HandleListModel;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ActivityComplainDetailBinding;
import com.einyun.app.pms.complain.viewmodel.DetailViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
public class ComplainDetailActivity extends BaseHeadViewModelActivity<ActivityComplainDetailBinding, DetailViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    CustomerComplainModelBean detail;
    ExtensionApplication applyExtApplication;
    ExtensionApplication closeExtApplication;
    List<CustomerComplainModelBean.InitDataBean.ComplainAppendBean> complainAppendList;
    List<HandleListModel> handleList;
    private String createTime;

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
        binding.setCallBack(this);
        fresh();
    }

    private void fresh() {
        viewModel.getComplainDetail(proInsId, taskId).observe(this, repairsDetailModel -> {
            this.detail = repairsDetailModel.getData().getCustomer_complain_model();

            if ("common_complain".equals(detail.getF_ts_property_id())) {
                binding.layoutReportComplainInfo.rbGeneral.setVisibility(View.VISIBLE);
                binding.layoutReportComplainInfo.rbWarning.setVisibility(View.GONE);
                binding.layoutReportComplainInfo.rbGeneral.setChecked(true);
            } else {
                binding.layoutReportComplainInfo.rbGeneral.setVisibility(View.GONE);
                binding.layoutReportComplainInfo.rbWarning.setVisibility(View.VISIBLE);
                binding.layoutReportComplainInfo.rbWarning.setChecked(true);
            }

            //处理时长
            createTime = detail.getF_ts_time();
            if (ComplainOrderState.CLOSED.getState().equals(detail.getF_state())) {
                if (StringUtil.isNullStr(detail.getF_close_time()))
                    binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detail.getF_ts_time(), detail.getF_close_time()));
            } else {
                binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detail.getF_ts_time()));
                runnable.run();
            }
            //延期
            applyExtApplication = repairsDetailModel.getExtApplication(ApplyType.POSTPONE.getState());
            if (applyExtApplication == null) {
                binding.layoutApplyLateInfo.getRoot().setVisibility(View.GONE);
            } else {
                binding.layoutApplyLateInfo.getRoot().setVisibility(View.VISIBLE);
                binding.setApplyExtApplication(applyExtApplication);
            }
            //闭单
            closeExtApplication = repairsDetailModel.getExtApplication(ApplyType.FORCECLOSE.getState());
            if (closeExtApplication == null) {
                binding.layoutApplyCloseInfo.getRoot().setVisibility(View.GONE);
            } else {
                binding.layoutApplyCloseInfo.getRoot().setVisibility(View.VISIBLE);
                binding.setCloseExtApplication(closeExtApplication);
            }
            //处理历史
            handleList = repairsDetailModel.getHandleList();
            handleList();

            //追加投诉信息
            complainAppendList = repairsDetailModel.getData().getCustomer_complain_model().getSub_complain_append();
            complainAppendList();

            //隐藏展示
            String value = detail.getF_state();
            if (value.equals(ComplainOrderState.ADD.getState()) || value.equals(ComplainOrderState.RESPONSE.getState())) {
                binding.layoutResponseInfo.getRoot().setVisibility(View.GONE);
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.VISIBLE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.VISIBLE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
            } else if (value.equals(ComplainOrderState.CLOSED.getState())) {
                binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.VISIBLE);
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.submit.setVisibility(View.GONE);
            } else if (value.equals(ComplainOrderState.DEALING.getState())) {
                binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.GONE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.VISIBLE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.VISIBLE);
                binding.submit.setVisibility(View.VISIBLE);
            } else if (value.equals(ComplainOrderState.RETURN_VISIT.getState())) {
                //待评价
                binding.layoutResponseInfo.getRoot().setVisibility(View.VISIBLE);
                binding.layoutAlreadyComplainEvaluate.getRoot().setVisibility(View.GONE);
                binding.complainEvaluate.getRoot().setVisibility(View.VISIBLE);
                binding.layoutComplainResponse.getRoot().setVisibility(View.GONE);
                binding.layoutApplyCloseBtn.getRoot().setVisibility(View.GONE);
                binding.layoutComplainDeadline.getRoot().setVisibility(View.GONE);
                binding.submit.setVisibility(View.VISIBLE);
            }

            binding.setComplain(detail);
            setImageList(binding.layoutReportComplainInfo.rvPhoto, detail.getF_ts_attachment());
        });
    }

    /**
     * 处理历史
     */
    private void handleList() {
        if (handleList != null && handleList.size() != 0) {
            binding.layoutComplainHistory.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutComplainHistory.getRoot().setVisibility(View.GONE);
        }
    }

    private void complainAppendList(){
        if (complainAppendList != null && complainAppendList.size() != 0) {
            binding.layoutAddComplainInfo.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutAddComplainInfo.getRoot().setVisibility(View.GONE);
        }
    }

    private void setImageList(RecyclerView view, String files) {
        if (files != null) {
            PhotoListAdapter adapter = new PhotoListAdapter(this);
            view.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            view.addItemDecoration(new SpacesItemDecoration(18));
            view.setAdapter(adapter);
            PicUrlModelConvert convert = new PicUrlModelConvert();
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(files);
            adapter.updateList(modelList);
        }
    }


    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
//        ARouter.getInstance()
//                .build(RouterUtils.ACTIVITY_HISTORY)
//                .withString(RouteKey.KEY_ORDER_ID, id)
//                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                .navigation();
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
}
