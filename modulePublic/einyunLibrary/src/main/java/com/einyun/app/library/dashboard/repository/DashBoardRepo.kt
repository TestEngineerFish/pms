package com.einyun.app.library.dashboard.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.EinyunException
import com.einyun.app.library.core.api.DashBoardService
import com.einyun.app.library.core.api.EinyunService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.dashboard.model.OperateCaptureData
import com.einyun.app.library.dashboard.model.UserMenuData
import com.einyun.app.library.dashboard.model.WorkOrderData
import com.einyun.app.library.dashboard.net.DashBoardServiceApi
import com.einyun.app.library.dashboard.net.request.WorkOrderRequest
import com.einyun.app.library.uc.user.model.TenantModel
import com.einyun.app.library.uc.user.net.URLs
import io.reactivex.functions.Consumer

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.dashboard.repository
 * @ClassName:      DashBoardRepo
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/4 0004 上午 11:22
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/4 0004 上午 11:22
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class DashBoardRepo : DashBoardService {
    override fun userMenuData(menuType: Int, callBack: CallBack<UserMenuData>): LiveData<UserMenuData> {
        val liveData = MutableLiveData<UserMenuData>()
        serviceApi?.userMenu(menuType)?.compose(RxSchedulers.inIoMain())
                ?.subscribe(
                        Consumer { response ->
                            liveData.postValue(response.value)
                            callBack.call(response.value)
                        },
                        Consumer {
                            callBack.onFaild(it)
                        }
                )
        return liveData
    }

    override fun operateCaptureData(orgCode: String, callBack: CallBack<OperateCaptureData>): LiveData<OperateCaptureData> {
        val liveData = MutableLiveData<OperateCaptureData>()
        serviceApi?.operateCapture(orgCode)?.compose(RxSchedulers.inIoMain())
                ?.subscribe(
                        Consumer { response ->
                            if (response.code.equals("0")) {
                                liveData.postValue(response.data)
                                callBack.call(response.data)
                            } else {
                                callBack.onFaild(EinyunHttpException(response))
                            }
                        },
                        Consumer {
                            callBack.onFaild(it)
                        }
                )
        return liveData
    }

    //工单处理情况总览
    override fun workOrderData(request: WorkOrderRequest, callBack: CallBack<WorkOrderData>): LiveData<WorkOrderData> {
        val liveData = MutableLiveData<WorkOrderData>()
        serviceApi?.workOrder(request)?.compose(RxSchedulers.inIoMain())
                ?.subscribe(
                        Consumer { response ->
                            if (response.isState) {
                                liveData.postValue(response.data)
                                callBack.call(response.data)
                            } else {
                                callBack.onFaild(EinyunHttpException(response))
                            }
                        },
                        Consumer {
                            callBack.onFaild(it)
                        }
                )
        return liveData
    }

    var serviceApi: DashBoardServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(DashBoardServiceApi::class.java)
    }

}