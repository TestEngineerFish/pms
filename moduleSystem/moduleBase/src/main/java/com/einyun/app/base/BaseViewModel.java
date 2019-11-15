package com.einyun.app.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.einyun.app.base.event.ErrorMessage;
import com.einyun.app.base.event.SingleLiveEvent;
import com.einyun.app.base.event.Status;

import org.jetbrains.annotations.NotNull;

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
    protected SingleLiveEvent<Status> singleLiveEvent =new SingleLiveEvent();

    public SingleLiveEvent<Status> getSingleLiveEvent() {
        return singleLiveEvent;
    }

    //弱引用持有
    public BaseViewModel(){
        ARouter.getInstance().inject(this);
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
