package com.einyun.app.library.mdm.model

class NoticeModel {
    var id: String? = null
    var releaseTime: String? = null
    var title: String? = ""
    var type: String? = null
    var thumbsUpNumber: Int? = 0
    var thumbsDownNumber: Int? = 0
    var content: String? = ""

    constructor(title: String?) {
        this.title = title
    }

    constructor()
}
