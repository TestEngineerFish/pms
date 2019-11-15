package com.einyun.app.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: IActivityLifecycle
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 14:08
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 14:08
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public interface IActivityLifecycle extends LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onLifecycleChanged(@NotNull LifecycleOwner owner,
                            @NotNull Lifecycle.Event event);
}
