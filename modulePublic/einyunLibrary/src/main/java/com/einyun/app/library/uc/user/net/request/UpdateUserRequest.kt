package com.einyun.app.library.uc.user.net.request

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.user.net.request
 * @ClassName:      UpdateUserRequest
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/16 16:14
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/16 16:14
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
data class UpdateUserRequest(
    val account: String,
    val address: String,
    val birthday: String,
    val education: String,
    val email: String,
    val entryDate: String,
    val from: String,
    val fullname: String,
    val id: String,
    val idCard: String,
    val isDelete: String,
    val leaveDate: String,
    val mobile: String,
    val params: Object,
    val password: String,
    val phone: String,
    val photo: String,
    val sex: String,
    val status: Int,
    val updateTime: String,
    val userNumber: String,
    val version: Int
)

