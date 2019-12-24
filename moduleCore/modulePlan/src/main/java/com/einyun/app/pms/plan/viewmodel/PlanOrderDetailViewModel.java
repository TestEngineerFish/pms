package com.einyun.app.pms.plan.viewmodel;

import android.os.Handler;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.viewmodel.BaseUploadViewModel;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.resource.workorder.model.Sub_jhgdgzjdb;
import com.einyun.app.library.resource.workorder.net.request.DoneDetialRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

public class PlanOrderDetailViewModel extends BaseUploadViewModel {
    MutableLiveData<PlanInfo> liveData = new MutableLiveData<>();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    ResourceWorkOrderService service;

    public PlanOrderDetailViewModel(){
        service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);

    }
    /**
     * 提交
     * @return
     */
    public LiveData<Boolean> submit(PatrolSubmitRequest request){
        MutableLiveData<Boolean> liveData=new MutableLiveData();
        showLoading();
        service.planSubmit(request, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                ThrowableParser.onFailed(throwable);
            }
        });
        return liveData;
    }

    /**
     * 工作节点
     *
     * @param patrolInfo
     * @return
     */
    public List<WorkNode> loadNodes(PlanInfo patrolInfo) {
        List<WorkNode> nodes;
        nodes = Observable
                .fromIterable(patrolInfo.getData().getZyjhgd().getSub_jhgdgzjdb())
                .map(index -> new WorkNode(
                        index.getF_WK_ID(),
                        index.getF_WK_CONTENT(),
                        index.getF_WK_NODE(),
                        index.getF_WK_RESULT()))
                .toList()
                .blockingGet();
        Collections.sort(nodes, (o1, o2) -> {
            if (TextUtils.isEmpty(o1.number) || TextUtils.isEmpty(o2.number)) {
                return 0;
            }
            int num1 = Integer.parseInt(o1.number);
            int num2 = Integer.parseInt(o2.number);
            return num1 - num2;
        });
        return nodes;
    }

    /**
     * 获取巡查详情
     *
     * @return
     */
    public LiveData<PlanInfo> loadDetail(String proInsId, String taskId, String taskNodeId, String fragmentTag) {
//        showLoading();
        if (fragmentTag.equals(FRAGMENT_PLAN_OWRKORDER_DONE)) {
            DoneDetialRequest request = new DoneDetialRequest();
            request.setProInsId(proInsId);
            request.setTaskNodeId(taskNodeId);
            service.planDoneDetial(request, new CallBack<PlanInfo>() {
                @Override
                public void call(PlanInfo data) {
                    liveData.postValue(data);
//                    hideLoading();
                }

                @Override
                public void onFaild(Throwable throwable) {
//                    hideLoading();
                    ThrowableParser.onFailed(throwable);
                }
            });
        } else {
            service.planOrderDetail(taskId, new CallBack<PlanInfo>() {
                @Override
                public void call(PlanInfo data) {
                    liveData.postValue(data);
//                    hideLoading();
                }

                @Override
                public void onFaild(Throwable throwable) {
//                    hideLoading();
                    ThrowableParser.onFailed(throwable);
                }
            });
        }

        return liveData;
    }

}
