package com.example.shimaostaff.pointcheck.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shimaostaff.http.CallBack;
import com.example.shimaostaff.pointcheck.model.PointCheckDetialModel;
import com.example.shimaostaff.pointcheck.model.State;
import com.example.shimaostaff.pointcheck.repository.PointCheckDetialRepository;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.viewmodel
 * @ClassName: PointCheckDetialViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:43
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckDetialViewModel extends ViewModel {
    PointCheckDetialRepository repository=new PointCheckDetialRepository();
    public MutableLiveData<State> state=new MutableLiveData<>();
    private MutableLiveData<PointCheckDetialModel> detial=new MutableLiveData<>();

    public LiveData<PointCheckDetialModel> queryDetial(String id){
        state.postValue(State.SHOWLOADING);
        repository.detial(id, new CallBack<PointCheckDetialModel>() {
            @Override
            public void call(PointCheckDetialModel data) {
                state.postValue(State.HIDELOADING);
                detial.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                state.postValue(State.HIDELOADING);
            }
        });
        return detial;
    }
}
