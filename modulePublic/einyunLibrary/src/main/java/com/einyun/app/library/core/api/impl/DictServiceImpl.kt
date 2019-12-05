package com.einyun.app.library.core.api.impl

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.core.api.DictService
import com.einyun.app.library.core.api.proxy.DashBoardServiceImplProxy
import com.einyun.app.library.core.api.proxy.DictServiceImplProxy
import com.einyun.app.library.portal.dictdata.model.DictDataModel

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
    override fun getByTypeKey(
        typeKey: String,
        callBack: CallBack<List<DictDataModel>>?
    ): LiveData<List<DictDataModel>> {
        return proxy.getByTypeKey(typeKey, callBack)
    }
    var proxy: DictServiceImplProxy = DictServiceImplProxy()
    fun getName(): String {
        return this.javaClass.simpleName
    }

}
