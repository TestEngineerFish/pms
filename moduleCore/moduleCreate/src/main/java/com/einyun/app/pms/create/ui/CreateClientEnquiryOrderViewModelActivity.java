package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectHouseView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.CheckUtil;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.model.TypeBigAndSmallModel;
import com.einyun.app.library.workorder.net.request.CreateClientEnquiryOrderRequest;
import com.einyun.app.library.uc.usercenter.model.HouseModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.pms.create.R;
import com.einyun.app.pms.create.SelectType;
import com.einyun.app.pms.create.databinding.ActivityCreateClientEnquiryOrderBinding;
import com.einyun.app.pms.create.viewmodel.CreateViewModel;
import com.einyun.app.pms.create.viewmodel.CreateViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * ??????????????????
 */
@Route(path = RouterUtils.ACTIVITY_CREATE_CLIENT_ENQUIRY_ORDER)
public class CreateClientEnquiryOrderViewModelActivity extends BaseHeadViewModelActivity<ActivityCreateClientEnquiryOrderBinding, CreateViewModel> implements  PeriodizationView.OnPeriodSelectListener {
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private CreateClientEnquiryOrderRequest request;
    private List<DictDataModel> dictComplainWayList = new ArrayList<>();
    private List<TypeAndLine> lines = new ArrayList<>();
    private List<DictDataModel> lineDictDataModelList = new ArrayList<>();
    private List<TypeBigAndSmallModel.ChildrenBeanX> typeBigs=new ArrayList<>();
    private TypeBigAndSmallModel.ChildrenBeanX childrenBeanX;

