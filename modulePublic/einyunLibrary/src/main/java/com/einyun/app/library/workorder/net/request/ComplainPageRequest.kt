package com.einyun.app.library.workorder.net.request

import com.einyun.app.library.resource.workorder.net.request.PageRquest
import com.google.gson.annotations.SerializedName

class ComplainPageRequest : PageRquest() {
    var ts_dk_id: String? = null
    var ts_area_id: String? = null
    var ts_cate_lv1_id: String? = null
    var ts_cate_lv2_id: String? = null
    var state: String? = null
    var node_id_: String? = null
    var owner_id_: String? = null
    var ts_time: String? = null
    var DESC: String? = null
    var tag: String? = null
}