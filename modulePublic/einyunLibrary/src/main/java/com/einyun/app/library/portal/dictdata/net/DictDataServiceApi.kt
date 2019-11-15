package com.einyun.app.library.portal.dictdata.net

import com.einyun.app.base.http.BaseResponse
import com.einyun.app.library.portal.dictdata.net.request.*
import com.einyun.app.library.portal.dictdata.net.response.DictPageResponse
import com.einyun.app.library.portal.dictdata.net.response.DictListResponse
import io.reactivex.Flowable
import retrofit2.http.*

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.portal.dictdata.net
 * @ClassName:      DictDataServiceApi
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 15:19
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 15:19
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface DictDataServiceApi {
    /**
     * 字典列表
     * @param request
     * @return
     */
    @POST(URLS.URL_DATA_DICT_LIST)
    fun dictDataList(@Body request: DictPageRequest): Flowable<DictPageResponse>

    /**
     * 根据分类key获取字典
     * @param typeKey
     * @return
     */
    @GET(URLS.URL_DATA_DICT_GET_BY_TYPE_KEY)
    fun getByTypeKey(@Query("typeKey") typeKey: String): Flowable<DictListResponse>

    /**
     *
     *数据字典明细页面
     */
    @POST(URLS.URL_DATA_DICT_GET_DETIAL)
    fun getDictDataDetial(@Body request: DictIdRequest):Flowable<DictListResponse>

    /**
     * 根据分类id获取字典
     */
    @POST(URLS.URL_DATA_DICT_GET_BY_TYPE_ID)
    fun getByTypeId(@Body request: DictTypeIdRequest):Flowable<DictListResponse>


    /**
     * 根据分类id获取字典(ComBo)
     */
    @POST(URLS.URL_DATA_DICT_GET_BY_ID_FOR_COMBO)
    fun getByTypeIdForComBo(@Body request: DictTypeIdRequest):Flowable<DictListResponse>

    /**
     * 通过groupKey、typeKey获取数据字典
     */
    @POST(URLS.URL_DATA_DICT_GET_BY_TYPE_KEY_FOR_COMBO)
    fun getByTypeKeyForComBo(@Part("typeKey") typeKey: String):Flowable<DictListResponse>

    /**
     * 根据分类key获取字典
     * 个多逗号分隔
     */
    @GET(URLS.URL_DATA_DICT_GET_BY_TYPE_KEYS)
    fun getByTypeKeys(@Query("typeKeys") typeKeys: String): Flowable<DictListResponse>

    /**
     *
     *根据字典key查询字典下级
     */
    @GET(URLS.URL_DATA_DICT_GET_CHILD_BY_KEY)
    fun getChildByKey(@Query("key") key: String): Flowable<DictListResponse>

    /**
     *
     *根据字典key查询字典下级
     */
    @GET(URLS.URL_DATA_DICT_GET_DATA_DICT_BY_TYPE_ID)
    fun getDataDictByTypeId(@Body request: DictTypeIdRequest): Flowable<DictListResponse>

    /**
     *
     * 通过typeKey获取数据字典
     */
    @GET(URLS.URL_DATA_DICT_GET_MOBILE_COMBO_BY_TYPE_KEY)
    fun getMoibleComBoByTypeKey(@Query("typeKey") typeKey : String): Flowable<DictListResponse>

    /**
     * 批量删除数据字典
     */
    @HTTP(method = "DELETE", path = URLS.URL_DATA_DICT_REMOVE, hasBody = true)
    fun remove(@Body request: DictIdRequest): Flowable<BaseResponse<Object>>

    /**
     * 保存数据字典信息
     */
    @POST(URLS.URL_DATA_DICT_SAVE)
    fun save(@Body request: DictRequest):Flowable<DictListResponse>


    /**
     * 排序
     */
    @POST(URLS.URL_DATA_DICT_SORT)
    fun sort(@Body request: DictSortRequest):Flowable<BaseResponse<Object>>


    /**
     * 排序列表页面
     */
    @POST(URLS.URL_DATA_DICT_SORT_LIST)
    fun sortList(@Body request: DictIdRequest):Flowable<DictListResponse>
}