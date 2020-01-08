package com.einyun.app.pms.disqualified.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedItemModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
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
    private MutableLiveData<List<DisqualifiedTypesBean>> detialType=new MutableLiveData<>();
    public LiveData<List<DisqualifiedTypesBean>> queryAduitType(){
        showLoading();
        repository.queryType( new CallBack<List<DisqualifiedTypesBean>>() {
            @Override
            public void call(List<DisqualifiedTypesBean> data) {
                hideLoading();
                detialType.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialType;
    }
}
