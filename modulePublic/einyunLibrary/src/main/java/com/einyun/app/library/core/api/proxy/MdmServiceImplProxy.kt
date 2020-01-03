package com.einyun.app.library.core.api.proxy

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.core.api.MdmService
import com.einyun.app.library.mdm.model.GridModel
import com.einyun.app.library.mdm.repository.MdmRepository

class MdmServiceImplProxy:MdmService {
    override fun gridPage(divideId: String, callBack: CallBack<PageResult<GridModel>>) {
        repo.gridPage(divideId,callBack)
    }

    var repo=MdmRepository()


}
