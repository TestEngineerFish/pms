package com.einyun.app.common.ui.component.blockchoose.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.ui.component.blockchoose.viewmodel
 * @ClassName: BlockChooseViewModel
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 11:00
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 11:00
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class BlockChooseViewModel extends BaseViewModel {
    public MutableLiveData<List<OrgModel>> orgList=new  MutableLiveData();

    public LiveData<List<OrgModel>> queryOrgs(String userId,String parentId){
       UserCenterService service= ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
       service.listOrChildByOrgId(parentId, userId, new CallBack<List<OrgModel>>() {
           @Override
           public void call(List<OrgModel> data) {
                orgList.postValue(data);
           }

           @Override
           public void onFaild(Throwable throwable) {
               ThrowableParser.onFailed(throwable);
           }
       });
        return orgList;
    }


    public void saveBlock2Local(String blockId, String blockName, String blockCode){
        if(!TextUtils.isEmpty(blockId)){
            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID,blockId);
        }
        if(!TextUtils.isEmpty(blockCode)){
            SPUtils.put(CommonApplication.getInstance(),SPKey.KEY_BLOCK_CODE,blockId);
        }
        if(!TextUtils.isEmpty(blockName)){
            SPUtils.put(CommonApplication.getInstance(),SPKey.KEY_BLOCK_NAME,blockId);
        }
    }

    public void saveChache2Local(List<OrgModel> selected){
       String josn= new Gson().toJson(selected);
       SPUtils.put(CommonApplication.getInstance(),SPKey.KEY_BLOCK_CHOOSE_CACHE,josn);
    }

    public LiveData<List<OrgModel>> loadFromCache(){
        MutableLiveData<List<OrgModel>> liveData=new MutableLiveData<>();
        String json=SPUtils.get(CommonApplication.getInstance(),SPKey.KEY_BLOCK_CHOOSE_CACHE,"").toString();
        if(!TextUtils.isEmpty(json)){
            List<OrgModel> list= new Gson().fromJson(json,new TypeToken<List<OrgModel>>(){}.getType());
            liveData.postValue(list);
        }else{
            liveData.postValue(null);
        }
        return liveData;
    }
}
