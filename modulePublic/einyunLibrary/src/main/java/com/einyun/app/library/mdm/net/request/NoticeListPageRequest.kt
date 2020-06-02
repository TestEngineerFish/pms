package com.einyun.app.library.mdm.net.request

import com.einyun.app.library.resource.workorder.net.request.PageRquest

class NoticeListPageRequest : PageRquest() {
     var org_id: String? = null
     var is_important:String?=null
     var end_time:String?=null
     var release_time:String?=null
}
