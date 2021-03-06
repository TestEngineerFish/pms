package com.einyun.app.pms.disqualified.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.SelectType;
import com.einyun.app.pms.disqualified.constants.DisqualifiedDataKey;
import com.einyun.app.pms.disqualified.databinding.ActivityCreateDisqualifiedOrderBinding;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedFragmentViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
import static com.einyun.app.pms.disqualified.SelectType.CHECK_DATE;
import static com.einyun.app.pms.disqualified.SelectType.DEADLINE;

@Route(path = RouterUtils.ACTIVITY_PROPERTY_CREATE)
public class CreateDisqualifiedActivity extends BaseHeadViewModelActivity<ActivityCreateDisqualifiedOrderBinding, DisqualifiedFragmentViewModel> implements PeriodizationView.OnPeriodSelectListener {
    int txDefaultPosLine = 0;
    int txDefaultPosSeverity = 0;
    private PhotoSelectAdapter photoSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    private List<DisqualifiedTypesBean> lineTypeLists;
    private List<DisqualifiedTypesBean> severityTypeLists;
    private String mOrderCode;
    private String orderCodeChange;
    public static final int HJ = 0;
    public static final int GC = 1;
    public static final int ZX = 2;
    public static final int KF = 3;
    private CreateUnQualityRequest mRequest;
    private String dimCode = "";
    private String divideId = "";
    private String format;
    @Autowired(name = RouteKey.KEY_MODEL_DATA)
    Serializable DbRequest;
    @Autowired(name = RouteKey.CODE)
    String mCode;
    private CreateUnQualityRequest mDbRequest;
    @Autowired(name = RouteKey.F_ORIGINAL_TYPE)
    String mORIGINAL_TYPE;//???????????????
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String id;//?????????id
    @Autowired(name = RouteKey.KEY_ORDER_NO)
    String orderNo;//????????????
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;//???????????????id
    @Autowired(name = RouteKey.KEY_FRAGEMNT_TAG)
    String fragmentTag;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)

    String mDivideId;
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String divideName;
    @Autowired(name = RouteKey.KEY_PROJECT)
    String projectName;
    @Autowired(name = RouteKey.KEY_LINE_ID)
    String lineId;
    @Autowired(name = RouteKey.KEY_LINE_CODE)
    String lineCode;
    @Autowired(name = RouteKey.KEY_LINE)
    String lineName;

    @Override
    protected DisqualifiedFragmentViewModel initViewModel() {
        return new ViewModelProvider(this, new DisqualifiedViewModelFactory()).get(DisqualifiedFragmentViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_disqualified_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_create_disqualified_order);
        selectPng();
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("onChanged", "onChanged: " + aBoolean);
            }
        });

        if (DbRequest != null) {
            mDbRequest = (CreateUnQualityRequest) DbRequest;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        viewModel.queryAduitType(DisqualifiedDataKey.LINE_TYPE_LIST).observe(this, model -> {
            lineTypeLists = model;
            if (StringUtil.isNullStr(orderNo)) {
                divideId = mDivideId;
                if (lineId == null) {
                    if (lineTypeLists!=null) {
                        for (DisqualifiedTypesBean lineTypeList : lineTypeLists) {
                            if (lineTypeList.getKey().equals(lineCode)) {
                                dimCode = lineTypeList.getId();
                                lineName = lineTypeList.getName();
                            }
                        }
                    }
                } else {
                    dimCode = lineId;
                }
                mRequest.getBizData().setDivide_id(divideId);
                mRequest.getBizData().setDivide_name(divideName);
                mRequest.getBizData().setLine(lineCode);
                mRequest.getBizData().setOriginal_prolnstld(proInsId);
                binding.tvDivide.setText(divideName);
                binding.tvLine.setText(lineName);
                binding.llOld.setVisibility(View.VISIBLE);
                binding.vLine.setVisibility(View.VISIBLE);
                binding.tvOldCode.setText(orderNo);
                if (StringUtil.isNullStr(orderNo)) {//????????????
//            request.setOrderNo(orderNo);
                    mRequest.setOriginal_code(orderNo);
                    mRequest.getBizData().setOriginal_code(orderNo);
                }
                if (StringUtil.isNullStr(id)) {//?????????ID
//            request.setOrderNo(orderNo);
                    mRequest.setOriginal_id(id);
                    mRequest.getBizData().setOriginal_id(id);
                }
                if (StringUtil.isNullStr(mORIGINAL_TYPE)) {//???????????????
//            request.setOrderNo(orderNo);
                    mRequest.setOriginal_type(mORIGINAL_TYPE);
                    mRequest.getBizData().setOriginal_type(mORIGINAL_TYPE);
                }
                if (StringUtil.isNullStr(proInsId)) {//???????????????ID
//            request.setOrderNo(orderNo);
                    mRequest.setOriginal_prolnstld(proInsId);
                    mRequest.getBizData().setOriginal_prolnstld(proInsId);
                }

            }
        });
        viewModel.queryAduitType(DisqualifiedDataKey.SEVERITY_TYPE_LIST).observe(this, model -> {
            severityTypeLists = model;
        });
        viewModel.queryOrderCode().observe(this, model2 -> {
            mOrderCode = model2;
            orderCodeChange = model2;

        });
        if (DbRequest == null) {
            createData();
        } else {
            createDbData(mDbRequest);
        }

    }

    private void createData() {
//        binding.tvInspected.setText(viewModel.getUserName());
        binding.tvCheckDate.setText(TimeUtil.getYMdTime(System.currentTimeMillis()));
        binding.tvDealLine.setText(TimeUtil.getYMdTime(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        mRequest = new CreateUnQualityRequest();
        mRequest.getStartFlowParamObject().setFlowKey("unqualified_key");

        mRequest.getBizData().setCheck_date(TimeUtil.getYMdTime(System.currentTimeMillis()));
        mRequest.getBizData().setCorrection_date(TimeUtil.getYMdTime(System.currentTimeMillis() + 1000 * 60 * 60 * 24));


        mRequest.getBizData().setCheck_user_id(viewModel.getUserId());
        mRequest.getBizData().setCheck_user_name(viewModel.getUserName());

//        mRequest.getBizData().setChecked_user_id(viewModel.getUserId());
//        mRequest.getBizData().setChecked_user_name(viewModel.getUserName());
    }

    private void createDbData(CreateUnQualityRequest mDbrequest) {
        binding.tvInspected.setText(mDbrequest.getBizData().getChecked_user_name().isEmpty() ? "?????????" : mDbrequest.getBizData().getChecked_user_name());
        binding.tvCheckDate.setText(mDbrequest.getBizData().getCheck_date());
        binding.tvDealLine.setText(mDbrequest.getBizData().getCorrection_date());
        binding.ltQuestionDesc.setText(mDbrequest.getBizData().getProblem_description());
        binding.tvDivide.setText(mDbrequest.getBizData().getDivide_name());
        divideId = mDbrequest.getBizData().getDivide_id();
        mRequest = new CreateUnQualityRequest();
        mRequest.getStartFlowParamObject().setFlowKey("unqualified_key");
        mRequest.getBizData().setCode(mDbrequest.getCode());
        mRequest.getBizData().setDivide_id(mDbrequest.getBizData().getDivide_id());
        mRequest.getBizData().setDivide_name(mDbrequest.getBizData().getDivide_name());

        mRequest.getBizData().setCheck_date(mDbrequest.getBizData().getCheck_date());
        mRequest.getBizData().setCorrection_date(mDbrequest.getBizData().getCorrection_date());


        mRequest.getBizData().setCheck_user_id(viewModel.getUserId());
        mRequest.getBizData().setCheck_user_name(viewModel.getUserName());

        mRequest.getBizData().setChecked_user_id(mDbrequest.getBizData().getChecked_user_id());
        mRequest.getBizData().setChecked_user_name(mDbrequest.getBizData().getChecked_user_name());

        mRequest.getBizData().setLine(mDbrequest.getBizData().getLine());
        String severity = mDbrequest.getBizData().getSeverity();
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
        String line = mDbrequest.getBizData().getLine();
        dimCode = mDbrequest.getBizData().getLine();
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
        mRequest.getBizData().setSeverity(mDbrequest.getBizData().getSeverity());

        updatePhotoUI(mDbrequest);
    }

    private void updatePhotoUI(CreateUnQualityRequest mDbrequest) {
        List<Uri> uris = viewModel.loadCachePhotoUris(mDbrequest);
        if (uris.size() > 0) {
            photoSelectAdapter.addPhotos(uris);
        }
    }

    //?????????????????? ?????????????????????????????????
    public void onOldCodeClick() {
        if (fragmentTag.equals(FRAGMENT_PLAN_OWRKORDER_DONE)) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID, id)
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .withString(RouteKey.KEY_TASK_ID, taskId)
                    .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                    .withString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTag)
                    .navigation();
        } else if (fragmentTag.equals(FRAGMENT_DISQUALIFIED_HAD_FOLLOW)) {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
                    .withString(RouteKey.KEY_TASK_ID,taskId)
                    .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
                    .withString(RouteKey.KEY_ID,id)
                    .withString(RouteKey.FRAGMENT_TAG,FRAGMENT_DISQUALIFIED_HAD_FOLLOW)
                    .navigation();
        } else {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                    .withString(RouteKey.KEY_ORDER_ID, id)
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                    .navigation();
        }
    }

    /**
     * ??????????????????
     *
     * @param type
     */
    public void pleaseSelect(SelectType type) {
//        if (!selectCheck(type)) {
//            return;
//        }
        switch (type) {
            case AGING:
                //??????
                aging();
                break;
            case CHECK_DATE:
                //????????????

                choosePayDate(CHECK_DATE);
                break;
            case LINE:
                //??????
                line();
                break;
            case SEVERITY:
                //????????????
                severity();
                break;
            case DEADLINE:
                //??????????????????
                choosePayDate(DEADLINE);
                break;
            case INSPECTED:
                //????????????
                chooseDisposePerson();
                break;
        }
    }

    private void chooseDisposePerson() {
        if (divideId.isEmpty()) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        if (dimCode.isEmpty()) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON).
                withString(RouteKey.KEY_ORG_ID, divideId).
                withBoolean(RouteKey.KEY_IS_UNQUALITY, true).
                withString(RouteKey.KEY_DIM_CODE, dimCode).
                navigation(this, RouterUtils.ACTIVITY_REQUEST_PERSON_CHOOSE);
    }

    /**
     * ????????????
     */
    public void onCacheClick() {

//        if (binding.tvDivide.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"???????????????");
//            return;
//        }
//        if (binding.tvCheckDate.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
        if (binding.ltQuestionDesc.getString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
//        if (binding.tvLine.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"???????????????");
//            return;
//        }
//        if (binding.tvSeverity.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
//        if (binding.tvDealLine.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"???????????????????????????");
//            return;
//        }
//        if (binding.tvInspected.getText().toString().equals("?????????")) {
//            ToastUtil.show(this,"?????????????????????");
//            return;
//        }
//        long dealtime = TimeUtil.ymdToLong(binding.tvDealLine.getText().toString());
//        long checkTime = TimeUtil.ymdToLong(binding.tvCheckDate.getText().toString());
//        long day=60*60*24*999;
//        if (dealtime-checkTime<day) {
//            ToastUtil.show(this,"????????????????????????????????????????????????");
//            return;
//        }
        if (binding.tvLine.getText().toString().equals("?????????") || binding.tvLine.getText().toString().isEmpty()) {
            ToastUtil.show(this, "???????????????");
            return;
        }
        cachePhoto(photoSelectAdapter.getSelectedPhotos());
        mRequest.getBizData().setProblem_description(binding.ltQuestionDesc.getString());
        viewModel.insertCreateRequest(mRequest);
        ToastUtil.show(this, "????????????");
        finish();
    }

    /**
     * ??????????????????
     */
    private static final String TAG = "CreateDisqualifiedActiv";

    public void onPassClick() {
//        Log.e(TAG, "onPassClick: "+mOrderCode);
//        Log.e(TAG, "onPassClick: "+viewModel.getUserId());
//        Log.e(TAG, "onPassClick: "+viewModel.getUserName());
//        checkSubmit();
        if (binding.tvDivide.getText().toString().equals("?????????") || binding.tvDivide.getText().toString().isEmpty()) {
            ToastUtil.show(this, "???????????????");
            return;
        }
        if (binding.tvCheckDate.getText().toString().equals("?????????") || binding.tvCheckDate.getText().toString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.ltQuestionDesc.getString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.tvLine.getText().toString().equals("?????????") || binding.tvLine.getText().toString().isEmpty()) {
            ToastUtil.show(this, "???????????????");
            return;
        }
        if (binding.tvSeverity.getText().toString().equals("?????????") || binding.tvSeverity.getText().toString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.tvDealLine.getText().toString().equals("?????????") || binding.tvDealLine.getText().toString().isEmpty()) {
            ToastUtil.show(this, "???????????????????????????");
            return;
        }
        if (binding.tvInspected.getText().toString().equals("?????????") || binding.tvInspected.getText().toString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        long dealtime = TimeUtil.ymdToLong(binding.tvDealLine.getText().toString());
        long checkTime = TimeUtil.ymdToLong(binding.tvCheckDate.getText().toString());
        long day = 60 * 60 * 24 * 999;
        if (dealtime - checkTime < day) {
            ToastUtil.show(this, "??????????????????????????????????????????");
            return;
        }
        mRequest.getBizData().setProblem_description(binding.ltQuestionDesc.getString());
        String s = new Gson().toJson(mRequest, CreateUnQualityRequest.class);
        Log.e(TAG, "onPassClick: requestjson=== " + s);
        if (IsFastClick.isFastDoubleClick()) {
            uploadImages(mRequest);
        }
    }

    /**
     * ????????????
     *
     * @param mRequest
     */
    private void uploadImages(CreateUnQualityRequest mRequest) {
        //??????????????????
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.deal(mRequest, data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.tv_create_suc);
                        viewModel.deleteCreateRequest(mRequest.getBizData().getCode());
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    private void checkSubmit() {
        if (binding.tvDivide.getText().toString().equals("?????????")) {
            ToastUtil.show(this, "???????????????");
            return;
        }
        if (binding.tvCheckDate.getText().toString().equals("?????????")) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.ltQuestionDesc.getString().isEmpty()) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.tvLine.getText().toString().equals("?????????")) {
            ToastUtil.show(this, "???????????????");
            return;
        }
        if (binding.tvSeverity.getText().toString().equals("?????????")) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        if (binding.tvDealLine.getText().toString().equals("?????????")) {
            ToastUtil.show(this, "???????????????????????????");
            return;
        }
        if (binding.tvInspected.getText().toString().equals("?????????")) {
            ToastUtil.show(this, "?????????????????????");
            return;
        }
        long l1 = TimeUtil.ymdToLong(binding.tvDealLine.getText().toString());
        long l2 = TimeUtil.ymdToLong(binding.tvCheckDate.getText().toString());
        long l3 = 60 * 60 * 24 * 999;
        if (l1 - l2 < l3) {
            ToastUtil.show(this, "????????????????????????????????????????????????");
            return;
        }
    }


    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    /**
     * ?????????????????????
     */
    private void selectPng() {
        //?????????????????????
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//????????????
        binding.rvImglist.setAdapter(photoSelectAdapter);
        binding.rvImglist.addItemDecoration(new SpacesItemDecoration(18));
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
     * ??????view
     */
    private void aging() {
        //????????????view
        PeriodizationView periodizationView = new PeriodizationView();
        periodizationView.setPeriodListener(this::onPeriodSelectListener);
        periodizationView.show(getSupportFragmentManager(), "");
    }

    //????????????
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        divideId = orgModel.getId();
        binding.tvDivide.setText(orgModel.getName());
        mRequest.getBizData().setDivide_id(orgModel.getId());
        mRequest.getBizData().setDivide_name(orgModel.getName());
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
                    case DEADLINE:
                        binding.tvDealLine.setText(dft.format(date));
                        mRequest.getBizData().setCorrection_date(dft.format(date));

                        break;
                    case CHECK_DATE:
                        binding.tvCheckDate.setText(dft.format(date));
                        mRequest.getBizData().setCheck_date(dft.format(date));
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
     * ????????????
     */
    private void line() {
        if (lineTypeLists == null || lineTypeLists.size() == 0) {
            ToastUtil.show(this, "????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DisqualifiedTypesBean data : lineTypeLists) {
            txStrList.add(data.getName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, txDefaultPosLine, new BottomPicker.OnItemPickListener() {


            @Override
            public void onPick(int position, String label) {
                if (position != txDefaultPosLine) {
//                    clearRequest(SelectType.LINE);
                }
                switch (position) {
                    case HJ:
                        mOrderCode = "HJ" + orderCodeChange;
                        break;
                    case GC:
                        mOrderCode = "GC" + orderCodeChange;
                        break;
                    case ZX:
                        mOrderCode = "ZX" + orderCodeChange;
                        break;
                    case KF:
                        mOrderCode = "KF" + orderCodeChange;
                        break;
                }
                txDefaultPosLine = position;
                binding.tvLine.setText(txStrList.get(position));
                mRequest.getBizData().setLine(lineTypeLists.get(position).getKey());
                mRequest.getBizData().setCode(mOrderCode);
                if (mCode == null) {//????????????
                } else {//???????????????????????? ????????????
                    mRequest.getBizData().setParent_code(mCode);
                }
                dimCode = lineTypeLists.get(position).getKey();
            }
        });
    }

    /**
     * ??????????????????
     */
    private void severity() {
        if (severityTypeLists == null || severityTypeLists.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DisqualifiedTypesBean data : severityTypeLists) {
            txStrList.add(data.getName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, txDefaultPosSeverity, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                if (position != txDefaultPosSeverity) {
//                    clearRequest(SelectType.LINE);
                }
                txDefaultPosSeverity = position;
                binding.tvSeverity.setText(txStrList.get(position));
                mRequest.getBizData().setSeverity(severityTypeLists.get(position).getKey());
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
                cachePhoto(photoSelectAdapter.getSelectedPhotos());
            }
        }
        //????????????
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_PERSON_CHOOSE && data != null) {
            Bundle bundle = data.getBundleExtra(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT);
            OrgModel orgModel = (OrgModel) bundle.getSerializable(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT);
            mRequest.getBizData().setChecked_user_id(orgModel.getId());
            mRequest.getBizData().setChecked_user_name(orgModel.getName());
            binding.tvInspected.setText(orgModel.getName());
//            request.setProcName(orgModel.getName());
//            binding.setBean(request);
        }
    }

    private void cachePhoto(List<Uri> uris) {
        viewModel.cachePhotos(uris, mRequest);
    }
}
