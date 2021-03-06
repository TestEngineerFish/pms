package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.model.BottomPickerModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectHouseView;
import com.einyun.app.common.ui.widget.SelectRepairsTypeView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.CheckUtil;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.model.Door;
import com.einyun.app.library.workorder.model.DoorResult;
import com.einyun.app.library.workorder.net.request.CreateClientComplainOrderRequest;
import com.einyun.app.library.uc.usercenter.model.HouseModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.library.workorder.net.request.CreateClientRepairOrderRequest;
import com.einyun.app.pms.create.R;
import com.einyun.app.pms.create.SelectType;
import com.einyun.app.pms.create.databinding.ActivityCreateClientRepairsOrderBinding;
import com.einyun.app.pms.create.viewmodel.CreateViewModel;
import com.einyun.app.pms.create.viewmodel.CreateViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * ??????????????????
 */
@Route(path = RouterUtils.ACTIVITY_CREATE_CLIENT_REPAIRS_ORDER)
public class CreateClientRepairsOrderViewModelActivity extends BaseHeadViewModelActivity<ActivityCreateClientRepairsOrderBinding, CreateViewModel> implements PeriodizationView.OnPeriodSelectListener {
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private CreateClientRepairOrderRequest request;
    private List<DictDataModel> dictWayList = new ArrayList<>();
    private List<DictDataModel> dictNatureList = new ArrayList<>();
    private List<DictDataModel> dictTimeList = new ArrayList<>();
    private List<Door> doorResult;

