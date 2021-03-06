package com.einyun.app.pms.disqualified.viewmodel;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.pms.disqualified.db.DisqualifiedDbRepository;
import com.einyun.app.pms.disqualified.db.UnQualityFeedBackRequest;
import com.einyun.app.pms.disqualified.db.UnQualityVerificationRequest;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.manager.ImageUploadManager;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.disqualified.constants.DisqualifiedDataKey;
import com.einyun.app.pms.disqualified.model.DisqualifiedDetailModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedItemModel;
import com.einyun.app.pms.disqualified.model.DisqualifiedTypesBean;
import com.einyun.app.pms.disqualified.net.request.DisqualifiedListRequest;
import com.einyun.app.pms.disqualified.repository.DataSourceFactory;
import com.einyun.app.pms.disqualified.repository.DisqualifiedRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.mockito.internal.matchers.InstanceOf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DisqualifiedFragmentViewModel extends BasePageListViewModel<DisqualifiedItemModel> {
    private final Map<String, String> uploadedImages = new ConcurrentHashMap<>();
    private ImageUploadManager uploadManager = new ImageUploadManager();
    DisqualifiedDbRepository dbRepository=new DisqualifiedDbRepository();

    public void insertCreateRequest(CreateUnQualityRequest request){
       dbRepository.insertCreateRequest(request);
    }

    public void deleteCreateRequest(String code){
       dbRepository.deleteCreateRequest(code);
    }
    public  LiveData<CreateUnQualityRequest> queryCreateRequest(String code){
        return dbRepository.queryCreateRequest(code);
    }
    public  LiveData<PagedList<CreateUnQualityRequest>> loadAllCreateRequest(){

        return new LivePagedListBuilder(dbRepository.loadAllCreateRequest(), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();
    }

    /*
    * ????????????
    * */
    public void insertFeedBackRequest(String orderId, UnQualityFeedBackRequest request){
        dbRepository.insertFeedBackRequest( orderId,  request);
    }
    public void deleteFeedBackRequest(String code){
        dbRepository.deleteFeedBackRequest(code);
    }
    public  LiveData<UnQualityFeedBackRequest> loadFeedBackRequest(String code){
        return dbRepository.loadFeedBackRequest(code);
    }

    /*
    * ????????????
    * */
    public void insertVerificationRequest(String orderId, UnQualityVerificationRequest request){
        dbRepository.insertVerificationRequest( orderId,  request);
    }
    public void deleteVerificationRequest(String code){
        dbRepository.deleteVerificationRequest(code);
    }
    public  LiveData<UnQualityVerificationRequest> loadVerificationRequest(String code){
        return dbRepository.loadVerificationRequest(code);
    }

    /**
     * ??????Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<DisqualifiedItemModel>> loadPadingData(DisqualifiedListRequest requestBean, String tag){

        pageList = new LivePagedListBuilder(new DataSourceFactory(requestBean,tag), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();

        return pageList;
    }
    DisqualifiedRepository repository=new DisqualifiedRepository();
    //??????????????????
    private MutableLiveData<List<DisqualifiedTypesBean>> detialLineType=new MutableLiveData<>();
    private MutableLiveData<List<DisqualifiedTypesBean>> detialStateType=new MutableLiveData<>();
    private MutableLiveData<List<DisqualifiedTypesBean>> detialSeverityType=new MutableLiveData<>();
    public LiveData<List<DisqualifiedTypesBean>> queryAduitType(String type){
        showLoading();
        repository.queryType( type,new CallBack<List<DisqualifiedTypesBean>>() {
            @Override
            public void call(List<DisqualifiedTypesBean> data) {
                hideLoading();
                switch (type) {
                    case DisqualifiedDataKey.LINE_TYPE_LIST:
                        detialLineType.postValue(data);
                        break;
                    case DisqualifiedDataKey.ORDER_STATE_TYPE_LIST:
                        detialStateType.postValue(data);
                        break;
                    case DisqualifiedDataKey.SEVERITY_TYPE_LIST:
                        detialSeverityType.postValue(data);
                        break;
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        switch (type) {
            case DisqualifiedDataKey.LINE_TYPE_LIST:
                return detialLineType;
            case DisqualifiedDataKey.ORDER_STATE_TYPE_LIST:
                return detialStateType;
            case DisqualifiedDataKey.SEVERITY_TYPE_LIST:
                return detialSeverityType;
            default:
                return detialLineType;

        }
    }
    private MutableLiveData<String> detailOrderCode=new MutableLiveData<>();
    public LiveData<String> queryOrderCode(){
        showLoading();
        repository.queryOrderCode(new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                detailOrderCode.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
                return detailOrderCode;
    }


    private MutableLiveData<DisqualifiedDetailModel> toDoDetailInfo=new MutableLiveData<>();
    public LiveData<DisqualifiedDetailModel> getTODODetailInfo(String taskId){
//        showLoading();
        repository.getTODODetailInfo(taskId, new CallBack<DisqualifiedDetailModel>() {
            @Override
            public void call(DisqualifiedDetailModel data) {
//                hideLoading();
                toDoDetailInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                toDoDetailInfo.postValue(null);
//                hideLoading();
            }
        });
        return toDoDetailInfo;
    }
    private MutableLiveData<DisqualifiedDetailModel> haveDoDetailInfo=new MutableLiveData<>();
    public LiveData<DisqualifiedDetailModel> getHaveDODetailInfo(String proInstId){
//        showLoading();
        repository.getHaveDODetailInfo(proInstId, new CallBack<DisqualifiedDetailModel>() {
            @Override
            public void call(DisqualifiedDetailModel data) {
//                hideLoading();
                haveDoDetailInfo.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                haveDoDetailInfo.postValue(null);
//                hideLoading();
            }
        });
        return haveDoDetailInfo;
    }
    /*
     * ????????????
     * */
    private MutableLiveData<Boolean> dealFeedBack=new MutableLiveData<>();
    public LiveData<Boolean> dealFeedBack(UnQualityFeedBackRequest dealRequest, List<PicUrl> images){
        if (uploadManager != null) {
            dealRequest.getBizData().setFeedback_enclosure(uploadManager.toJosnString(images));
        }
        showLoading();
        repository.dealFeedBack(dealRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                dealFeedBack.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return dealFeedBack;
    }
    /*
     * ????????????
     * */
    private MutableLiveData<Boolean> dealValidate=new MutableLiveData<>();
    public LiveData<Boolean> dealValidate(UnQualityVerificationRequest dealRequest, List<PicUrl> images){
        if (uploadManager != null) {
            dealRequest.getBizData().setVerification_enclosure(uploadManager.toJosnString(images));
        }
        showLoading();
        repository.dealValidate(dealRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                dealFeedBack.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return dealFeedBack;
    }
    /*
    * ?????????json
    * */
    public String toJsonString(List<PicUrl> images){
        if (uploadManager != null) {
            return uploadManager.toJosnString(images);
        }
        return "";
    }
    /*
     * ????????????
     * */
    private MutableLiveData<Boolean> deal=new MutableLiveData<>();
    public LiveData<Boolean> deal(CreateUnQualityRequest dealRequest,List<PicUrl> images){
        if (uploadManager != null) {
            dealRequest.getBizData().setCreate_enclosure(uploadManager.toJosnString(images));
        }
        showLoading();
        repository.dealSubmit(dealRequest, new CallBack<Boolean>() {
            @Override
            public void call(Boolean data) {
                hideLoading();
                deal.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return deal;
    }

    public LiveData<List<PicUrl>> uploadImages(List<Uri> allSelectedPhotos) {
        MutableLiveData<List<PicUrl>> uploadState = new MutableLiveData<>();
        List<Uri> todoUploadUris = filterUris(allSelectedPhotos);
        // ??????????????????????????????????????????????????????
        if (allSelectedPhotos.size() == uploadedImages.size()) {
            uploadState.postValue(new ArrayList<>());
            return uploadState;
        }

        showLoading();
        try {
            uploadManager.upload(todoUploadUris, new CallBack<List<PicUrl>>() {
                @Override
                public void call(List<PicUrl> data) {
                    for (PicUrl picUrl : data) {
                        if (TextUtils.isEmpty(picUrl.getOriginUrl())) {
                            uploadedImages.put(picUrl.getOriginUrl(), picUrl.getUploaded());
                        }
                    }
                    hideLoading();
                    uploadState.postValue(data);
                }

                @Override
                public void onFaild(Throwable throwable) {
                    uploadState.postValue(null);
                    hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            uploadState.postValue(null);
        }
        return uploadState;
    }
    @NotNull
    private List<Uri> filterUris(List<Uri> allSelectedPhotos) {
        List<Uri> todoUploadUris = new ArrayList<>();

        // ???????????????????????????????????????????????????
        for (Uri selectedPhoto : allSelectedPhotos) {
            if (!uploadedImages.keySet().contains(selectedPhoto.toString())) {
                todoUploadUris.add(selectedPhoto);
            }
        }

        // ??????????????????????????????????????????????????????
        for (String uploadeUrl : uploadedImages.keySet()) {
            Uri uploadedUri = Uri.fromFile(new File(uploadeUrl));
            if (!allSelectedPhotos.contains(uploadedUri)) {
                uploadedImages.remove(uploadedUri);
            }
        }
        return todoUploadUris;
    }
    /**
     * ????????????Id
     *
     * @return
     */
    public String getUserId() {
        return userModuleService.getUserId();
    }
    public String getUserName() {
        return userModuleService.getRealName();
    }
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    public DisqualifiedListRequest getRequestBean(int page, int pageSize, String line, String status, String divideId){

        JsonObject jsonObject = new JsonObject();
        JsonObject pageBean = new JsonObject();
        pageBean.addProperty("page", page);
        pageBean.addProperty("pageSize", pageSize);
        pageBean.addProperty("showTotal", false);

        JsonArray querys = new JsonArray();

//        JsonArray sorters = new JsonArray();
//        JsonObject sorter = new JsonObject();
//        sorter.addProperty("property", "wx_time");
//        sorter.addProperty("direction", "DESC");
//        sorters.add(sorter);

        JsonObject querys1 = new JsonObject();
        querys1.addProperty("property", "line");
        querys1.addProperty("operation", "EQUAL");
        querys1.addProperty("value", line);
        querys1.addProperty("relation", "AND");

        JsonObject querys2 = new JsonObject();
        querys2.addProperty("property", "status");
        querys2.addProperty("operation", "EQUAL");
        querys2.addProperty("value", status);
        querys2.addProperty("relation", "AND");

        JsonObject querys3 = new JsonObject();
        querys3.addProperty("property", "divide_id");
        querys3.addProperty("operation", "EQUAL");
        querys3.addProperty("value", divideId);
        querys3.addProperty("relation", "AND");


        if (!divideId.isEmpty()) {
            querys.add(querys3);
        }

        if (!line.isEmpty()) {
            querys.add(querys1);
        }
        if (!status.isEmpty()) {
            querys.add(querys2);
        }

        jsonObject.add("pageBean", pageBean);
        jsonObject.add("querys", querys);
//        jsonObject.add("sorter", sorters);
        Log.e("????????????", "RequestTodoList: "+jsonObject.toString() );

        return new Gson().fromJson(jsonObject.toString(),DisqualifiedListRequest.class);
    }
    public DisqualifiedListRequest getRequestSearchBean(int page, int pageSize, String line, String status, String divideId,String code,String desc){

        JsonObject jsonObject = new JsonObject();
        JsonObject pageBean = new JsonObject();
        pageBean.addProperty("page", page);
        pageBean.addProperty("pageSize", pageSize);
        pageBean.addProperty("showTotal", false);

        JsonArray querys = new JsonArray();

//        JsonArray sorters = new JsonArray();
//        JsonObject sorter = new JsonObject();
//        sorter.addProperty("property", "wx_time");
//        sorter.addProperty("direction", "DESC");
//        sorters.add(sorter);

        JsonObject querys1 = new JsonObject();
        querys1.addProperty("property", "line");
        querys1.addProperty("operation", "EQUAL");
        querys1.addProperty("value", line);
        querys1.addProperty("relation", "AND");

        JsonObject querys2 = new JsonObject();
        querys2.addProperty("property", "status");
        querys2.addProperty("operation", "EQUAL");
        querys2.addProperty("value", status);
        querys2.addProperty("relation", "AND");

        JsonObject querys3 = new JsonObject();
        querys3.addProperty("property", "divide_id");
        querys3.addProperty("operation", "EQUAL");
        querys3.addProperty("value", divideId);
        querys3.addProperty("relation", "AND");

        JsonObject querys4 = new JsonObject();
        querys4.addProperty("property", "code");
        querys4.addProperty("operation", "LIKE");
        querys4.addProperty("value", code);
        querys4.addProperty("relation", "OR");

        JsonObject querys5 = new JsonObject();
        querys5.addProperty("property", "problem_description");
        querys5.addProperty("operation", "LIKE");
        querys5.addProperty("value", desc);
        querys5.addProperty("relation", "OR");
        if (!divideId.isEmpty()) {
            querys.add(querys3);
        }

        if (!line.isEmpty()) {
            querys.add(querys1);
        }
        if (!status.isEmpty()) {
            querys.add(querys2);
        }
        if (!code.isEmpty()) {
            querys.add(querys4);
        }
        if (!desc.isEmpty()) {
            querys.add(querys5);
        }
        jsonObject.add("pageBean", pageBean);
        jsonObject.add("querys", querys);
//        jsonObject.add("sorter", sorters);
        Log.e("????????????", "RequestTodoList: "+jsonObject.toString() );

        return new Gson().fromJson(jsonObject.toString(),DisqualifiedListRequest.class);
    }
    public DisqualifiedListRequest getRequestSearchListBean(int page, int pageSize,String code){
        JsonObject jsonObject = new JsonObject();
        JsonObject pageBean = new JsonObject();
        pageBean.addProperty("page", page);
        pageBean.addProperty("pageSize", pageSize);
        pageBean.addProperty("showTotal", false);
        JsonObject params = new JsonObject();
        params.addProperty("searchValue", code);
        jsonObject.add("pageBean", pageBean);
        jsonObject.add("params", params);
        Log.e("????????????", "RequestTodoList: "+jsonObject.toString() );

        return new Gson().fromJson(jsonObject.toString(),DisqualifiedListRequest.class);
    }
    public void cacheFeedBackPhotos(List<Uri> uris, String code,UnQualityFeedBackRequest mRequest) {
        String paths=new Gson().toJson(cachedPhotoList(uris));
        mRequest.getBizData().setFeedback_enclosure(paths);
        insertFeedBackRequest(code,mRequest);
    }
    public List<Uri> loadCacheFeedbackPhotoUris(UnQualityFeedBackRequest mRequest){
        List<Uri> uris=new ArrayList<>();
        if(!TextUtils.isEmpty(mRequest.getBizData().getFeedback_enclosure())){
            List<String> list=new Gson().fromJson(mRequest.getBizData().getFeedback_enclosure(),new TypeToken<List<String>>(){}.getType());
            for(String path:list){
                Uri uri=Uri.parse(path);
                uris.add(uri);
            }
        }
        return uris;
    }
    public void cacheVerifiPhotos(List<Uri> uris, String code,UnQualityVerificationRequest mRequest) {
        String paths=new Gson().toJson(cachedPhotoList(uris));
        mRequest.getBizData().setVerification_enclosure(paths);
        insertVerificationRequest(code,mRequest);
    }

    public List<Uri> loadCacheVerifiPhotoUris(UnQualityVerificationRequest mRequest){
        List<Uri> uris=new ArrayList<>();
        if(!TextUtils.isEmpty(mRequest.getBizData().getVerification_enclosure())){
            List<String> list=new Gson().fromJson(mRequest.getBizData().getVerification_enclosure(),new TypeToken<List<String>>(){}.getType());
            for(String path:list){
                Uri uri=Uri.parse(path);
                uris.add(uri);
            }
        }
        return uris;
    }
    public void cachePhotos(List<Uri> uris, CreateUnQualityRequest mRequest) {
        String paths=new Gson().toJson(cachedPhotoList(uris));
        mRequest.getBizData().setCreate_enclosure(paths);
        insertCreateRequest(mRequest);
    }
    public List<Uri> loadCachePhotoUris(CreateUnQualityRequest mRequest){
        List<Uri> uris=new ArrayList<>();
        if(!TextUtils.isEmpty(mRequest.getBizData().getCreate_enclosure())){
            List<String> list=new Gson().fromJson(mRequest.getBizData().getCreate_enclosure(),new TypeToken<List<String>>(){}.getType());
            for(String path:list){
                Uri uri=Uri.parse(path);
                uris.add(uri);
            }
        }
        return uris;
    }



    private List<String> cachedPhotoList(List<Uri> uris){
        List<String> list=new ArrayList<>();
        for(Uri uri:uris){
            String path=uri.toString();
            list.add(path);
        }
        return list;
    }
}
