package com.einyun.app.pms.repairs.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.workorder.model.Door;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.net.request.RepairSendOrderRequest;
import com.einyun.app.library.workorder.net.request.SaveHandleRequest;

import java.util.List;

public class RepairDetailViewModel extends BaseWorkOrderHandelViewModel {

    private WorkOrderService workOrderService;
    private DictService dictService;
    public RepairDetailViewModel() {
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
    }


    /**
     *报修详情
     * */
    public LiveData<RepairsDetailModel> getRepairDetail(String instId){
        MutableLiveData<RepairsDetailModel> liveData = new MutableLiveData<>();
        showLoading();
        workOrderService.getRepairDetail(instId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }

    /**
     * 派单
     * */
    public LiveData<Boolean> repairSend(RepairSendOrderRequest request){
        MutableLiveData<Boolean> liveData = new MutableLiveData<Boolean>();
        showLoading();
        workOrderService.repaireSend(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }

    /**
     * 处理保存
     * */
    public LiveData<Boolean> saveHandler(SaveHandleRequest request){
        MutableLiveData<Boolean> liveData = new MutableLiveData<Boolean>();
        showLoading();
        workOrderService.saveHandler(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }
   /**
    * 获取预约上门时间
    * */
    public LiveData<List<DictDataModel>> getByTypeKey(String typeKey) {
        return dictService.getByTypeKey(typeKey, new CallBack<List<DictDataModel>>() {
            @Override
            public void call(List<DictDataModel> data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    /**
     * 获取报修类别与条线
     * @return
     */
    public LiveData<Door> repairTypeList() {
        return workOrderService.repairTypeList(new CallBack<Door>() {
            @Override
            public void call(Door data) {
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }
}
