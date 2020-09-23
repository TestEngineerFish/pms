package com.einyun.app.pms.create.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.component.searchhistory.SingleSearchFragment;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectHouseView;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.CheckUtil;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.net.request.CreateClientComplainOrderRequest;
import com.einyun.app.library.workorder.net.request.CreateClientEnquiryOrderRequest;
import com.einyun.app.library.uc.usercenter.model.HouseModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.einyun.app.pms.create.BR;
import com.einyun.app.pms.create.R;
import com.einyun.app.pms.create.SelectType;
import com.einyun.app.pms.create.databinding.ActivityCreateClientComplainOrderBinding;
import com.einyun.app.pms.create.databinding.ItemComplainInfoBinding;
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

import static androidx.test.InstrumentationRegistry.getContext;

/**
 * 创建客户询问
 */
@Route(path = RouterUtils.ACTIVITY_CREATE_CLIENT_COMPLAIN_ORDER)
public class CreateClientComplainOrderViewModelActivity extends BaseHeadViewModelActivity<ActivityCreateClientComplainOrderBinding, CreateViewModel> implements PeriodizationView.OnPeriodSelectListener, TextWatcher {
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private CreateClientComplainOrderRequest request;
    private List<DictDataModel> dictComplainWayList = new ArrayList<>();
    private List<DictDataModel> dictComplainNatureList = new ArrayList<>();
    private List<TypeAndLine> lines = new ArrayList<>();
    private RVBindingAdapter<ItemComplainInfoBinding, ComplainModel> adapter;

    @Override
    protected CreateViewModel initViewModel() {
        return new ViewModelProvider(this, new CreateViewModelFactory()).get(CreateViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_client_complain_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.create_complain_order_title);
        request = new CreateClientComplainOrderRequest();
        binding.setCallBack(this);
        //获取投诉、问询、报修方式
        viewModel.getByTypeKey(Constants.ENQUIRY_WAY).observe(this, dictDataModels -> {
            dictComplainWayList = dictDataModels;
        });
        //获取投诉性质
        viewModel.getByTypeKey(Constants.COMPLAIN_WAY).observe(this, dictDataModels -> {
            dictComplainNatureList = dictDataModels;
        });
        viewModel.typeAndLineList().observe(this, lines -> {
            this.lines = lines;
        });
        selectPng();
        binding.phone.addTextChangedListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.cv.setLayoutManager(mLayoutManager);

        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemComplainInfoBinding, ComplainModel>(this, BR.model) {
                @Override
                public void onBindItem(ItemComplainInfoBinding binding, ComplainModel model, int position) {
                    binding.llAddComplainInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_ADD_COMPLAIN_INFO)
                                    .withString(RouteKey.ID,model.getID_()).navigation();
                        }
                    });
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_complain_info;
                }
            };
        }
        binding.cv.setAdapter(adapter);
        binding.cv.addItemDecoration(new SpacesItemDecoration(30));
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
                    .maxSelectable(MAX_PHOTO_SIZE - photoSelectAdapter.getSelectedPhotos().size())
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
                //园区
                aging();
                break;
            case COMPLAIN_WAY:
                //问询方式
                complainWay();
                break;
            case COMPLAIN_TYPE:
                complainType();
                break;
            case COMPLAIN_NATURE:
                complainNature();
                break;
            case HOUSE:
                selectHouse();
                break;
        }
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
                    viewModel.getUserInfoByHouseId(request.getBizData().getHouseId()).observe(CreateClientComplainOrderViewModelActivity.this, users -> {
                        if (users != null && users.size() != 0) {
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
     * 投诉性质
     */
    private void complainNature() {
        if (dictComplainNatureList.size() == 0) {
            ToastUtil.show(this, "暂无投诉性质");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (DictDataModel data : dictComplainNatureList) {
            txStrList.add(data.getName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, cnDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                cnDefaultPos = position;
                //获取条线id
                for (DictDataModel childBean : dictComplainNatureList) {
                    if (childBean.getName().equals(txStrList.get(position))) {
                        request.getBizData().setPropertyName(childBean.getName());
                        request.getBizData().setPropertyId(childBean.getKey());
                    }
                }
                binding.setBean(request);
            }
        });
    }

    int cwDefaultPos = 0;

    /**
     * 投诉方式
     */
    private void complainWay() {
        if (dictComplainWayList.size() == 0) {
            ToastUtil.show(this, "暂无投诉方式");
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
                //获取条线id
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

    /**
     * 投诉类别
     */
    private void complainType() {
        if (lines.size() == 0) {
            ToastUtil.show(this, "暂无投诉类别");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (TypeAndLine line : lines) {
            txStrList.add(line.getDataName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, ctDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                ctDefaultPos = position;
                for (TypeAndLine line : lines) {
                    if (line.getDataName().equals(txStrList.get(position))) {
                        request.getBizData().setCateId(line.getDataKey());
                        request.getBizData().setCate(line.getDataName());
                        request.getBizData().setLineKey(line.getMajorLine().getKey());
                        request.getBizData().setLineName(line.getMajorLine().getName());
                    }
                }
                binding.setBean(request);
            }
        });
    }

    /**
     * 园区view
     */
    private void aging() {
        //弹出园区view
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
     * 工单类型和负责人前置校验
     */
    private boolean selectCheck(SelectType selectType) {
        if (selectType == SelectType.HOUSE) {
            if (!StringUtil.isNullStr(request.getBizData().getDivideId())) {
                ToastUtil.show(this, "请先选择园区");
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
        if (!StringUtil.isNullStr(request.getBizData().getDivideName())) {
            ToastUtil.show(this, "请选择园区");
            return false;
        }
        if (!StringUtil.isNullStr(binding.phone.getText().toString())) {
            ToastUtil.show(this, "请填写联系电话");
            return false;
        }
        if (!CheckUtil.getInstance(this).isMatch(binding.phone,CheckUtil.REG_PHONE)){
            return false;
        }
        if (!StringUtil.isNullStr(binding.userName.getText().toString())) {
            ToastUtil.show(this, "请填写投诉人");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getWay())) {
            ToastUtil.show(this, "请选择投诉方式");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getCate())) {
            ToastUtil.show(this, "请选择投诉类别");
            return false;
        }
        if (!StringUtil.isNullStr(request.getBizData().getPropertyName())) {
            ToastUtil.show(this, "请选择投诉性质");
            return false;
        }
        String problemDescription = binding.ltQuestionDesc.getString();
        if (!StringUtil.isNullStr(problemDescription)) {
            ToastUtil.show(this, "请填写投诉内容");
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
     * 园区回调
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
     * 上传照片
     */
    private void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data != null) {
                viewModel.createClientComplainOrder(buidRequest(), data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_client_complain_order_success);
                        finish();
                    }
                });
            } else {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }

    private CreateClientComplainOrderRequest buidRequest() {
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
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        viewModel.complainWorkListdPage(s.toString()).observe(this, result -> {
            if (result.getRows() != null && result.getRows().size() != 0) {
                binding.complainInfo.setVisibility(View.VISIBLE);
                binding.cv.setVisibility(View.VISIBLE);
                adapter.setDataList(result.getRows());
                binding.complainInfo.setText(String.format(getResources().getString(R.string.text_user_add_complain_info), String.valueOf(result.getRows().size())));
            } else {
                binding.complainInfo.setVisibility(View.GONE);
                binding.cv.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
