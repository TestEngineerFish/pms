package com.einyun.app.library.uc.user.model

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.user.core.model
 * @ClassName: UserInfoModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 11:07
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 11:07
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class UserModel {
    var token: String? = null
    var username: String? = null
    var account: String? = null
    var userId: String? = null
    var password: String? = null

    constructor(token: String, userId: String, account: String, username: String) {
        this.token = token
        this.userId = userId
        this.username = username
        this.account = account
    }

    constructor(token: String, userId: String, account: String, username: String, password: String) {
        this.token = token
        this.userId = userId
        this.username = username
        this.account = account
        this.password = password
    }
}
