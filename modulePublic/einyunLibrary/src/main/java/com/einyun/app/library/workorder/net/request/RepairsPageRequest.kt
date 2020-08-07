package com.einyun.app.library.workorder.net.request

import com.einyun.app.base.paging.bean.Query
import com.einyun.app.library.resource.workorder.net.request.PageRquest
import com.google.gson.annotations.SerializedName

class RepairsPageRequest : PageRquest() {
    var bx_dk_id: String? = ""
    var searchValue: String? = ""
    var bx_area_id: String? = ""
    var bx_cate_lv1_id: String? = ""
    var bx_cate_lv2_id: String? = ""
    var state: String? = ""
    var node_id_: String? = ""
    var owner_id_: String? = ""
    var bx_time: String? = ""
    var DESC: String? = Query.SORT_DESC
    var tag: String? = ""

    var ts_time: String? = null
}