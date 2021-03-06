package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectWorkOrderTypeView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.FileProviderUtil;
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
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;

@Route(path = RouterUtils.ACTIVITY_CREATE_SEND_ORDER)
public class CreateSendOrderViewModelActivity extends BaseHeadViewModelActivity<ActivityCreateSendOrderBinding, CreateViewModel> implements RadioGroup.OnCheckedChangeListener, PeriodizationView.OnPeriodSelectListener {
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String id;
    @Autowired(name = RouteKey.KEY_ORDER_NO)
    String orderNo;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_FRAGEMNT_TAG)
    String fragmentTag;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.F_ORIGINAL_TYPE)
    String mORIGINAL_TYPE;
    @Autowired(name = RouteKey.KEY_LINE)
    String lineName;
    @Autowired(name = RouteKey.KEY_RESOUSE)
    String resouseName;
    @Autowired(name = RouteKey.KEY_LINE_ID)
    String lineId;
    @Autowired(name = RouteKey.KEY_LINE_CODE)
    String lineCode;
    @Autowired(name = RouteKey.KEY_RESOUSE_ID)
    String resouseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String divideName;
    @Autowired(name = RouteKey.KEY_PROJECT)
    String projectName;
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private CreateSendOrderRequest request;
    private List<DictDataModel> dictDataModelList = new ArrayList<>();
    private List<DictDataModel> dictDataModelWorkTypeList = new ArrayList<>();
    private List<DictDataModel> lineDictDataModelList = new ArrayList<>();
    private File imageFile;
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
            for (int i = 0; i < dictDataModelList.size(); i++) {
                if (dictDataModelList.get(i).getName().equals(lineName)) {
                    txDefaultPos = i;
                }
            }
            dictDataModelWorkTypeList = dictDataModels;
        });
        viewModel.getTypesListByKey(Constants.RESOURCE_TYPE).observe(this, dictDataModels -> {
            lineDictDataModelList = dictDataModels;
        });

        selectPng();
        if (StringUtil.isNullStr(orderNo)) {
            binding.llOld.setVisibility(View.VISIBLE);
            binding.llLine.setVisibility(View.VISIBLE);
            binding.tvOldCode.setText(orderNo);
            binding.tvLine.setText(lineName);
            binding.tvResource.setText(resouseName);
//            request.setResName(resouseName);
            request.setTxName(lineName);
//            request.setResId(resouseId);
            request.setTxId(lineId);
            request.setTxCode(lineCode);
//            request.setDivideCode(divideId);
            request.setDivideId(divideId);
            request.setDivideName(divideName);
            request.setProjectName(projectName);
            binding.setBean(request);
        }
        if (request.getDivideId()!=null&&request.getTxCode()!=null) {
            viewModel.getResourceInfos(request).observe(this, resourceTypeBeans -> {
                if (resourceTypeBeans.size() == 0) {
//                    ToastUtil.show(this, "??????????????????");
                    return;
                }
                List<String> resourceTypeList = new ArrayList<>();
                for (ResourceTypeBean bean : resourceTypeBeans) {
                    resourceTypeList.add(bean.getName());
                }
                for (int i = 0; i < resourceTypeList.size(); i++) {
                    if (resourceTypeList.get(i).equals(resouseName)) {
                        rsDefaultPos = i;
                        resourceTypeBean = resourceTypeBeans.get(i);
                    }
                }
            });
        }
    }

    /**
     * ?????????????????????
     */
    private void selectPng() {
        photoSelectAdapter = new PhotoSelectAdapter(this);//?????????????????????
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
     * ??????????????????
     *
     * @param type
     */
    public void pleaseSelect(SelectType type) {
        if (!selectCheck(type)) {
            return;
        }
        switch (type) {
            case AGING:
                //??????
                aging();
                break;
            case LINE:
                //??????
                line();
                break;
            case WORKY_TYPE:
                //???????????????
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

    private void chooseDisposePerson() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CHOOSE_DISPOSE_PERSON).
                withString(RouteKey.KEY_ORG_ID, request.getDivideId()).
                withString(RouteKey.KEY_DIM_CODE, request.getTxCode()).
                navigation(this, RouterUtils.ACTIVITY_REQUEST_PERSON_CHOOSE);
    }

    private int rsDefaultPos = 0;

    /**
     * ????????????
     */
    private void resource() {
        List<String> resourceList = new ArrayList<>();
        List<ResourceChildBean> children = (List<ResourceChildBean>) resourceTypeBean.getChildren();
        for (ResourceChildBean childBean : children) {
            resourceList.add(childBean.getResourceName());
        }
        if (resourceList.size() == 0) {
            ToastUtil.show(this, "????????????");
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
                        binding.tvRes.setText(childBean.getResourceName());
                    }
                }
//                binding.setBean(request);
            }
        });
    }

    private int rsTypeDefaultPos = 0;
    private ResourceTypeBean resourceTypeBean;

    /**
     * ????????????
     */
    private void resourceType() {
        viewModel.getResourceInfos(request).observe(this, resourceTypeBeans -> {
            if (resourceTypeBeans.size() == 0) {
                ToastUtil.show(this, "??????????????????");
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
     * ????????????
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
                binding.tvOrderType.setText(request.getTypeName() + (!StringUtil.isNullStr(request.getEnvType2Name()) ? "" : "-" + request.getEnvType2Name()) + (!StringUtil.isNullStr(request.getEnvType3Name()) ? "" : "-" + request.getEnvType3Name()));
//                binding.setBean(request);
            }
        });
        selectWorkOrderTypeView.show(getSupportFragmentManager(), "");
    }

    int txDefaultPos = 0;

    /**
     * ????????????
     */
    private void line() {
        if (dictDataModelList.size() == 0) {
            ToastUtil.show(this, "????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictDataModelList) {
            if (!txStrList.contains(data.getName())) {
                txStrList.add(data.getName());
            }
        }
        BottomPicker.buildBottomPicker(this, txStrList, txDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                if (position != txDefaultPos) {
                    clearRequest(SelectType.LINE);
                }
                txDefaultPos = position;
                //????????????id
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
     * ??????view
     */
    private void aging() {
        //????????????view
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
            copy.setProjectName(request.getProjectName());
            request = copy;
            resourceTypeBean = null;
            binding.tvResource.setText("?????????");
            binding.tvRes.setText("?????????");
        }
        if (SelectType.WORKY_TYPE == type) {
            request.setType("");
            request.setResName(request.getResName());
            request.setTypeName("");
            request.setEnvType2Code("");
            request.setEnvType2Name("");
            request.setEnvType3Code("");
            request.setEnvType3Name("");
//            binding.tvResource.setText("?????????");
//            binding.tvRes.setText("?????????");
        }
        if (SelectType.RESOURCE_TYPE == type) {
            request.setResId("");
            request.setResName("");
            binding.tvResource.setText("?????????");
            binding.tvRes.setText("?????????");
        }
    }

    /**
     * ????????????????????????????????????
     */
    private boolean selectCheck(SelectType selectType) {
        if (selectType == SelectType.AGING) {
            return true;
        }
        if (!StringUtil.isNullStr(request.getDivideId())) {
            ToastUtil.show(this, "??????????????????");
            return false;
        }
        if (selectType == SelectType.LINE) {
            return true;
        }
        if (!StringUtil.isNullStr(request.getTxId())) {
            ToastUtil.show(this, "??????????????????");
            return false;
        }
        if (selectType == SelectType.RESOURCE) {
            if (resourceTypeBean == null) {
                ToastUtil.show(this, "????????????");
                return false;
            }
        }
        return true;
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    private boolean checkData() {
        if (!StringUtil.isNullStr(request.getTxId())) {
            ToastUtil.show(this, "???????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getType())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        if ("??????".equals(request.getTxName()) && !StringUtil.isNullStr(request.getEnvType2Code())) {
            ToastUtil.show(this, "???????????????????????????");
            return false;
        }
        if ("??????".equals(request.getTxName()) && !StringUtil.isNullStr(request.getEnvType3Code())) {
            ToastUtil.show(this, "???????????????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getProcId())) {
            ToastUtil.show(this, "??????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getOtLevel())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        String problemDescription = binding.ltQuestionDesc.getString();
        if (!StringUtil.isNullStr(problemDescription)) {
            ToastUtil.show(this, "??????????????????????????????????????????");
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
     * ??????????????????
     * @param radioGroup
     * @param i
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (R.id.rb_normal == i) {
            request.setOtLevel("1");
        }
        if (R.id.rb_general == i) {
            request.setOtLevel("2");
        }
        if (R.id.rb_warning == i) {
            request.setOtLevel("3");
        }
    }

    /**
     * ????????????
     *
     * @param orgModel
     */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
//        if (!orgModel.getId().equals(request.getDivideId())) {
        clearRequest(SelectType.AGING);
        request.setDivideId(orgModel.getId());
        request.setDivideCode(orgModel.getCode());
        request.setDivideName(orgModel.getName());
        binding.setBean(request);
        String[] split = orgModel.getPathName().split("/");
        if (split.length > 1) {

            request.setProjectName(split[split.length - 2]);
        }
//        }
    }

    /**
     * ????????????
     */
    private void uploadImages() {
        //??????????????????
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
        if (StringUtil.isNullStr(id)) {
            request.setId(id);
        }
        if (StringUtil.isNullStr(orderNo)) {//????????????
//            request.setOrderNo(orderNo);
            request.setF_ORIGINAL_CODE(orderNo);
        }
        if (StringUtil.isNullStr(id)) {//?????????ID
//            request.setOrderNo(orderNo);
            request.setF_ORIGINAL_ID(id);
        }
        if (StringUtil.isNullStr(mORIGINAL_TYPE)) {//???????????????
//            request.setOrderNo(orderNo);
            request.setF_ORIGINAL_TYPE(mORIGINAL_TYPE);
        }
        if (StringUtil.isNullStr(proInsId)) {//???????????????ID
//            request.setOrderNo(orderNo);
            request.setF_ORIGINAL_PROLNSTLD(proInsId);
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
                for (Uri uri : uris) {
                    addWater(uri);
                }
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
    private void addWater(Uri uri) {
        String file = FileProviderUtil.getUploadImagePath(uri);
        Observable.just(file).subscribeOn(Schedulers.io())
                .subscribe(path -> {
                    BitmapUtil.AddTimeWatermark(new File(path));
                    runOnUiThread(() -> {
                        if (uri != null) {
                            photoSelectAdapter.addPhotos(Arrays.asList(uri));
                        }
                    });
                });
    }

}
