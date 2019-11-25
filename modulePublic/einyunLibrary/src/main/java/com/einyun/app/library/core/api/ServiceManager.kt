package com.einyun.app.library.core.api

import com.einyun.app.library.core.api.impl.DictServiceImpl
import com.einyun.app.library.core.api.impl.UCServiceImpl
import com.einyun.app.library.core.api.impl.UploadServiceImpl
import com.einyun.app.library.core.api.impl.UserCenterServiceImpl

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
        addService(SERVICE_DICT,DictServiceImpl())//添加数据字典服务
        addService(SERVICE_UC,UCServiceImpl())//添加UC服务
        addService(SERVICE_USER_CENTER,UserCenterServiceImpl())
        addService(SERVICE_UPLOAD,UploadServiceImpl())
    }

    companion object{
        const val SERVICE_DICT:String="dict"
        const val SERVICE_UC:String="uc"
        const val SERVICE_USER_CENTER:String="user-center"
        const val SERVICE_UPLOAD:String="upload"

        private val services: HashMap<String,EinyunService>?=HashMap()
        private var instance:ServiceManager?=null

        private val LOCK = Any()
        /**
         * 单例，dobule check
         */
        fun obtain():ServiceManager{
            if(instance==null){
                synchronized(LOCK){
                    if(instance==null){
                        instance= ServiceManager()
                    }
                }
            }
            return instance!!
        }
    }

    /**
     * 添加服务
     */
    private fun addService(serviceName: String,service :EinyunService){
        services?.put(serviceName,service)
    }

    /**
     * 获取服务
     */
    fun <T> getService(serviceName: String):T{
        return services?.get(serviceName) as T
    }

}