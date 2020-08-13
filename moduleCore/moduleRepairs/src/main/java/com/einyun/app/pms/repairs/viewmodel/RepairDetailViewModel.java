package com.einyun.app.pms.repairs.viewmodel;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.GetNodeIdModel;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
import com.einyun.app.library.workorder.model.ArriveCodeModel;
import com.einyun.app.library.workorder.model.Door;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.net.request.RepairSendOrderRequest;
import com.einyun.app.library.workorder.net.request.SaveHandleRequest;
import com.einyun.app.library.workorder.net.response.ArriveCheckResponse;
import com.einyun.app.library.workorder.net.response.ArriveCodeResponse;

import java.util.List;

public class RepairDetailViewModel extends BaseWorkOrderHandelViewModel {
    public ResourceWorkOrderService resourceWorkOrderService;
    public WorkOrderService workOrderService;
    private DictService dictService;
    public RepairDetailViewModel() {
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
    }


    /**
     *报修详情
     * */
    public LiveData<RepairsDetailModel> getRepairDetail(String instId){
        MutableLiveData<RepairsDetailModel> liveData = new MutableLiveData<>();
//        showLoading();
        workOrderService.getRepairDetail(instId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
//                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    /**
     * 展示输入框后几位小数
     * @param editPoint
     * @param num 小数点后位数
     */
    public void setEditPoint(EditText editPoint,int num){
        editPoint.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String lastInputContent = dest.toString();
                if (lastInputContent.contains(".")) {
                    int index = lastInputContent.indexOf(".");
                    if(dend - index >= num + 1){
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    /**
     * 派单
     * */
    public LiveData<BaseResponse<Object>> repairSend(RepairSendOrderRequest request){
        MutableLiveData<BaseResponse<Object>> liveData = new MutableLiveData<BaseResponse<Object>>();
        showLoading();
        workOrderService.repaireSend(request, new CallBack<BaseResponse<Object>>() {
            @Override
            public void call(BaseResponse<Object> data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
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
    public MutableLiveData<GetNodeIdModel> getNodeIdModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArriveCodeResponse> getArriveCodeMultableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArriveCheckResponse> getArriveCheckMultableLiveData = new MutableLiveData<>();

    private String nodeId;
    /**
     * 获取组织架构 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<GetNodeIdModel> getNodeId(GetNodeIdRequest request) {
        showLoading();
        resourceWorkOrderService.getNodeId(request, new CallBack<GetNodeIdModel>() {


            @Override
            public void call(GetNodeIdModel data) {
                hideLoading();
                if (data==null) {
                    nodeId="";
                }else {
                    if (TextUtils.isEmpty(data.getNodeId())){
                        nodeId="";
                    }else {
                        nodeId=data.getNodeId();
                    }
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
//                        .withString(RouteKey.KEY_ORDER_ID, model.getID_())
//                        .withString(RouteKey.KEY_TASK_NODE_ID, nodeId)
//                        .withString(RouteKey.KEY_TASK_ID, "")
//                        .withString(RouteKey.KEY_PRO_INS_ID, model.getInstance_id())
//                        .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
//                        .navigation();
                getNodeIdModelMutableLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return getNodeIdModelMutableLiveData;
    }

    /**
     * 获取上门验证码 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ArriveCodeResponse> getArriveCode(String orderId) {
        showLoading();
        workOrderService.getArriveCode(orderId, new CallBack<ArriveCodeResponse>() {
            @Override
            public void call(ArriveCodeResponse data) {
                hideLoading();
                getArriveCodeMultableLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return getArriveCodeMultableLiveData;
    }

    /**
     * 校验上门验证码 LiveData
     *
     * @return LiveData
     */
    public MutableLiveData<ArriveCheckResponse> checkArriveCode(String orderId,String code) {
        showLoading();
        workOrderService.checkArriveCode(orderId,code, new CallBack<ArriveCheckResponse>() {
            @Override
            public void call(ArriveCheckResponse data) {
                hideLoading();
                getArriveCheckMultableLiveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

        return getArriveCheckMultableLiveData;
    }
}
