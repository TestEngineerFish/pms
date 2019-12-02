package com.einyun.app.pms.main.core.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.JsonUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.library.dashboard.model.WorkOrder;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.Constants;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.FragmentWorkBenchBinding;
import com.einyun.app.pms.main.databinding.ItemWorkTablePendingNumBinding;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

/**
 * 工作台Fragment
 */
public class WorkBenchViewModelFragment extends BaseViewModelFragment<FragmentWorkBenchBinding, WorkBenchViewModel> {
    public static WorkBenchViewModelFragment newInstance() {
        return new WorkBenchViewModelFragment();
    }

    RVBindingAdapter<ItemWorkTablePendingNumBinding, String> adapter;
    //所有的项目CODE集合（disabked == 1）  运营收缴率使用
    ArrayList<String> projectCode = new ArrayList<>();
    //所有的分期集合（disabked == 1）  待处理工单使用
    ArrayList<String> divideCode = new ArrayList<>();
    NumberFormat formatDouble = new DecimalFormat("#.##");
    DecimalFormat formatInt = new DecimalFormat("#,###");

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
        onlyInitData();
    }

    private void onlyInitData() {
        //获取配置信息
        viewModel.userMenuData(2).observe(this, userMenu -> {
            handleUserMenu(userMenu);
            //获取分期数据
            viewModel.userCenterUserList(userModuleService.getUserId()).observe(this, orgModels -> {
                handleStagingData(orgModels);
                freshData();
            });
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            freshData();
        });
    }

    /**
     *
     */
    private void freshData() {
        //运营收缴率
        if (binding.itemWorkBenchThird.layoutMain.getVisibility() == View.VISIBLE) {
            viewModel.operateCaptureData(projectCode).observe(this, operateCaptureData -> {
                binding.itemWorkBenchThird.tvTodayIncomeRate.setText(formatDouble.format(operateCaptureData.getTodayIncomeRate()));
                binding.itemWorkBenchThird.tvTodayArrearsRate.setText(formatDouble.format(operateCaptureData.getTodayArrearsRate()));
                //涨幅 下跌
                calculateOperateUpDown(binding.itemWorkBenchThird.tvDown, binding.itemWorkBenchThird.tvDownNum, operateCaptureData.getTodayIncomeRise());
                calculateOperateUpDown(binding.itemWorkBenchThird.tvUp, binding.itemWorkBenchThird.tvUpNum, operateCaptureData.getTodayArrearsRise());
            });
        }
        //工单处理情况总览
        if (binding.itemWorkBenchSecond.llWorkOrderPendingPandect.getVisibility() == View.VISIBLE) {
            viewModel.workOrderData(divideCode).observe(this, workOrderData -> {
                //工单完成率
                String completedRate = workOrderData.getRate().getCompletedRate();
                binding.itemWorkBenchSecond.tvWorkOrderProcess.setText(
                        formatDouble.format(Double.valueOf(
                                completedRate.substring(0, completedRate.length() - 1))));
                //工单及时率
                String timelyRate = workOrderData.getRate().getTimelyRate();
                binding.itemWorkBenchSecond.tvWorkOrderTimeliness.setText(
                        formatDouble.format(Double.valueOf(
                                timelyRate.substring(0, timelyRate.length() - 1))));
                //总单总数
                int num = 0;
                for (WorkOrder workOrder : workOrderData.getWorkOrder()) {
                    num += workOrder.getCount();
                }
                String format = formatInt.format(num);
                setWorkTablePendingNum(format.toCharArray());
            });
        }
        //获取审批数量
        if (binding.itemWorkBenchFirst.ssvCommonFun.getVisibility() == View.VISIBLE) {
            viewModel.getAuditCount().observe(this, integer -> {
                binding.itemWorkBenchFirst.ssvCommonFun.setRed_SP(integer);
            });
        }
        //带处理工单条数
        if (binding.itemWorkBenchFirst.llWorkOrderList.getVisibility() == View.VISIBLE) {
            //获取待办数量（客户报修，客户询问，客户投诉）
            viewModel.getBlocklogNums().observe(this, blocklogNums -> {
                binding.itemWorkBenchFirst.tvClentComplainNum.setText(StringUtil.isNullStr(blocklogNums.getComplainNum()) ? blocklogNums.getComplainNum() : "0");
                binding.itemWorkBenchFirst.tvClentInquiryNum.setText(StringUtil.isNullStr(blocklogNums.getEnquiryNum()) ? blocklogNums.getEnquiryNum() : "0");
                binding.itemWorkBenchFirst.tvClentRepairsNum.setText(StringUtil.isNullStr(blocklogNums.getRepairNum()) ? blocklogNums.getRepairNum() : "0");
            });
            //待办统计-计划、巡查、派工单
            viewModel.getWaitCount().observe(this, waitCount -> {
                //派工单
                binding.itemWorkBenchFirst.tvWorkTableDispatchNum.setText("" + waitCount.getDispatchOrderCount());
                //计划工单
                binding.itemWorkBenchFirst.tvWorkTablePlanNum.setText("" + waitCount.getPlanOrderCount());
                //巡查工单
                binding.itemWorkBenchFirst.tvWorkTablePatrolNum.setText("" + waitCount.getInspectionOrderCount());
            });
        }
    }

    private void calculateOperateUpDown(TextView tv, TextView tvNum, Double todayRise) {
        String todayNum = formatDouble.format(todayRise);
        if (todayRise < 0) {
            tv.setText(getResources().getString(R.string.down));
            tvNum.setTextColor(getResources().getColor(R.color.tv_down_color));
            tvNum.setText(todayNum + "%");
        } else if (todayRise > 0) {
            tv.setText(getResources().getString(R.string.up));
            tvNum.setTextColor(getResources().getColor(R.color.tv_up_color));
            tvNum.setText(todayNum + "%");
        } else {
            todayNum = "0";
            tvNum.setText(todayNum + "%");
        }
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
     * 处理用户首页配置项
     *
     * @param userMenu
     */
    private void handleUserMenu(String userMenu) {
        int index = 0;
        //运营收缴率
        if (userMenu.indexOf("sjlck") != -1) {
            binding.itemWorkBenchThird.layoutMain.setVisibility(View.VISIBLE);
        } else {
            index++;
            binding.itemWorkBenchThird.layoutMain.setVisibility(View.GONE);
        }
        //待处理工单列表
        if (userMenu.indexOf("dclgdck") != -1) {
            binding.itemWorkBenchFirst.llWorkOrderList.setVisibility(View.VISIBLE);
            binding.itemWorkBenchFirst.tvWorkOrderPending.setVisibility(View.VISIBLE);
        } else {
            index++;
            binding.itemWorkBenchFirst.llWorkOrderList.setVisibility(View.GONE);
            binding.itemWorkBenchFirst.tvWorkOrderPending.setVisibility(View.GONE);
        }
        //常用功能
        if (userMenu.indexOf("cygn") != -1) {
            binding.itemWorkBenchFirst.ssvCommonFun.setVisibility(View.VISIBLE);
            binding.itemWorkBenchFirst.ssvCommonFun.initUI(getActivity());
        } else {
            index++;
            binding.itemWorkBenchFirst.ssvCommonFun.setVisibility(View.GONE);
        }
        //工单处理总览
        if (userMenu.indexOf("gdclqkzl") != -1) {
            binding.itemWorkBenchSecond.llWorkOrderPendingPandect.setVisibility(View.VISIBLE);
        } else {
            index++;
            binding.itemWorkBenchSecond.llWorkOrderPendingPandect.setVisibility(View.GONE);
        }
        //客户满意度
//        if (userMenu.indexOf("khmyd") != -1) {
//            llMyd.setVisibility(View.VISIBLE);
//        } else {
//            index++;
//            llMyd.setVisibility(View.GONE);
//        }
        //经营详情洞察
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
                    });
        }
        List<String> functionList = new ArrayList<>();
        if (userMenu.indexOf("gdlbck") != -1) {
            functionList.add("gdlb");
        }
