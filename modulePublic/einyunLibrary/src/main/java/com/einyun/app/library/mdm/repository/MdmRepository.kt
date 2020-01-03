package com.einyun.app.library.mdm.repository

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.PageBean
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.base.paging.bean.Query
import com.einyun.app.base.paging.bean.QueryBuilder
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.net.MdmServiceApi

class MdmRepository :MdmService{
    var serviceApi:MdmServiceApi?=null
    init {
        serviceApi=EinyunHttpService.getInstance().getServiceApi(MdmServiceApi::class.java)
    }

    /**
     * 获取分期下面的网格信息
     */
    override fun gridPage(divideId :String,callBack: CallBack<PageResult<GridModel>>){
        var queryBuilder=QueryBuilder()
            .setPageBean(PageBean())
            .addQueryItem("divideId",divideId)
            .addQueryItem("gridType","building_grid")
            .addQueryItem("isDeleted",0)
            .addQueryItem("enabledFlag",1)
        serviceApi?.gridPage(queryBuilder.build())?.compose(RxSchedulers.inIoMain())
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
