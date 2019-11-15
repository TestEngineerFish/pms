package com.einyun.app.library.uc.user.net.request

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.user.net.request
 * @ClassName:      ChangePwdRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/16 13:29
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/16 13:29
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class ChangePwdRequest(
    val account: String,
    val newPwd: String,
    val oldPwd: String,
    val userNumber: String


)