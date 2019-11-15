package com.einyun.app.library.core.net

import com.einyun.app.base.http.BaseResponse
import java.lang.Exception

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.core
 * @ClassName:      EinyunHttpException
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/16 17:40
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/16 17:40
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class EinyunHttpException(var response:BaseResponse<*>) :Exception(){
//    fun getResponse():BaseResponse<Any>{
//        return response
//    }
}