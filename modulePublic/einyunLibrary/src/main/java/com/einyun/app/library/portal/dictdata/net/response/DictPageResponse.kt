package com.einyun.app.library.portal.dictdata.net.response

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.portal.dictdata.model.DictDataModel

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.portal.dictdata.net.response
 * @ClassName:      DictPageResponse
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 15:16
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 15:16
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class DictPageResponse: BaseResponse<PageResult<DictDataModel>>()