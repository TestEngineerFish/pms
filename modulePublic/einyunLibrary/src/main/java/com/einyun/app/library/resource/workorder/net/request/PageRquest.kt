package com.einyun.app.library.resource.workorder.net.request

import com.einyun.app.base.paging.bean.PageBean
import com.google.gson.annotations.SerializedName

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.resource.workorder.net.request
 * @ClassName:      PageRquest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/10/18 10:36
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/10/18 10:36
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class PageRquest {

    companion object{
        const val PERIOD_DAY="day"
        const val PERIOD_WEEK="week"
        const val PERIOD_MONTH="month"
        const val PERIOD_SEASON="season"
        const val DEFAULT_PAGE=1
        const val DEFAULT_PAGE_SIZE=10
    }
    var userId:String?=null
    var page :Int=DEFAULT_PAGE
    var pageSize:Int=DEFAULT_PAGE_SIZE
    var period:String?=null
    var pageBean:PageBean?=null
    var showTotal:Boolean=true
}