    @Override
    protected CreateViewModel initViewModel() {
        return new ViewModelProvider(this, new CreateViewModelFactory()).get(CreateViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_client_repairs_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.create_repairs_order_title);
        request = new CreateClientRepairOrderRequest();
        binding.setCallBack(this);
        //????????????????????????????????????
        viewModel.getByTypeKey(Constants.ENQUIRY_WAY).observe(this, dictDataModels -> {
            dictWayList = dictDataModels;
        });
        //???????????????????????????
        viewModel.repairTypeList().observe(this, doorResult -> {
            this.doorResult = doorResult.getChildren();
        });
        //????????????????????????
        viewModel.getByTypeKey(Constants.REPAIR_NATURE).observe(this, dictDataModels -> {
            dictNatureList = dictDataModels;
        });
        //????????????????????????
        viewModel.getByTypeKey(Constants.REPAIR_TIME).observe(this, dictDataModels -> {
            dictTimeList = dictDataModels;
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
            case REPAIRS_WAY:
                //????????????
                complainWay();
                break;
            case REPAIRS_TYPE:
                repairType();
                break;
            case REPAIRS_NATURE:
                complainNature();
                break;
            case HOUSE:
                selectHouse();
                break;
            case REPAIRS_LOCATION:
                complainLocation();
                break;
            case REPAIRS_TIME:
                repairTime();
                break;
        }
    }

    private void repairType() {
        if (doorResult.get(clDefaultPos).getChildren() == null || doorResult.get(clDefaultPos).getChildren().size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        SelectRepairsTypeView view = new SelectRepairsTypeView(doorResult.get(clDefaultPos).getChildren());
        view.setWorkTypeListener(new SelectRepairsTypeView.OnWorkTypeSelectListener() {
            @Override
            public void onWorkTypeSelectListener(List<Door> model) {
                request.getBizData().setLineKey(model.get(0).getExpand().getMajorLine().getKey());
                request.getBizData().setLineName(model.get(0).getExpand().getMajorLine().getName());
                request.getBizData().setCateLv1(model.get(0).getDataName());
                request.getBizData().setCateLv1Id(model.get(0).getDataKey());
                request.getBizData().setCateLv2(model.get(1).getDataName());
                request.getBizData().setCateLv2Id(model.get(1).getDataKey());
                request.getBizData().setCateLv3(model.get(2).getDataName());
                request.getBizData().setCateLv3Id(model.get(2).getDataKey());
                binding.setBean(request);
            }
        });
        view.show(getSupportFragmentManager(), "");
    }

    int rtTimeDefaultPos = 0;
    int rtDateDefaultPos = 0;

    private void repairTime() {
        if (dictTimeList == null || dictTimeList.size() == 0) {
            ToastUtil.show(this, "????????????????????????");
            return;
        }
        List<BottomPickerModel> models = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            BottomPickerModel model = new BottomPickerModel();
            model.setData(getOldDate(i));
            List<String> dataList = new ArrayList<>();
            for (DictDataModel data : dictTimeList) {
                dataList.add(data.getName());
            }
            model.setDataList(dataList);
            models.add(model);
        }

        BottomPicker.buildBottomPicker(this, models, rtDateDefaultPos, rtTimeDefaultPos, new BottomPicker.OnItemDoublePickListener() {
            @Override
            public void onPick(int position1, int position2) {
                rtDateDefaultPos = position1;
                rtTimeDefaultPos = position2;
                BottomPickerModel model = models.get(position1);
                binding.time.setText(model.getData() + " " + model.getDataList().get(position2));
                request.getBizData().setAppointTime(model.getData());
                request.getBizData().setAppointTimePeriodId(dictTimeList.get(position2).getKey());
                request.getBizData().setAppointTimePeriod(dictTimeList.get(position2).getName());
            }
        });
    }

    private void selectHouse() {
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
                    viewModel.getUserInfoByHouseId(request.getBizData().getHouseId()).observe(CreateClientRepairsOrderViewModelActivity.this, users -> {
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

    int cnDefaultPos = 0;

    /**
     * ????????????
     */
    private void complainNature() {
        if (dictNatureList.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictNatureList) {
            txStrList.add(data.getName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, cnDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                cnDefaultPos = position;
                //????????????id
                for (DictDataModel childBean : dictNatureList) {
                    if (childBean.getName().equals(txStrList.get(position))) {
                        request.getBizData().setPropertyAss(childBean.getName());
                        request.getBizData().setPropertyAssId(childBean.getKey());
                    }
                }
                binding.setBean(request);
            }
        });
    }

    int cwDefaultPos = 0;

    /**
     * ????????????
     */
    private void complainWay() {
        if (dictWayList.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictWayList) {
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
                for (DictDataModel childBean : dictWayList) {
                    if (childBean.getName().equals(txStrList.get(position))) {
                        request.getBizData().setWay(childBean.getName());
                        request.getBizData().setWayId(childBean.getKey());
                    }
                }
                binding.setBean(request);
            }
        });
    }

    int clDefaultPos = 0;

    /**
     * ????????????
     */
    private void complainLocation() {
        if (doorResult.size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (Door data : doorResult) {
            txStrList.add(data.getDataName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, clDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                if (position != clDefaultPos){
                    clearRequest(SelectType.REPAIRS_LOCATION);
                }
                clDefaultPos = position;
                for (Door data : doorResult) {
                    if (data.getDataName().equals(txStrList.get(position))) {
                        request.getBizData().setArea(data.getDataName());
                        request.getBizData().setAreaId(data.getDataKey());
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
            request.getBizData().setBuildId("");
            request.getBizData().setUnitId("");
            request.getBizData().setHouseId("");
            request.getBizData().setHouse("");
        }
        if (SelectType.REPAIRS_LOCATION == type) {
            //??????????????????
            request.getBizData().setLineKey("");
            request.getBizData().setLineName("");
            request.getBizData().setCateLv1("");
            request.getBizData().setCateLv1Id("");
            request.getBizData().setCateLv2("");
            request.getBizData().setCateLv2Id("");
            request.getBizData().setCateLv3("");
            request.getBizData().setCateLv3Id("");
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
        if (selectType == SelectType.REPAIRS_TYPE) {
            //???????????????????????????
            if (!StringUtil.isNullStr(request.getBizData().getAreaId())) {
                ToastUtil.show(this, "????????????????????????");
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
        if (!StringUtil.isNullStr(request.getBizData().getAreaId())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getCateLv3())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getPropertyAssId())) {
            ToastUtil.show(this, "?????????????????????");
            return false;
        }
        //?????????????????????  ??????????????????????????????
        if ("indoor".equals(request.getBizData().getAreaId())) {
            if (!StringUtil.isNullStr(request.getBizData().getAppointTime())) {
                new AlertDialog(this).builder().
                        setTitle(getResources().getString(R.string.tip))
                        .setMsg("???????????????????????????").setPositiveButton("????????????", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                return false;
            }
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
                viewModel.createClientRepairOrder(buidRequest(), data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_client_repair_order_success);
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    private CreateClientRepairOrderRequest buidRequest() {
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
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

}
