package com.einyun.app.pms.repairs.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.WorkOrder;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.BottomPickerModel;
import com.einyun.app.common.model.IsClosedState;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.WorkOrderType;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.SelectRepairsTypeView;
import com.einyun.app.common.ui.widget.SwipeItemLayout;
import com.einyun.app.common.utils.FileProviderUtil;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.library.workorder.model.Door;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.net.request.RepairSendOrderRequest;
import com.einyun.app.library.workorder.net.request.SaveHandleRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.repairs.BR;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.ActivityRepairsDetailBinding;
import com.einyun.app.pms.repairs.databinding.ItemHandleBinding;
import com.einyun.app.pms.repairs.databinding.ItemRepairFeedbackHistoryLayoutBinding;
import com.einyun.app.pms.repairs.viewmodel.RepairDetailViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
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

@Route(path = RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
public class RepairsDetailActivity extends BaseHeadViewModelActivity<ActivityRepairsDetailBinding, RepairDetailViewModel> implements View.OnClickListener {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String nodeId;
    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    String listTtype;
    RepairsDetailModel detialModel;
    PhotoListAdapter photoListInfoAdapter;
    RVBindingAdapter<ItemHandleBinding, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean> materialAdapter;
    SaveHandleRequest saveHandleRequest;
    RVBindingAdapter<ItemRepairFeedbackHistoryLayoutBinding, RepairsDetailModel.HandleListBean> handleAdapter;
    PhotoSelectAdapter photoListFormAdapter;
    private final int MAX_PHOTO_SIZE = 4;
    private List<DictDataModel> dictNatureList = new ArrayList<>();
    private List<DictDataModel> dictTimeList = new ArrayList<>();
    private List<DictDataModel> dictPayTypeLsit = new ArrayList<>();
    private List<DictDataModel> dictAscriptLsit = new ArrayList<>();
    int rtTimeDefaultPos = 0;
    int rtDateDefaultPos = 0;
    int clDefaultPos = 0;
    private List<Door> doorResult;
    static final String HANDLE_PAID = "1";//??????
    static final String HANDLE_NO_PAID = "0";//??????
    RepairsDetailModel.DataBean.CustomerRepairModelBean customerRepair;
    private IsClosedRequest isClosedRequest;
    private String return_visit_time;
    private String return_time;


    @Override
    protected RepairDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(RepairDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repairs_detail;
    }

    private void getTotalMaterialPrice(List<RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean> materialsBeans) {
        Float totalPrice = 0.0f;
        for (RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean model : materialsBeans) {
            totalPrice = Float.parseFloat(model.getTotal_price()) + totalPrice;
        }
        binding.repairHandlePaid.repairMaterialPrice.setText(totalPrice + "");
        if (!TextUtils.isEmpty(binding.repairHandlePaid.repairHandleManMoney.getText().toString())) {
            binding.repairHandlePaid.repairHandleTotalMoney.setText(totalPrice + Float.parseFloat(binding.repairHandlePaid.repairHandleManMoney.getText().toString()) + "");
        } else {
            binding.repairHandlePaid.repairHandleTotalMoney.setText(totalPrice + "");
        }
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_repair);
        setRightOption(R.drawable.iv_histroy);
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
//        setView(nodeId);//?????????????????????????????????
        //????????????
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.sendOrder.repairSelectedPepple.setText(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user_id(model.getId());
        });
        //????????????
        LiveEventBus.get(LiveDataBusKey.POST_REPAIR_ADD_MATERIAL, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean.class).observe(this, model -> {
            if (detialModel != null) {
                detialModel.getData().getCustomer_repair_model().getSub_repair_materials().add(model);
                materialAdapter.setDataList(detialModel.getData().getCustomer_repair_model().getSub_repair_materials());
                getTotalMaterialPrice(detialModel.getData().getCustomer_repair_model().getSub_repair_materials());
            }
        });
        materialAdapter = new RVBindingAdapter<ItemHandleBinding, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean>(this, com.einyun.app.pms.repairs.BR.material) {
            @Override
            public void onBindItem(ItemHandleBinding binding, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean model, int position) {
                //????????????
                binding.leftToDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detialModel.getData().getCustomer_repair_model().getSub_repair_materials().remove(model);
                        materialAdapter.setDataList(detialModel.getData().getCustomer_repair_model().getSub_repair_materials());
                    }
                });
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_handle;
            }
        };
        binding.repairUseMaterial.repairHandleRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.repairUseMaterial.repairHandleRec.setAdapter(materialAdapter);
        binding.repairUseMaterial.repairHandleRec.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        handleAdapter = new RVBindingAdapter<ItemRepairFeedbackHistoryLayoutBinding, RepairsDetailModel.HandleListBean>(this, BR.history) {
            @Override
            public void onBindItem(ItemRepairFeedbackHistoryLayoutBinding binding, RepairsDetailModel.HandleListBean model, int position) {
                if (position == handleAdapter.getDataList().size() - 1) {
                    binding.itemHistroyImg.setVisibility(View.INVISIBLE);
                } else {
                    binding.itemHistroyImg.setVisibility(View.VISIBLE);
                }
                binding.itemRepairHistroyTime.setText(FormatUtil.formatDate(model.getHandle_time()));
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_repair_feedback_history_layout;
            }
        };
        binding.repairHandleHistory.repairHandleHistroyList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.repairHandleHistory.repairHandleHistroyList.setAdapter(handleAdapter);
        binding.repairHandleInfo.repairHandledRec.setAdapter(materialAdapter);
        binding.repairHandleInfo.repairHandledRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setEnterNum();
    }

    @Override
    protected void initData() {
        super.initData();
        photoListInfoAdapter = new PhotoListAdapter(this);
        photoListFormAdapter = new PhotoSelectAdapter(this);
        binding.repairHandleResult.sendOrderImgList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.repairHandleResult.sendOrderImgList.setAdapter(photoListFormAdapter);
        binding.repairsInfo.repairOrderDetailList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.repairsInfo.repairOrderDetailList.addItemDecoration(new SpacesItemDecoration(18, 0, 0, 0));
        binding.repairsInfo.repairOrderDetailList.setAdapter(photoListInfoAdapter);
        if (taskId == null) {
            taskId = "";
        }
        viewModel.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId).observe(this, repairsDetail -> {
            if (repairsDetail == null) {
                return;
            }
            detialModel = repairsDetail;
            String handle_is_paid = detialModel.getData().getCustomer_repair_model().getHandle_is_paid();
            if ("1".equals(handle_is_paid)) {
                binding.repairHandle.rbYes.setChecked(true);
//                binding.repairHandlePaid.getRoot().setVisibility(View.VISIBLE);
                if (detialModel.getData().getCustomer_repair_model().getMaterial_cost() != null) {
                    binding.repairHandlePaid.repairMaterialPrice.setText(detialModel.getData().getCustomer_repair_model().getMaterial_cost() + "");
                }
                if (detialModel.getData().getCustomer_repair_model().getArtificial_cost() != null) {
                    binding.repairHandlePaid.repairHandleManMoney.setText(detialModel.getData().getCustomer_repair_model().getArtificial_cost() + "");
                }
                if (detialModel.getData().getCustomer_repair_model().getHandle_fee() != null) {
                    binding.repairHandlePaid.repairHandleTotalMoney.setText(detialModel.getData().getCustomer_repair_model().getHandle_fee() + "");
                }

            } else {
                if (detialModel.getData().getCustomer_repair_model().getMaterial_cost() != null) {
                    binding.repairHandlePaid.repairMaterialPrice.setText(detialModel.getData().getCustomer_repair_model().getMaterial_cost() + "");
                }
                if (detialModel.getData().getCustomer_repair_model().getArtificial_cost() != null) {
                    binding.repairHandlePaid.repairHandleManMoney.setText(detialModel.getData().getCustomer_repair_model().getArtificial_cost() + "");
                }
                if (detialModel.getData().getCustomer_repair_model().getHandle_fee() != null) {
                    binding.repairHandlePaid.repairHandleTotalMoney.setText(detialModel.getData().getCustomer_repair_model().getHandle_fee() + "");
                }
            }
            return_visit_time = repairsDetail.getData().getCustomer_repair_model().getReturn_visit_time();
            return_time = repairsDetail.getData().getCustomer_repair_model().getReturn_time();
            GetNodeIdRequest getNodeIdRequest = new GetNodeIdRequest();
            getNodeIdRequest.setDefkey("customer_repair_flow");
            getNodeIdRequest.setId(repairsDetail.getData().getCustomer_repair_model().getId_());
            orderId = repairsDetail.getData().getCustomer_repair_model().getId_();
            viewModel.getNodeId(getNodeIdRequest).observe(this, nodeIdModel -> {
                if (nodeIdModel == null) {
                    return;
                }

                if (nodeIdModel.getNodeId() == null) {
                    setView("");//????????????????????????????????????????????????
                    repairsDetail.setNodeId("");
                    nodeId = "";
                } else {
                    nodeId = nodeIdModel.getNodeId();
                    setView(nodeIdModel.getNodeId());
                    repairsDetail.setNodeId(nodeIdModel.getNodeId());
                }
//            detialModel.setNodeId(nodeIdModel.getNodeId()==null?"":nodeIdModel.getNodeId());
//                bindData(detialModel);
                updateUI(repairsDetail);
                saveHandleRequest = new SaveHandleRequest(orderId, detialModel.getData().getCustomer_repair_model());
            });

        });
        //???????????????????????????
        viewModel.repairTypeList().observe(this, doorResult -> {
            this.doorResult = doorResult.getChildren();
        });
        //????????????????????????
        viewModel.getByTypeKey(Constants.REPAIR_NATURE).observe(this, dictDataModels -> {
            dictNatureList = dictDataModels;
            setProperTy();
        });
        //????????????????????????
        viewModel.getByTypeKey(Constants.REPAIR_TIME).observe(this, dictDataModels -> {
            dictTimeList = dictDataModels;
        });
        //??????????????????
        viewModel.getByTypeKey(Constants.REPAIR_PAY_TYPE).observe(this, dictDataModels -> {
            dictPayTypeLsit = dictDataModels;
        });
        //????????????????????????
        viewModel.getByTypeKey(Constants.REPAIR_WORK_ASCRIPTION).observe(this, dictDataModels -> {
            dictAscriptLsit = dictDataModels;
            if (dictAscriptLsit != null) {
                setAscription();
            }
        });
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        //?????????????????????????????????
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .navigation();
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.repairDetailSubmit.setOnClickListener(this);
        binding.sendOrder.repairSelectPeople.setOnClickListener(this);
        binding.repairUseMaterial.handleAddMaterial.setOnClickListener(this);
        binding.handlerDetailSave.setOnClickListener(this);
        binding.handlerDetailSubmit.setOnClickListener(this);
        binding.repairUseMaterial.handleAddMaterial.setOnClickListener(this);
        binding.repairEvaluate.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_un_solve) {
                    binding.repairEvaluate.repairEvaluateMarkLn.setVisibility(View.VISIBLE);
                } else {
                    binding.repairEvaluate.repairEvaluateMarkLn.setVisibility(View.GONE);
                }
            }
        });
        /**
         * ????????????
         * */
        binding.repairsInfo.rgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_normal) {
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass(dictNatureList.get(0).getName());
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass_id(dictNatureList.get(0).getKey());
                }
                if (checkedId == R.id.rb_general) {
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass(dictNatureList.get(1).getName());
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass_id(dictNatureList.get(1).getKey());
                }
                if (checkedId == R.id.rb_warning) {
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass(dictNatureList.get(2).getName());
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass_id(dictNatureList.get(2).getKey());
                }
            }
        });
        /**
         * ??????????????????
         */
        photoListFormAdapter.setAddListener(selectedSize -> {
            if (photoListFormAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //??????????????????zf
                    .choose(MimeType.ofImage())
                    .captureStrategy(new CaptureStrategy(true, DataConstants.DATA_PROVIDER_NAME))
                    .capture(true)
                    .countable(true)
                    .maxSelectable(MAX_PHOTO_SIZE - photoListFormAdapter.getSelectedPhotos().size())
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK);
        }, RepairsDetailActivity.this);
        //??????????????????
        binding.repairsInfo.repairReportAppointChange.setOnClickListener(this);
        //????????????
        binding.repairsInfo.repariReportAreaChange.setOnClickListener(this);
        //????????????
        binding.repairsInfo.repairReportKindChange.setOnClickListener(this);
        //??????????????????
        binding.repairHandlePaid.repairHandlePayway.setOnClickListener(this);
        //??????????????????
        binding.repairHandlePaid.repairHandlePayDate.setOnClickListener(this);
        //????????????
        binding.repairHandle.repairHandleIfPaid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_yes) {
                    binding.repairHandlePaid.getRoot().setVisibility(View.GONE);
                    detialModel.getData().getCustomer_repair_model().setHandle_is_paid(HANDLE_PAID);//??????
                } else {
                    binding.repairHandlePaid.getRoot().setVisibility(View.GONE);
                    detialModel.getData().getCustomer_repair_model().setHandle_is_paid(HANDLE_NO_PAID);//??????
                }
            }
        });
        //????????????
        binding.repairHandleHistory.handleAddMore.setOnClickListener(this);
        //????????????
        binding.repairClosePostpone.applyPostpone.setOnClickListener(this);
        //????????????
        binding.repairClosePostpone.repairApplyLate.setOnClickListener(this);
        //????????????
        binding.repariResponse.rgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_normal) {
                    customerRepair.setWork_ascription(dictAscriptLsit.get(0).getName());
                    customerRepair.setWork_ascription_code(dictAscriptLsit.get(0).getKey());
                }
                if (checkedId == R.id.rb_general) {
                    customerRepair.setWork_ascription(dictAscriptLsit.get(1).getName());
                    customerRepair.setWork_ascription_code(dictAscriptLsit.get(1).getKey());
                }
            }
        });

        //???????????????????????????????????????
        viewModel.setEditPoint(binding.repairHandle.repairWorkHours, 1);
        //???????????????????????????????????????
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                RepairsDetailActivity.this.finish();
            }
        });
        //?????????????????????
        binding.repairHandlePaid.repairMaterialPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(binding.repairHandlePaid.repairHandleManMoney.getText())) {
                } else {
                    String handle_is_paid = detialModel.getData().getCustomer_repair_model().getHandle_is_paid();
                    if ("1".equals(handle_is_paid)) {

                        try {
                            binding.repairHandlePaid.repairHandleTotalMoney.setText(Float.parseFloat(binding.repairHandlePaid.repairMaterialPrice.getText().toString()) * Float.parseFloat(binding.repairHandlePaid.repairHandleManMoney.getText().toString()) + "");
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
        binding.repairHandlePaid.repairHandleManMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(binding.repairHandlePaid.repairMaterialPrice.getText())) {
                } else if (TextUtils.isEmpty(binding.repairHandlePaid.repairHandleManMoney.getText())) {
                    binding.repairHandlePaid.repairHandleTotalMoney.setText(Float.parseFloat(binding.repairHandlePaid.repairMaterialPrice.getText().toString()) + "");
                } else {
                    String s1 = binding.repairHandlePaid.repairMaterialPrice.getText().toString();
                    String s2 = binding.repairHandlePaid.repairHandleManMoney.getText().toString();

                    try {
                        binding.repairHandlePaid.repairHandleTotalMoney.setText(Float.parseFloat(s1) + Float.parseFloat(s2) + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        binding.repairHandle.arriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getArriveCode(orderId).observe(RepairsDetailActivity.this, model -> {
                    ToastUtil.show(RepairsDetailActivity.this, model.getData());
                    if (model == null) {
                        return;
                    }
                    if (model.isState()) {
                        detialModel.getData().getCustomer_repair_model().setSm_sendflag("1");
                        binding.repairHandle.arriveBtn.setVisibility(View.GONE);
                        binding.repairHandle.sendBtn.setVisibility(View.VISIBLE);
                        binding.repairHandle.arriveCodeLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        binding.repairHandle.checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(binding.repairHandle.checkEdt.getText().toString().trim())){
                    ToastUtil.show(RepairsDetailActivity.this,"??????????????????");
                    return;
                }
                viewModel.checkArriveCode(orderId, binding.repairHandle.checkEdt.getText().toString().trim()).observe(RepairsDetailActivity.this, model -> {
                    if (model == null) {
                        return;
                    }
                    detialModel.getData().getCustomer_repair_model().setSm_flag("1");
                    ToastUtil.show(RepairsDetailActivity.this,model.getMsg());
                });
            }
        });
    }

    public void setEnterNum() {
        binding.repairHandlePaid.repairHandleTogetherMan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int editEnd = binding.repairHandlePaid.repairHandleTogetherMan.getSelectionEnd();

                // ?????????????????????????????????????????????
                binding.repairHandlePaid.repairHandleTogetherMan.removeTextChangedListener(this);

                // ????????????????????????????????????EditText????????????????????????????????????????????????????????????
                // ????????????????????????????????????????????????calculateLength??????????????????1
                if (s.toString().length() > 300) { // ??????????????????????????????????????????????????????????????????
                    int length = s.toString().length();
                    s.delete(editEnd - (length - 300), editEnd);
                    binding.repairHandlePaid.repairHandleTogetherMan.setSelection(editEnd - (length - 300));//?????????????????????
                    ToastUtil.show(CommonApplication.getInstance(), "????????????" + 300 + "?????????");
                }

                // ???????????????
                binding.repairHandlePaid.repairHandleTogetherMan.addTextChangedListener(this);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* viewModel.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId).observe(this, repairsDetail -> {
            updateUI(repairsDetail);
            saveHandleRequest = new SaveHandleRequest(orderId, detialModel.getData().getCustomer_repair_model());
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //??????????????????
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
                            photoListFormAdapter.addPhotos(Arrays.asList(uri));
                        }
                    });
                });
    }
    //??????????????????
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            //????????????
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(customerRepair.getBx_time()));
        }
    };

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    /**
     * ???????????????????????????UI??????
     *
     * @param
     */
    private void updateUI(RepairsDetailModel repairsOrderDetail) {
        if (repairsOrderDetail == null) {
            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        detialModel = repairsOrderDetail;
        updatePageUIState(PageUIState.FILLDATA.getState());
        detialModel.setNodeId(nodeId);
//        if ("normal".equals(detialModel.getData().getCustomer_repair_model().getBx_property_ass_id())) {
//            binding.repairsInfo.repairAssesTxt.setText("??????");
//        }else if ("general".equals(detialModel.getData().getCustomer_repair_model().getBx_property_ass_id())){
//            binding.repairsInfo.repairAssesTxt.setText("??????");
//        }else {
//            binding.repairsInfo.repairAssesTxt.setText("??????");
//        }
        if (detialModel.getData().getCustomer_repair_model().getBx_property_ass() != null) {
            binding.repairsInfo.repairAssesTxt.setText(detialModel.getData().getCustomer_repair_model().getBx_property_ass());
        }
        customerRepair = detialModel.getData().getCustomer_repair_model();
        binding.tvHandleTime.setText(TimeUtil.getTimeExpend(customerRepair.getBx_time()));
        if (detialModel.getNodeId().equals("closed") || detialModel.getNodeId().equals("")) {
        } else {
            runnable.run();
        }
        bindData(repairsOrderDetail);
        if (detialModel.getData().getCustomer_repair_model().getPd_time() == null) {
            binding.sendOrderInfo.getRoot().setVisibility(View.GONE);
        }
        if (detialModel.getData().getCustomer_repair_model().getHandle_time() != null) {
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detialModel.getData().getCustomer_repair_model().getHandle_time().toString()));
        }
        if (detialModel.getHandleList() != null) {
            binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
            if (detialModel.getHandleList().size() > 3) {
                handleAdapter.setDataList(detialModel.getHandleList().subList(0, 3));
                binding.repairHandleHistory.handleAddMore.setVisibility(View.VISIBLE);
            } else {
                handleAdapter.setDataList(detialModel.getHandleList());
                binding.repairHandleHistory.handleAddMore.setVisibility(View.GONE);
            }

        } else {
            binding.repairHandleHistory.getRoot().setVisibility(View.GONE);
        }
        //????????????
        if (detialModel.getData().getCustomer_repair_model().getSub_repair_materials() != null) {
            materialAdapter.setDataList(detialModel.getData().getCustomer_repair_model().getSub_repair_materials());
            List<RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean> sub_repair_materials = detialModel.getData().getCustomer_repair_model().getSub_repair_materials();
        } else {
//            binding.repairUseMaterial.getRoot().setVisibility(View.GONE);
        }
        //??????????????????
        if (detialModel.getForceCloseInfo() != null) {
            binding.repairCloseInfo.getRoot().setVisibility(View.VISIBLE);
            if (detialModel.getForceCloseInfo().getAttachment() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.repairCloseInfo.repairOrderClosePicList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));
                binding.repairCloseInfo.repairOrderClosePicList.addItemDecoration(new SpacesItemDecoration(18, 0, 0, 0));
                binding.repairCloseInfo.repairOrderClosePicList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(detialModel.getForceCloseInfo().getAttachment());
                adapter.updateList(modelList);
            }

        }
        //??????????????????
        if (detialModel.getDelayInfo() != null) {
            binding.repairLateInfo.getRoot().setVisibility(View.VISIBLE);
            if (detialModel.getDelayInfo().getAttachment() != null) {
                PhotoListAdapter adapter = new PhotoListAdapter(this);
                binding.repairLateInfo.repairOrderPostponePicList.setLayoutManager(new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false));
                binding.repairLateInfo.repairOrderPostponePicList.addItemDecoration(new SpacesItemDecoration(18, 0, 0, 0));
                binding.repairLateInfo.repairOrderPostponePicList.setAdapter(adapter);
                PicUrlModelConvert convert = new PicUrlModelConvert();
                List<PicUrlModel> modelList = convert.stringToSomeObjectList(detialModel.getDelayInfo().getAttachment());
                adapter.updateList(modelList);
            }
        }
        //??????????????????
        if (customerRepair.getHandle_attach() != null) {
            PhotoListAdapter adapter = new PhotoListAdapter(this);
            binding.repairHandleInfo.repairOrderDetailList.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false));
            binding.repairHandleInfo.repairOrderDetailList.addItemDecoration(new SpacesItemDecoration(18, 0, 0, 0));
            binding.repairHandleInfo.repairOrderDetailList.setAdapter(adapter);
            PicUrlModelConvert convert = new PicUrlModelConvert();
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(customerRepair.getHandle_attach().toString());
            adapter.updateList(modelList);
        }
        //??????????????????
        if (customerRepair.getReturn_score() != null) {
            binding.repairEvaluateInfo.attitudeStar.setStar(Float.parseFloat(customerRepair.getReturn_score()));
//            binding.repairCallEvaluateInfo.attitudeStar.setStar(Float.parseFloat(customerRepair.getReturn_score()));
        } else {
            binding.repairEvaluateInfo.getRoot().setVisibility(View.GONE);
//            binding.repairCallEvaluateInfo.getRoot().setVisibility(View.GONE);
        }
        if (customerRepair.getService_quality_score() != null) {
            binding.repairEvaluateInfo.qualityStar.setStar(Float.parseFloat(customerRepair.getService_quality_score()));
//            binding.repairCallEvaluateInfo.qualityStar.setStar(Float.parseFloat(customerRepair.getService_quality_score()));
            binding.repairEvaluateInfo.qualityStar.setClickable(false);
//            binding.repairCallEvaluateInfo.qualityStar.setClickable(false);
        }
        //??????????????????
        if (customerRepair.getBx_property_ass_id() != null) {
            for (int i = 0; i < dictNatureList.size(); i++) {
                if (dictNatureList.get(i).getKey().equals(customerRepair.getBx_property_ass_id())) {
                    if (i == 0) {
                        binding.repairsInfo.rbNormal.setChecked(true);
                    }
                    if (i == 1) {
                        binding.repairsInfo.rbGeneral.setChecked(true);
                    }
                    if (i == 2) {
                        binding.repairsInfo.rbWarning.setChecked(true);
                    }
                }
            }
        }
        updateImagesUI(repairsOrderDetail);
    }

    private void updateImagesUI(RepairsDetailModel repairsDetailModel) {
        if (detialModel.getData().getCustomer_repair_model().getBx_attachment() == null) {
            return;
        }
        PicUrlModelConvert convert = new PicUrlModelConvert();
        List<PicUrlModel> modelList = convert.stringToSomeObjectList(repairsDetailModel.getData().getCustomer_repair_model().getBx_attachment().toString());
        photoListInfoAdapter.updateList(modelList);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.repair_detail_submit) {
            handleSubmit();
        }
        if (v.getId() == R.id.repair_select_people) {
            selectPeple();
        }
        if (v.getId() == R.id.repair_handle_rec) {

        }
        if (v.getId() == R.id.handler_detail_save) {
            saveHandler();
        }
        if (v.getId() == R.id.handler_detail_submit) {
            doReuest();
        }
        if (v.getId() == R.id.handle_add_material) {
            addMaterial();
        }
        if (v.getId() == R.id.repair_report_appoint_change) {
            repairTime();
        }
        if (v.getId() == R.id.repari_report_area_change) {
            repairLocation();
        }
        if (v.getId() == R.id.repair_report_kind_change) {
            repairType();
        }
        if (v.getId() == R.id.repair_handle_payway) {
            choosePayWay();
        }
        if (v.getId() == R.id.repair_handle_pay_date) {
            choosePayDate();
        }
        if (v.getId() == R.id.handle_add_more) {
            binding.repairHandleHistory.handleAddMore.setVisibility(View.GONE);
            handleAdapter.setDataList(detialModel.getHandleList());
        }
        if (v.getId() == R.id.apply_postpone) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_CLOSE).withString(RouteKey.KEY_MID_URL, RouteKey.KEY_MID_URL_REPAIRS)
                    .withString(RouteKey.KEY_TASK_ID, taskId)
                    .navigation();
        }
        if (v.getId() == R.id.repair_apply_late) {
            IsClosedRequest request = new IsClosedRequest();
            request.setId(orderId);
            request.setType(WorkOrder.POSTPONED_REPAIR);
//            viewModel.isClosed(request, true);
            viewModel.resourceWorkOrderService.isClosed(request, new CallBack<Boolean>() {
                @Override
                public void call(Boolean data) {
                    if (data) {
//                            if (model.getType().equals(WorkOrder.POSTPONED_REPAIR)) {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_LATE).withString(RouteKey.KEY_ORDER_ID, orderId)
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_CUSTOMER_REPAIRS)
                                .withString(RouteKey.KEY_DIVIDE_ID, customerRepair.getBx_dk_id())
                                .withString(RouteKey.KEY_DIVIDE_NAME, customerRepair.getBx_dk())
                                .navigation();
//                            }
                    } else {
                        ToastUtil.show(RepairsDetailActivity.this, "??????????????????????????????????????????????????????");
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
        }

    }

    /**
     * ????????????
     */
    private void handleSubmit() {
        if (detialModel == null) {
            return;
        }
        doReuest();
    }

    /**
     * ???????????????
     */
    private void selectPeple() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, detialModel.getData().getCustomer_repair_model().getBx_dk_id())
                .withString(RouteKey.KEY_PROJECT_ID, detialModel.getData().getCustomer_repair_model().getU_project_id())
                .navigation();
    }

    /**
     * ???????????????,???????????????
     */
    private void doReuest() {
//        filterRequest(nodeId);
        //????????????
        if (nodeId.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            if (TextUtils.isEmpty(binding.repariResponse.repairResponseReason.getString())) {
                ToastUtil.show(this, R.string.text_please_enter_reason);
                return;
            } else {
                customerRepair.setResponse_result(binding.repariResponse.repairResponseReason.getString());
            }
            if (binding.repariResponse.rgs.getCheckedRadioButtonId() == R.id.rb_normal) {
                customerRepair.setWork_ascription(dictAscriptLsit.get(0).getName());
                customerRepair.setWork_ascription_code(dictAscriptLsit.get(0).getKey());
            } else if (binding.repariResponse.rgs.getCheckedRadioButtonId() == R.id.rb_general) {
                customerRepair.setWork_ascription(dictAscriptLsit.get(1).getName());
                customerRepair.setWork_ascription_code(dictAscriptLsit.get(1).getKey());
            }else {
                customerRepair.setWork_ascription(dictAscriptLsit.get(2).getName());
                customerRepair.setWork_ascription_code(dictAscriptLsit.get(2).getKey());
            }

        }
        //????????????
        if (nodeId.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            mergeHandleRequest();

        }
        //????????????
        if (nodeId.equals(RouteKey.REPAIR_STATUS_SEND_ORDER_LATE)) {
            if (TextUtils.isEmpty(binding.sendOrder.repairSelectedPepple.getText().toString()) || "?????????".equals(binding.sendOrder.repairSelectedPepple.getText().toString())) {
                ToastUtil.show(this, R.string.txt_plese_select_people);
                return;
            } else {
                customerRepair.setPd_remark(binding.sendOrder.repairSendReason.getString());
            }

        }
        //???????????????
        if (nodeId.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            mergeEvaluateRequest();
            if (binding.repairEvaluate.radiogroup.getCheckedRadioButtonId() == R.id.rb_solve) {

            } else {
                if (TextUtils.isEmpty(binding.repairEvaluate.unsolvedMark.getString())) {
                    ToastUtil.show(this, R.string.text_please_enter_reason);
                    return;
                }
            }
        }
        //?????????
        if (nodeId.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            if (TextUtils.isEmpty(binding.sendOrder.repairSendReason.getString())) {
            } else {
                detialModel.getData().getCustomer_repair_model().setHandle_result(binding.sendOrder.repairSendReason.getString());
//                return;
            }
            String s = binding.repairsInfo.repairReportArea.getText().toString();
            if (s != null && s.equals("??????")) {
                if (binding.repairsInfo.repairAppointPeriod.getText().toString().isEmpty()) {
                    ToastUtil.show(this, R.string.txt_plese_select_time);
                    return;
                }
            }
            if (TextUtils.isEmpty(detialModel.getData().getCustomer_repair_model().getAssign_grab_user())) {
                ToastUtil.show(this, R.string.txt_plese_select_people);
                return;
            }
            customerRepair.setPd_remark(binding.sendOrder.repairSendReason.getString());
        }
        if (nodeId.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            //???????????????????????????
        } else {
            submit();
        }
    }

    /**
     * ???????????????,???????????????
     */
    private void submit() {
        viewModel.repairSend(new RepairSendOrderRequest(customerRepair, new RepairSendOrderRequest.DoNextParamBean(taskId))).observe(this, status -> {
            if (status.isState()) {
                new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                        .setMsg(getResources().getString(R.string.text_submit_success)).
                        setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.refreshUI();
                                RepairsDetailActivity.this.finish();
                            }
                        }).show();
            } else {

                ToastUtil.show(this, status.getMsg());
            }
        });
    }

    /**
     * ????????????
     */
    private void saveHandler() {
        if (TextUtils.isEmpty(binding.repairHandleResult.repairHandleReason.getString())) {
            ToastUtil.show(this, R.string.text_please_enter_reason);
        } else {
            SaveHandleRequest saveHandleRequest = new SaveHandleRequest(orderId, new RepairsDetailModel.DataBean.CustomerRepairModelBean());

//            this.saveHandleRequest.getBizData().setHandle_result(binding.repairHandleResult.repairHandleReason.getString());
            saveHandleRequest.setID_(orderId);
            saveHandleRequest.getBizData().setHandle_result(binding.repairHandleResult.repairHandleReason.getString());
            viewModel.saveHandler(saveHandleRequest).observe(this, status -> {
                if (status) {
                    RepairsDetailModel.HandleListBean handleListBean = new RepairsDetailModel.HandleListBean();
                    handleListBean.setHandle_time(System.currentTimeMillis());
                    new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                            .setMsg(getResources().getString(R.string.text_save_success)).
                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewModel.refreshUI();
                                    binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);

                                    handleListBean.setHandle_result(binding.repairHandleResult.repairHandleReason.getString());

                                    handleListBean.setHandle_user(detialModel.getData().getCustomer_repair_model().getHandle_user());
                                    if (detialModel.getHandleList() == null) {
                                        List<RepairsDetailModel.HandleListBean> handleListBeanList = new ArrayList<>();
                                        detialModel.setHandleList(handleListBeanList);
                                    }
                                    detialModel.getHandleList().add(0, handleListBean);
                                    handleAdapter.setDataList(detialModel.getHandleList());

                                }
                            }).show();
                }
            });
        }
    }

    /**
     * ????????????????????????
     */

    private void setView(String status) {
        //????????????
        if (status.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getPd_time())) {
                binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            }
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_WAIT_FEED) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_COPY_ME)) {
                binding.repariResponse.getRoot().setVisibility(View.VISIBLE);
                binding.repairClosePostpone.getRoot().setVisibility(View.VISIBLE);
                binding.repairDetailSubmit.setVisibility(View.VISIBLE);
                binding.repairsInfo.repairReportAppointChange.setVisibility(View.VISIBLE);
            }
            ifApplyClose();
            return;
        }
        //????????????
        if (status.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getPd_time())) {
                binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            }
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getResponse_time())) {
                binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            }
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_WAIT_FEED) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_COPY_ME)) {
                binding.repairHandle.getRoot().setVisibility(View.VISIBLE);
                if (detialModel.getData().getCustomer_repair_model().getSm_sendflag().equals("0")){
                }else {
                    binding.repairHandle.arriveBtn.setVisibility(View.GONE);
                    binding.repairHandle.sendBtn.setVisibility(View.VISIBLE);
                    binding.repairHandle.arriveCodeLayout.setVisibility(View.VISIBLE);
                }
                binding.repairClosePostpone.getRoot().setVisibility(View.VISIBLE);
                binding.repairHandleResult.getRoot().setVisibility(View.VISIBLE);
                binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
                binding.repairUseMaterial.getRoot().setVisibility(View.VISIBLE);
//                binding.repairHandlePaid.getRoot().setVisibility(View.VISIBLE);
                binding.handleSaveSubmit.setVisibility(View.VISIBLE);
            }
            ifApplyClose();
            return;
        }
        //???????????????
        if (status.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getPd_time())) {
                binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            }
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getResponse_time())) {
                binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            }
            binding.repairHandleInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            if (StringUtil.isNullStr(return_visit_time)) {
                binding.repairCallEvaluateInfo.getRoot().setVisibility(View.VISIBLE);
            }
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_WAIT_FEED) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_COPY_ME) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_COPY_ME)) {
                binding.repairEvaluate.getRoot().setVisibility(View.VISIBLE);
                binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            }
            return;
        }
        //?????????
        if (status.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
//            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_WAIT_FEED) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_COPY_ME)) {
                binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
                binding.repairsInfo.repairReportAreaRed.setVisibility(View.VISIBLE);
                binding.repairsInfo.repairReportKindRed.setVisibility(View.VISIBLE);
                binding.repairsInfo.repairReportNatureRed.setVisibility(View.VISIBLE);
                binding.repairsInfo.repariReportAreaChange.setVisibility(View.VISIBLE);
                binding.repairsInfo.repairReportKindChange.setVisibility(View.VISIBLE);
                binding.repairsInfo.repairReportAppointChange.setVisibility(View.VISIBLE);
                binding.repairsInfo.rgs.setVisibility(View.VISIBLE);
                binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            } else {
                binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            }
            return;
        }
        //?????????
        if (status.equals("")) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getPd_time())) {
                binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            }
            if (StringUtil.isNullStr(detialModel.getData().getCustomer_repair_model().getResponse_time())) {
                binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            }
            binding.repairHandleInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);

            if (StringUtil.isNullStr(return_visit_time)) {
                binding.repairCallEvaluateInfo.getRoot().setVisibility(View.VISIBLE);
            }
            if (StringUtil.isNullStr(return_time)) {
                binding.repairEvaluateInfo.getRoot().setVisibility(View.VISIBLE);
            }
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            return;
        }
        //????????????
        if (status.equals(RouteKey.REPAIR_STATUS_SEND_ORDER_LATE) || status.equals(RouteKey.REPAIR_STATUS_WAIT_GRAB)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrder.repairSendTxt.setText(R.string.text_late_send_order);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_WAIT_FEED) && !listTtype.equals(RouteKey.FRAGMENT_REPAIR_COPY_ME)) {
                binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
                binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            }
            return;
        }


    }

    /**
     * ???????????????????????????
     */
    private void ifApplyClose() {
        isClosedRequest = new IsClosedRequest(orderId, WorkOrder.FORCE_CLOSE_REPAIR);
        viewModel.isClosed(isClosedRequest).observe(this, model -> {
            if (!model.isClosed()) {
                binding.repairDetailSubmit.setVisibility(View.GONE);
                binding.handleSaveSubmit.setVisibility(View.GONE);
                binding.repairHandlePaid.getRoot().setVisibility(View.GONE);
                binding.repairUseMaterial.getRoot().setVisibility(View.GONE);
                binding.repariResponse.getRoot().setVisibility(View.GONE);
                binding.repairClosePostpone.getRoot().setVisibility(View.GONE);
                binding.repairHandleResult.getRoot().setVisibility(View.GONE);
                binding.repairsInfo.repairReportAppointChange.setVisibility(View.GONE);
                binding.repairHandle.getRoot().setVisibility(View.GONE);
                binding.sendOrder.getRoot().setVisibility(View.GONE);
                binding.repariResponse.getRoot().setVisibility(View.GONE);


            }
        });

    }

    /**
     * ????????????
     */
    private void addMaterial() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_ADD_MATERIAL).navigation();
    }

    /**
     * ????????????
     */
    private void filterRequest(String status) {
        //????????????
        if (status.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            if (TextUtils.isEmpty(binding.repariResponse.repairResponseReason.getString())) {
                ToastUtil.show(this, R.string.text_please_enter_reason);
            } else {
                customerRepair.setResponse_result(binding.repariResponse.repairResponseReason.getString());
            }
            if (binding.repariResponse.rgs.getCheckedRadioButtonId() == R.id.rb_normal) {
                customerRepair.setWork_ascription(dictAscriptLsit.get(0).getName());
                customerRepair.setWork_ascription_code(dictAscriptLsit.get(0).getKey());
            } else {
                customerRepair.setWork_ascription(dictAscriptLsit.get(1).getName());
                customerRepair.setWork_ascription_code(dictAscriptLsit.get(1).getKey());
            }
            return;
        }
        //????????????
        if (status.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            mergeHandleRequest();
            return;
        }
        //???????????????
        if (status.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            mergeEvaluateRequest();
            return;
        }
        //?????????
        if (status.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            if (TextUtils.isEmpty(binding.sendOrder.repairSendReason.getString())) {
            } else {
                detialModel.getData().getCustomer_repair_model().setHandle_result(binding.sendOrder.repairSendReason.getString());
                return;
            }
            if (TextUtils.isEmpty(detialModel.getData().getCustomer_repair_model().getAssign_grab_user())) {
                ToastUtil.show(this, R.string.txt_plese_select_people);
                return;
            }
        }
    }


    /**
     * ????????????request
     */
    private void mergeEvaluateRequest() {
        if (binding.repairEvaluate.radiogroup.getCheckedRadioButtonId() == R.id.rb_solve) {
            detialModel.getData().getCustomer_repair_model().setC_is_solve(1);//?????????
        } else {
            detialModel.getData().getCustomer_repair_model().setC_is_solve(0);//?????????
            if (TextUtils.isEmpty(binding.repairEvaluate.unsolvedMark.getString())) {
                ToastUtil.show(this, R.string.text_please_enter_reason);
                return;
            } else {
                detialModel.getData().getCustomer_repair_model().setReturn_result(binding.repairEvaluate.unsolvedMark.getString());
            }
        }
        customerRepair.setService_attitude_content(binding.repairEvaluate.etLimitSuggestionHandle.getString());
        customerRepair.setService_quality_content(binding.repairEvaluate.etLimitSuggestionService.getString());
        customerRepair.setService_quality_score(binding.repairEvaluate.repairAttitudeScore.getSelectedStarts() + "");
        customerRepair.setReturn_score(binding.repairEvaluate.repairServieScore.getSelectedStarts() + "");
    }

    /**
     * ????????????request
     */
    private void mergeHandleRequest() {
        if (TextUtils.isEmpty(binding.repairHandleResult.repairHandleReason.getString())) {
            ToastUtil.show(this, R.string.text_please_enter_reason);
            return;
        } else {
            //????????????
            customerRepair.setHandle_man_hour(binding.repairHandle.repairWorkHours.getText().toString().trim());
            customerRepair.setHandle_result(binding.repairHandleResult.repairHandleReason.getString());
            if (binding.repairHandle.repairHandleIfPaid.getCheckedRadioButtonId() == R.id.rb_yes) {
                customerRepair.setHandle_is_paid(HANDLE_PAID);
                customerRepair.setMaterial_cost(binding.repairHandlePaid.repairMaterialPrice.getText().toString().trim());
                customerRepair.setArtificial_cost(binding.repairHandlePaid.repairHandleManMoney.getText().toString().trim());
                customerRepair.setHandle_fee(binding.repairHandlePaid.repairHandleTotalMoney.getText().toString().trim());
                customerRepair.setJoint_processor(binding.repairHandlePaid.repairHandleTogetherMan.getText().toString().trim());
            } else {
                customerRepair.setHandle_is_paid(HANDLE_NO_PAID);
                customerRepair.setMaterial_cost(binding.repairHandlePaid.repairMaterialPrice.getText().toString().trim());
                customerRepair.setArtificial_cost(binding.repairHandlePaid.repairHandleManMoney.getText().toString().trim());
                customerRepair.setHandle_fee(binding.repairHandlePaid.repairHandleTotalMoney.getText().toString().trim());
                customerRepair.setJoint_processor(binding.repairHandlePaid.repairHandleTogetherMan.getText().toString().trim());
            }

        }
        if (photoListFormAdapter.getSelectedPhotos().size() >= 0) {
            uploadImages();
        } else {
            ToastUtil.show(this, R.string.text_alert_handle_pic);
        }

    }

    /**
     * ????????????
     */
    private void uploadImages() {
        viewModel.uploadImages(photoListFormAdapter.getSelectedPhotos()).observe(this, picUrls -> {
            customerRepair.setHandle_attach(new ImageUploadManager().toJosnString(picUrls));
            submit();//????????????
        });
    }

    /**
     * ??????????????????
     */


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
                binding.repairsInfo.repairAppointTime.setText(model.getData());
                binding.repairsInfo.repairAppointPeriod.setText(model.getDataList().get(position2));
                customerRepair.setBx_appoint_time(model.getData());
                customerRepair.setBx_appoint_time_period_id(dictTimeList.get(position2).getKey());
                customerRepair.setBx_appoint_time_period(dictTimeList.get(position2).getName());
            }
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

    /**
     * ????????????
     */
    private void repairLocation() {
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

                for (Door data : doorResult) {
                    if (data.getDataName().equals(txStrList.get(position))) {
                        customerRepair.setBx_area(data.getDataName());
                        customerRepair.setBx_area_id(data.getDataKey());
                        binding.repairsInfo.repairReportArea.setText(data.getDataName());
                    }
                }
//                customerRepair.setLine_key(model.get(0).getExpand().getMajorLine().getKey());
//                customerRepair.setLine_name(model.get(0).getExpand().getMajorLine().getName());
                if (clDefaultPos != position) {
                    customerRepair.setBx_cate_lv1("");
                    customerRepair.setBx_cate_lv2("");
                    customerRepair.setBx_cate_lv3("");
                    customerRepair.setBx_cate_lv1_id("");
                    customerRepair.setBx_cate_lv2_id("");
                    customerRepair.setBx_cate_lv3_id("");
                    binding.repairsInfo.repairType.setText("");
                }
                clDefaultPos = position;
            }
        });
    }

    /**
     * ????????????
     */
    private void repairType() {
        if (doorResult.get(clDefaultPos).getChildren() == null || doorResult.get(clDefaultPos).getChildren().size() == 0) {
            ToastUtil.show(this, "??????????????????");
            return;
        }
        SelectRepairsTypeView view = new SelectRepairsTypeView(doorResult.get(clDefaultPos).getChildren());
        view.setWorkTypeListener(new SelectRepairsTypeView.OnWorkTypeSelectListener() {
            @Override
            public void onWorkTypeSelectListener(List<Door> model) {
                customerRepair.setLine_key(model.get(0).getExpand().getMajorLine().getKey());
                customerRepair.setLine_name(model.get(0).getExpand().getMajorLine().getName());
                customerRepair.setBx_cate_lv1(model.get(0).getDataName());
                customerRepair.setBx_cate_lv2(model.get(1).getDataName());
                customerRepair.setBx_cate_lv3(model.get(2).getDataName());
                customerRepair.setBx_cate_lv1_id(model.get(0).getDataKey());
                customerRepair.setBx_cate_lv2_id(model.get(1).getDataKey());
                customerRepair.setBx_cate_lv3_id(model.get(2).getDataKey());
                binding.repairsInfo.repairType.setText(model.get(0).getExpand().getMajorLine().getName() + "-" + model.get(0).getDataName() + "-" + model.get(1).getDataName() + "-" + model.get(2).getDataName());
            }
        });
        view.show(getSupportFragmentManager(), "");
    }

    /**
     * ??????????????????
     */
    private void choosePayWay() {
        List<String> payWays = new ArrayList<>();
        for (DictDataModel dictDataModel : dictPayTypeLsit) {
            payWays.add(dictDataModel.getName());
        }

        BottomPicker.buildBottomPicker(this, payWays, clDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                binding.repairHandlePaid.repairHandlerPaywayTxt.setText(payWays.get(position));
                customerRepair.setHandle_pay_type(label);
                customerRepair.setHandle_pay_type_id(dictPayTypeLsit.get(position).getId());
            }
        });
    }

    /**
     * ??????????????????
     */
    private void choosePayDate() {
        //???????????????
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                binding.repairHandlePaid.repairHandlePayDate.setText(dft.format(date));
                customerRepair.setHandle_pay_time(dft.format(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true})// ??????????????????
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .build();
        pvTime.show();
    }

    /**
     * ???????????????
     */
    private void bindData(RepairsDetailModel repairsOrderDetail) {
        binding.setRepairs(repairsOrderDetail);
        binding.orderInfo.setRepairs(repairsOrderDetail);
        binding.repairsInfo.setRepairs(repairsOrderDetail);
        binding.sendOrder.setRepairs(repairsOrderDetail);
        binding.repairResponseInfo.setRepairs(repairsOrderDetail);
        binding.sendOrderInfo.setRepairs(repairsOrderDetail);
        binding.repairResponseInfo.setRepairs(repairsOrderDetail);
        binding.repairHandleInfo.setRepairs(repairsOrderDetail);
        binding.repairHandleHistory.setRepairs(repairsOrderDetail);
        binding.repairUseMaterial.setRepairs(repairsOrderDetail);
        binding.repairEvaluateInfo.setRepairs(repairsOrderDetail);
        binding.repairCallEvaluateInfo.setRepairs(repairsOrderDetail);
        binding.repairCloseInfo.setRepairs(repairsOrderDetail);
        binding.repairLateInfo.setRepairs(repairsOrderDetail);
    }

    /**
     * ????????????????????????
     */
    private void setAscription() {
        binding.repariResponse.rbNormal.setText(dictAscriptLsit.get(0).getName());
        binding.repariResponse.rbGeneral.setText(dictAscriptLsit.get(1).getName());
        binding.repariResponse.rbProject.setText(dictAscriptLsit.get(2).getName());
    }

    /**
     * ??????????????????
     */
    private void setProperTy() {
        binding.repairsInfo.rbNormal.setText(dictNatureList.get(0).getName());
        binding.repairsInfo.rbGeneral.setText(dictNatureList.get(1).getName());
        binding.repairsInfo.rbWarning.setText(dictNatureList.get(2).getName());
    }

}