//  去除抢单
//        if (userMenu.indexOf("qd") != -1) {
//            functionList.add("qd");
//        }
        if (userMenu.indexOf("sprk") != -1) {
            functionList.add("sp");
        }
        functionList.add("cjgd");
        functionList.add("gzyl");
        functionList.add("smcl");
        Log.d(this.getActivity().getLocalClassName(), "functionList --->" + JsonUtil.toJson(functionList));
        binding.itemWorkBenchFirst.ssvCommonFun.setImageData(getActivity(), functionList);
    }

    /**
     * 处理分期数据
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

        //运营收缴率
        for (int i = 0; i < XM_List.size(); i++) {
            for (int j = 0; j < parentId_list.size(); j++) {
                if (XM_List.get(i).getId().equals(parentId_list.get(j)) &&
                        !XM_List.get(i).getDisabled().equals("1")) {
                    yy_list.add(XM_List.get(i));
                }
            }
        }

        //经营洞察详情  下面地区
        ArrayList<OrgModel> yy_list2 = new ArrayList<>();
        yy_list2.addAll(yy_list);
        for (int i = 0; i < yy_list2.size(); i++) {
            projectCode.add(yy_list2.get(i).getCode());
        }
    }

    /**
     * 设置待处理数字
     *
     * @param chars
     */
    private void setWorkTablePendingNum(char[] chars) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemWorkTablePendingNumBinding, String>(getActivity(), com.einyun.app.common.BR.numAdapter) {

                @Override
                public void onBindItem(ItemWorkTablePendingNumBinding binding, String model, int position) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_table_pending_num;
                }
            };
        }
        List<String> nums = new ArrayList<>();
        for (char ch : chars) {
            nums.add(String.valueOf(ch));
        }
        adapter.setDataList(nums);
        binding.itemWorkBenchSecond.rvWorkTablePendingNum.setAdapter(adapter);
    }

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    public void scanner() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SCANNER)
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
        } else {
            url = Constants.MORE_HTML_URL + "userToken=" + userModuleService.getUserId()
                    + "&userId=" + userModuleService.getUserId() + "&type=" + type;
        }

        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                .withString(RouteKey.KEY_WEB_URL, url)
                .navigation();
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
                ToastUtil.show(getActivity(), data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT));
            }
        }
    }
}
