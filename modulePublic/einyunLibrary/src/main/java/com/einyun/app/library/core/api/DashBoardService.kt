package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.dashboard.model.*
import com.einyun.app.library.dashboard.net.request.AllChargedRequest
import com.einyun.app.library.dashboard.net.request.OperateInRequest
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.dashboard.net.response.WorkOrderResponse
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      UCService
 * @Description:    UC服务接口，高级抽象，约束
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 14:46
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 14:46
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface DashBoardService : EinyunService {
    //工单处理情况总览
    fun workOrderData(request: WorkOrderRequest, callBack: CallBack<WorkOrderData>): LiveData<WorkOrderData>
    //运营收缴率
    fun operateCaptureData(orgCodes: List<String>, callBack: CallBack<OperateCaptureData>): LiveData<OperateCaptureData>
    //菜单配置
    fun userMenuData(menuType: Int, callBack: CallBack<String>): LiveData<String>
    //全部收费项目
    fun allChargedProject(request:AllChargedRequest,callBack: CallBack<AllChargedModel>):LiveData<AllChargedModel>
    //运营收缴率内部
    fun operatePercentIn(request:OperateInRequest,callBack: CallBack<OperateInModel>):LiveData<OperateInModel>
}