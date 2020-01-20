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
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.uc.usercenter.model.HouseModel;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.mockito.internal.matchers.Or;

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
    public MutableLiveData<List<OrgModel>> orgList = new MutableLiveData();

    public LiveData<List<OrgModel>> queryOrgs(String userId, String parentId) {
        UserCenterService service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
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

    public LiveData<List<DictDataModel>> getByTypeKey() {
        DictService service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
        return service.getByTypeKey("pgdlx", new CallBack<List<DictDataModel>>() {
            @Override
            public void call(List<DictDataModel> data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public LiveData<List<HouseModel>> getHouseByCondition(String divide, String id) {
        UserCenterService service = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        return service.getHouseByCondition(divide, id, new CallBack<List<HouseModel>>() {
            @Override
            public void call(List<HouseModel> data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    public void saveBlock2Local(String blockId, String blockName, String blockCode) {
        if (!TextUtils.isEmpty(blockId)) {
            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, blockId);
        }
        if (!TextUtils.isEmpty(blockCode)) {
            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CODE, blockCode);
        }
        if (!TextUtils.isEmpty(blockName)) {
            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, blockName);
        }
    }


    public void saveChache2Local(List<OrgModel> selected) {
        String josn = new Gson().toJson(selected);
        SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CHOOSE_CACHE, josn);
    }

    public LiveData<List<OrgModel>> loadFromCache() {
        MutableLiveData<List<OrgModel>> liveData = new MutableLiveData<>();
        String json = SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CHOOSE_CACHE, "").toString();
        if (!TextUtils.isEmpty(json)) {
            List<OrgModel> list = new Gson().fromJson(json, new TypeToken<List<OrgModel>>() {
            }.getType());
            liveData.postValue(list);
        } else {
            liveData.postValue(null);
        }
        return liveData;
    }

//    public void saveBlock2LocalNoJump(String blockId, String blockName, String blockCode) {
//        if (!TextUtils.isEmpty(blockId)) {
//            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID_NO_JUMP, blockId);
//        }
//        if (!TextUtils.isEmpty(blockCode)) {
//            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CODE_NO_JUMP, blockCode);
//        }
//        if (!TextUtils.isEmpty(blockName)) {
//            SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME_NO_JUMP, blockName);
//        }
//    }
//
//
//    public void saveChache2LocalNoJump(List<OrgModel> selected) {
//        String josn = new Gson().toJson(selected);
//        SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CHOOSE_CACHE_NO_JUMP, josn);
//    }
//
//    public LiveData<List<OrgModel>> loadFromCacheNoJump() {
//        MutableLiveData<List<OrgModel>> liveData = new MutableLiveData<>();
//        String json = SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CHOOSE_CACHE_NO_JUMP, "").toString();
//        if (!TextUtils.isEmpty(json)) {
//            List<OrgModel> list = new Gson().fromJson(json, new TypeToken<List<OrgModel>>() {
//            }.getType());
//            liveData.postValue(list);
//        } else {
//            liveData.postValue(null);
//        }
//        return liveData;
//    }

    public void saveChacheWorkType(List<DictDataModel> selected) {
        String josn = new Gson().toJson(selected);
        SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_WORK_TYPE_CHOOSE_CACHE, josn);
    }


    public LiveData<List<DictDataModel>> loadFromCacheWorkType() {
        MutableLiveData<List<DictDataModel>> liveData = new MutableLiveData<>();
        String json = SPUtils.get(CommonApplication.getInstance(), SPKey.KEY_WORK_TYPE_CHOOSE_CACHE, "").toString();
        if (!TextUtils.isEmpty(json)) {
            List<DictDataModel> list = new Gson().fromJson(json, new TypeToken<List<DictDataModel>>() {
            }.getType());
            liveData.postValue(list);
        } else {
            liveData.postValue(null);
        }
        return liveData;
    }
}