    @Override
    protected CreateViewModel initViewModel() {
        return new ViewModelProvider(this, new CreateViewModelFactory()).get(CreateViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_client_enquiry_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.create_enquiry_order_title);
        request = new CreateClientEnquiryOrderRequest();
        binding.setCallBack(this);
        viewModel.getByTypeKey(Constants.ENQUIRY_WAY).observe(this, dictDataModels -> {
            dictComplainWayList = dictDataModels;
        });
//        viewModel.typeAndLineList().observe(this, lines -> {
//            this.lines = lines;
//        });
        viewModel.typeBigAndSmall().observe(this, model -> {
            typeBigs = model.getChildren();
        });
        selectPng();
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
                    .maxSelectable(MAX_PHOTO_SIZE-photoSelectAdapter.getSelectedPhotos().size())
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
            case ENQUIRY_WAY:
                //????????????
                complainWay();
                break;
//            case ENQUIRY_TYPE:
//                complainType();
//                break;
            case ENQUIRY_TYPE_BIG:
                complainType();
                break;
            case ENQUIRY_TYPE_SMALL:
                complainTypeSmall();
                break;
            case HOUSE:
                selectHouse();
                break;
        }
    }



    private void selectHouse(){
        SelectHouseView view = new SelectHouseView(request.getBizData().getDivideId());
        view.setWorkTypeListener(new SelectHouseView.OnWorkTypeSelectListener() {
            @Override
            public void onWorkTypeSelectListener(List<HouseModel> model) {
                request.getBizData().setBuildId(model.get(0).getId());
                if (model.size() >= 2) {
                    request.getBizData().setUnitId(model.get(1).getId());
                }
                if (model.size() >= 3) {
                    request.getBizData().setHouseId(model.get(2).getId());
                    request.getBizData().setHouse(model.get(2).getName());
                }
                binding.setBean(request);
                if (StringUtil.isNullStr(request.getBizData().getHouseId())) {
                    viewModel.getUserInfoByHouseId(request.getBizData().getHouseId()).observe(CreateClientEnquiryOrderViewModelActivity.this, users -> {
                        if (users != null && users.size()!=0){
                            binding.phone.setText(users.get(0).getCellphone());
                            binding.userName.setText(users.get(0).getName());
                        }
                    });
                }
            }
        });
        view.show(getSupportFragmentManager(), "");
    }


    int cwDefaultPos = 0;

    /**
     * ????????????
     */
    private void complainWay() {
        if (dictComplainWayList.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictComplainWayList) {
            if (!data.getKey().equals("400") &&
                    !data.getKey().equals("proprietor_app") &&
                    !data.getKey().equals("owner_app") &&
                    !data.getKey().equals("mobile_association")) {
                txStrList.add(data.getName());
            }
        }
        BottomPicker.buildBottomPicker(this, txStrList, cwDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                cwDefaultPos = position;
                //????????????id
                for (DictDataModel childBean : dictComplainWayList) {
                    if (childBean.getName().equals(txStrList.get(position))) {
                        request.getBizData().setWay(childBean.getName());
                        request.getBizData().setWayId(childBean.getKey());
                    }
                }
                binding.setBean(request);
            }
        });
    }

    int ctDefaultPos = 0;
    int smallDefaultPos = 0;

    /**
     * ????????????
     */
    private void complainType() {
        if (typeBigs.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (TypeBigAndSmallModel.ChildrenBeanX childrenBeanX : typeBigs) {
            txStrList.add(childrenBeanX.getDataName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, ctDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {

                smallDefaultPos=0;
                childrenBeanX = typeBigs.get(position);
                if (childrenBeanX!=null) {
                    request.getBizData().setCateId(childrenBeanX.getDataKey());
                    request.getBizData().setCate(childrenBeanX.getDataName());
                    if (ctDefaultPos!=position) {
                        request.getBizData().setSubCate("");
                        request.getBizData().setSubCateId("");
                    }
                    binding.setBean(request);
                }
                ctDefaultPos = position;
            }
        });
    }
    /**
     * ????????????
     */
    private void complainTypeSmall() {
        if (typeBigs.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        if (!StringUtil.isNullStr(request.getBizData().getCate())) {
            ToastUtil.show(this, "????????????????????????");
            return ;
        }
        List<String> txStrList = new ArrayList<>();

        for (TypeBigAndSmallModel.ChildrenBeanX.ChildrenBean child : childrenBeanX.getChildren()) {
            txStrList.add(child.getDataName());
        }


        BottomPicker.buildBottomPicker(this, txStrList, smallDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                ctDefaultPos = position;
                if (childrenBeanX!=null) {
                    TypeBigAndSmallModel.ChildrenBeanX.ChildrenBean childrenBean = childrenBeanX.getChildren().get(position);
                    request.getBizData().setSubCateId(childrenBean.getDataKey());
                    request.getBizData().setSubCate(childrenBean.getDataName());
                    binding.setBean(request);
                }
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
            request.getBizData().setBuildId("");
            request.getBizData().setUnitId("");
            request.getBizData().setHouseId("");
            request.getBizData().setHouse("");
        }
    }

    /**
     * ????????????????????????????????????
     */
    private boolean selectCheck(SelectType selectType) {
        if (selectType == SelectType.HOUSE) {
            if (!StringUtil.isNullStr(request.getBizData().getDivideId())) {
                ToastUtil.show(this, "??????????????????");
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
        if (!StringUtil.isNullStr(request.getBizData().getDivideName())) {
            ToastUtil.show(this, "???????????????");
            return false;
        }
        if (!StringUtil.isNullStr(binding.phone.getText().toString())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        if (!CheckUtil.getInstance(this).isMatch(binding.phone,CheckUtil.REG_PHONE)){
            return false;
        }
        if (!StringUtil.isNullStr(binding.userName.getText().toString())) {
            ToastUtil.show(this, "??????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getWay())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getCate())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getSubCate())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        String problemDescription = binding.ltQuestionDesc.getString();
        if (!StringUtil.isNullStr(problemDescription)) {
            ToastUtil.show(this, "?????????????????????");
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



    /**
     * ????????????
     *
     * @param orgModel
     */
    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        if (!orgModel.getId().equals(request.getBizData().getDivideId())) {
            clearRequest(SelectType.AGING);
            request.getBizData().setDivideId(orgModel.getId());
            request.getStartFlowParamObject().getVars().setDivideCode(orgModel.getCode());
            request.getBizData().setDivideName(orgModel.getName());
            binding.setBean(request);
        }
    }

    /**
     * ????????????
     */
    private void uploadImages() {
        //??????????????????
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.createClientEnquiryOrder(buidRequest(), data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_client_enquire_order_success);
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    private CreateClientEnquiryOrderRequest buidRequest() {
        request.getBizData().setContent(binding.ltQuestionDesc.getString());
        request.getBizData().setMobile(binding.phone.getText().toString());
        request.getBizData().setUserName(binding.userName.getText().toString());
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
