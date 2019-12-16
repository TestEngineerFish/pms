package com.einyun.app.base;

import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.einyun.app.base.event.ErrorMessage;
import com.einyun.app.base.event.SingleLiveEvent;
import com.einyun.app.base.event.Status;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: BaseViewModel
 * @Description: ViewModel基类
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 09:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 09:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseViewModel extends ViewModel implements IActivityLifecycle{

    protected MutableLiveData<Status> singleLiveEvent =new MutableLiveData();

    public LiveData<Status> getLiveEvent() {
        return singleLiveEvent;
    }

    //弱引用持有
    public BaseViewModel(){
        ARouter.getInstance().inject(this);
    }

    /**
     * 刷新页面
     */
    public void refreshUI(){
        Status status=new Status();
        status.setRefresShown(true);
        singleLiveEvent.postValue(status);
    }

    /**
     * 停止刷新页面
     */
    public void stopRefresh(){
        Status status=new Status();
        status.setRefresShown(false);
        singleLiveEvent.postValue(status);
    }


    /**
     * 显示Loading
     */
    public void showLoading(){
        Status status=new Status();
        status.setLoadingShow(true);
        singleLiveEvent.postValue(status);
    }

    /**
     * 关闭Loading
     */
    public void hideLoading(){
        Status status=new Status();
        status.setLoadingShow(false);
        singleLiveEvent.postValue(status);
    }

    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }
}
