package com.einyun.app.library.upload.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.EinyunException
import com.einyun.app.library.core.api.UploadService
import com.einyun.app.library.core.net.EinyunHttpException
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.upload.model.ResourceModel
import com.einyun.app.library.upload.net.FileResServiceApi
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.upload.repository
 * @ClassName:      FileResRepository
 * @Description:     网络文件存储/下载处理
 * @Author:         chumingjun
 * @CreateDate:     2019/09/23 13:54
 * @UpdateUser:     更新者：
 * @UpdateDate:     2019/09/23 13:54
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
open class FileResRepository :UploadService{
    var serviceApi: FileResServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(FileResServiceApi::class.java)
    }

    /**
     * 上传图片
     */
    override fun uploadImage(filePath: String, callBack: CallBack<ResourceModel>):LiveData<ResourceModel> {
        var liveData=MutableLiveData<ResourceModel>()
        var image = File(filePath)
        var requestFile = RequestBody.create(MediaType.parse("image/jpg"), image);
        serviceApi?.upload(requestFile)?.compose(RxSchedulers.inIo())
                ?.subscribe({ response ->
                    if (response.isState) {
                        callBack.call(response.data)
                        liveData.postValue(response.data)
                    }
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 上传图片集合
     */
    fun uploadImageList(images: List<String>,callBack: CallBack<List<ResourceModel>>):LiveData<List<ResourceModel>>{
        var liveData=MutableLiveData<List<ResourceModel>>()
        //创建及执行
        val fixedThreadPool: ExecutorService = Executors.newFixedThreadPool(5)
        var resources=Vector<ResourceModel>()
        var countDownLatch=CountDownLatch(images.size)
        for (image in images){
            var runnable= object :Runnable {
                override fun run() {
                    var callBack=object :CallBack<ResourceModel>{
                        override fun onFaild(throwable: Throwable?) {
                            callBack.onFaild(throwable)
                            countDownLatch.countDown()
                        }

                        override fun call(data: ResourceModel?) {
                            countDownLatch.countDown()
                            data?.key=image//绑定key
                            resources.add(data)
                        }
                    }
                    uploadImage(image,callBack)
                }
            }
            fixedThreadPool.execute(runnable)
        }
        countDownLatch.await()
        callBack.call(resources)
        liveData.postValue(resources)
        fixedThreadPool.shutdown()
        return liveData
    }
}