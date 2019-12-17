package com.einyun.app.library.core.api

import com.einyun.app.base.event.CallBack
import com.einyun.app.library.resource.model.LineType

interface ResourceService :EinyunService{
    /**
     * 获取条线分类
     */
    fun getLineType(callBack: CallBack<List<LineType>>)
}
