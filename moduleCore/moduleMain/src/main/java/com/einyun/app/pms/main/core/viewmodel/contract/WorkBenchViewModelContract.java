package com.einyun.app.pms.main.core.viewmodel.contract;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.library.dashboard.model.OperateCaptureData;
import com.einyun.app.library.dashboard.model.UserMenuData;
import com.einyun.app.library.dashboard.model.WorkOrderData;
import com.einyun.app.library.resource.workorder.model.WaitCount;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.BlocklogNums;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;

import java.util.List;

public interface WorkBenchViewModelContract {
    //待处理工单
    LiveData<WorkOrderData> workOrderData(String orgId,String userId);

    //运营收缴率
    LiveData<OperateCaptureData> operateCaptureData(List<String> orgCodes);

    //菜单配置
    LiveData<String> userMenuData(int menuType);

    //获取审批数量
    LiveData<Integer> getAuditCount();

    //分期集合
    LiveData<List<OrgModel>> userCenterUserList(String userId);

    //获取待办数量（客户报修，客户询问，客户投诉）
    LiveData<BlocklogNums> getBlocklogNums();

    //待办统计-计划、巡查、派工单
    LiveData<WaitCount> getWaitCount();
}
