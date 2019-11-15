package com.einyun.app.library.portal.dictdata.net

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.portal.dictdata.net
 * @ClassName:      URLS
 * @Description:     字典网络接口URL
 *                   《业主小程序接口文档1.9》
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 14:54
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 14:54
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class URLS {
    companion object{
        //获取字典列表
        const val URL_DATA_DICT_LIST = "/portal/sys/dataDict/v1/listJson"

        //数据字典明细页面
        const val URL_DATA_DICT_GET_DETIAL="/portal/sys/dataDict/v1/dataDictGet"

        //根据分类id获取字典
        const val URL_DATA_DICT_GET_BY_TYPE_ID ="/portal/sys/dataDict/v1/getByTypeId"

        //根据分类id获取字典(ComBo)
        const val URL_DATA_DICT_GET_BY_ID_FOR_COMBO="/portal/sys/dataDict/v1/getByTypeIdForComBo"

        //根据分类id获取字典
        const val URL_DATA_DICT_GET_BY_TYPE_KEY = "/portal/sys/dataDict/v1/getByTypeKey"

        //通过groupKey、typeKey获取数据字典
        const val URL_DATA_DICT_GET_BY_TYPE_KEY_FOR_COMBO = "/portal/sys/dataDict/v1/getByTypeKeyForComBo"

        //根据分类key获取字典
        const val URL_DATA_DICT_GET_BY_TYPE_KEYS = "/portal/sys/dataDict/v1/getByTypeKeys"

        //根据字典key查询字典下级
        const val URL_DATA_DICT_GET_CHILD_BY_KEY = "/portal/sys/dataDict/v1/getChildByKey"

        //根据分类数据字典
        const val URL_DATA_DICT_GET_DATA_DICT_BY_TYPE_ID = "/portal/sys/dataDict/v1/getDataDictByTypeId"

        //通过typeKey获取数据字典
        const val URL_DATA_DICT_GET_MOBILE_COMBO_BY_TYPE_KEY = "/portal/sys/dataDict/v1/getMoibleComBoByTypeKey"

        //批量删除数据字典
        const val URL_DATA_DICT_REMOVE = "/portal/sys/dataDict/v1/remove"

        //保存数据字典信息
        const val URL_DATA_DICT_SAVE = "/portal/sys/dataDict/v1/save"

        //排序
        const val URL_DATA_DICT_SORT = "/portal/sys/dataDict/v1/sort"

        //排序列表页面
        const val URL_DATA_DICT_SORT_LIST= "/sys/dataDict/v1/sortList"
    }
}