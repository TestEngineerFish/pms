package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.resource.workorder.net.request.PageRquest
import com.google.gson.annotations.SerializedName

class RepairsPageRequest : PageRquest() {
    var bx_dk_id: String? = null
    var bx_area_id: String? = null
    var bx_cate_lv1_id: String? = null
    var bx_cate_lv2_id: String? = null
    var state: String? = null
    var node_id_: String? = null
    var owner_id_: String? = null
    var bx_time: String? = null
    var DESC: String? = null
    var tag: String? = null

    var ts_time: String? = null
}