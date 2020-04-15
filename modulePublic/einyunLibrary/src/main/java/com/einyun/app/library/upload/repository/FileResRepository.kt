package com.einyun.app.library.upload.repository

import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einyun.app.base.event.CallBack
import com.einyun.app.base.http.RxSchedulers
import com.einyun.app.library.core.api.UploadService
import com.einyun.app.library.core.net.EinyunHttpService
import com.einyun.app.library.upload.model.PicUrl
import com.einyun.app.library.upload.model.ResourceModel
import com.einyun.app.library.upload.net.FileResServiceApi
import okhttp3.MediaType
import okhttp3.MultipartBody
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
//    override fun uploadImage(uri: Uri, callBack: CallBack<ResourceModel>): LiveData<ResourceModel> {
//       return uploadImage(uri.toString(),callBack)
//    }
//
//    override fun uploadUriList(images: List<Uri>, callBack: CallBack<List<ResourceModel>>): LiveData<List<ResourceModel>> {
//        var paths=ArrayList<String>()
//        for (uri in images){
//            paths.add(uri.toString())
//        }
//        return uploadImageList(paths,callBack)
//    }

    var serviceApi: FileResServiceApi? = null

    init {
        serviceApi = EinyunHttpService.getInstance().getServiceApi(FileResServiceApi::class.java)
    }

    /**
     * 上传图片
     */
    override fun uploadImage(filePath: String, callBack: CallBack<PicUrl>):LiveData<PicUrl> {
        var liveData=MutableLiveData<PicUrl>()
        var image = File(filePath)
        var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val body = MultipartBody.Part.createFormData("file", System.currentTimeMillis().toString(), requestFile)
        serviceApi?.upload(body)?.compose(RxSchedulers.inIo())
                ?.subscribe({ response ->
                    if (response.isState) {
                        var picUrl=PicUrl()
                        picUrl.compressed=filePath
                        picUrl.uploaded=response.data
                        callBack.call(picUrl)
                        liveData.postValue(picUrl)
                    }
                }, {
                    callBack.onFaild(it)
                })
        return liveData
    }

    /**
     * 上传图片集合
     */
    override
    fun uploadImageList(images: List<String>,callBack: CallBack<List<PicUrl>>):LiveData<List<PicUrl>>{
        var liveData=MutableLiveData<List<PicUrl>>()
        //创建及执行
        val fixedThreadPool: ExecutorService = Executors.newFixedThreadPool(5)
        var resources=Vector<PicUrl>()
        var countDownLatch=CountDownLatch(images.size)
        for (image in images){
            var runnable= object :Runnable {
                override fun run() {
                    var callBack=object :CallBack<PicUrl>{
                        override fun onFaild(throwable: Throwable?) {
                            callBack.onFaild(throwable)
                            countDownLatch.countDown()
                        }

                        override fun call(data: PicUrl?) {
                            countDownLatch.countDown()
//                            data?.key=image//绑定key
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