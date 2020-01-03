package com.einyun.app.library.resource.workorder.net.request

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class IsClosedRequest(
    @NonNull
    var id: String? = null,
    @NonNull
    var type: String? = null
)

