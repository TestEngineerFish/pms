package com.einyun.app.pms.main.core.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.JsonUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.dashboard.model.WorkOrder;
import com.einyun.app.library.mdm.model.NoticeModel;
import com.einyun.app.library.mdm.net.request.NoticeListPageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.main.BR;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.Constants;
import com.einyun.app.pms.main.core.ui.adapter.HomeCommunityNoticeAdapter;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.FragmentWorkBenchBinding;
import com.einyun.app.pms.main.databinding.ItemWorkTablePendingNumBinding;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.VISIBLE;
import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

/**
 * ?????????Fragment
 */
public class WorkBenchViewModelFragment extends BaseViewModelFragment<FragmentWorkBenchBinding, WorkBenchViewModel> {
    private KaoqingFragment kaoqingFragment;//??????
    private MapFragment mapFragment;

    public static WorkBenchViewModelFragment newInstance() {
        return new WorkBenchViewModelFragment();
    }

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    RVBindingAdapter<ItemWorkTablePendingNumBinding, String> adapter;
    //???????????????CODE?????????disabked == 1???  ?????????????????????
    ArrayList<String> projectCode = new ArrayList<>();
    //????????????????????????disabked == 1???  ?????????????????????
    ArrayList<String> divideCode = new ArrayList<>();
    NumberFormat formatDouble = new DecimalFormat("#.##");
    DecimalFormat formatInt = new DecimalFormat("#,###");
    boolean firstFresh = false;
    HomeCommunityNoticeAdapter marqueeFactory;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_bench;
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpData() {
        binding.setCallBack(this);
        kaoqingFragment = new KaoqingFragment();
        onlyInitData();
        List<String> divideCodes=userModuleService.getDivideCodes();
        Log.d("test",divideCodes.size()+"");
    }

