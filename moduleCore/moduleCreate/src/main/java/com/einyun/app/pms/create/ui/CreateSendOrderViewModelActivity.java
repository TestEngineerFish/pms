package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectWorkOrderTypeView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.ResourceChildBean;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.net.request.CreateSendOrderRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.create.R;
import com.einyun.app.pms.create.SelectType;
import com.einyun.app.pms.create.viewmodel.CreateViewModel;
import com.einyun.app.pms.create.viewmodel.CreateViewModelFactory;
import com.einyun.app.pms.create.databinding.ActivityCreateSendOrderBinding;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CREATE_SEND_ORDER)
public class CreateSendOrderViewModelActivity extends BaseHeadViewModelActivity<ActivityCreateSendOrderBinding, CreateViewModel> implements RadioGroup.OnCheckedChangeListener, PeriodizationView.OnPeriodSelectListener {
    @Autowired(name = RouteKey.ID)
    String id;
    @Autowired(name = RouteKey.KEY_ORDER_NO)
    String orderNo;
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private CreateSendOrderRequest request;
    private List<DictDataModel> dictDataModelList = new ArrayList<>();
    private List<DictDataModel> dictDataModelWorkTypeList = new ArrayList<>();
    private List<DictDataModel> lineDictDataModelList = new ArrayList<>();
    @Override
    protected CreateViewModel initViewModel() {
        return new ViewModelProvider(this, new CreateViewModelFactory()).get(CreateViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_send_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.create_send_order_title);
        request = new CreateSendOrderRequest();
        binding.setCallBack(this);
        binding.rgs.setOnCheckedChangeListener(this);
        viewModel.getByTypeKey(Constants.WORK_TYPE).observe(this, dictDataModels -> {
            for (DictDataModel model : dictDataModels) {
                if (model.getParentId().equals(model.getTypeId())) {
                    dictDataModelList.add(model);
                }
            }
            dictDataModelWorkTypeList = dictDataModels;
        });
        viewModel.getTypesListByKey(Constants.RESOURCE_TYPE).observe(this, dictDataModels -> {
            lineDictDataModelList = dictDataModels;
        });
        selectPng();
    }

