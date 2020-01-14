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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
import java.util.Date;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
public class DisqualifiedDetailActivity extends BaseHeadViewModelActivity<ActivityDisqualifiedDetailBinding, DisqualifiedFragmentViewModel> {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String mTaskId;
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
        selectPng();
        selectValidationPng();
        binding.setCallBack(this);
        mFeedBackRequest = new UnQualityFeedBackRequest();
        mValidateRequest = new UnQualityVerificationRequest();
        /**
         * 工单信息
         */
        photoOrderInfoAdapter = new PhotoListAdapter(this);
        binding.listPicOrderInfo.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicOrderInfo.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicOrderInfo.setAdapter(photoOrderInfoAdapter);
        /**
         * 反馈信息
         */
        photoFeedBackInfoAdapter = new PhotoListAdapter(this);
        binding.listPicFeedback.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicFeedback.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicFeedback.setAdapter(photoFeedBackInfoAdapter);
        /**
         * 验证信息
         */
        photoValidationInfoAdapter = new PhotoListAdapter(this);
        binding.listPicInvalition.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicInvalition.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicInvalition.setAdapter(photoValidationInfoAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        switch (fragmenTag) {
            case FRAGMENT_DISQUALIFIED_WAIT_FOLLOW:
                /**
                 * 获取详情信息
                 */
                viewModel.getTODODetailInfo(mTaskId).observe(this,module->{
                    updateUI(module);
                });
                break;
            case FRAGMENT_DISQUALIFIED_HAD_FOLLOW:
                /**
                 * 获取详情信息
                 */
                viewModel.getHaveDODetailInfo(mProInstId).observe(this,module->{
                    updateUI(module);
                });
                break;
        }

        binding.tvOpValidatePerson.setText(viewModel.getUserName());
    }
    private void updateUI(DisqualifiedDetailModel detailModule) {
        if (detailModule == null) {
//            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
//        updatePageUIState(PageUIState.FILLDATA.getState());
        String status = detailModule.getData().getUnqualified_model().getStatus();
        switch (status) {
            case DisqualifiedDataKey.STATUS_CREATE_STEP://新生成
                binding.cdOpFeedback.setVisibility(View.VISIBLE);
                break;
            case DisqualifiedDataKey.STATUS_PROCESSING_STEP://处理中  显示 工单信息 反馈信息  验证操作
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                binding.cdOpValidation.setVisibility(View.VISIBLE);

                break;
            case DisqualifiedDataKey.STATUS_COMPLETED_STEP://已完成 显示 工单信息 反馈信息  验证信息
                binding.cdFeedbackInfo.setVisibility(View.VISIBLE);
                binding.cdValidationInfo.setVisibility(View.VISIBLE);
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
     * 反馈信息缓存按钮
     */
    public void onFeedBackCacheClick(){}
    /**
     * 反馈信息提交
     */
    public void onFeedBackPassClick(){
        if (binding.tvOpFeedbackDate.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择反馈日期");
            return;
        }
        if (binding.ltReason.getString().isEmpty()) {
            ToastUtil.show(this,"请输入原因分析");
            return;
        }
        if (binding.ltMeasures.getString().isEmpty()) {
            ToastUtil.show(this,"请输入文字信息");
            return;
        }
        mFeedBackRequest.getBizData().setReason(binding.ltReason.getString());
        mFeedBackRequest.getBizData().setCorrective_action(binding.ltMeasures.getString());
        mFeedBackRequest.getDoNextParamt().setTaskId(mTaskId);
        uploadFeedBackImages(mFeedBackRequest);
    }
    //反馈日期
    public void onFeedBackDateClick(){
        choosePayDate(SelectType.FEEDBACK);
    }
    //验证日期
    public void onValidateDateClick(){
        choosePayDate(SelectType.VALIDATE);
    }
    //创建新工单
    public void onCreateOrderClick(){}
    //验证信息缓存
    public void onValidationCacheClick(){}
    //验证信息提交
    public void onValidationPassClick(){
        if (binding.tvOpValidateDate.getText().toString().equals("请选择")) {
            ToastUtil.show(this,"请选择验证日期");
            return;
        }
        if (binding.ltValidation.getString().isEmpty()) {
            ToastUtil.show(this,"请输入验证信息");
            return;
        }
        mValidateRequest.getBizData().setVerification_situation(binding.ltValidation.getString());
        mValidateRequest.getBizData().setIs_pass(1);
        mValidateRequest.getDoNextParamt().setTaskId(mTaskId);
        uploadValidateImages(mValidateRequest);
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 反馈上传照片
     */
    private void uploadFeedBackImages(UnQualityFeedBackRequest request) {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.dealFeedBack(request,data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.tv_create_suc);
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }
    /**
     * 验证上传照片
     */
    private void uploadValidateImages(UnQualityVerificationRequest request) {
        //开始上传照片
        viewModel.uploadImages(photoValidationSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.dealValidate(request,data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.tv_create_suc);
                        finish();
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
            }
        }else if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK_SECOND){
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoValidationSelectAdapter.addPhotos(uris);
            }
        }
    }
    /**
     * 日期选择
     */
    private void choosePayDate(SelectType type) {
        //时间选择器
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
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .build();
        pvTime.show();
    }
    /**
     * 反馈
     * 初始化选择图片
     */
    private void selectPng() {
        //图片选择适配器
        photoSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvFeedbackList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.rvFeedbackList.setAdapter(photoSelectAdapter);
        binding.rvFeedbackList.addItemDecoration(new SpacesItemDecoration(18));
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
     * 验证
     * 初始化选择图片
     */
    private void selectValidationPng() {
        //图片选择适配器
        photoValidationSelectAdapter = new PhotoSelectAdapter(this);
        binding.rvValidationList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.rvValidationList.setAdapter(photoValidationSelectAdapter);
        binding.rvValidationList.addItemDecoration(new SpacesItemDecoration(18));
        photoValidationSelectAdapter.setAddListener(selectedSize -> {
            if (photoValidationSelectAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //加号添加图片
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
}
