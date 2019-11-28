package com.einyun.app.library.core.api.proxy

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.DashBoardService
import com.einyun.app.library.core.api.UCService
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.dashboard.repository.DashBoardRepo
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.model.UserInfoModel
import com.einyun.app.library.uc.user.model.UserModel
import com.einyun.app.library.uc.user.net.request.UpdateUserRequest
import com.einyun.app.library.uc.user.repository.UserRepository

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.core.api.proxy
 * @ClassName: UCSericeImplProxy
 * @Description: UC服务代理类
 *                单一原则，开闭原则，李氏置换原则
 * @Author: chumingjun
 * @CreateDate: 2019/09/26 16:04
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/26 16:04
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class DashBoardServiceImplProxy : DashBoardService {
    override fun userMenuData(menuType: Int, callBack: CallBack<String>): LiveData<String> {
        return instance?.userMenuData(menuType, callBack)!!
    }

    override fun operateCaptureData(orgCode: String, callBack: CallBack<OperateCaptureData>): LiveData<OperateCaptureData> {
        return instance?.operateCaptureData(orgCode, callBack)!!
    }

    override fun workOrderData(request: WorkOrderRequest, callBack: CallBack<WorkOrderData>): LiveData<WorkOrderData> {
        return instance?.workOrderData(request, callBack)!!
    }

    private var instance: DashBoardService? = null

    constructor() {
        //数据代理，灵活更换实现
        instance = DashBoardRepo()//真实实现，可替换
    }

    fun getName(): String {
        return this.javaClass.simpleName
    }

}
