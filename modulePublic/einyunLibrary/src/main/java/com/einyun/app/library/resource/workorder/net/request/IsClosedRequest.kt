package com.einyun.app.library.resource.workorder.net.request

import androidx.annotation.NonNull

data class IsClosedRequest (
    @NonNull
    var id:String?=null,
    @NonNull
    var type:String?=null
)

