package com.einyun.app.library.core.api.impl

import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.ResourceService
import com.einyun.app.library.core.api.proxy.ResourceServiceProxy
import com.einyun.app.library.resource.model.LineType

class ResourceServiceImpl:ResourceService {
    var proxy=ResourceServiceProxy()

    override fun getLineType(callBack: CallBack<List<LineType>>) {
        proxy.getLineType(callBack)
    }
}
