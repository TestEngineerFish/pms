package com.einyun.app.common.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.model.IsClosedState;
import com.einyun.app.common.model.UrlxcgdGetInstBOModule;
import com.einyun.app.common.repository.MsgRepository;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.resource.workorder.net.request.ExtenDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;

public class BaseWorkOrderHandelViewModel extends BaseUploadViewModel{
     protected MutableLiveData<Boolean> forceCloseLiveData=new MutableLiveData<>();
     protected MutableLiveData<Boolean> postponeLiveData=new MutableLiveData<>();
     public MutableLiveData<IsClosedState> isClosedLiveData=new MutableLiveData<>();

     public ResourceWorkOrderService workOrderService= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);

     protected String workOrderType;

     /**
      * 申请强制闭单
      * @param request
      * @return
      */
     public LiveData<Boolean> forceClose(ApplyCloseRequest request){
          workOrderService.forceClose(workOrderType, request, new CallBack<Boolean>() {
               @Override
               public void call(Boolean data) {
                    forceCloseLiveData.postValue(data);
               }

               @Override
               public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
               }
          });
          return forceCloseLiveData;
     }

     /**
      * 申请延期
      * @param request
      * @return
      */
     public LiveData<Boolean> postpone(ExtenDetialRequest request){
          workOrderService.postpone(workOrderType, request, new CallBack<Boolean>() {
               @Override
               public void call(Boolean data) {
                    postponeLiveData.postValue(data);
               }

               @Override
               public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
               }
          });
          return postponeLiveData;
     }

     /**
      * 判断是否强制关闭，申请延期
      * @param request
      * @return
      */
     public LiveData<IsClosedState> isClosed(IsClosedRequest request){

          workOrderService.isClosed(request, new CallBack<Boolean>() {
               @Override
               public void call(Boolean data) {
                    isClosedLiveData.postValue(new IsClosedState(data,request.getType()));
               }

               @Override
               public void onFaild(Throwable throwable) {
                    isClosedLiveData.postValue(null);
//                    ThrowableParser.onFailed(throwable);
               }
          });

          return isClosedLiveData;
     }

     /**
      * 判断是否强制关闭，申请延期
      * @param request
      * @return
      */
     public LiveData<IsClosedState> isClosed(IsClosedRequest request,boolean showLoading){
          if (showLoading){
               showLoading();
          }
          workOrderService.isClosed(request, new CallBack<Boolean>() {
               @Override
               public void call(Boolean data) {
                    isClosedLiveData.postValue(new IsClosedState(data,request.getType()));
                    if (showLoading){
                         hideLoading();
                    }
               }

               @Override
               public void onFaild(Throwable throwable) {
                    if (showLoading){
                         hideLoading();
                    }
               }
          });

          return isClosedLiveData;
     }

    public MsgRepository repository=new MsgRepository();
     /**
      * 单个消息从未读到已读
      */
     private MutableLiveData<Boolean> singleReadModel=new MutableLiveData<>();
     public LiveData<Boolean> singleRead(String id){
          repository.singleRead(id, new CallBack<Boolean>() {
               @Override
               public void call(Boolean data) {
                    hideLoading();
                    singleReadModel.postValue(data);
               }

               @Override
               public void onFaild(Throwable throwable) {
                    hideLoading();
               }
          });
          return singleReadModel;
     }

     /*
      * 获取审批详情页 基本信息数据
      * */
     private MutableLiveData<UrlxcgdGetInstBOModule> approvalBasicInfo=new MutableLiveData<>();
     public LiveData<UrlxcgdGetInstBOModule> queryApprovalBasicInfo(String id){
          showLoading();
          repository.getApprovalBasicInfo(id, new CallBack<UrlxcgdGetInstBOModule>() {
               @Override
               public void call(UrlxcgdGetInstBOModule data) {
                    hideLoading();
                    approvalBasicInfo.postValue(data);
               }

               @Override
               public void onFaild(Throwable throwable) {
                    hideLoading();
               }
          });
          return approvalBasicInfo;
     }
}
