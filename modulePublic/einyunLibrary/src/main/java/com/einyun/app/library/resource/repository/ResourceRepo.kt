package com.einyun.app.library.resource.repository

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.ResourceService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.resource.model.LineType
import com.einyun.app.library.resource.net.ResourceServiceApi

class ResourceRepo :ResourceService{
    var serviceApi:ResourceServiceApi?=null
    init {
        serviceApi=EinyunHttpService.getInstance().getServiceApi(ResourceServiceApi::class.java)
    }

    /**
     * 获取条线类型
     */
    override fun getLineType(callBack: CallBack<List<LineType>>) {
        serviceApi?.getLineAndLine()?.compose(RxSchedulers.inIo())
            ?.subscribe(
                {
                    if(it.isState){
                        callBack.call(it.data)
                    }else{
                        callBack.onFaild(EinyunHttpException(it))
                    }
                },{
                    callBack.onFaild(it)
                }
            )
    }
}
