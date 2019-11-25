package com.example.shimaostaff.pointcheck.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shimaostaff.http.CallBack;
import com.example.shimaostaff.pointcheck.model.PageModel;
import com.example.shimaostaff.pointcheck.model.State;
import com.example.shimaostaff.pointcheck.model.CheckPointPage;
import com.example.shimaostaff.pointcheck.repository.PointCheckListRepository;

/**
 * @ProjectName: pms_old
 * @Package: com.example.shimaostaff.pointcheck.viewmodel
 * @ClassName: PointCheckListViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:43
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:43
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class PointCheckListViewModel extends ViewModel {
    private MutableLiveData<CheckPointPage> pageList=new MutableLiveData<>();
    private PointCheckListRepository repository=new PointCheckListRepository();
    public MutableLiveData<State> state=new MutableLiveData<>();
    public int page;

    public MutableLiveData<CheckPointPage> queryPage(int page,int pageSize){
//        state.postValue(State.SHOWLOADING);
        PageModel pageModel=new PageModel();
        pageModel.setPage(page);
        pageModel.setPageSize(pageSize);
        repository.pageQuery(pageModel, new CallBack<CheckPointPage>() {
            @Override
            public void call(CheckPointPage data) {
                if(data!=null){
                    pageList.postValue(data);
                }
                state.postValue(State.HIDELOADING);
            }

            @Override
            public void onFaild(Throwable throwable) {
                state.postValue(State.HIDELOADING);
            }
        });
        return pageList;
    }
}
