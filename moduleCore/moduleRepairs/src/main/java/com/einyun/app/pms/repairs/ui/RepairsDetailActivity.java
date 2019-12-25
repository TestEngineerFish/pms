package com.einyun.app.pms.repairs.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.model.BottomPickerModel;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.photo.PhotoSelectAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.ui.widget.SelectRepairsTypeView;
import com.einyun.app.common.ui.widget.SwipeItemLayout;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.Glide4Engine;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    static final String HANDLE_PAID = "1";//有偿
    static final String HANDLE_NO_PAID = "0";//无偿
    RepairsDetailModel.DataBean.CustomerRepairModelBean customerRepair;

    @Override
    protected RepairDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(RepairDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repairs_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_repair);
        setRightOption(R.drawable.iv_histroy);
        setRightTxt(R.string.text_histroy);
        setRightTxtColor(R.color.blueTextColor);
        setView(nodeId);//根据状态值显示相应布局
        //选择人员
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.sendOrder.repairSelectedPepple.setText(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user_id(model.getId());
        });
        //添加材料
        LiveEventBus.get(LiveDataBusKey.POST_REPAIR_ADD_MATERIAL, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean.class).observe(this, model -> {
            if (detialModel != null) {
                detialModel.getData().getCustomer_repair_model().getSub_repair_materials().add(model);
                materialAdapter.setDataList(detialModel.getData().getCustomer_repair_model().getSub_repair_materials());
            }
        });
        materialAdapter = new RVBindingAdapter<ItemHandleBinding, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean>(this, com.einyun.app.pms.repairs.BR.material) {
            @Override
            public void onBindItem(ItemHandleBinding binding, RepairsDetailModel.DataBean.CustomerRepairModelBean.InitDataBean.RepairMaterialsBean model, int position) {
                //左滑删除
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
            updateUI(repairsDetail);
            saveHandleRequest = new SaveHandleRequest(orderId, detialModel.getData().getCustomer_repair_model());
        });
        //获取报修类别与条线
        viewModel.repairTypeList().observe(this, doorResult -> {
            this.doorResult = doorResult.getChildren();
        });
        //获取报修性质评估
        viewModel.getByTypeKey(Constants.REPAIR_NATURE).observe(this, dictDataModels -> {
            dictNatureList = dictDataModels;
            setProperTy();
        });
        //获取预约上门时间
        viewModel.getByTypeKey(Constants.REPAIR_TIME).observe(this, dictDataModels -> {
            dictTimeList = dictDataModels;
        });
        //获取支付方式
        viewModel.getByTypeKey(Constants.REPAIR_PAY_TYPE).observe(this, dictDataModels -> {
            dictPayTypeLsit = dictDataModels;
        });
        //获取报修工单归属
        viewModel.getByTypeKey(Constants.REPAIR_WORK_ASCRIPTION).observe(this, dictDataModels -> {
            dictAscriptLsit = dictDataModels;
            setAscription();
        });

    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        //标题栏右侧文字点击时件
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
         * 报修性质
         * */
        binding.repairsInfo.rgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_normal) {
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass(dictNatureList.get(0).getName());
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass_id(dictNatureList.get(0).getId());

                }
                if (checkedId == R.id.rb_general) {
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass(dictNatureList.get(1).getName());
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass_id(dictNatureList.get(1).getId());
                }
                if (checkedId == R.id.rb_warning) {
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass(dictNatureList.get(2).getName());
                    detialModel.getData().getCustomer_repair_model().setBx_property_ass_id(dictNatureList.get(2).getId());
                }
            }
        });
        /**
         * 处理添加图片
         */
        photoListFormAdapter.setAddListener(selectedSize -> {
            if (photoListFormAdapter.getSelectedPhotos().size() >= MAX_PHOTO_SIZE) {
                ToastUtil.show(getApplicationContext(), R.string.upload_pic_max);
                return;
            }
            Matisse.from(this) //加号添加图片
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
        //预约上门时间
        binding.repairsInfo.repairReportAppointChange.setOnClickListener(this);
        //报修区域
        binding.repairsInfo.repariReportAreaChange.setOnClickListener(this);
        //报修类别
        binding.repairsInfo.repairReportKindChange.setOnClickListener(this);
        //选择付款方式
        binding.repairHandlePaid.repairHandlePayway.setOnClickListener(this);
        //选择付款日期
        binding.repairHandlePaid.repairHandlePayDate.setOnClickListener(this);
        //是否有偿
        binding.repairHandle.repairHandleIfPaid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_yes) {
                    binding.repairHandlePaid.getRoot().setVisibility(View.VISIBLE);
                    detialModel.getData().getCustomer_repair_model().setHandle_is_paid(HANDLE_PAID);//有偿
                } else {
                    binding.repairHandlePaid.getRoot().setVisibility(View.GONE);
                    detialModel.getData().getCustomer_repair_model().setHandle_is_paid(HANDLE_NO_PAID);//无偿
                }
            }
        });
        //加载更多
        binding.repairHandleHistory.handleAddMore.setOnClickListener(this);
        //强制闭单
        binding.repairClosePostpone.applyPostpone.setOnClickListener(this);
        //申请延期
        binding.repairClosePostpone.repairApplyLate.setOnClickListener(this);
        //工单归属
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择图片返回
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_REQUEST_PIC_PICK) {
            if (data == null) return;
            List<Uri> uris = Matisse.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                photoListFormAdapter.addPhotos(uris);
            }
        }
    }
    //立即调用方法
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            //计算时间
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(customerRepair.getBx_time()));
        }
    };
    /**
     * 详情数据获取后进行UI展示
     *
     * @param
     */
    private void updateUI(RepairsDetailModel repairsOrderDetail) {
        detialModel = repairsOrderDetail;
        if (detialModel == null) {
            return;
        }
        detialModel.setNodeId(nodeId);
        customerRepair = detialModel.getData().getCustomer_repair_model();
        binding.tvHandleTime.setText(TimeUtil.getTimeExpend(customerRepair.getBx_time()));
        runnable.run();
        bindData(repairsOrderDetail);
        if (detialModel.getData().getCustomer_repair_model().getHandle_time() != null) {
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detialModel.getData().getCustomer_repair_model().getHandle_time().toString()));
        }
        if (detialModel.getHandleList() != null) {
            binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
            if (detialModel.getHandleList().size() > 3) {
                handleAdapter.setDataList(detialModel.getHandleList().subList(0, 3));
            } else {
                handleAdapter.setDataList(detialModel.getHandleList());
            }

        } else {
            binding.repairHandleHistory.getRoot().setVisibility(View.GONE);
        }
        //添加材料
        if (detialModel.getData().getCustomer_repair_model().getSub_repair_materials() != null) {
            materialAdapter.setDataList(detialModel.getData().getCustomer_repair_model().getSub_repair_materials());
        }
        //申请闭单信息
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
        //申请延期信息
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
        //评价状态评分
        if (customerRepair.getReturn_score() != null) {
            binding.repairEvaluateInfo.attitudeStar.setStar(Float.parseFloat(customerRepair.getReturn_score()));
        }
        if (customerRepair.getService_quality_score() != null) {
            binding.repairEvaluateInfo.qualityStar.setStar(Float.parseFloat(customerRepair.getService_quality_score()));
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
            handleAdapter.setDataList(detialModel.getHandleList());
        }
        if (v.getId() == R.id.apply_postpone) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_CLOSE).withString(RouteKey.KEY_MID_URL, RouteKey.KEY_MID_URL_REPAIRS)
                    .withString(RouteKey.KEY_TASK_ID, taskId)
                    .navigation();
        }
        if (v.getId() == R.id.repair_apply_late) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_LATE).withString(RouteKey.KEY_ORDER_ID, orderId)
                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                    .withString(RouteKey.KEY_LATER_ID, RouteKey.KEY_CUSTOMER_REPAIRS)
                    .withString(RouteKey.KEY_DIVIDE_ID, customerRepair.getBx_dk_id())
                    .withString(RouteKey.KEY_DIVIDE_NAME, customerRepair.getBx_dk())
                    .navigation();
        }

    }

    /**
     * 处理表单
     */
    private void handleSubmit() {
        if (detialModel == null) {
            return;
        }
        doReuest();
    }

    /**
     * 选择指派人
     */
    private void selectPeple() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, detialModel.getData().getCustomer_repair_model().getBx_dk_id())
                .withString(RouteKey.KEY_PROJECT_ID, detialModel.getData().getCustomer_repair_model().getU_project_id())
                .navigation();
    }

    /**
     * 派单，响应,处理，评价
     */
    private void doReuest() {
        filterRequest(nodeId);
        viewModel.repairSend(new RepairSendOrderRequest(detialModel.getData().getCustomer_repair_model(), new RepairSendOrderRequest.DoNextParamBean(taskId))).observe(this, status -> {
            if (status) {
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
                ToastUtil.show(this, R.string.text_submit_fale);
            }
        });
    }

    /**
     * 保存处理
     */
    private void saveHandler() {
        if (TextUtils.isEmpty(binding.repairHandleResult.repairHandleReason.getString())) {
            ToastUtil.show(this, R.string.text_please_enter_reason);
        } else {
            saveHandleRequest.getBizData().setHandle_result(binding.repairHandleResult.repairHandleReason.getString());
            viewModel.saveHandler(saveHandleRequest).observe(this, status -> {
                if (status) {
                    new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                            .setMsg(getResources().getString(R.string.text_save_success)).
                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewModel.refreshUI();
                                    binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
                                    RepairsDetailModel.HandleListBean handleListBean = new RepairsDetailModel.HandleListBean();
                                    handleListBean.setHandle_result(binding.repairHandleResult.repairHandleReason.getString());
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
     * 根据状态显示布局
     */

    private void setView(String status) {
        //响应状态
        if (status.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            binding.repairsInfo.repairReportAppointChange.setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)) {
                binding.repariResponse.getRoot().setVisibility(View.VISIBLE);
                binding.repairClosePostpone.getRoot().setVisibility(View.VISIBLE);
                binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            }
            return;
        }
        //处理状态
        if (status.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairClosePostpone.getRoot().setVisibility(View.VISIBLE);
            binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)) {
                binding.repairHandle.getRoot().setVisibility(View.VISIBLE);
                binding.repairHandleResult.getRoot().setVisibility(View.VISIBLE);
                binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
                binding.repairUseMaterial.getRoot().setVisibility(View.VISIBLE);
                binding.repairHandlePaid.getRoot().setVisibility(View.VISIBLE);
                binding.handleSaveSubmit.setVisibility(View.VISIBLE);
            }
            return;
        }
        //待评价状态
        if (status.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
            if (!listTtype.equals(RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)) {
                binding.repairEvaluate.getRoot().setVisibility(View.VISIBLE);
                binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            }
            return;
        }
        //待派单
        if (status.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.repairReportAreaRed.setVisibility(View.VISIBLE);
            binding.repairsInfo.repairReportKindRed.setVisibility(View.VISIBLE);
            binding.repairsInfo.repairReportNatureRed.setVisibility(View.VISIBLE);
            binding.repairsInfo.repariReportAreaChange.setVisibility(View.VISIBLE);
            binding.repairsInfo.repairReportKindChange.setVisibility(View.VISIBLE);
            binding.repairsInfo.repairReportAppointChange.setVisibility(View.VISIBLE);
            binding.repairsInfo.rgs.setVisibility(View.VISIBLE);
            binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            return;
        }
        //已关闭
        if (status.equals("")) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleHistory.getRoot().setVisibility(View.VISIBLE);
            binding.repairEvaluateInfo.getRoot().setVisibility(View.VISIBLE);
            return;
        }
        //超时派单
        if (status.equals(RouteKey.REPAIR_STATUS_SEND_ORDER_LATE)) {
            binding.orderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairsInfo.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrder.getRoot().setVisibility(View.VISIBLE);
            binding.sendOrder.repairSendTxt.setText(R.string.text_late_send_order);
            binding.repairsInfo.repairAssesTxt.setVisibility(View.VISIBLE);
            binding.repairDetailSubmit.setVisibility(View.VISIBLE);
            return;
        }


    }

    /**
     * 选择材料
     */
    private void addMaterial() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_ADD_MATERIAL).navigation();
    }

    /**
     * 过滤请求
     */
    private void filterRequest(String status) {
        //响应状态
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
        //处理状态
        if (status.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            mergeHandleRequest();
            return;
        }
        //待评价状态
        if (status.equals(RouteKey.REPAIR_STATUS_EVALUATE)) {
            mergeEvaluateRequest();
            return;
        }
        //待派单
        if (status.equals(RouteKey.REPAIR_STATUS_SEND_ORDER)) {
            if (TextUtils.isEmpty(binding.sendOrder.repairSendReason.getString())) {
                ToastUtil.show(this, R.string.text_please_enter_reason);
            } else {
                detialModel.getData().getCustomer_repair_model().setHandle_result(binding.sendOrder.repairSendReason.getString());
            }
            if (TextUtils.isEmpty(detialModel.getData().getCustomer_repair_model().getAssign_grab_user())) {
                ToastUtil.show(this, R.string.txt_plese_select_people);
            }
            return;
        }
    }

    /**
     * 处理评价request
     */
    private void mergeEvaluateRequest() {
        if (binding.repairEvaluate.radiogroup.getCheckedRadioButtonId() == R.id.rb_solve) {
            detialModel.getData().getCustomer_repair_model().setC_is_solve(1);//已解决
        } else {
            detialModel.getData().getCustomer_repair_model().setC_is_solve(0);//未解决
            if (TextUtils.isEmpty(binding.repairEvaluate.unsolvedMark.getString())) {
                ToastUtil.show(this, R.string.text_please_enter_reason);
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
     * 处理处理request
     */
    private void mergeHandleRequest() {
        if (TextUtils.isEmpty(binding.repairHandleResult.repairHandleReason.getString())) {
            ToastUtil.show(this, R.string.text_please_enter_reason);
        } else {
            //维修工时
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
            }

        }
        if (photoListFormAdapter.getSelectedPhotos().size() >= 0) {
            uploadImages();
        } else {
            ToastUtil.show(this, R.string.text_alert_handle_pic);
        }

    }

    /**
     * 上传图片
     */
    private void uploadImages() {
        viewModel.uploadImages(photoListFormAdapter.getSelectedPhotos()).observe(this, picUrls -> {
            customerRepair.setHandle_attach(new ImageUploadManager().toJosnString(picUrls));
        });
    }

    /**
     * 预约上门时间
     */


    private void repairTime() {
        if (dictTimeList == null || dictTimeList.size() == 0) {
            ToastUtil.show(this, "暂无预约上门时间");
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
     * 报修区域
     */
    private void repairLocation() {
        if (doorResult.size() == 0) {
            ToastUtil.show(this, "暂无报修区域");
            return;
        }
        List<String> txStrList = new ArrayList<>();
        for (Door data : doorResult) {
            txStrList.add(data.getDataName());
        }
        BottomPicker.buildBottomPicker(this, txStrList, clDefaultPos, new BottomPicker.OnItemPickListener() {
            @Override
            public void onPick(int position, String label) {
                clDefaultPos = position;
                for (Door data : doorResult) {
                    if (data.getDataName().equals(txStrList.get(position))) {
                        customerRepair.setBx_area(data.getDataName());
                        customerRepair.setBx_area_id(data.getDataKey());
                        binding.repairsInfo.repairReportArea.setText(data.getDataName());
                    }
                }
            }
        });
    }

    /**
     * 报修类别
     */
    private void repairType() {
        if (doorResult.get(clDefaultPos).getChildren() == null || doorResult.get(clDefaultPos).getChildren().size() == 0) {
            ToastUtil.show(this, "暂无报修类别");
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
     * 付款方式选择
     */
    private void choosePayWay() {
        List<String> payWays = new ArrayList<>();
        for (DictDataModel dictDataModel : dictPayTypeLsit) {
            payWays.add(dictDataModel.getName());
            Log.d("Test", dictDataModel.getName());
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
     * 付款日期选择
     */
    private void choosePayDate() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                customerRepair.setHandle_pay_time(date.getTime());
//                    request.setExpectTime(dft.format(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .build();
        pvTime.show();
    }

    /**
     * 绑定数据源
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
        binding.repairCloseInfo.setRepairs(repairsOrderDetail);
        binding.repairLateInfo.setRepairs(repairsOrderDetail);
    }

    /**
     * 设置工单归属数据
     */
    private void setAscription() {
        binding.repariResponse.rbNormal.setText(dictAscriptLsit.get(0).getName());
        binding.repariResponse.rbGeneral.setText(dictAscriptLsit.get(1).getName());

    }

    /**
     * 设置性质评估
     */
    private void setProperTy() {
        binding.repairsInfo.rbNormal.setText(dictNatureList.get(0).getName());
        binding.repairsInfo.rbGeneral.setText(dictNatureList.get(1).getName());
        binding.repairsInfo.rbWarning.setText(dictNatureList.get(2).getName());
    }

}
