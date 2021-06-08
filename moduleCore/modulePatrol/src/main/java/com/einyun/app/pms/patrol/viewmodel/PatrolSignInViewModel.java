package com.einyun.app.pms.patrol.viewmodel;

import android.app.Activity;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.patrol.repository.PatrolRepo;
import java.util.ArrayList;
import java.util.List;

public class PatrolSignInViewModel extends PatrolViewModel {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userService;
    PatrolRepo repo=new PatrolRepo();

    public PatrolSignInViewModel(){
        setUserModuleService(userService);
    }

    /**
     * 缓存图片
     * @param workNode
     * @param orderId
     * @param uris
     */
    public void cachePhotos(WorkNode workNode, String orderId, List<Uri> uris, Activity activity){
        repo.loadLocalUserData(orderId, userService.getUserId(), new CallBack<PatrolLocal>() {
            @Override
            public void call(PatrolLocal data) {
                List<WorkNode> nodes=data.getNodes();
                for(WorkNode node:nodes){
                    if(workNode.patrol_point_id.equals(node.patrol_point_id)){
                        List<String> images=getImageList(uris);
                        node.setCachedImages(images);
                        node.setPic_url(workNode.getPic_url());
                        repo.saveLocalData(data);
                    }
                }
                activity.finish();
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * 获取缓存图片信息
     * @param workNode
     * @param orderId
     * @return
     */
    public LiveData<List<String>> loadCachedImageList(WorkNode workNode,String orderId){
        MutableLiveData<List<String>> liveData=new MutableLiveData<>();
        repo.loadLocalUserData(orderId, userService.getUserId(), new CallBack<PatrolLocal>() {
            @Override
            public void call(PatrolLocal data) {
                if(data!=null){
                    List<WorkNode> nodes=data.getNodes();
                    if (nodes==null) {
                        return;
                    }
                    for(WorkNode node:nodes){
                        if(workNode.patrol_point_id.equals(node.patrol_point_id)){
                            liveData.postValue(node.getCachedImages());
                        }
                    }
                }
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
        return liveData;
    }

    public List<String> getImageList(List<Uri> uris){
        List<String> images=new ArrayList<>();
        if(uris!=null&&uris.size()>0){
            for(Uri uri:uris){
                String path=uri.toString();
                images.add(path);
            }
        }
        return images;
    }
}
