package com.einyun.app.library.core.net

import com.einyun.app.base.http.HttpService
import com.einyun.app.library.EinyunSDK
import com.einyun.app.library.core.EinyunException
import com.einyun.app.library.uc.user.net.URLs
import java.util.concurrent.locks.ReentrantLock

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.net
 * @ClassName: CommonHttpService
 * @Description: 模块自定义网络
 * @Author: chumingjun
 * @CreateDate: 2019/09/10 17:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/10 17:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
open class EinyunHttpService protected constructor() : HttpService() {
    protected val HTTPS_HEAD_AUTHORIZATION_KEY = "Authorization"
    protected val HTTPS_HEAD_AUTHORIZATION_VALUE_PRE = "Bearer "
    protected val HTTPS_HEAD_TENANT_ID_KEY = "tenant-id"
    protected val HTTPS_HEAD_API_RESPONSE_HANDLE = "api-response-handler"

    init {
        init()
    }

    open fun authorToken(token: String?) {
        addHeader(HTTPS_HEAD_AUTHORIZATION_KEY, HTTPS_HEAD_AUTHORIZATION_VALUE_PRE + token)
    }

    open fun tenantId(tenantId: String?) {
        if (needTenantId) {
            addHeader(HTTPS_HEAD_TENANT_ID_KEY, tenantId)
        }
    }

    open fun init() {
        //添加自定义请求头
        addHeader(HTTPS_HEAD_API_RESPONSE_HANDLE, "true")
        BASE_URL = EinyunSDK.server
    }

    companion object {
        private var instance: EinyunHttpService? = null
        private val mLock = ReentrantLock()

        var needTenantId = false

        fun getInstance(): EinyunHttpService {
            if(instance==null){
                mLock.lock()
                if (instance == null) {
                    instance = EinyunHttpService()
                }
                mLock.unlock()
            }
            return instance!!
        }
    }

    override fun filter(url:String) {
        super.filter(url)
        //except
        if(url.contains(URLs.URL_USER_GET_TENTANTID)||url.contains(URLs.URL_APP_UPDATE)){
            return
        }
        if(needTenantId){
            if (!headMap.containsKey(HTTPS_HEAD_TENANT_ID_KEY)){
                throw EinyunException("tenant-id not found in heads")
            }
        }
    }
}
