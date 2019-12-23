package com.einyun.app.library.core.api.impl

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.core.api.proxy.MdmServiceImplProxy
import com.einyun.app.library.mdm.model.GridModel

class MdmServiceImpl:MdmService {
    override fun gridPage(divideId: String, callBack: CallBack<PageResult<GridModel>>) {
        proxy.gridPage(divideId,callBack)
    }

    var proxy=MdmServiceImplProxy()

}
