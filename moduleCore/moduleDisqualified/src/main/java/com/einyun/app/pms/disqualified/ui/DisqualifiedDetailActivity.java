package com.einyun.app.pms.disqualified.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.utils.NetWorkUtils;
import com.einyun.app.pms.disqualified.db.UnQualityFeedBackRequest;
import com.einyun.app.pms.disqualified.db.UnQualityVerificationRequest;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.SelectType;
import com.einyun.app.pms.disqualified.constants.DisqualifiedDataKey;
import com.einyun.app.pms.disqualified.databinding.ActivityDisqualifiedDetailBinding;
import com.einyun.app.pms.disqualified.model.DisqualifiedDetailModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedFragmentViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_ORDER_LIST;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
public class DisqualifiedDetailActivity extends BaseHeadViewModelActivity<ActivityDisqualifiedDetailBinding, DisqualifiedFragmentViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String mTaskId;
    @Autowired(name = RouteKey.KEY_ID)
    String ID;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String mProInstId;
    @Autowired(name = RouteKey.FRAGMENT_TAG)
    String fragmenTag;
    private PhotoSelectAdapter photoSelectAdapter;
    private PhotoSelectAdapter photoValidationSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    private PhotoListAdapter photoOrderInfoAdapter;
    private PhotoListAdapter photoFeedBackInfoAdapter;
    private PhotoListAdapter photoValidationInfoAdapter;
    private UnQualityFeedBackRequest mFeedBackRequest;
    private UnQualityVerificationRequest mValidateRequest;
    private String format;
    private String code;
    private DisqualifiedDetailModel mDetailModel;
    private DisqualifiedDetailModel.DataBean.UnqualifiedModelBean mUnqualified_model;

    @Override
    protected DisqualifiedFragmentViewModel initViewModel() {
        return new ViewModelProvider(this, new DisqualifiedViewModelFactory()).get(DisqualifiedFragmentViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_disqualified_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_disqualified_order);
        setRightOption(R.drawable.histroy);
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
        selectPng();
        selectValidationPng();
        binding.setCallBack(this);
        mFeedBackRequest = new UnQualityFeedBackRequest();
        mValidateRequest = new UnQualityVerificationRequest();
        /**
         * ????????????
         */
        photoOrderInfoAdapter = new PhotoListAdapter(this);
        binding.listPicOrderInfo.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicOrderInfo.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicOrderInfo.setAdapter(photoOrderInfoAdapter);
        /**
         * ????????????
         */
        photoFeedBackInfoAdapter = new PhotoListAdapter(this);
        binding.listPicFeedback.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicFeedback.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicFeedback.setAdapter(photoFeedBackInfoAdapter);
        /**
         * ????????????
         */
        photoValidationInfoAdapter = new PhotoListAdapter(this);
        binding.listPicInvalition.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicInvalition.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicInvalition.setAdapter(photoValidationInfoAdapter);
        binding.rlOldCode.setOnClickListener(view -> {
            if ("1".equals(mDetailModel.getData().getUnqualified_model().getOriginal_type())) {//1 ????????????
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, mDetailModel.getData().getUnqualified_model().getOriginal_prolnstld())
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_PLAN_OWRKORDER_DONE)
                        .navigation();
            } else if ("3".equals(mDetailModel.getData().getUnqualified_model().getOriginal_type())){
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
                        .withString(RouteKey.KEY_TASK_ID,"")
                        .withString(RouteKey.KEY_PRO_INS_ID, mDetailModel.getData().getUnqualified_model().getOriginal_prolnstld())
                        .withString(RouteKey.KEY_ID,"")
                        .withString(RouteKey.FRAGMENT_TAG,FRAGMENT_DISQUALIFIED_HAD_FOLLOW)
                        .navigation();
            }
            else {//????????????
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, mDetailModel.getData().getUnqualified_model().getOriginal_prolnstld())
                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .navigation();
            }
        });
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_ORDER_ID, mDetailModel.getData().getUnqualified_model().getId_())
                .withString(RouteKey.KEY_PRO_INS_ID, mDetailModel.getData().getUnqualified_model().getProc_inst_id_())
                .navigation();
    }

    @Override
    protected void initData() {
        super.initData();
        binding.tvOpFeedbackDate.setText(TimeUtil.getYMdTime(System.currentTimeMillis()));
        mFeedBackRequest.getBizData().setFeedback_date(TimeUtil.getYMdTime(System.currentTimeMillis()));
        binding.tvOpValidateDate.setText(TimeUtil.getYMdTime(System.currentTimeMillis()));
        mValidateRequest.getBizData().setVerification_date(TimeUtil.getYMdTime(System.currentTimeMillis()));
        switch (fragmenTag) {
            case FRAGMENT_DISQUALIFIED_WAIT_FOLLOW:
                /**
                 * ??????????????????
                 */
                viewModel.getTODODetailInfo(mTaskId).observe(this, module -> {
                    mDetailModel = module;
                    if (mDetailModel != null) {

                        mUnqualified_model = mDetailModel.getData().getUnqualified_model();
                        updateUI(module);
                    }
                });
                initCache();
                break;
            case FRAGMENT_DISQUALIFIED_HAD_FOLLOW:
            case FRAGMENT_DISQUALIFIED_ORDER_LIST:
                /**
                 * ??????????????????
                 */
                viewModel.getHaveDODetailInfo(mProInstId).observe(this, module -> {
                    mDetailModel = module;
                    updateUI(module);
                });
                break;
        }

        binding.tvOpValidatePerson.setText(viewModel.getUserName());
    }

    private void initCache() {
        viewModel.loadFeedBackRequest("f_" + mTaskId).observe(this, model -> {
            if (model == null) {
                return;
            }
            updateF_PhotoUI(model);
            binding.ltReason.setText(model.getBizData().getReason());
            binding.ltMeasures.setText(model.getBizData().getCorrective_action());
            binding.tvOpFeedbackDate.setText(model.getBizData().getFeedback_date());
            mFeedBackRequest.getBizData().setFeedback_date(model.getBizData().getFeedback_date());
            mFeedBackRequest.getBizData().setReason(binding.ltReason.getString());
            mFeedBackRequest.getBizData().setCorrective_action(binding.ltMeasures.getString());
            mFeedBackRequest.getDoNextParamt().setTaskId(mTaskId);
            initOrderinfo(model);
        });
        viewModel.loadVerificationRequest("v_" + mTaskId).observe(this, model -> {
            if (model == null) {
                return;
            }
            updateV_PhotoUI(model);
            binding.ltValidation.setText(model.getBizData().getVerification_situation());
            binding.tvOpValidateDate.setText(model.getBizData().getVerification_date());

            mValidateRequest.getBizData().setVerification_situation(binding.ltValidation.getString());
            mValidateRequest.getBizData().setIs_pass(1);
            mValidateRequest.getBizData().setVerification_date(model.getBizData().getVerification_date()
            );
            mValidateRequest.getDoNextParamt().setTaskId(mTaskId);
            initOrderinfo2(model);
        });
    }

    /**
     * ??????????????????
     */
    private void initOrderinfo(UnQualityFeedBackRequest model) {
        code = model.getBizData().getOrder_info_code();
        String status = model.getBizData().getOrder_info_state();
        switch (status) {
            case DisqualifiedDataKey.STATUS_CREATE_STEP://?????????
                if (!fragmenTag.equals(FRAGMENT_DISQUALIFIED_ORDER_LIST)) {
                    binding.cdOpFeedback.setVisibility(View.VISIBLE);
                }
                break;
            case DisqualifiedDataKey.STATUS_PROCESSING_STEP://?????????  ?????? ???????????? ????????????  ????????????
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                if (fragmenTag.equals(FRAGMENT_DISQUALIFIED_ORDER_LIST) || fragmenTag.equals(FRAGMENT_DISQUALIFIED_HAD_FOLLOW)) {
                } else {
                    binding.cdOpValidation.setVisibility(View.VISIBLE);
                }

                break;
            case DisqualifiedDataKey.STATUS_COMPLETED_STEP://????????? ?????? ???????????? ????????????  ????????????
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                binding.cdValidationInfo.setVisibility(View.VISIBLE);
                break;
        }
        String severity = model.getBizData().getOrder_info_serv();
        switch (severity) {
            case DisqualifiedDataKey.SEVERITY_HIGHT_LEVEL:
                binding.tvSeverity.setText("???");
                break;
            case DisqualifiedDataKey.SEVERITY_MIDDLE_LEVEL:
                binding.tvSeverity.setText("???");
                break;
            case DisqualifiedDataKey.SEVERITY_LOW_LEVEL:
                binding.tvSeverity.setText("???");
                break;
        }
        String line = model.getBizData().getOrder_info_line();
        switch (line) {
            case DisqualifiedDataKey.LINE_ENV://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_ENG://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_ORDER://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_CUSTOMER://??????
                binding.tvLine.setText("??????");
                break;
        }
//        binding.setModel(detailModule);
        binding.tvCode.setText(model.getBizData().getOrder_info_code());
        binding.tvCreateTime.setText(model.getBizData().getOrder_info_create_time());
        binding.tvDivide.setText(model.getBizData().getOrder_info_divide());
        binding.tvCheckDate.setText(model.getBizData().getOrder_info_check_date());
        binding.tvQueDesc.setText(model.getBizData().getOrder_info_qus_desc());
        binding.tvCorDate.setText(model.getBizData().getOrder_info_ver_date());
        binding.tvCheckedPerson.setText(model.getBizData().getOrder_info_checked_person());
        if ("createStep".equals(model.getBizData().getOrder_info_state())) {
            binding.tvDealState.setText(R.string.text_state_new);
            binding.tvDealState.setTextColor((getResources().getColor(R.color.repair_detail_evaluate_color)));
        } else if ("processingStep".equals(model.getBizData().getOrder_info_state())) {
            binding.tvDealState.setText(R.string.text_state_processing);
            binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
        } else if ("completedStep".equals(model.getBizData().getOrder_info_state())) {
            binding.tvDealState.setText(R.string.text_finished);
            binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
        }

        PicUrlModelConvert convert = new PicUrlModelConvert();

        List<PicUrlModel> modelList = convert.stringToSomeObjectList(model.getBizData().getOrder_info_enclosure());
        photoOrderInfoAdapter.updateList(modelList);

        List<PicUrlModel> modelFeedBackList = convert.stringToSomeObjectList(model.getBizData().getFed_info_enclosure());
        photoFeedBackInfoAdapter.updateList(modelFeedBackList);

//        List<PicUrlModel> modelValidateList = convert.stringToSomeObjectList(mo);
//        photoValidationInfoAdapter.updateList(modelValidateList);

    }

    /**
     * ??????????????????he????????????
     */
    private void initOrderinfo2(UnQualityVerificationRequest model) {
        code = model.getBizData().getOrder_info_code();
        String status = model.getBizData().getOrder_info_state();
        switch (status) {
            case DisqualifiedDataKey.STATUS_CREATE_STEP://?????????
                if (!fragmenTag.equals(FRAGMENT_DISQUALIFIED_ORDER_LIST)) {
                    binding.cdOpFeedback.setVisibility(View.VISIBLE);
                }
                break;
            case DisqualifiedDataKey.STATUS_PROCESSING_STEP://?????????  ?????? ???????????? ????????????  ????????????
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                if (fragmenTag.equals(FRAGMENT_DISQUALIFIED_ORDER_LIST) || fragmenTag.equals(FRAGMENT_DISQUALIFIED_HAD_FOLLOW)) {
                } else {
                    binding.cdOpValidation.setVisibility(View.VISIBLE);
                }

                break;
            case DisqualifiedDataKey.STATUS_COMPLETED_STEP://????????? ?????? ???????????? ????????????  ????????????
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                binding.cdValidationInfo.setVisibility(View.VISIBLE);
                break;
        }
        String severity = model.getBizData().getOrder_info_serv();
        switch (severity) {
            case DisqualifiedDataKey.SEVERITY_HIGHT_LEVEL:
                binding.tvSeverity.setText("???");
                break;
            case DisqualifiedDataKey.SEVERITY_MIDDLE_LEVEL:
                binding.tvSeverity.setText("???");
                break;
            case DisqualifiedDataKey.SEVERITY_LOW_LEVEL:
                binding.tvSeverity.setText("???");
                break;
        }
        String line = model.getBizData().getOrder_info_line();
        switch (line) {
            case DisqualifiedDataKey.LINE_ENV://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_ENG://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_ORDER://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_CUSTOMER://??????
                binding.tvLine.setText("??????");
                break;
        }
//        binding.setModel(detailModule);
        binding.tvCode.setText(model.getBizData().getOrder_info_code());
        binding.tvCreateTime.setText(model.getBizData().getOrder_info_create_time());
        binding.tvDivide.setText(model.getBizData().getOrder_info_divide());
        binding.tvCheckDate.setText(model.getBizData().getOrder_info_check_date());
        binding.tvQueDesc.setText(model.getBizData().getOrder_info_qus_desc());
        binding.tvCorDate.setText(model.getBizData().getOrder_info_ver_date());
        binding.tvCheckedPerson.setText(model.getBizData().getOrder_info_checked_person());


        binding.tvFeedbackDate.setText(model.getBizData().getFed_info_date());
        binding.tvCauseReason.setText(model.getBizData().getFed_info_reason());
        binding.tvPrevention.setText(model.getBizData().getFed_info_cor_action());
        binding.tvDealTime.setText(model.getBizData().getFed_info_del_time());

        if ("createStep".equals(model.getBizData().getOrder_info_state())) {
            binding.tvDealState.setText(R.string.text_state_new);
            binding.tvDealState.setTextColor((getResources().getColor(R.color.repair_detail_evaluate_color)));
        } else if ("processingStep".equals(model.getBizData().getOrder_info_state())) {
            binding.tvDealState.setText(R.string.text_state_processing);
            binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
        } else if ("completedStep".equals(model.getBizData().getOrder_info_state())) {
            binding.tvDealState.setText(R.string.text_finished);
            binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
        }

        PicUrlModelConvert convert = new PicUrlModelConvert();

        List<PicUrlModel> modelList = convert.stringToSomeObjectList(model.getBizData().getOrder_info_enclosure());
        photoOrderInfoAdapter.updateList(modelList);

        List<PicUrlModel> modelFeedBackList = convert.stringToSomeObjectList(model.getBizData().getFed_info_enclosure());
        photoFeedBackInfoAdapter.updateList(modelFeedBackList);

//        List<PicUrlModel> modelValidateList = convert.stringToSomeObjectList(mo);
//        photoValidationInfoAdapter.updateList(modelValidateList);

    }

    private void updateUI(DisqualifiedDetailModel detailModule) {
        if (detailModule == null) {
//            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        if (!detailModule.getData().getUnqualified_model().getOriginal_code().isEmpty()) {
            binding.rlOldCode.setVisibility(View.VISIBLE);
            binding.tvOldCode.setText(detailModule.getData().getUnqualified_model().getOriginal_code());
        }
//        updatePageUIState(PageUIState.FILLDATA.getState());
        code = detailModule.getData().getUnqualified_model().getCode();
        String status = detailModule.getData().getUnqualified_model().getStatus();
        switch (status) {
            case DisqualifiedDataKey.STATUS_CREATE_STEP://?????????
                if (!fragmenTag.equals(FRAGMENT_DISQUALIFIED_ORDER_LIST)) {
                    binding.cdOpFeedback.setVisibility(View.VISIBLE);
                }
                break;
            case DisqualifiedDataKey.STATUS_PROCESSING_STEP://?????????  ?????? ???????????? ????????????  ????????????
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                if (fragmenTag.equals(FRAGMENT_DISQUALIFIED_ORDER_LIST) || fragmenTag.equals(FRAGMENT_DISQUALIFIED_HAD_FOLLOW)) {
                } else {
                    binding.cdOpValidation.setVisibility(View.VISIBLE);
                }

                break;
            case DisqualifiedDataKey.STATUS_COMPLETED_STEP://????????? ?????? ???????????? ????????????  ????????????
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                binding.cdValidationInfo.setVisibility(View.VISIBLE);
                break;
        }
        String severity = detailModule.getData().getUnqualified_model().getSeverity();
        switch (severity) {
            case DisqualifiedDataKey.SEVERITY_HIGHT_LEVEL:
                binding.tvSeverity.setText("???");
                break;
            case DisqualifiedDataKey.SEVERITY_MIDDLE_LEVEL:
                binding.tvSeverity.setText("???");
                break;
            case DisqualifiedDataKey.SEVERITY_LOW_LEVEL:
                binding.tvSeverity.setText("???");
                break;
        }
        String line = detailModule.getData().getUnqualified_model().getLine();
        switch (line) {
            case DisqualifiedDataKey.LINE_ENV://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_ENG://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_ORDER://??????
                binding.tvLine.setText("??????");
                break;
            case DisqualifiedDataKey.LINE_CUSTOMER://??????
                binding.tvLine.setText("??????");
                break;
        }
        binding.setModel(detailModule);
        PicUrlModelConvert convert = new PicUrlModelConvert();

        List<PicUrlModel> modelList = convert.stringToSomeObjectList(detailModule.getData().getUnqualified_model().getCreate_enclosure());
        photoOrderInfoAdapter.updateList(modelList);

        List<PicUrlModel> modelFeedBackList = convert.stringToSomeObjectList(detailModule.getData().getUnqualified_model().getFeedback_enclosure());
        photoFeedBackInfoAdapter.updateList(modelFeedBackList);

        List<PicUrlModel> modelValidateList = convert.stringToSomeObjectList(detailModule.getData().getUnqualified_model().getVerification_enclosure());
        photoValidationInfoAdapter.updateList(modelValidateList);
    }

    /**
     * ????????????????????????
     */
    public void onFeedBackCacheClick() {
        cacheF_Photos(photoSelectAdapter.getSelectedPhotos());
//        if (binding.tvOpFeedbackDate.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
//        if (binding.ltReason.getString().isEmpty()) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
//        if (binding.ltMeasures.getString().isEmpty()) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
        mFeedBackRequest.getBizData().setReason(binding.ltReason.getString());
        mFeedBackRequest.getBizData().setFeedback_date(binding.tvOpFeedbackDate.getText().toString());
        mFeedBackRequest.getBizData().setCorrective_action(binding.ltMeasures.getString());
        mFeedBackRequest.getDoNextParamt().setTaskId(mTaskId);
        //????????????????????????
        if (mUnqualified_model != null) {
            mFeedBackRequest.getBizData().setOrder_info_state(mUnqualified_model.getStatus());
            mFeedBackRequest.getBizData().setOrder_info_code(mUnqualified_model.getCode());
            mFeedBackRequest.getBizData().setOrder_info_create_time(mUnqualified_model.getCreated_time());
            mFeedBackRequest.getBizData().setOrder_info_divide(mUnqualified_model.getDivide_name());
            mFeedBackRequest.getBizData().setOrder_info_check_date(mUnqualified_model.getCheck_date());
            mFeedBackRequest.getBizData().setOrder_info_qus_desc(mUnqualified_model.getProblem_description());
            mFeedBackRequest.getBizData().setOrder_info_line(mUnqualified_model.getLine());
            mFeedBackRequest.getBizData().setOrder_info_serv(mUnqualified_model.getSeverity());
            mFeedBackRequest.getBizData().setOrder_info_ver_date(mUnqualified_model.getVerification_date());
            mFeedBackRequest.getBizData().setOrder_info_checked_person(mUnqualified_model.getChecked_user_name());
            mFeedBackRequest.getBizData().setOrder_info_enclosure(mUnqualified_model.getCreate_enclosure());
        }
//        uploadFeedBackImages(mFeedBackRequest);
        String fCode = "f_" + mTaskId;
        viewModel.insertFeedBackRequest(fCode, mFeedBackRequest);
        ToastUtil.show(this, "????????????????????????");
        finish();
    }

    /**
     * ??????????????????
     */
    public void onFeedBackPassClick() {
        if (!NetWorkUtils.isNetworkConnected(DisqualifiedDetailActivity.this)) {
            ToastUtil.show(DisqualifiedDetailActivity.this, "???????????????????????????");
            return;
        }
        if (binding.tvOpFeedbackDate.getText().toString().equals("?????????") || binding.tvOpFeedbackDate.getText().toString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.ltReason.getString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.ltMeasures.getString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        mFeedBackRequest.getBizData().setReason(binding.ltReason.getString());
        mFeedBackRequest.getBizData().setCorrective_action(binding.ltMeasures.getString());
        mFeedBackRequest.getDoNextParamt().setTaskId(mTaskId);
        uploadFeedBackImages(mFeedBackRequest);
    }

    //????????????
    public void onFeedBackDateClick() {
        choosePayDate(SelectType.FEEDBACK);
    }

    //????????????
    public void onValidateDateClick() {
        choosePayDate(SelectType.VALIDATE);
    }

    //???????????????
    public void onCreateOrderClick() {

        new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                .setMsg("?????????????????????")
                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!NetWorkUtils.isNetworkConnected(DisqualifiedDetailActivity.this)) {
                            ToastUtil.show(DisqualifiedDetailActivity.this, "???????????????????????????");
                            return;
                        }
                        validateSubmit(true);

                    }
                }).show();


    }

    //??????????????????
    public void onValidationCacheClick() {
        cacheV_Photos(photoValidationSelectAdapter.getSelectedPhotos());
//        if (binding.tvOpValidateDate.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
//        if (binding.ltValidation.getString().isEmpty()) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
        mValidateRequest.getBizData().setVerification_situation(binding.ltValidation.getString());
        mValidateRequest.getBizData().setIs_pass(1);
        mValidateRequest.getBizData().setVerification_date(binding.tvOpValidateDate.getText().toString());
        mValidateRequest.getDoNextParamt().setTaskId(mTaskId);

        //????????????????????????
        if (mUnqualified_model != null) {
            mValidateRequest.getBizData().setOrder_info_state(mUnqualified_model.getStatus());
            mValidateRequest.getBizData().setOrder_info_code(mUnqualified_model.getCode());
            mValidateRequest.getBizData().setOrder_info_create_time(mUnqualified_model.getCreated_time());
            mValidateRequest.getBizData().setOrder_info_divide(mUnqualified_model.getDivide_name());
            mValidateRequest.getBizData().setOrder_info_check_date(mUnqualified_model.getCheck_date());
            mValidateRequest.getBizData().setOrder_info_qus_desc(mUnqualified_model.getProblem_description());
            mValidateRequest.getBizData().setOrder_info_line(mUnqualified_model.getLine());
            mValidateRequest.getBizData().setOrder_info_serv(mUnqualified_model.getSeverity());
            mValidateRequest.getBizData().setOrder_info_ver_date(mUnqualified_model.getVerification_date());
            mValidateRequest.getBizData().setOrder_info_checked_person(mUnqualified_model.getChecked_user_name());
            mValidateRequest.getBizData().setOrder_info_enclosure(mUnqualified_model.getCreate_enclosure());

            mValidateRequest.getBizData().setFed_info_date(mUnqualified_model.getFeedback_date());
            mValidateRequest.getBizData().setFed_info_reason(mUnqualified_model.getReason());
            mValidateRequest.getBizData().setFed_info_cor_action(mUnqualified_model.getCorrective_action());
            mValidateRequest.getBizData().setFed_info_del_time(mUnqualified_model.getFeedback_time());
            mValidateRequest.getBizData().setFed_info_enclosure(mUnqualified_model.getFeedback_enclosure());
        }

        viewModel.insertVerificationRequest("v_" + mTaskId, mValidateRequest);
        ToastUtil.show(this, "????????????????????????");
        finish();
    }

    //??????????????????
    public void onValidationPassClick() {
        if (!NetWorkUtils.isNetworkConnected(DisqualifiedDetailActivity.this)) {
            ToastUtil.show(DisqualifiedDetailActivity.this, "???????????????????????????");
            return;
        }
        validateSubmit(false);
    }

    private void validateSubmit(boolean isCreateNew) {
        if (binding.tvOpValidateDate.getText().toString().equals("?????????") || binding.tvOpValidateDate.getText().toString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.ltValidation.getString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        mValidateRequest.getBizData().setVerification_situation(binding.ltValidation.getString());
        mValidateRequest.getBizData().setIs_pass(1);
        mValidateRequest.getDoNextParamt().setTaskId(mTaskId);
        uploadValidateImages(mValidateRequest, isCreateNew);
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    /**
     * ??????????????????
     */
    private void uploadFeedBackImages(UnQualityFeedBackRequest request) {
        //??????????????????
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.dealFeedBack(request, data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.tv_feed_back_suc);
                        finish();
                        viewModel.deleteFeedBackRequest("f_" + mTaskId);
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    /**
     * ??????????????????
     */
    private void uploadValidateImages(UnQualityVerificationRequest request, boolean isCreateNew) {
        //??????????????????
        viewModel.uploadImages(photoValidationSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.dealValidate(request, data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        if (!isCreateNew) {
                            ToastUtil.show(getApplicationContext(), R.string.tv_validate_suc);
                            finish();
                            viewModel.deleteVerificationRequest("v_" + mTaskId);
                        } else {
//                            ToastUtil.show(getApplicationContext(), R.string.tv_validate_suc);
                            viewModel.deleteVerificationRequest("v_" + mTaskId);
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_PROPERTY_CREATE)
                                    .withString(RouteKey.F_ORIGINAL_TYPE, "3")
                                    .withString(RouteKey.KEY_ORDER_ID, mDetailModel.getData().getUnqualified_model().getId_())
                                    .withString(RouteKey.KEY_ORDER_NO, mDetailModel.getData().getUnqualified_model().getCode())
                                    .withString(RouteKey.KEY_PRO_INS_ID, mDetailModel.getData().getUnqualified_model().getProc_inst_id_())
                                    .withString(RouteKey.CODE, code)
                                    .withString(RouteKey.KEY_LINE_CODE, mDetailModel.getData().getUnqualified_model().getLine())
                                    .withString(RouteKey.KEY_DIVIDE_NAME, mDetailModel.getData().getUnqualified_model().getDivide_name())
                                    .withString(RouteKey.KEY_DIVIDE_ID, mDetailModel.getData().getUnqualified_model().getDivide_id())
                                    .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_DISQUALIFIED_HAD_FOLLOW)
                                    .navigation();
                            finish();
                        }
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoSelectAdapter.addPhotos(uris);
                cacheF_Photos(photoSelectAdapter.getSelectedPhotos());
            }
        } else if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK_SECOND) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoValidationSelectAdapter.addPhotos(uris);
                cacheV_Photos(photoValidationSelectAdapter.getSelectedPhotos());
            }
        }
    }

    /**
     * ????????????
     */
    private void choosePayDate(SelectType type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
        format = simpleDateFormat.format(System.currentTimeMillis());
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        String[] split = format.split(",");
        startDate.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]));//??????????????????
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 1, 1);//??????????????????
        //???????????????
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
                switch (type) {
                    case FEEDBACK:
                        binding.tvOpFeedbackDate.setText(dft.format(date));
                        mFeedBackRequest.getBizData().setFeedback_date(dft.format(date));

                        break;
                    case VALIDATE:
                        binding.tvOpValidateDate.setText(dft.format(date));
                        mValidateRequest.getBizData().setVerification_date(dft.format(date));
                        break;
                }

            }
        }).setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
                .setRangDate(startDate, endDate)
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .build();
        pvTime.show();
    }

    /**
     * ??????
     * ?????????????????????
     */
    private void selectPng() {
        //?????????????????????
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvFeedbackList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//????????????
        binding.rvFeedbackList.setAdapter(photoSelectAdapter);
        binding.rvFeedbackList.addItemDecoration(new SpacesItemDecoration(18));
        photoSelectAdapter.setAddListener(selectedSize -> {
            if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //??????????????????
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE - photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, this);
    }

    /**
     * ??????
     * ?????????????????????
     */
    private void selectValidationPng() {
        //?????????????????????
        photoValidationSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvValidationList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//????????????
        binding.rvValidationList.setAdapter(photoValidationSelectAdapter);
        binding.rvValidationList.addItemDecoration(new SpacesItemDecoration(18));
        photoValidationSelectAdapter.setAddListener(selectedSize -> {
            if (photoValidationSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //??????????????????
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE - photoValidationSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK_SECOND);
        }, this);
    }

    private void cacheF_Photos(List<Uri> uris) {
        viewModel.cacheFeedBackPhotos(uris, "f_" + mTaskId, mFeedBackRequest);
    }

    private void cacheV_Photos(List<Uri> uris) {
        viewModel.cacheVerifiPhotos(uris, "v_" + mTaskId, mValidateRequest);
    }

    private void updateF_PhotoUI(UnQualityFeedBackRequest mDbrequest) {
        List<Uri> uris = viewModel.loadCacheFeedbackPhotoUris(mDbrequest);
        if (uris.size() > 0) {
            photoSelectAdapter.addPhotos(uris);
        }
    }

    private void updateV_PhotoUI(UnQualityVerificationRequest mDbrequest) {
        List<Uri> uris = viewModel.loadCacheVerifiPhotoUris(mDbrequest);
        if (uris.size() > 0) {
            photoValidationSelectAdapter.addPhotos(uris);
        }
    }
}
