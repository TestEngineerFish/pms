package com.einyun.app.library.finance.repository

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.finance.model.FinanceModel
import com.einyun.app.library.finance.net.FinanceServiceApi
import com.einyun.app.library.finance.net.request.FinanceRequest
import io.reactivex.functions.Consumer

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.finance.repository
 * @ClassName:      FinanceRepo
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/5 0005 上午 10:25
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/5 0005 上午 10:25
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class FinanceRepo {
    var serviceApi:FinanceServiceApi?=null
    init {
        serviceApi=EinyunHttpService.getInstance().getServiceApi(FinanceServiceApi::class.java)
    }

    //经营详情洞察
    fun finaceList(request: FinanceRequest,callBack: CallBack<List<FinanceModel>>){
        serviceApi?.financeList(request)?.compose(RxSchedulers.inIo())
                ?.subscribe(Consumer {
                    response->
                    if(response.isState){
                        callBack.call(response.data)
                    }else{
                        callBack.onFaild(EinyunHttpException(response))
                    }
                }, Consumer {
                    callBack.onFaild(it)
                })
    }
}