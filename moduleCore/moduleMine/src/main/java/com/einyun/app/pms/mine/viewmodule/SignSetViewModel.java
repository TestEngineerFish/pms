package com.einyun.app.pms.mine.viewmodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.pms.mine.model.MsgModel;
import com.einyun.app.pms.mine.model.RequestPageBean;
import com.einyun.app.pms.mine.model.SignSetModule;
import com.einyun.app.pms.mine.repository.DataSourceFactory;
import com.einyun.app.pms.mine.repository.FeedBackRepository;

public class SignSetViewModel extends BasePageListViewModel<MsgModel> {
    FeedBackRepository repository=new FeedBackRepository();
    private MutableLiveData<Boolean> approval=new MutableLiveData<>();
    public LiveData<Boolean> sumitSignText(SignSetModule Bean){
        showLoading();
        repository.SignTextSumit(Bean, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                approval.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return approval;
    }
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
    /**
     * 全部已读
     */
    private MutableLiveData<Boolean> allReadModel=new MutableLiveData<>();
    public LiveData<Boolean> allRead(String startTime,String endTime){
        repository.allRead(startTime,endTime, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                allReadModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return allReadModel;
    }
    /**
     * 获取Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<MsgModel>> loadPadingData(RequestPageBean requestBean, String tag){

        pageList = new LivePagedListBuilder(new DataSourceFactory(requestBean,tag), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();

        return pageList;
    }
}
