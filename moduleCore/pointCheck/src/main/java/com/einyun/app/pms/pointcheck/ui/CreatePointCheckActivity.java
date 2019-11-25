package com.example.shimaostaff.pointcheck.ui;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shimaostaff.R;
import com.example.shimaostaff.activity.BaseActivity;
import com.example.shimaostaff.blockchoose.BlockChooseActivity;
import com.example.shimaostaff.checkworkordersdetail.adapter.PhotoSelectAdapter;
import com.example.shimaostaff.net.Constants;
import com.example.shimaostaff.pointcheck.model.ProjectContentItemModel;
import com.example.shimaostaff.pointcheck.model.ProjectResultModel;
import com.example.shimaostaff.pointcheck.model.State;
import com.example.shimaostaff.pointcheck.net.request.CreatePointCheckRequest;
import com.example.shimaostaff.pointcheck.viewmodel.CreateCheckViewModel;
import com.example.shimaostaff.tools.Glide4Engine;
import com.example.shimaostaff.tools.ToastUtil;
import com.example.shimaostaff.view.BottomPicker;
import com.example.shimaostaff.view.MyApp;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
public class CreatePointCheckActivity extends BaseActivity {

    @Bind(R.id.tv_check_divide)
    TextView tvCheckBlock;
    @Bind(R.id.tv_check_project)
    TextView tvCheckProject;
    @Bind(R.id.et_check_object)
    TextView tvCheckObject;
    @Bind(R.id.et_check_point_obj_address)
    TextView tvCheckPointObjAddress;
    @Bind(R.id.et_check_note)
    EditText etCheckNote;
    @Bind(R.id.point_ck_imglist)
    RecyclerView pointCkImglist;
    @Bind(R.id.rv_projects)
    RecyclerView rvProject;
    @Bind(R.id.btn_submmit)
    Button btnSubmmit;

    private static final int REQUEST_CODE_PICK = 1001;
    private final int MAX_PHOTO_SIZE = 4;
    PhotoSelectAdapter photoSelectAdapter;
    private String divideId;
    private String divideCode;
    private String divideName;
    private final int RQUEST_CODE_DIVIDE = 11;
    CreateCheckViewModel viewModel;
    ProjectContentAdapter contentAdapter;

