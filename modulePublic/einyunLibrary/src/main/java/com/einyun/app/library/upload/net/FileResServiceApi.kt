package com.einyun.app.library.upload.net

import io.reactivex.Flowable
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.http.POST



/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.uc.user.net
 * @ClassName:      UserServiceApi
 * @Description:     文件上传下载API
 * @Author:         chumingjun
 * @CreateDate:     2019/09/16 11:14
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/16 11:14
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface FileResServiceApi {

    @Multipart
    @POST(URLs.URL_UPLOAD)
    fun upload(@Part("upload_file\";file=\"1.jpg") Body: RequestBody): Flowable<UploadResponse>
}