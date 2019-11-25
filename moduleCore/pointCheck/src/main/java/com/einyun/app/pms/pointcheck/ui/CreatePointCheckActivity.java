package com.einyun.app.pms.pointcheck.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.pms.pointcheck.R;
import com.einyun.app.pms.pointcheck.databinding.ActivityPointCheckCreateBinding;
import com.einyun.app.pms.pointcheck.databinding.ItemPointCheckProjectEditBinding;
import com.einyun.app.pms.pointcheck.model.ProjectContentItemModel;
import com.einyun.app.pms.pointcheck.model.ProjectResultModel;
import com.einyun.app.pms.pointcheck.net.request.CreatePointCheckRequest;
import com.einyun.app.pms.pointcheck.viewmodel.CreateCheckViewModel;
import com.einyun.app.pms.pointcheck.viewmodel.ViewModelFactory;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck
 * @ClassName: CreatePointCheckActivity
 * @Description: 新增设备检点
 * @Author: chumingjun
 * @CreateDate: 2019/09/29 10:20
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/29 10:20
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_POINT_CHECK_CREATE)
public class CreatePointCheckActivity extends BaseHeadViewModelActivity<ActivityPointCheckCreateBinding, CreateCheckViewModel> {
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private String divideId;
    private String divideCode;
    private String divideName;
    //    ProjectContentAdapter contentAdapter;
    RVBindingAdapter<ItemPointCheckProjectEditBinding, ProjectContentItemModel> adapter;


