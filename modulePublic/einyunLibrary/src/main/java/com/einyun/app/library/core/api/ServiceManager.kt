package com.einyun.app.library.core.api

import com.einyun.app.library.core.api.impl.*

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core.api
 * @ClassName:      ServiceManager
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/26 14:56
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/26 14:56
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ServiceManager {

    /**
     * 添加对外发布服务
     */
    init {
        addService(SERVICE_DICT, DictServiceImpl())//添加数据字典服务
        addService(SERVICE_UC, UCServiceImpl())//添加UC服务
        addService(SERVICE_USER_CENTER, UserCenterServiceImpl())
        addService(SERVICE_UPLOAD, UploadServiceImpl())
        addService(SERVICE_DASHBOARD, DashBoardServiceImpl())
        addService(SERVICE_WORK_ORDER, WorkOrderServiceImpl())
        addService(SERVICE_RESOURCE_WORK_ORDER, ResourceWorkOrderServiceImpl())
        addService(SERVICE_RESOURCE,ResourceServiceImpl())
    }

    companion object {
        const val SERVICE_DICT: String = "dict"
        const val SERVICE_UC: String = "uc"
        const val SERVICE_USER_CENTER: String = "user-center"
        const val SERVICE_UPLOAD: String = "upload"
        const val SERVICE_DASHBOARD: String = "dashboard"
        const val SERVICE_WORK_ORDER: String = "work-order"
        const val SERVICE_RESOURCE:String="resource"
        const val SERVICE_RESOURCE_WORK_ORDER: String = "resource-work-order"
        private val services: HashMap<String, EinyunService>? = HashMap()
        private var instance: ServiceManager? = null

        private val LOCK = Any()
        /**
         * 单例，dobule check
         */
        fun obtain(): ServiceManager {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = ServiceManager()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 添加服务
     */
    private fun addService(serviceName: String, service: EinyunService) {
        services?.put(serviceName, service)
    }

    /**
     * 获取服务
     */
    fun <T> getService(serviceName: String): T {
        return services?.get(serviceName) as T
    }

}
