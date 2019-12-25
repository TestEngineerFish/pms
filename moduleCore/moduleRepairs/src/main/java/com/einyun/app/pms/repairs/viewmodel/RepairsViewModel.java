package com.einyun.app.pms.repairs.viewmodel;

import android.hardware.Camera;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.net.request.WorkOrderHanlerRequest;
import com.einyun.app.library.workorder.model.AreaModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.repairs.repository.DataSourceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE2;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE3;

/**
 *RepairsViewModel
 */
public class RepairsViewModel extends BasePageListViewModel<RepairsModel> {
    // TODO: Implement the ViewModel
    private WorkOrderService workOrderService;
    //public List<SelectModel> selectModels=new ArrayList<>();
    public SelectModel areaModel;
    LiveData<PagedList<RepairsModel>> liveData;
    RepairsPageRequest request;
    public void refresh(){
        if(liveData!=null){
        }
    }

    public RepairsViewModel() {
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);

    }

    public RepairsPageRequest getRequest() {
        return request;
    }

    public void setRequest(RepairsPageRequest request) {
        this.request = request;
    }

    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<RepairsModel>> loadPagingData(RepairsPageRequest repairsPageRequest,String tag){
            liveData= new LivePagedListBuilder(new DataSourceFactory(repairsPageRequest,tag), config)
                    .build();
        return liveData;
    }

    /**
     * 抢单
     * */
    public LiveData<Boolean> grabRepair(String taskId){
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        showLoading();
        workOrderService.grabRepair(taskId, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                liveData.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    /**
     * 获取筛选数据
     * */
    public LiveData<AreaModel> getAreaType(){
        MutableLiveData<AreaModel> liveData = new MutableLiveData<>();
        showLoading();
        workOrderService.getAreaType( new CallBack<AreaModel>() {
            @Override
            public void call(AreaModel data) {
                hideLoading();
                liveData.postValue(data);
                areaModel=turnToSelectModel(data);
                //selectModels.add(turnToSelectModel(data));

            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return liveData;
    }

    /**
     * 递归转成SelectModel
     * */
    public SelectModel turnToSelectModel(AreaModel model){
        SelectModel selectModel=new SelectModel();
        selectModel.setId(model.getId());
        selectModel.setName(model.getDataName());
        if(model.getParentId().equals("-")){
            selectModel.setGrade(0);
            model.setGrade(0);
            selectModel.setType("报修区域");
        }

        selectModel.setContent(model.getDataName());
        selectModel.setConditionType(model.getDataName());

        if (model.getChildren()!=null){
            List<SelectModel> selectModelList=new ArrayList<>();
            for (AreaModel model1:model.getChildren()){
                model1.setGrade(model.getGrade()+1);
                SelectModel child=turnToSelectModel(model1);
                if(model1.getGrade()==1){
                    child.setType("报修大类");
                    selectModel.setConditionType(SelectPopUpView.SELECT_AREA);
                }else if(model1.getGrade()==2){
                    child.setType("报修小类");
                    selectModel.setConditionType(SelectPopUpView.SELECT_AREA_FIR);
                }else if(model1.getGrade()==3){
                    child.setType("");
                    selectModel.setConditionType(SelectPopUpView.SELECT_AREA_SEC);
                }else if (model1.getGrade()==4){
                    child.setType("");
                    selectModel.setConditionType(SelectPopUpView.SELECT_AREA_THIR);
                }
                selectModelList.add(child);
            }
            selectModel.setSelectModelList(selectModelList);
        }

        return selectModel;

    }
}
