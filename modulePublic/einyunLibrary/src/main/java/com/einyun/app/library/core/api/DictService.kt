package com.einyun.app.library.core.api

import androidx.lifecycle.LiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.library.portal.dictdata.model.DictDataModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      DictService
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 14:45
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 14:45
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface DictService :EinyunService{
    //条线等信息
    fun getByTypeKey(typeKey: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>>
}