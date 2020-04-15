package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.databinding.ActivityApplyForceCloseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.resource.workorder.net.request.ApplyCusCloseRequest;
import com.einyun.app.pms.sendorder.R;
//import com.einyun.app.pms.sendorder.databinding.ActivityApplyForceCloseBinding;
import com.einyun.app.pms.sendorder.viewmodel.ApplyCloseViewModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CLOSE)
public class ApplyForceCloseActivity extends BaseHeadViewModelActivity<ActivityApplyForceCloseBinding, ApplyCloseViewModel> implements RadioGroup.OnCheckedChangeListener {
    private PhotoSelectAdapter photoSelectAdapter;
    private static final int MAX_PHOTO_SIZE = 4;
    private ApplyCloseRequest request;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    public String id;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_CLOSE_ID)
    String keyId;
    @Autowired(name = RouteKey.KEY_MID_URL)
    String midUrl;

    private List<DictDataModel> dictDataModels = new ArrayList<>();
    @Override
    protected ApplyCloseViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(ApplyCloseViewModel.class);

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_force_close;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_apply_close);
        request = new ApplyCloseRequest();
        if (StringUtil.isNullStr(keyId) && RouteKey.KEY_PLAN.equals(keyId)){
            request.setID(id);
            request.setTaskId(taskId);
            request.setInstId(proInsId);
            request.setMessageType("1");
        }else{
            request.setId(id);
            request.setApplyTaskId(taskId);
            request.setInstId(proInsId);
        }
        applyCusCloseRequest= new ApplyCusCloseRequest(new ApplyCusCloseRequest.BizDataBean(),new ApplyCusCloseRequest.DoNextParamBean());
        applyCusCloseRequest.getBizData().setF_fclose_apply_invalid("0");
        if (StringUtil.isNullStr(midUrl)){
            if (RouteKey.KEY_MID_URL_COMPLAIN.equals(midUrl)){
                binding.ll1.setVisibility(View.VISIBLE);
                binding.rg.setOnCheckedChangeListener(this);
                viewModel.getByTypeKey(Constants.COMPLAIN_REASON).observe(this,dictDataModels -> {
                    this.dictDataModels = dictDataModels;
                });
                binding.ll2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectComplainReason();
                    }
                });
            }
        }
        selectPng();
    }

    int scDefaultPos;
    private void selectComplainReason(){
        if (dictDataModels.size() == 0) {
            ToastUtil.show(this, "暂无投诉性质");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictDataModels) {
            txStrList.add(data.getName());
        }

        BottomPicker.buildBottomPicker(this, txStrList, scDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                scDefaultPos = position;
                //获取条线id
                for (DictDataModel childBean : dictDataModels) {
                    if (childBean.getName().equals(txStrList.get(position))) {
                        applyCusCloseRequest.getBizData().setF_invalid_reason_cate_id(childBean.getKey());
                        applyCusCloseRequest.getBizData().setF_invalid_reason_cate(childBean.getName());
                    }
                }
                binding.tvComplainReason.setText(label);
            }
        });
    }

    /**
     * 初始化选择图片
     */
    private void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        binding.sendOrderCloseList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.sendOrderCloseList.addItemDecoration(new SpacesItemDecoration(18));
        binding.sendOrderCloseList.setAdapter(photoSelectAdapter);
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
                    .maxSelectable(MAX_PHOTO_SIZE-photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, this);
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
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.applyCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setApplicationDescription(binding.applyCloseReason.getString());
                request.setEndReason(binding.applyCloseReason.getString());
                if (TextUtils.isEmpty(binding.applyCloseReason.getString())) {
                    ToastUtil.show(ApplyForceCloseActivity.this, R.string.txt_plese_enter_reason);
                } else {
                    uploadImages();
                }
            }
        });
    }
    ApplyCusCloseRequest applyCusCloseRequest;
    /**
     * 上传照片
     */
    private void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                if (!StringUtil.isNullStr(midUrl)) {
                    if (StringUtil.isNullStr(keyId)) {
                        if (RouteKey.KEY_PLAN.equals(keyId)) {
                            viewModel.applyClosePlan(request, data).observe(this, model -> {
                                if (model.getCode().equals("0")) {
                                    LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                                    ToastUtil.show(this, R.string.apply_close_success);
                                    this.finish();
                                } else {
                                    ToastUtil.show(this,"强制闭单失败！");
                                }
                            });
                        }
                    } else {
                        viewModel.applyClose(request, data).observe(this, model -> {
                            if (model.getCode().equals("0")) {
                                LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                                ToastUtil.show(this, R.string.apply_close_success);
                                this.finish();
                            } else {
                                ToastUtil.show(this, "强制闭单失败！");
                            }
                        });
                    }
                }else {
                    if (RouteKey.KEY_MID_URL_COMPLAIN.equals(midUrl)){
                        if ("1".equals(applyCusCloseRequest.getBizData().getF_fclose_apply_invalid())){
                            if (!StringUtil.isNullStr(applyCusCloseRequest.getBizData().getF_invalid_reason_cate())){
                                ToastUtil.show(this,"请选择无效原因");
                                return;
                            }
                        }
                    }
                    applyCusCloseRequest.getDoNextParam().setTaskId(taskId);
                    applyCusCloseRequest.getBizData().setFclose_apply_reason(binding.applyCloseReason.getString());
                    applyCusCloseRequest.getBizData().setF_fclose_apply_reason(binding.applyCloseReason.getString());
                    viewModel.applyCustomerClose(applyCusCloseRequest,midUrl, data).observe(this, model -> {
                        if (model.getCode().equals("0")) {
                            LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                            ToastUtil.show(this, R.string.apply_close_success);
                            this.finish();
                        } else {
                            ToastUtil.show(this, "强制闭单失败！");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_yes){
            binding.ll2.setVisibility(View.VISIBLE);
            binding.line.setVisibility(View.VISIBLE);
            applyCusCloseRequest.getBizData().setF_fclose_apply_invalid("1");
        }
        if (checkedId == R.id.rb_no){
            binding.ll2.setVisibility(View.GONE);
            binding.line.setVisibility(View.GONE);
            applyCusCloseRequest.getBizData().setF_fclose_apply_invalid("0");
        }
    }
}
