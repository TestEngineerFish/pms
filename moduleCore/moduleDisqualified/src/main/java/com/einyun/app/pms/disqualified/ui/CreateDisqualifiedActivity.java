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
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
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

import static com.einyun.app.pms.disqualified.SelectType.CHECK_DATE;
import static com.einyun.app.pms.disqualified.SelectType.DEADLINE;

@Route(path = RouterUtils.ACTIVITY_PROPERTY_CREATE)
public class CreateDisqualifiedActivity extends BaseHeadViewModelActivity<ActivityCreateDisqualifiedOrderBinding, DisqualifiedFragmentViewModel>implements PeriodizationView.OnPeriodSelectListener{
    int txDefaultPosLine = 0;
    int txDefaultPosSeverity = 0;
    private PhotoSelectAdapter photoSelectAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    private List<DisqualifiedTypesBean> lineTypeLists;
    private List<DisqualifiedTypesBean> severityTypeLists;
    private String mOrderCode;
    private String orderCodeChange;
    public static final int HJ=0;
    public static final int GC=1;
    public static final int ZX=2;
    public static final int KF=3;
    private CreateUnQualityRequest mRequest;
    private String dimCode="";
    private String divideId="";
    private String format;
    @Autowired(name = RouteKey.KEY_MODEL_DATA)
    Serializable DbRequest;
    private CreateUnQualityRequest mDbRequest;

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
        setHeadTitle(R.string.tv_disqualified_order);
        selectPng();
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("onChanged", "onChanged: "+aBoolean);
            }
        });

        if (DbRequest!=null) {
            mDbRequest = (CreateUnQualityRequest) DbRequest;
        }
    }
    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        viewModel.queryAduitType(DisqualifiedDataKey.LINE_TYPE_LIST).observe(this,model->{
            lineTypeLists = model;

        });
        viewModel.queryAduitType(DisqualifiedDataKey.SEVERITY_TYPE_LIST).observe(this,model->{
            severityTypeLists = model;
        });
        viewModel.queryOrderCode().observe(this,model2->{
            mOrderCode = model2;
            orderCodeChange = model2;

        });
        if (DbRequest==null) {
            createData();
        }else {
            createDbData(mDbRequest);
        }
    }

    private void createData() {
        binding.tvInspected.setText(viewModel.getUserName());
        binding.tvCheckDate.setText(TimeUtil.getYMdTime(System.currentTimeMillis()));
        binding.tvDealLine.setText(TimeUtil.getYMdTime(System.currentTimeMillis()+1000*60*60*24));
        mRequest = new CreateUnQualityRequest();
        mRequest.getStartFlowParamObject().setFlowKey("unqualified_key");

        mRequest.getBizData().setCheck_date(TimeUtil.getYMdTime(System.currentTimeMillis()));
        mRequest.getBizData().setCorrection_date(TimeUtil.getYMdTime(System.currentTimeMillis()+1000*60*60*24));


        mRequest.getBizData().setCheck_user_id(viewModel.getUserId());
        mRequest.getBizData().setCheck_user_name(viewModel.getUserName());

        mRequest.getBizData().setChecked_user_id(viewModel.getUserId());
        mRequest.getBizData().setChecked_user_name(viewModel.getUserName());
    }
    private void createDbData(CreateUnQualityRequest mDbrequest) {
        binding.tvInspected.setText(mDbrequest.getBizData().getChecked_user_name());
        binding.tvCheckDate.setText(mDbrequest.getBizData().getCheck_date());
        binding.tvDealLine.setText(mDbrequest.getBizData().getCorrection_date());
        binding.ltQuestionDesc.setText(mDbrequest.getBizData().getProblem_description());
        binding.tvDivide.setText(mDbrequest.getBizData().getDivide_name());
        divideId=mDbrequest.getBizData().getDivide_id();
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
                binding.tvSeverity.setText("高");

                break;
            case DisqualifiedDataKey.SEVERITY_MIDDLE_LEVEL:
                binding.tvSeverity.setText("中");
                break;
            case DisqualifiedDataKey.SEVERITY_LOW_LEVEL:
                binding.tvSeverity.setText("低");
                break;
        }
        String line = mDbrequest.getBizData().getLine();
        dimCode=mDbrequest.getBizData().getLine();
        switch (line) {
            case DisqualifiedDataKey.LINE_ENV://环境
                binding.tvLine.setText("环境");
                break;
            case DisqualifiedDataKey.LINE_ENG://工程
                binding.tvLine.setText("工程");
                break;
            case DisqualifiedDataKey.LINE_ORDER://秩序
                binding.tvLine.setText("秩序");
                break;
            case DisqualifiedDataKey.LINE_CUSTOMER://客服
                binding.tvLine.setText("客服");
                break;
        }
        mRequest.getBizData().setSeverity(mDbrequest.getBizData().getSeverity());

        updatePhotoUI(mDbrequest);
    }

    private void updatePhotoUI(CreateUnQualityRequest mDbrequest) {
        List<Uri> uris=viewModel.loadCachePhotoUris(mDbrequest);
        if(uris.size()>0){
            photoSelectAdapter.addPhotos(uris);
        }
    }

    /**
     * 点击事件回馈
     *
     * @param type
     */
    public void pleaseSelect(SelectType type) {
//        if (!selectCheck(type)) {
//            return;
//        }
        switch (type) {
            case AGING:
                //分期
                aging();
                break;
            case CHECK_DATE:
                //检查日期

                choosePayDate(CHECK_DATE);
                break;
            case LINE:
                //条线
                line();
                break;
            case SEVERITY:
                //严重程度
                severity();
                break;
            case DEADLINE:
                //纠正截至日期
                choosePayDate(DEADLINE);
                break;
            case INSPECTED:
                //被检查人
                chooseDisposePerson();
                break;
        }
    }
    private void chooseDisposePerson() {
        if (divideId.isEmpty()) {
            ToastUtil.show(this,"请先选择分期");
            return;
        }
        if (dimCode.isEmpty()) {
            ToastUtil.show(this,"请先选择条线");
            return;
        }
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON).
                withString(RouteKey.KEY_ORG_ID, divideId).
                withBoolean(RouteKey.KEY_IS_UNQUALITY, true).
                withString(RouteKey.KEY_DIM_CODE, dimCode).
                navigation(this, RouterUtils.ACTIVITY_REQUEST_PERSON_CHOOSE);
    }
    /**
     * 缓存按钮
     */
    public void onCacheClick(){
        cachePhoto(photoSelectAdapter.getSelectedPhotos());
//        if (binding.tvDivide.getText().toString().equals("请选择")) {
//            ToastUtil.show(this,"请选择分期");
//            return;
//        }
//        if (binding.tvCheckDate.getText().toString().equals("请选择")) {
//            ToastUtil.show(this,"请选择检查日期");
//            return;
//        }
//        if (binding.ltQuestionDesc.getString().isEmpty()) {
//            ToastUtil.show(this,"请输入问题描述");
//            return;
//        }
//        if (binding.tvLine.getText().toString().equals("请选择")) {
//            ToastUtil.show(this,"请选择条线");
//            return;
//        }
//        if (binding.tvSeverity.getText().toString().equals("请选择")) {
//            ToastUtil.show(this,"请选择严重程度");
//            return;
//        }
//        if (binding.tvDealLine.getText().toString().equals("请选择")) {
//            ToastUtil.show(this,"请选择纠正截至日期");
//            return;
//        }
//        if (binding.tvInspected.getText().toString().equals("请选择")) {
//            ToastUtil.show(this,"请选择被检查人");
//            return;
//        }
//        long dealtime = TimeUtil.ymdToLong(binding.tvDealLine.getText().toString());
//        long checkTime = TimeUtil.ymdToLong(binding.tvCheckDate.getText().toString());
//        long day=60*60*24*999;
//        if (dealtime-checkTime<day) {
//            ToastUtil.show(this,"纠正截至日期至少早于检查日期一天");
//            return;
//        }
        if (binding.tvLine.getText().toString().equals("请选择")||binding.tvLine.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择条线");
            return;
        }
        mRequest.getBizData().setProblem_description(binding.ltQuestionDesc.getString());
        viewModel.insertCreateRequest(mRequest);
        ToastUtil.show(this,"缓存成功");
        finish();
    }
    /**
     * 创建提交按钮
     */
    private static final String TAG = "CreateDisqualifiedActiv";
    public void onPassClick(){
//        Log.e(TAG, "onPassClick: "+mOrderCode);
//        Log.e(TAG, "onPassClick: "+viewModel.getUserId());
//        Log.e(TAG, "onPassClick: "+viewModel.getUserName());
//        checkSubmit();
        if (binding.tvDivide.getText().toString().equals("请选择")||binding.tvDivide.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择分期");
            return;
        }
        if (binding.tvCheckDate.getText().toString().equals("请选择")||binding.tvCheckDate.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择检查日期");
            return;
        }
        if (binding.ltQuestionDesc.getString().isEmpty()) {
            ToastUtil.show(this,"请输入问题描述");
            return;
        }
        if (binding.tvLine.getText().toString().equals("请选择")||binding.tvLine.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择条线");
            return;
        }
        if (binding.tvSeverity.getText().toString().equals("请选择")||binding.tvSeverity.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择严重程度");
            return;
        }
        if (binding.tvDealLine.getText().toString().equals("请选择")||binding.tvDealLine.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择纠正截至日期");
            return;
        }
        if (binding.tvInspected.getText().toString().equals("请选择")||binding.tvInspected.getText().toString().isEmpty()) {
            ToastUtil.show(this,"请选择被检查人");
            return;
        }
        long dealtime = TimeUtil.ymdToLong(binding.tvDealLine.getText().toString());
        long checkTime = TimeUtil.ymdToLong(binding.tvCheckDate.getText().toString());
        long day=60*60*24*999;
        if (dealtime-checkTime<day) {
            ToastUtil.show(this,"纠正截至日期至少早于检查日期一天");
            return;
        }
        mRequest.getBizData().setProblem_description(binding.ltQuestionDesc.getString());
        String s = new Gson().toJson(mRequest, CreateUnQualityRequest.class);
        Log.e(TAG, "onPassClick: requestjson=== "+s );
        if (IsFastClick.isFastDoubleClick()) {
            uploadImages(mRequest);
        }
    }
    /**
     * 上传照片
     * @param mRequest
     */
    private void uploadImages(CreateUnQualityRequest mRequest) {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.deal(mRequest,data).observe(this, flag -> {
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
        if (binding.tvDivide.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择分期");
            return;
        }
        if (binding.tvCheckDate.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择检查日期");
            return;
        }
        if (binding.ltQuestionDesc.getString().isEmpty()) {
            ToastUtil.show(this,"请输入问题描述");
            return;
        }
        if (binding.tvLine.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择条线");
            return;
        }
        if (binding.tvSeverity.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择严重程度");
            return;
        }
        if (binding.tvDealLine.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择纠正截至日期");
            return;
        }
        if (binding.tvInspected.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择被检查人");
            return;
        }
        long l1 = TimeUtil.ymdToLong(binding.tvDealLine.getText().toString());
        long l2 = TimeUtil.ymdToLong(binding.tvCheckDate.getText().toString());
        long l3=60*60*24*999;
        if (l1-l2<l3) {
            ToastUtil.show(this,"纠正截至日期至少早于检查日期一天");
            return;
        }
    }


    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 初始化选择图片
     */
    private void selectPng() {
        //图片选择适配器
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.rvImglist.setAdapter(photoSelectAdapter);
        binding.rvImglist.addItemDecoration(new SpacesItemDecoration(18));
        photoSelectAdapter.setAddListener(selectedSize -> {
            if (photoSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //加号添加图片
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
     * 分期view
     */
    private void aging() {
        //弹出分期view
        PeriodizationView periodizationView = new PeriodizationView();
        periodizationView.setPeriodListener(this::onPeriodSelectListener);
        periodizationView.show(getSupportFragmentManager(), "");
    }
    //分期结果
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        divideId = orgModel.getId();
        binding.tvDivide.setText(orgModel.getName());
        mRequest.getBizData().setDivide_id(orgModel.getId());
        mRequest.getBizData().setDivide_name(orgModel.getName());
    }
    /**
     * 日期选择
     */
    private void choosePayDate(SelectType type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
        format = simpleDateFormat.format(System.currentTimeMillis());
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        String[] split = format.split(",");
        startDate.set(Integer.parseInt(split[0]),Integer.parseInt(split[1])-1,Integer.parseInt(split[2]));//设置起始年份
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100,1,1);//设置结束年份
        //时间选择器
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
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setRangDate(startDate,endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .build();
        pvTime.show();
    }
    /**
     * 条线选择
     */
    private void line() {
        if (lineTypeLists==null||lineTypeLists.size() == 0) {
            ToastUtil.show(this, "暂无条线");
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
                        mOrderCode="HJ"+orderCodeChange;
                        break;
                    case GC:
                        mOrderCode="GC"+orderCodeChange;
                        break;
                    case ZX:
                        mOrderCode="ZX"+orderCodeChange;
                        break;
                    case KF:
                        mOrderCode="KF"+orderCodeChange;
                        break;
                }
                txDefaultPosLine = position;
                binding.tvLine.setText(txStrList.get(position));
                mRequest.getBizData().setLine(lineTypeLists.get(position).getKey());
                mRequest.getBizData().setCode(mOrderCode);
                dimCode = lineTypeLists.get(position).getKey();
            }
        });
    }
    /**
     * 严重程度选择
     */
    private void severity() {
        if (severityTypeLists==null||severityTypeLists.size() == 0) {
            ToastUtil.show(this, "暂无严重程度");
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
        //被检查人
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

    private void cachePhoto(List<Uri> uris){
        viewModel.cachePhotos(uris,mRequest);
    }
}
