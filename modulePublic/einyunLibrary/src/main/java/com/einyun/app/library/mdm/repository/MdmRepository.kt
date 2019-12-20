package com.einyun.app.library.mdm.repository

import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.mdm.net.MdmServiceApi

class MdmRepository {
    var serviceApi:MdmServiceApi?=null
    init {
        serviceApi=EinyunHttpService.getInstance().getServiceApi(MdmServiceApi::class.java)
    }


}