    private void onlyInitData() {
        //??????????????????
        viewModel.userCenterUserList(userModuleService.getUserId()).observe(this, orgModels -> {
            SPUtils.put(BasicApplication.getInstance(), Constants.SP_KEY_STAGING, new Gson().toJson(orgModels));
            handleStagingData(orgModels);
            userModuleService.saveDivideCodes(divideCode);
            firstFresh = true;
            StringBuilder builder = new StringBuilder();
            for (String divide : divideCode) {
                builder.append(",").append(divide);
            }
            if (builder.length() > 1) {
                divides = builder.substring(1);
            }
            freshData();
        });

        initWheel();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            freshData();
        });
    }

    private String divides;

    private void initWheel() {
        //????????????
        marqueeFactory = new HomeCommunityNoticeAdapter(getActivity());
        binding.mvCommunityNotice.setAdapter(marqueeFactory);
    }

    @Override
    public void onResume() {
        super.onResume();
        freshData();
    }

    /**
     * ??????????????????
     */
    public void freshData() {
        if (!firstFresh) {
            return;
        }
        //??????????????????
        viewModel.userMenuData(2).observe(this, userMenu -> {
            handleUserMenu(userMenu);
            //???????????????
            if (binding.itemWorkBenchThird.layoutMain.getVisibility() == View.VISIBLE) {
                viewModel.operateCaptureData(projectCode).observe(this, operateCaptureData -> {
                    if (operateCaptureData == null) {
                        return;
                    }
                    binding.itemWorkBenchThird.tvTodayIncomeRate.setText(formatDouble.format(operateCaptureData.getTodayIncomeRate() == null ? 0 : operateCaptureData.getTodayIncomeRate()) + "%");
                    binding.itemWorkBenchThird.tvTodayArrearsRate.setText(formatDouble.format(operateCaptureData.getTodayArrearsRate() == null ? 0 : operateCaptureData.getTodayArrearsRate()) + "%");
                    //?????? ??????
//                calculateOperateUpDown(binding.itemWorkBenchThird.tvDown, binding.itemWorkBenchThird.tvDownNum, operateCaptureData.getTodayIncomeRise());
//                calculateOperateUpDown(binding.itemWorkBenchThird.tvUp, binding.itemWorkBenchThird.tvUpNum, operateCaptureData.getTodayArrearsRise());
                });
            }
            //????????????????????????
            if (binding.itemWorkBenchSecond.llWorkOrderPendingPandect.getVisibility() == View.VISIBLE) {
                setWorkTablePendingNum("0".toCharArray());
                viewModel.workOrderData("", userModuleService.getUserId()).observe(this, workOrderData -> {
                    if (workOrderData.getRate() != null) {
                        //???????????????
                        String completedRate = workOrderData.getRate().getCompletedRate();
                        binding.itemWorkBenchSecond.tvWorkOrderProcess.setText(completedRate);
                        //???????????????
                        String timelyRate = workOrderData.getRate().getTimelyRate();
                        binding.itemWorkBenchSecond.tvWorkOrderTimeliness.setText(timelyRate);
                    } else {
                        binding.itemWorkBenchSecond.tvWorkOrderProcess.setText("0%");
                        binding.itemWorkBenchSecond.tvWorkOrderTimeliness.setText("0%");
                        return;
                    }

                    //????????????
                    int num = 0;
                    for (WorkOrder workOrder : workOrderData.getWorkOrder()) {
                        num += workOrder.getCount();
                    }
                    String format = formatInt.format(num);
                    setWorkTablePendingNum(format.toCharArray());
                });
            }
            //??????????????????
            if (binding.itemWorkBenchFirst.ssvCommonFun.getVisibility() == View.VISIBLE) {
                viewModel.getAuditCount().observe(this, integer -> {
                    binding.itemWorkBenchFirst.ssvCommonFun.setRed_SP(integer);
                });
            }
            //?????????????????????
            if (binding.itemWorkBenchFirst.llWorkOrderList.getVisibility() == View.VISIBLE) {
                //??????????????????????????????????????????????????????????????????
                viewModel.getBlocklogNums().observe(this, blocklogNums -> {
                    binding.itemWorkBenchFirst.ivWaringComplain.setVisibility(blocklogNums.getComplainTimeout() == 1 ? View.VISIBLE : View.INVISIBLE);
                    binding.itemWorkBenchFirst.ivWaringRepairs.setVisibility(blocklogNums.getRepairTimeout() == 1 ? View.VISIBLE : View.INVISIBLE);
                    binding.itemWorkBenchFirst.ivWaringEnq.setVisibility(blocklogNums.getEnquiryTimeout() == 1 ? View.VISIBLE : View.INVISIBLE);
                    binding.itemWorkBenchFirst.tvClentComplainNum.setText(StringUtil.isNullStr(blocklogNums.getComplainNum()) ? blocklogNums.getComplainNum() : "0");
                    binding.itemWorkBenchFirst.tvClentInquiryNum.setText(StringUtil.isNullStr(blocklogNums.getEnquiryNum()) ? blocklogNums.getEnquiryNum() : "0");
                    binding.itemWorkBenchFirst.tvClentRepairsNum.setText(StringUtil.isNullStr(blocklogNums.getRepairNum()) ? blocklogNums.getRepairNum() : "0");
                    binding.itemWorkBenchFirst.tvWorkTableDisqualifiedNum.setText(StringUtil.isNullStr(blocklogNums.getUnqualifiedNum()) ? blocklogNums.getUnqualifiedNum() : "0");
                    binding.itemWorkBenchFirst.ivWaringDisqualified.setVisibility(blocklogNums.getUnqualifiedTimeout() == 1 ? View.VISIBLE : View.INVISIBLE);
                });
                //????????????-???????????????????????????
                viewModel.getWaitCount().observe(this, waitCount -> {
                    binding.itemWorkBenchFirst.ivWaringPlan.setVisibility(waitCount.getPlanOrderFlowListIsComing() == 1 ? View.VISIBLE : View.INVISIBLE);
                    binding.itemWorkBenchFirst.ivWaringSendOrder.setVisibility(waitCount.getDispatchOrderFlowListIsComing() == 1 ? View.VISIBLE : View.INVISIBLE);
                    binding.itemWorkBenchFirst.ivWaringPatrol.setVisibility(waitCount.getInspectionOrderFlowListIsComing() == 1 ? View.VISIBLE : View.INVISIBLE);
                    //?????????
                    binding.itemWorkBenchFirst.tvWorkTableDispatchNum.setText("" + waitCount.getDispatchOrderCount());
                    //????????????
                    binding.itemWorkBenchFirst.tvWorkTablePlanNum.setText("" + waitCount.getPlanOrderCount());
                    //????????????
                    binding.itemWorkBenchFirst.tvWorkTablePatrolNum.setText("" + waitCount.getInspectionOrderCount());
                });
            }
            getMainNote();
            showSystemNotice();
        });
    }

    private boolean isShowSystemNotice = true;

    public void showSystemNotice() {
        viewModel.getSystemNotice().observe(this, data -> {
//            String systemNotice = (String) SPUtils.get(getActivity(), "systemNotice", "");
//            boolean contains = systemNotice.contains(data.getId());
            if (data == null || !isShowSystemNotice) {
                binding.itemWorkBenchFirst.llSystemNotice.setVisibility(View.GONE);
            } else {
                binding.itemWorkBenchFirst.llSystemNotice.setVisibility(VISIBLE);
                if (StringUtil.isNullStr(data.getType())) {
                    switch (data.getType()) {
                        case "system_upgrade":
                            binding.itemWorkBenchFirst.tvSystemNotice.setText("[????????????]" + data.getTitle());
                            break;
                        case "advertisement":
                            binding.itemWorkBenchFirst.tvSystemNotice.setText("[??????]" + data.getTitle());
                            break;
                        case "new_product":
                            binding.itemWorkBenchFirst.tvSystemNotice.setText("[??????]" + data.getTitle());
                            break;
                    }
                }
                binding.itemWorkBenchFirst.tvSystemNotice.setFocusable(true);
                binding.itemWorkBenchFirst.tvSystemNotice.setFocusableInTouchMode(true);
                binding.itemWorkBenchFirst.tvSystemNotice.requestFocus();
                binding.itemWorkBenchFirst.tvSystemNotice.setOnClickListener(v -> {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_SYSTEM_NOTICE_DETAIL)
                            .withString(RouteKey.KEY_ID, data.getId()).navigation();
                });
                binding.itemWorkBenchFirst.ivSystemNoticeClose.setOnClickListener(v -> {
//                    SPUtils.put(getActivity(), "systemNotice", systemNotice + "_" + data.getId());
                    binding.itemWorkBenchFirst.llSystemNotice.setVisibility(View.GONE);
                    this.isShowSystemNotice = false;
                });
            }
        });
    }

    /**
     * ????????????????????????
     */
    List<NoticeModel> noticeModels = new ArrayList<>();

    private void getMainNote() {

        NoticeListPageRequest noticeListPageRequest = new NoticeListPageRequest();
        noticeListPageRequest.setOrg_id(divides);
        viewModel.getNotices(noticeListPageRequest, null).observe(this, dataBeans -> {
            noticeModels = dataBeans;
            marqueeFactory.setOnItemClickListener(new HomeCommunityNoticeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    if (StringUtil.isNullStr(noticeModels.get(position).getId())) {
                        if (IsFastClick.isFastDoubleClick()) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_NOTICE_DETAIL)
                                    .withString(RouteKey.KEY_ID, noticeModels.get(position).getId())
                                    .withString(RouteKey.KEY_WEB_TITLE, "????????????")
                                    .navigation();
                        }
                    }
                }
            });
            if (noticeModels != null && noticeModels.size() != 0) {
                marqueeFactory.setData(null);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //??????????????????
                        if (noticeModels.size() <= 5) {
                            marqueeFactory.setData(noticeModels);
                        } else {
                            marqueeFactory.setData(noticeModels.subList(0, 5));
                        }
                        if (noticeModels != null && noticeModels.size() == 1) {
                            binding.mvCommunityNotice.stopFlipping();
                        } else {
                            binding.mvCommunityNotice.startFlipping();
                        }
                    }
                }, 1000);

            } else {
                noticeModels = new ArrayList<>();
                noticeModels.add(new NoticeModel("????????????"));
                marqueeFactory.setData(noticeModels);
                binding.mvCommunityNotice.stopFlipping();
            }
        });
    }

    @Override
    protected WorkBenchViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(WorkBenchViewModel.class);
    }

    public void onPagingButtonClick() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
                .navigation();
    }

    public void onX5WebViewButtonClick() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                .withString(RouteKey.KEY_WEB_URL, "http://soft.imtt.qq.com/browser/tes/feedback.html")
                .navigation();
    }

    public void onPointCheckButtonClick() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_POINT_CHECK_ACTIVITY).navigation();
    }

    public void onWorkButtonClick() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_BLOCK_CHOOSE)
                .withString(RouteKey.KEY_USER_ID, userModuleService.getUserId())
                .navigation(getActivity(), RouterUtils.ACTIVITY_REQUEST_BLOCK_CHOOSE);
    }

    /**
     * ???????????????????????????
     *
     * @param userMenu
     */
    private void handleUserMenu(String userMenu) {
        int index = 0;
        //???????????????
        if (userMenu.indexOf("sjlck") != -1) {
            binding.itemWorkBenchThird.layoutMain.setVisibility(View.VISIBLE);
        } else {
            index++;
            binding.itemWorkBenchThird.layoutMain.setVisibility(View.GONE);
        }
        //?????????????????????
        if (userMenu.indexOf("dclgdck") != -1) {
            binding.itemWorkBenchFirst.llWorkOrderList.setVisibility(View.VISIBLE);
            binding.itemWorkBenchFirst.tvWorkOrderPending.setVisibility(View.VISIBLE);
        } else {
            index++;
            binding.itemWorkBenchFirst.llWorkOrderList.setVisibility(View.GONE);
            binding.itemWorkBenchFirst.tvWorkOrderPending.setVisibility(View.GONE);
        }
        //????????????
        if (userMenu.indexOf("cygn") != -1) {
            binding.itemWorkBenchFirst.ssvCommonFun.setVisibility(View.VISIBLE);
//            binding.itemWorkBenchFirst.ssvCommonFun.initUI(getActivity());
        } else {
            index++;
            binding.itemWorkBenchFirst.ssvCommonFun.setVisibility(View.GONE);
        }
        //??????????????????
        if (userMenu.indexOf("gdclqkzl") != -1) {
            binding.itemWorkBenchSecond.llWorkOrderPendingPandect.setVisibility(View.VISIBLE);
        } else {
            index++;
            binding.itemWorkBenchSecond.llWorkOrderPendingPandect.setVisibility(View.GONE);
        }
        //???????????????
//        if (userMenu.indexOf("khmyd") != -1) {
//            llMyd.setVisibility(View.VISIBLE);
//        } else {
//            index++;
//            llMyd.setVisibility(View.GONE);
//        }
        //??????????????????
//        if (userMenu.indexOf("jyxqdc") != -1) {
//            llJyxqdc.setVisibility(View.VISIBLE);
//        } else {
//            index++;
//            llJyxqdc.setVisibility(View.GONE);
//        }

        if (index == 4) {
            binding.home.setVisibility(View.GONE);
            new AlertDialog(getActivity()).builder().setTitle(getResources().getString(R.string.tip))
                    .setMsg(getResources().getString(R.string.user_no_permission)).
                    setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_USER_LOGIN)
                                    .navigation();
                            getActivity().finish();
                        }
                    }).show();
        }

        int functionIndex = 0;
        List<String> functionList = new ArrayList<>();
        //????????????
        if (userMenu.indexOf("smcl") != -1) {
            functionList.add("smcl");
            functionIndex++;
        }
        //??????
        if (userMenu.indexOf("dj_new") != -1) {
            functionList.add("dj");
            functionIndex++;
        }
        //??????
        if (userMenu.indexOf("sprk_new") != -1) {
            functionList.add("sp");
            functionIndex++;
        }
        //??????
        if (userMenu.indexOf("sfzl") != -1) {
            functionList.add("sfzl");
            functionIndex++;
        }
        //????????????
        if (userMenu.indexOf("cjgd") != -1) {
            functionList.add("cjgd");
            functionIndex++;
        }
        //????????????
        if (userMenu.indexOf("gdlbck_new") != -1) {
            functionList.add("gdlb");
            functionIndex++;
        }
        //????????????
        if (userMenu.indexOf("gzyl") != -1) {
            functionList.add("gzyl");
            functionIndex++;
        }
        if (functionIndex == 0) {
            binding.itemWorkBenchFirst.ssvCommonFun.setVisibility(View.GONE);
        }
        Log.d(this.getActivity().getLocalClassName(), "functionList --->" + JsonUtil.toJson(functionList));
        binding.itemWorkBenchFirst.ssvCommonFun.setImageData(getActivity(), functionList);
        orderTitle(userMenu);
    }

    int orderTitleFirstIndex = 0;
    int orderTitleSecondIndex = 0;

    private void orderTitle(String userMenu) {
        orderTitleFirstIndex = 0;
        orderTitleSecondIndex = 0;
        //????????????
        menuFirst(binding.itemWorkBenchFirst.rlUnqualified, userMenu, "unqualified");
        //?????????
        menuFirst(binding.itemWorkBenchFirst.rlDispatch, userMenu, "dispatch");
        //????????????
        menuFirst(binding.itemWorkBenchFirst.rlPlan, userMenu, "plan");
        //????????????
        menuFirst(binding.itemWorkBenchFirst.rlInspection, userMenu, "inspection");
        //????????????
        menuSecond(binding.itemWorkBenchFirst.rlComplain, userMenu, "complain");
        //????????????
        menuSecond(binding.itemWorkBenchFirst.rlEnquiry, userMenu, "enquiry");
        //????????????
        menuSecond(binding.itemWorkBenchFirst.rlRepair, userMenu, "repair");
        if (orderTitleFirstIndex == 0 && orderTitleSecondIndex == 0) {
            binding.itemWorkBenchFirst.llWorkOrderList.setVisibility(View.GONE);
            binding.itemWorkBenchFirst.tvWorkOrderPending.setVisibility(View.GONE);
        } else {
            //??????????????????
            if (orderTitleFirstIndex == 0) {
                binding.itemWorkBenchFirst.tvWorkOrderTitleFirst.setVisibility(View.GONE);
            } else {
                binding.itemWorkBenchFirst.tvWorkOrderTitleFirst.setVisibility(VISIBLE);
            }
            //??????????????????
            if (orderTitleSecondIndex == 0) {
                binding.itemWorkBenchFirst.tvWorkOrderTitleSecond.setVisibility(View.GONE);
            } else {
                binding.itemWorkBenchFirst.tvWorkOrderTitleSecond.setVisibility(VISIBLE);
            }
            binding.itemWorkBenchFirst.llWorkOrderList.setVisibility(VISIBLE);
            binding.itemWorkBenchFirst.tvWorkOrderPending.setVisibility(VISIBLE);
        }
    }

    private void menuFirst(View v, String userMenu, String tag) {
        if (userMenu.indexOf(tag) != -1) {
            v.setVisibility(VISIBLE);
            orderTitleFirstIndex++;
        } else {
            v.setVisibility(View.GONE);
        }
    }

    private void menuSecond(View v, String userMenu, String tag) {
        if (userMenu.indexOf(tag) != -1) {
            v.setVisibility(VISIBLE);
            orderTitleSecondIndex++;
        } else {
            v.setVisibility(View.GONE);
        }
    }

    /**
     * ??????????????????
     *
     * @param orgModels
     */
    private void handleStagingData(List<OrgModel> orgModels) {
        ArrayList<String> parentId_list = new ArrayList<>();
        ArrayList<OrgModel> yy_list = new ArrayList<>();
        ArrayList<OrgModel> ZB_List = new ArrayList<>();
        ArrayList<OrgModel> CQ_List = new ArrayList<>();
        ArrayList<OrgModel> XM_List = new ArrayList<>();
        ArrayList<OrgModel> DK_List = new ArrayList<>();
        for (OrgModel orgModel : orgModels) {
            String disabked = orgModel.getDisabled() + "";
            String grade = orgModel.getGrade() + "";
            switch (grade) {
                case "organization_type_company":
                    ZB_List.add(orgModel);
                    break;
                case "organization_type_area":
                    CQ_List.add(orgModel);
                    break;
                case "organization_type_project":
                    XM_List.add(orgModel);
                    if (disabked.equals("1")) {
                        projectCode.add(orgModel.getCode());
                    }
                    break;
                case "organization_type_divide":
                    DK_List.add(orgModel);
                    if (disabked.equals("1")) {
                        divideCode.add(orgModel.getId());
                        parentId_list.add(orgModel.getParentId());
                    }
                    break;
            }
        }

        //???????????????
        for (int i = 0; i < XM_List.size(); i++) {
            for (int j = 0; j < parentId_list.size(); j++) {
                if (XM_List.get(i).getId().equals(parentId_list.get(j)) &&
                        !XM_List.get(i).getDisabled().equals("1")) {
                    yy_list.add(XM_List.get(i));
                }
            }
        }

        //??????????????????  ????????????
        ArrayList<OrgModel> yy_list2 = new ArrayList<>();
        yy_list2.addAll(yy_list);
        for (int i = 0; i < yy_list2.size(); i++) {
            projectCode.add(yy_list2.get(i).getCode());
        }
    }

    /**
     * ?????????????????????
     *
     * @param chars
     */
    private void setWorkTablePendingNum(char[] chars) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemWorkTablePendingNumBinding, String>(getActivity(), BR.num) {

                @Override
                public void onBindItem(ItemWorkTablePendingNumBinding binding, String model, int position) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_table_pending_num;
                }
            };
        }
        binding.itemWorkBenchSecond.rvWorkTablePendingNum.setAdapter(adapter);
        ArrayList<String> nums = new ArrayList<>();
        for (char ch : chars) {
            nums.add(String.valueOf(ch));
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        binding.itemWorkBenchSecond.rvWorkTablePendingNum.setLayoutManager(manager);
        adapter.setDataList(nums);
    }

    public void scanner() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SCANNER)
                .withString(RouteKey.KEY_HOME_ENTER, RouteKey.KEY_HOME_ENTER)
                .navigation(getActivity(), RouterUtils.ACTIVITY_REQUEST_SCANNER);
    }

    public void jumpWorkTable(String routerName) {
        ARouter.getInstance()
                .build(routerName)
                .navigation();
    }

    public void jumpX5Web(int type) {
        String url;
        if (type == 0) {
            url = Constants.MORE_HTML_URL + "userToken=" + userModuleService.getUserId()
                    + "&userId=" + userModuleService.getUserId();
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                    .withString(RouteKey.KEY_WEB_URL, url)
                    .navigation();
        } else if (type == 2) {//???????????????????????? ????????????
            HashMap<String, String> map = new HashMap<>();
            map.put("user_name", UserUtil.getUserName());
            MobclickAgent.onEvent(getActivity(), CustomEventTypeEnum.ORDER_HANDLE.getTypeName(), map);
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_ORDER_CONDITION_PANDECT)
                    .navigation();
        } else if (type == 1) {//??????????????? ????????????
            HashMap<String, String> map = new HashMap<>();
            map.put("user_name", UserUtil.getUserName());
            MobclickAgent.onEvent(getActivity(), CustomEventTypeEnum.OPERATE_CAPTURE_RATE.getTypeName(), map);
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_OPERATE_PERCENT)
                    .withObject(RouteKey.ORGCODE, projectCode)
                    .navigation();
        } else {
            url = Constants.MORE_HTML_URL + "userToken=" + userModuleService.getUserId()
                    + "&userId=" + userModuleService.getUserId() + "&type=" + type;
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                    .withString(RouteKey.KEY_WEB_URL, url)
                    .navigation();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RouterUtils.ACTIVITY_REQUEST_BLOCK_CHOOSE) {
                String blockId = data.getStringExtra(DataConstants.KEY_BLOCK_ID);
                String blockName = data.getStringExtra(DataConstants.KEY_BLOCK_NAME);
                String blockCode = data.getStringExtra(DataConstants.KEY_BLOCK_CODE);
                Logger.d(blockId + ":" + blockName + ":" + blockCode);
            } else if (requestCode == RouterUtils.ACTIVITY_REQUEST_SCANNER) {
//                ToastUtil.show(getActivity(), data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT));
//                String code = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT);
//                if (code.length()<3) {
//                    return;
//                }
//                String scanCode = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT).substring(2, code.length());
//                if (code.startsWith("30")) {
//                    ARouter.getInstance()
//                            .build(RouterUtils.ACTIVITY_SCAN_RES)
//                            .withString(RouteKey.KEY_RES_ID,scanCode)
//                            .withString(RouteKey.KEY_PATROL_ID, scanCode)
//                            .withString(RouteKey.KEY_TYPE,"30")
//                            .navigation();
//                }else if (code.startsWith("31")){
//                    ARouter.getInstance()
//                            .build(RouterUtils.ACTIVITY_SCAN_RES)
//                            .withString(RouteKey.KEY_RES_ID, scanCode)
//                            .withString(RouteKey.KEY_PATROL_ID, scanCode)
//                            .withString(RouteKey.KEY_TYPE,"31")
//                            .navigation();
//                }else {
//                    ToastUtil.show(getActivity(), "?????????????????????");
//                }


            }
        }
    }

    public void goToNotice() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_NOTICE_LIST).navigation();
    }

    public void kaoqing() {
        kaoqingFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    public void showMap() {
        mapFragment.show(getActivity().getSupportFragmentManager(), "");
    }
}
