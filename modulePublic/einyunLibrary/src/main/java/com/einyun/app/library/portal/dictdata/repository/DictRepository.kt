package com.einyun.app.library.portal.dictdata.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.BaseResponse
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.base.paging.bean.PageBean
import com.einyun.app.base.paging.bean.PageResult
import com.einyun.app.library.EinyunSDK
import com.einyun.app.library.portal.dictdata.db.DictDataDao
import com.einyun.app.library.portal.dictdata.db.DictDatabase
import com.einyun.app.library.portal.dictdata.model.DictDataModel
import com.einyun.app.library.portal.dictdata.net.DictDataServiceApi
import com.einyun.app.library.portal.dictdata.net.request.*
import com.einyun.app.library.portal.dictdata.net.response.DictPageResponse
import com.einyun.app.library.core.net.EinyunHttpService

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.portal.dictdata.repository
 * @ClassName:      DictRepository
 * @Description:     数据字典数据处理
 * @Author:         chumingjun
 * @CreateDate:     2019/09/12 09:49
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/12 09:49
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class DictRepository {

    var dictDao: DictDataDao? = null
    var serviceApi: DictDataServiceApi? = null

    init {
        dictDao = DictDatabase.instance(EinyunSDK.context!!).dictDao()
        serviceApi = EinyunHttpService.getInstance().getServiceApi(DictDataServiceApi::class.java)
    }

    /**
     * 分页查询
     */
    fun dictDataList(pageBean: PageBean, callBack: CallBack<PageResult<DictDataModel>>?): LiveData<PageResult<DictDataModel>>? {
        var liveData = MutableLiveData<PageResult<DictDataModel>>()
        var request = DictPageRequest()
        request.pageBean = pageBean
        serviceApi?.dictDataList(request)?.compose(RxSchedulers.inIo())
                ?.subscribe({ response: DictPageResponse? ->
                    if (response?.isState!!) {
                        callBack?.call(response?.data)
//                        insertAllToDb(response.data?.rows!!)
                        liveData.postValue(response?.data)
                    }
                }, { error ->
                    error.printStackTrace()
                    callBack?.onFaild(error)
                })
        return liveData
    }

    /**
     * 通过Typekey 获取字典
     */
    fun getByTypeKey(typeKey: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        serviceApi?.getByTypeKey(typeKey)
                ?.compose(RxSchedulers.inIo())
                ?.subscribe({ response ->
                    if (response.isState) {
                        callBack?.call(response.data)
                        liveData.postValue(response.data)
                    }
                }, { error ->
                    error.printStackTrace()
                })
        return liveData
    }

    /**
     * 根据分类id获取字典
     */
    fun getByTypeId(typeId: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        var request = DictTypeIdRequest()
        request.typeId = typeId
        serviceApi?.getByTypeId(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }


    /**
     * 根据分类id获取字典(ComBo)
     */
    fun getByTypeIdForComBo(typeId: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        var request = DictTypeIdRequest()
        request.typeId = typeId
        serviceApi?.getByTypeIdForComBo(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 通过groupKey、typeKey获取数据字典
     */
    fun getByTypeKeyForComBo(typeKey: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
//        var request = DictTypeKeyRequest()
//        request.typeKey = typeKey
        serviceApi?.getByTypeKeyForComBo(typeKey)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 根据分类key获取字典
     * 个多逗号分隔
     */
    fun getByTypeKeys(typeKey: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        serviceApi?.getByTypeKeys(typeKey)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     *
     *根据字典key查询字典下级
     */
    fun getChildByKey(queryFilter: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        serviceApi?.getChildByKey(queryFilter)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     *
     *根据字典key查询字典下级
     */
    fun getDataDictByTypeId(typeId: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        var request=DictTypeIdRequest()
        request.typeId=typeId
        serviceApi?.getDataDictByTypeId(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     *
     * 通过typeKey获取数据字典
     */
    fun getMoibleComBoByTypeKey(typeKey: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        serviceApi?.getMoibleComBoByTypeKey(typeKey)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 批量删除数据字典
     */
    fun remove(id: String, callBack: CallBack<BaseResponse<Object>>?): LiveData<BaseResponse<Object>> {
        var liveData = MutableLiveData<BaseResponse<Object>>()
        var request = DictIdRequest()
        request.id = id
        serviceApi?.remove(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response)
                        callBack?.call(response)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 保存数据字典信息
     */
    fun save(dict: DictDataModel, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        serviceApi?.save(dict as DictRequest)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 排序
     */
    fun sort(dicIds: List<String>?, callBack: CallBack<BaseResponse<Object>>?): LiveData<BaseResponse<Object>> {
        var liveData = MutableLiveData<BaseResponse<Object>>()
        var request = DictSortRequest()
        request.dicIds = dicIds
        serviceApi?.sort(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response)
                        callBack?.call(response)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }


    /**
     * 排序列表页面
     */
    fun sortList(id: String, callBack: CallBack<List<DictDataModel>>?): LiveData<List<DictDataModel>> {
        var liveData = MutableLiveData<List<DictDataModel>>()
        var request = DictIdRequest()
        request.id = id
        serviceApi?.sortList(request)
                ?.compose(RxSchedulers.inIoMain())
                ?.subscribe({ response ->
                    if (response.isState) {
                        liveData.postValue(response.data)
                        callBack?.call(response.data)
                    }
                }, {
                    callBack?.onFaild(it)
                })
        return liveData
    }

    /**
     * 从数据库获取所有字典列表
     */
    fun listAllFromDb(): LiveData<List<DictDataModel>>? {
        return dictDao?.listAll()
    }

    /**
     * 插入数据
     */
    fun insertAllToDb(list: List<DictDataModel>) {
        dictDao?.insert(list)
    }
}