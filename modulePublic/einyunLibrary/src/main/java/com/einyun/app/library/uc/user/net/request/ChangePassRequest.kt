package com.einyun.app.library.uc.user.net.request

public class ChangePassRequest {
    /**
     * account : account
     * newPwd : newPwd
     * phone : phone
     * code : code
     * checkCode : clientAppv1
     */
    var account: String? = null
    var newPwd: String? = null
    var phone: String? = null
    var code: String? = null
    var checkCode: String = "clientAppv1"

}