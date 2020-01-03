package com.einyun.app.library.core.api.proxy

import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.ResourceService
import com.einyun.app.library.resource.model.LineType
import com.einyun.app.library.resource.repository.ResourceRepo

class ResourceServiceProxy:ResourceService {
    var instance=ResourceRepo()
    override fun getLineType(callBack: CallBack<List<LineType>>) {
        instance.getLineType(callBack)
    }
}