    /**
     * 初始化选择图片
     */
    private void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
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
                    .maxSelectable(MAX_PHOTO_SIZE-photoSelectAdapter.getSelectedPhotos().size())
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, this);
    }

    /**
     * 点击事件回馈
     *
     * @param type
     */
    public void pleaseSelect(SelectType type) {
        if (!selectCheck(type)) {
            return;
        }
        switch (type) {
            case AGING:
                //分期
                aging();
                break;
            case LINE:
                //条线
                line();
                break;
            case WORKY_TYPE:
                //工单类型：
                workTable();
                break;
            case RESOURCE_TYPE:
                resourceType();
                break;
            case RESOURCE:
                resource();
                break;
            case DISPOSE_PERSON:
                chooseDisposePerson();
                break;
        }
    }

    private void chooseDisposePerson() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON).
                withString(RouteKey.KEY_ORG_ID, request.getDivideId()).
                withString(RouteKey.KEY_DIM_CODE, request.getTxCode()).
                navigation(this, RouterUtils.ACTIVITY_REQUEST_PERSON_CHOOSE);
    }

    private int rsDefaultPos = 0;

    /**
     * 选择资源
     */
    private void resource() {
        List<String> resourceList = new ArrayList<>();
        List<ResourceChildBean> children = (List<ResourceChildBean>) resourceTypeBean.getChildren();
        for (ResourceChildBean childBean : children) {
            resourceList.add(childBean.getResourceName());
        }
        if (resourceList.size() == 0) {
            ToastUtil.show(this, "暂无资源");
            return;
        }

        BottomPicker.buildBottomPicker(this, resourceList, rsDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                rsDefaultPos = position;
                for (ResourceChildBean childBean : children) {
                    if (childBean.getResourceName().equals(resourceList.get(position))) {
                        request.setResId(childBean.getId());
                        request.setResName(childBean.getResourceName());
                    }
                }
                binding.setBean(request);
            }
        });
    }

    private int rsTypeDefaultPos = 0;
    private ResourceTypeBean resourceTypeBean;

    /**
     * 资源类型
     */
    private void resourceType() {
        viewModel.getResourceInfos(request).observe(this, resourceTypeBeans -> {
            if (resourceTypeBeans.size() == 0) {
                ToastUtil.show(this, "暂无资源分类");
                return;
            }
            List<String> resourceTypeList = new ArrayList<>();
            for (ResourceTypeBean bean : resourceTypeBeans) {
                resourceTypeList.add(bean.getName());
            }
            BottomPicker.buildBottomPicker(this, resourceTypeList, rsTypeDefaultPos, new BottomPicker.OnItemPickListener() {
                @Override
                public void onPick(int position, String label) {
                    if (position != rsTypeDefaultPos) {
                        clearRequest(SelectType.RESOURCE_TYPE);
                    }
                    rsTypeDefaultPos = position;
                    for (ResourceTypeBean bean : resourceTypeBeans) {
                        if (bean.getName().equals(resourceTypeList.get(position))) {
                            resourceTypeBean = bean;
                        }
                    }
                    binding.tvResource.setText(resourceTypeBean.getName());
                }
            });
        });
    }

    /**
     * 工单类型
     */
    private void workTable() {
        SelectWorkOrderTypeView selectWorkOrderTypeView = new SelectWorkOrderTypeView(dictDataModelWorkTypeList, dictDataModelList.get(txDefaultPos).getId());
        selectWorkOrderTypeView.setWorkTypeListener(new SelectWorkOrderTypeView.OnWorkTypeSelectListener() {
            @Override
            public void onWorkTypeSelectListener(List<DictDataModel> model) {
                clearRequest(SelectType.WORKY_TYPE);
                request.setType(model.get(0).getKey());
                request.setTypeName(model.get(0).getName());
                if (model.size() == 3) {
                    request.setEnvType2Code(model.get(1).getKey());
                    request.setEnvType2Name(model.get(1).getName());
                    request.setEnvType3Code(model.get(2).getKey());
                    request.setEnvType3Name(model.get(2).getName());
                }
                binding.setBean(request);
            }
        });
        selectWorkOrderTypeView.show(getSupportFragmentManager(), "");
    }

    int txDefaultPos = 0;

    /**
     * 条线选择
     */
    private void line() {
        if (dictDataModelList.size() == 0) {
            ToastUtil.show(this, "暂无条线");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictDataModelList) {
            txStrList.add(data.getName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, txDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                if (position != txDefaultPos) {
                    clearRequest(SelectType.LINE);
                }
                txDefaultPos = position;
                //获取条线id
                if (lineDictDataModelList.size() != 0) {
                    for (DictDataModel model : lineDictDataModelList) {
                        if (model.getTypeKey().equals(dictDataModelList.get(txDefaultPos).getKey().replace("_map", ""))) {
                            request.setTxId(model.getId());
                            request.setTxCode(model.getTypeKey());
                            request.setTxName(model.getName());
                        }
                    }
                }
                binding.setBean(request);
            }
        });
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

    private void clearRequest(SelectType type) {
        if (SelectType.AGING == type) {
            request = new CreateSendOrderRequest();
            resourceTypeBean = null;
        }
        if (SelectType.LINE == type) {
            CreateSendOrderRequest copy = new CreateSendOrderRequest();
            copy.setDivideId(request.getDivideId());
            copy.setDivideCode(request.getDivideCode());
            copy.setDivideName(request.getDivideName());
            request = copy;
            resourceTypeBean = null;
            binding.tvResource.setText("请选择");
        }
        if (SelectType.WORKY_TYPE == type) {
            request.setType("");
            request.setTypeName("");
            request.setEnvType2Code("");
            request.setEnvType2Name("");
            request.setEnvType3Code("");
            request.setEnvType3Name("");
        }
        if (SelectType.RESOURCE_TYPE == type) {
            request.setResId("");
            request.setResName("");
        }
    }

    /**
     * 工单类型和负责人前置校验
     */
    private boolean selectCheck(SelectType selectType) {
        if (selectType == SelectType.AGING) {
            return true;
        }
        if (!StringUtil.isNullStr(request.getDivideId())) {
            ToastUtil.show(this, "请先选择分期");
            return false;
        }
        if (selectType == SelectType.LINE) {
            return true;
        }
        if (!StringUtil.isNullStr(request.getTxId())) {
            ToastUtil.show(this, "请先选择条线");
            return false;
        }
        if (selectType == SelectType.RESOURCE) {
            if (resourceTypeBean == null) {
                ToastUtil.show(this, "请先选择资源分类");
                return false;
            }
        }
        return true;
    }

    /**
     * 上传前数据检测
     *
     * @return
     */
    private boolean checkData() {
        if (!StringUtil.isNullStr(request.getTxId())) {
            ToastUtil.show(this, "请选择条线");
            return false;
        }
        if (!StringUtil.isNullStr(request.getType())) {
            ToastUtil.show(this, "请选择工单类型");
            return false;
        }
        if ("环境".equals(request.getTxName()) && !StringUtil.isNullStr(request.getEnvType2Code())) {
            ToastUtil.show(this, "请选择二级工单类型");
            return false;
        }
        if ("环境".equals(request.getTxName()) && !StringUtil.isNullStr(request.getEnvType3Code())) {
            ToastUtil.show(this, "请选择三级工单类型");
            return false;
        }
        if (!StringUtil.isNullStr(request.getProcId())) {
            ToastUtil.show(this, "请选择负责人");
            return false;
        }
        if (!StringUtil.isNullStr(request.getOtLevel())) {
            ToastUtil.show(this, "请选择超时级别");
            return false;
        }
        String problemDescription = binding.ltQuestionDesc.getString();
        if (!StringUtil.isNullStr(problemDescription)) {
            ToastUtil.show(this, "请填写派出任务的具体描述信息");
            return false;
        }
        return true;
    }

    public void submit() {
        if (!checkData()) {
            return;
        }
        uploadImages();
    }

    /***
     * 超时级别选择
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (R.id.rb_normal == i){
            request.setOtLevel("1");
        }
        if (R.id.rb_general == i){
            request.setOtLevel("2");
        }
        if (R.id.rb_warning == i){
            request.setOtLevel("3");
        }
    }

    /**
     * 分期回调
     *
     * @param orgModel
     */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        if (!orgModel.getId().equals(request.getDivideId())) {
            clearRequest(SelectType.AGING);
            request.setDivideId(orgModel.getId());
            request.setDivideCode(orgModel.getCode());
            request.setDivideName(orgModel.getName());
            binding.setBean(request);
        }
    }

    /**
     * 上传照片
     */
    private void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.createSendOrder(buidRequest(), data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_work_order_success);
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    private CreateSendOrderRequest buidRequest() {
        request.setDesc(binding.ltQuestionDesc.getString());
        request.setLocation(binding.ltLocationInfo.getString());
        if (StringUtil.isNullStr(id)){
            request.setId(id);
        }
        if (StringUtil.isNullStr(orderNo)){
            request.setOrderNo(orderNo);
        }
        return request;
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
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_PERSON_CHOOSE && data != null) {
            Bundle bundle = data.getBundleExtra(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT);
            OrgModel orgModel = (OrgModel) bundle.getSerializable(DataConstants.KEY_CHOOSE_DISPOSE_PERSON_CONTENT);
            request.setProcId(orgModel.getId());
            request.setProcName(orgModel.getName());
            binding.setBean(request);
        }
    }


}
