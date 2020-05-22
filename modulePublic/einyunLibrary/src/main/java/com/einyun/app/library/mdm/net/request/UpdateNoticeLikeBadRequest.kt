package com.einyun.app.library.mdm.net.request

class UpdateNoticeLikeBadRequest {
    var communityAnnouncementId: String? = null
    var memberId: String? = null
    //1-好评 2-差评
    var thumbsUpDown: String? = null
    var activityId: String? = null
}
