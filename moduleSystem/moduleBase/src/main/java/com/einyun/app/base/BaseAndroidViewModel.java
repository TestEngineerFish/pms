package com.einyun.app.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: BaseAndroidViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/08/30 09:22
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/08/30 09:22
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BaseAndroidViewModel extends AndroidViewModel {
    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
        ARouter.getInstance().inject(this);
    }
}
