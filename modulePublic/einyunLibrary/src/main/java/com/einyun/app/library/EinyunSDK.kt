package com.einyun.app.library

import android.content.Context
import android.text.TextUtils
import com.einyun.app.library.core.EinyunException

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library
 * @ClassName:      EinyunSDK
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 10:00
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class EinyunSDK {
    companion object{
        internal var context:Context?=null
        internal var server:String?=null
        fun init(applicationContext: Context,server:String){
            context=applicationContext
            this.server=server
            if(applicationContext==null){
                throw EinyunException("context is empty")
            }
            if(TextUtils.isEmpty(server)){
                throw EinyunException("server url is empty")
            }
        }
    }

}