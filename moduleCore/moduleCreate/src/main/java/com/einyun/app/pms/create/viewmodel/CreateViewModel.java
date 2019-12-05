package com.einyun.app.pms.create.viewmodel;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.pms.create.viewmodel.contract.CreateViewModelContract;

/**
 * 业务逻辑处理，UI交互
 */
public class CreateViewModel extends BaseViewModel implements CreateViewModelContract {
    private static final String TAG = CreateViewModel.class.getSimpleName();
    private DictService dictService;
//    private LiveData<UserModel> mUserModel;

    public CreateViewModel() {
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
    }



}