    @Override
    protected int getResourceId() {
        return R.layout.activity_add_point_check;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_point_check_create);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        super.initView();
        viewModel = ViewModelProviders.of(this).get(CreateCheckViewModel.class);
        divideId = MyApp.get().diKuaiId();
        divideName = MyApp.get().diKuaiName();
        tvCheckBlock.setText(divideName);
        photoSelectAdapter = new PhotoSelectAdapter(this);//图片选择适配器
        pointCkImglist.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));//设置横向
        pointCkImglist.setAdapter(photoSelectAdapter);
        photoSelectAdapter.setAddListener(selectedSize -> {
            if(photoSelectAdapter.getSelectedPhotos().size()>=MAX_PHOTO_SIZE){
                ToastUtil.show("最多可上传4张照片");
                return;
            }
            Matisse.from(this) //加号添加图片
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, Constants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE)
    //                .maxSelectable(4 - (photoSelectAdapter.getItemCount() - 1))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(REQUEST_CODE_PICK);
        }, CreatePointCheckActivity.this);

        //点检事项集加载
        viewModel.projectItems.observe(this, items -> {
            BottomPicker.buildBottomPicker(this, items, (position, label) -> {

                tvCheckProject.setText(label);
                viewModel.loadProjectContent(label).observe(this, projectContentModel -> {
                    tvCheckObject.setText(projectContentModel.getResourceName());
                    tvCheckPointObjAddress.setText(projectContentModel.getSpecificLocation());
                    if (contentAdapter == null) {
                        contentAdapter = new ProjectContentAdapter(projectContentModel.getContentList());
                        rvProject.setLayoutManager(new LinearLayoutManager(this));
                        rvProject.setNestedScrollingEnabled(false);
                        rvProject.setAdapter(contentAdapter);
                    } else {
                        contentAdapter.updateContent(projectContentModel.getContentList());
                    }
                });
            });
        });

        /**
         * Loading Dialog show/hide
         */
        viewModel.state.observe(this, state -> {
            if (state == State.HIDELOADING) {
                hideLoading();
            } else if (state == State.SHOWLOADING) {
                showLoading();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoSelectAdapter.addPhotos(uris);
            }
        } else if (requestCode == 11) {
            if (resultCode == -1) {
                divideId = data.getStringExtra("DiKuaiID");
                divideCode = data.getStringExtra("DiKuaiCode");
                divideName = data.getStringExtra("DiKuaiValue");
                tvCheckBlock.setText(divideName);
            }
        }
    }

    @OnClick({R.id.tv_check_divide, R.id.iv_check_divide_arrow})
    public void onDivideClick() {
        //选择组织地块
        Intent intent = new Intent(this, BlockChooseActivity.class);
        startActivityForResult(intent, RQUEST_CODE_DIVIDE);
    }

    @OnClick({R.id.tv_check_project, R.id.iv_check_project_arrow})
    public void onProjectClick() {
        if (TextUtils.isEmpty(divideId)) {
            ToastUtil.show(R.string.alert_choose_massif);
            return;
        }
        //加载所有点检事项
        viewModel.loadProjects(divideId);
    }

    @OnClick(R.id.btn_submmit)
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
        if (TextUtils.isEmpty(tvCheckBlock.getText())) {
            ToastUtil.show(R.string.alert_choose_massif);
            return false;
        }
        if (TextUtils.isEmpty(tvCheckProject.getText())) {
            ToastUtil.show(R.string.alert_choose_project);
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
        List<ProjectContentItemModel> models = contentAdapter.getItemModels();
        if (models != null) {
            for (ProjectContentItemModel model : models) {
                if (model.getCheckResult() < 0) {
                    ToastUtil.show(R.string.alert_input_result);
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
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, state -> {
            if (state) {
                viewModel.create(buidRequest()).observe(this, flag -> {
                    if (!flag) {
                        ToastUtil.show(R.string.alert_submit_error);
                    } else {
                        finish();
                    }
                });
            }
        });
    }

    /**
     * CreatePointCheckRequest
     *
     * @return
     */
    private CreatePointCheckRequest buidRequest() {
        CreatePointCheckRequest request = new CreatePointCheckRequest();
        request.setMassifId(divideId);
        request.setCreateId(MyApp.get().userId());
        request.setResults(buildResultList());
        request.setCheckProjectId(viewModel.loadProjectIdByName(tvCheckProject.getText().toString().trim()));
        request.setRemark(etCheckNote.getText().toString());
        return request;
    }

    private List<ProjectResultModel> buildResultList() {
        List<ProjectResultModel> list = new ArrayList<>();
        List<ProjectContentItemModel> items = contentAdapter.getItemModels();
        for (ProjectContentItemModel model : items) {
            ProjectResultModel resultModel = new ProjectResultModel();
            resultModel.setCheckContentId(model.getId());
            resultModel.setCheckResult(model.getCheckResult() + "");
            list.add(resultModel);
        }
        return list;
    }

    /**
     * 点检事项内容适配器
     */
    class ProjectContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<ProjectContentItemModel> itemModels;

        public List<ProjectContentItemModel> getItemModels() {
            return itemModels;
        }

        public void updateContent(List<ProjectContentItemModel> contents) {
            if (itemModels == null) {
                itemModels = contents;
            } else {
                itemModels.clear();
                itemModels.addAll(contents);
            }
            notifyDataSetChanged();
        }

        public ProjectContentAdapter(List<ProjectContentItemModel> list) {
            this.itemModels = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = getLayoutInflater().inflate(R.layout.item_check_point_project_edit, viewGroup, false);
            RecyclerView.ViewHolder holder = new ProjectContentViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ProjectContentViewHolder holder = (ProjectContentViewHolder) viewHolder;
            ProjectContentItemModel model = itemModels.get(i);
            holder.tvCheckContent.setText(model.getCheckContent());
            if (model.getCheckType() == 2) {
                holder.ivCheckResult.setVisibility(View.VISIBLE);
                holder.checkPointRangSplit.setVisibility(View.GONE);
                holder.checkPointRangContainer.setVisibility(View.GONE);
                holder.edCheckResult.setText("");
                holder.tvCheckRange.setText(R.string.item_qualified);
                holder.edCheckResult.setHint("");
                holder.edCheckResult.setInputType(InputType.TYPE_NULL);
                holder.edCheckResult.setFocusable(false);
                holder.edCheckResult.setOnClickListener(v -> {
                    BottomPicker.buildBottomPicker(CreatePointCheckActivity.this, Arrays.asList(getResources().getString(R.string.item_no_qualified), getResources().getString(R.string.item_qualified)), (position, label) -> {
                        holder.edCheckResult.setText(label);
                    });
                });
                holder.tvProjectName.setText(getResources().getString(R.string.name_project) + (i + 1));
                holder.tvCheckContent.setText(model.getCheckContent());
                holder.tvCheckNote.setText(model.getRemark());
            } else {
                holder.edCheckResult.setOnClickListener(v -> {

                });
                holder.checkPointRangSplit.setVisibility(View.VISIBLE);
                holder.checkPointRangContainer.setVisibility(View.VISIBLE);
                holder.ivCheckResult.setVisibility(View.INVISIBLE);
                holder.edCheckResult.setText("");
                holder.edCheckResult.setHint(R.string.alert_input);
                holder.edCheckResult.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                holder.tvCheckRange.setText(model.getMinValue() + "-" + model.getMaxVal());
                holder.edCheckResult.setFocusable(true);
            }
            holder.edCheckResult.addTextChangedListener(new TextWatcher() {
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
        public int getItemCount() {
            return itemModels == null ? 0 : itemModels.size();
        }
    }

    /**
     * 点检内容viewholder
     */
    class ProjectContentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_project_name)
        TextView tvProjectName;
        @Bind(R.id.tv_check_content)
        TextView tvCheckContent;
        @Bind(R.id.tv_check_note)
        TextView tvCheckNote;
        @Bind(R.id.ed_check_result)
        EditText edCheckResult;
        @Bind(R.id.iv_check_result)
        ImageView ivCheckResult;
        @Bind(R.id.tv_check_range)
        TextView tvCheckRange;
        @Bind(R.id.check_point_rang_split)
        LinearLayout checkPointRangSplit;
        @Bind(R.id.check_point_rang_container)
        LinearLayout checkPointRangContainer;

        public ProjectContentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
