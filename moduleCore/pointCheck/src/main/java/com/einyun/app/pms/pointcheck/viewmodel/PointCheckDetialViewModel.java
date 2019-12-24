package com.einyun.app.pms.pointcheck.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.pointcheck.model.PointCheckDetialModel;
import com.einyun.app.pms.pointcheck.model.State;
import com.einyun.app.pms.pointcheck.repository.PointCheckDetialRepository;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.viewmodel
 * @ClassName: PointCheckDetialViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:43
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckDetialViewModel extends BaseViewModel {
    PointCheckDetialRepository repository=new PointCheckDetialRepository();
    private MutableLiveData<PointCheckDetialModel> detial=new MutableLiveData<>();

    public LiveData<PointCheckDetialModel> queryDetial(String id){
//        showLoading();
        repository.detial(id, new CallBack<PointCheckDetialModel>() {
            @Override
            public void call(PointCheckDetialModel data) {
//                hideLoading();
                detial.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
            }
        });
        return detial;
    }
}
