package com.einyun.app.common.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.model.IsClosedState;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.net.request.ApplyCloseRequest;
import com.einyun.app.library.resource.workorder.net.request.ExtenDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;

public class BaseWorkOrderHandelViewModel extends BaseUploadViewModel{
     protected MutableLiveData<Boolean> forceCloseLiveData=new MutableLiveData<>();
     protected MutableLiveData<Boolean> postponeLiveData=new MutableLiveData<>();
     public MutableLiveData<IsClosedState> isClosedLiveData=new MutableLiveData<>();

     protected ResourceWorkOrderService workOrderService= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);

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
}
