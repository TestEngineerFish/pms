package com.einyun.app.pms.disqualified.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.disqualified.constants.DisqualifiedDataKey;
import com.einyun.app.pms.disqualified.model.DisqualifiedItemModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
import com.einyun.app.pms.disqualified.model.OrderCodeBean;
import com.einyun.app.pms.disqualified.net.request.DisqualifiedListRequest;
import com.einyun.app.pms.disqualified.repository.DataSourceFactory;
import com.einyun.app.pms.disqualified.repository.DisqualifiedRepository;

import java.util.List;

public class DisqualifiedFragmentViewModel extends BasePageListViewModel<DisqualifiedItemModel> {
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<DisqualifiedItemModel>> loadPadingData(DisqualifiedListRequest requestBean, String tag){

        pageList = new LivePagedListBuilder(new DataSourceFactory(requestBean,tag), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();

        return pageList;
    }
    DisqualifiedRepository repository=new DisqualifiedRepository();
    //筛选数据类型
    private MutableLiveData<List<DisqualifiedTypesBean>> detialLineType=new MutableLiveData<>();
    private MutableLiveData<List<DisqualifiedTypesBean>> detialStateType=new MutableLiveData<>();
    private MutableLiveData<List<DisqualifiedTypesBean>> detialSeverityType=new MutableLiveData<>();
    public LiveData<List<DisqualifiedTypesBean>> queryAduitType(String type){
        showLoading();
        repository.queryType( type,new CallBack<List<DisqualifiedTypesBean>>() {
            @Override
            public void call(List<DisqualifiedTypesBean> data) {
                hideLoading();
                switch (type) {
                    case DisqualifiedDataKey.LINE_TYPE_LIST:
                        detialLineType.postValue(data);
                        break;
                    case DisqualifiedDataKey.ORDER_STATE_TYPE_LIST:
                        detialStateType.postValue(data);
                        break;
                    case DisqualifiedDataKey.SEVERITY_TYPE_LIST:
                        detialSeverityType.postValue(data);
                        break;
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        switch (type) {
            case DisqualifiedDataKey.LINE_TYPE_LIST:
                return detialLineType;
            case DisqualifiedDataKey.ORDER_STATE_TYPE_LIST:
                return detialStateType;
            case DisqualifiedDataKey.SEVERITY_TYPE_LIST:
                return detialSeverityType;
            default:
                return detialLineType;

        }
    }
    private MutableLiveData<String> detailOrderCode=new MutableLiveData<>();
    public LiveData<String> queryOrderCode(){
        showLoading();
        repository.queryOrderCode(new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                detailOrderCode.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
                return detailOrderCode;
    }
    /*
     * 处理接口
     * */
    private MutableLiveData<Boolean> deal=new MutableLiveData<>();
    public LiveData<Boolean> deal(CreateUnQualityRequest dealRequest){
        showLoading();
        repository.dealSubmit(dealRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                deal.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return deal;
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    public String getUserId() {
        return userModuleService.getUserId();
    }
    public String getUserName() {
        return userModuleService.getUserName();
    }
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

}
