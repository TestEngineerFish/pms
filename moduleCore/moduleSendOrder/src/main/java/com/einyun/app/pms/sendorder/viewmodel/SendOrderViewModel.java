package com.einyun.app.pms.sendorder.viewmodel;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.repository.SendOrderRespository;

public class SendOrderViewModel extends BaseViewModel {
    private SendOrderRespository sendOrderRespository;
    private UCService mUCService;
    private LiveData<SendOrderModel> mUserModel;

    /*public SendOrderViewModel(SendOrderRespository sendOrderRespository, UCService mUCService) {
        this.sendOrderRespository = sendOrderRespository;
        this.mUCService = mUCService;
    }*/
}
