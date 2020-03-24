package com.einyun.app.library.uc.user.net.request

import com.einyun.app.base.BasicApplication
import com.einyun.app.base.util.DeviceUtil

class UpdateAppRequest {
    var type: Int? = 0
    var osVersion: String? = DeviceUtil.getSDKVersion().toString()
    var version: String? = DeviceUtil.getVersionName(BasicApplication.getInstance()).toString()
    var appCode: String? = "cms"
}