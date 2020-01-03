package com.einyun.app.library.core.api

import com.einyun.app.base.event.CallBack
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.mdm.model.GridModel

interface MdmService :EinyunService{
    fun gridPage(divideId :String,callBack: CallBack<PageResult<GridModel>>)
}
