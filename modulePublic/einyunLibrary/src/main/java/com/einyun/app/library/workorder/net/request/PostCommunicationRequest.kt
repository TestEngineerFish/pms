package com.einyun.app.library.workorder.net.request

public class PostCommunicationRequest {
    var taskId: String? = null
    var messageType: String? = "inner,app_push"
    var opinion: String? = null
    var userId: String? = null
    var expectTime: String? = null
}