    protected CreateCheckViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(CreateCheckViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_point_check_create;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_point_check_create);
        binding.setCallBack(this);
        divideId = SPUtils.get(getApplicationContext(), SPKey.KEY_BLOCK_ID, "").toString();
        divideName = SPUtils.get(getApplicationContext(), SPKey.KEY_BLOCK_NAME, "").toString();
        binding.tvCheckDivide.setText(divideName);
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        binding.pointCkImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        binding.pointCkImglist.setAdapter(photoSelectAdapter);
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
                    .maxSelectable(MAX_PHOTO_SIZE)
                    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, CreatePointCheckActivity.this);

        //点检事项集加载
        viewModel.projectItems.observe(this, items -> {
            BottomPicker.buildBottomPicker(this, items, (position, label) -> {

                binding.tvCheckProject.setText(label);
                viewModel.loadProjectContent(label).observe(this, projectContentModel -> {
                    binding.tvCheckObject.setText(projectContentModel.getResourceName());
                    binding.tvPointCheckObjAddress.setText(projectContentModel.getSpecificLocation());
                    if (adapter == null) {
                        adapter = new RVBindingAdapter<ItemPointCheckProjectEditBinding, ProjectContentItemModel>(this, com.einyun.app.pms.pointcheck.BR.project) {
                            @Override
                            public void onBindItem(ItemPointCheckProjectEditBinding binding, ProjectContentItemModel model, int position) {
                                binding.tvCheckContent.setText(model.getCheckContent());
                                if (model.getCheckType() == 2) {
                                    binding.ivCheckResult.setVisibility(View.VISIBLE);
                                    binding.pointCheckRangSplit.setVisibility(View.GONE);
                                    binding.pointCheckRangContainer.setVisibility(View.GONE);
                                    binding.edCheckResult.setText("");
                                    binding.tvCheckRange.setText(R.string.item_qualified);
                                    binding.edCheckResult.setHint("");
                                    binding.edCheckResult.setInputType(InputType.TYPE_NULL);
                                    binding.edCheckResult.setFocusable(false);
                                    binding.edCheckResult.setOnClickListener(v -> {
                                        BottomPicker.buildBottomPicker(CreatePointCheckActivity.this, Arrays.asList(getResources().getString(R.string.item_no_qualified), getResources().getString(R.string.item_qualified)), (pos, label) -> {
                                            binding.edCheckResult.setText(label);
                                        });
                                    });
                                    binding.tvProjectName.setText(getResources().getString(R.string.name_project) + (position + 1));
                                    binding.tvCheckContent.setText(model.getCheckContent());
                                    binding.tvCheckNote.setText(model.getRemark());
                                } else {
                                    binding.edCheckResult.setOnClickListener(v -> {

                                    });
                                    binding.pointCheckRangSplit.setVisibility(View.VISIBLE);
                                    binding.pointCheckRangContainer.setVisibility(View.VISIBLE);
                                    binding.ivCheckResult.setVisibility(View.INVISIBLE);
                                    binding.edCheckResult.setText("");
                                    binding.edCheckResult.setHint(R.string.alert_input);
                                    binding.edCheckResult.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                                    binding.tvCheckRange.setText(model.getMinValue() + "-" + model.getMaxVal());
                                    binding.edCheckResult.setFocusable(true);
                                }
                                binding.edCheckResult.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        String value = s.toString();
                                        int result = 0;
                                        if (model.getCheckType() == 2) {
                                            if (value.equals(getResources().getString(R.string.item_qualified))) {
                                                result = 1;
                                            }
                                        } else {
                                            if (TextUtils.isEmpty(value)) {
                                                result = -1;
                                            } else {
                                                try {
                                                    result = Integer.parseInt(value);
                                                } catch (NumberFormatException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        model.setCheckResult(result);
                                    }
                                });
                            }

                            @Override
                            public int getLayoutId() {
                                return R.layout.item_point_check_project_edit;
                            }
                        };
                        binding.rvProjects.setLayoutManager(new LinearLayoutManager(this));
                        binding.rvProjects.setNestedScrollingEnabled(false);
                        binding.rvProjects.setAdapter(adapter);
                    }
                    adapter.addAll(projectContentModel.getContentList());
                });
            });
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
        }
        if(resultCode==RESULT_OK){
            if(requestCode==RouterUtils.ACTIVITY_REQUEST_BLOCK_CHOOSE){
                divideId=data.getStringExtra(DataConstants.KEY_BLOCK_ID);
                divideName=data.getStringExtra(DataConstants.KEY_BLOCK_NAME);
                divideCode=data.getStringExtra(DataConstants.KEY_BLOCK_CODE);
                binding.tvCheckDivide.setText(divideName);
            }
        }
    }

    public void onDivideClick() {
        //选择组织地块
        ARouter.getInstance().build(RouterUtils.ACTIVITY_BLOCK_CHOOSE)
                .withString(RouteKey.KEY_USER_ID, userModuleService.getUserId())
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_BLOCK_CHOOSE);
    }

    public void onProjectClick() {
        if (TextUtils.isEmpty(divideId)) {
            ToastUtil.show(getApplication(),R.string.alert_choose_massif);
            return;
        }
        //加载所有点检事项
        viewModel.loadProjects(divideId);
    }

    public void onSubmitClick() {
        if (!validateForm()) {
            return;
        }
        uploadImages();
    }

    /**
     * 表单验证
     */
    private boolean validateForm() {
        if (TextUtils.isEmpty(binding.tvCheckDivide.getText())) {
            ToastUtil.show(getApplicationContext(), R.string.alert_choose_massif);
            return false;
        }
        if (TextUtils.isEmpty(binding.tvCheckProject.getText())) {
            ToastUtil.show(getApplicationContext(), R.string.alert_choose_project);
            return false;
        }
        if (!validateProjectItems()) {
            return false;
        }
//        if(photoSelectAdapter.getSelectedPhotos()==null||photoSelectAdapter.getSelectedPhotos().size()<=0){
//            ToastUtil.show(R.string.alert_choose_pic);
//            return false;
//        }
        return true;
    }

    private boolean validateProjectItems() {
        List<ProjectContentItemModel> models = adapter.getItemModels();
        if (models != null) {
            for (ProjectContentItemModel model : models) {
                if (model.getCheckResult() < 0) {
                    ToastUtil.show(getApplication(), R.string.alert_input_result);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 上传照片
     */
    private void uploadImages() {
        //开始上传照片
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, data -> {
            hideLoading();
            if (data!=null) {
                viewModel.create(buidRequest(),data).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(getApplicationContext(), R.string.alert_submit_error);
                    } else {
                        finish();
                    }
                });
            }else{
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_failed);
            }
        });
    }


    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    /**
     * CreatePointCheckRequest
     *
     * @return
     */
    private CreatePointCheckRequest buidRequest() {
        CreatePointCheckRequest request = new CreatePointCheckRequest();
        request.setMassifId(divideId);
        request.setCreateId(userModuleService.getUserId());
        request.setResults(buildResultList());
        request.setCheckProjectId(viewModel.loadProjectIdByName(binding.tvCheckProject.getText().toString().trim()));
        request.setRemark(binding.etCheckNote.getText().toString());
        return request;
    }

    private List<ProjectResultModel> buildResultList() {
        List<ProjectResultModel> list = new ArrayList<>();
        List<ProjectContentItemModel> items = adapter.getItemModels();
        for (ProjectContentItemModel model : items) {
            ProjectResultModel resultModel = new ProjectResultModel();
            resultModel.setCheckContentId(model.getId());
            resultModel.setCheckResult(model.getCheckResult() + "");
            list.add(resultModel);
        }
        return list;
    }

}
