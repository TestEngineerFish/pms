package com.einyun.app.library.core.api.impl

import com.einyun.app.library.core.api.DictService

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.library.core.api.impl
 * @ClassName: DictServiceImpl
 * @Description: 数据字典服务接口
 * @Author: chumingjun
 * @CreateDate: 2019/09/26 15:35
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/26 15:35
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class DictServiceImpl :DictService{
    fun getName(): String {
        return this.javaClass.simpleName
    }

}
