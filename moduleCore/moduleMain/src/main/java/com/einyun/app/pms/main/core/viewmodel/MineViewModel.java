package com.einyun.app.pms.main.core.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.viewmodel.BasePageListViewModel;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.core.api.DashBoardService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.UCService;
import com.einyun.app.library.core.api.UserCenterService;
import com.einyun.app.library.uc.user.model.UserInfoModel;
import com.einyun.app.library.uc.usercenter.model.WorkStatusModel;
import com.einyun.app.pms.main.core.model.HasReadModel;
import com.einyun.app.pms.main.core.model.LineListModel;
import com.einyun.app.pms.main.core.model.ScanPatrolModel;
import com.einyun.app.pms.main.core.model.ScanRequest;
import com.einyun.app.pms.main.core.model.ScanResItemModel;
import com.einyun.app.pms.main.core.model.ScanResModel;
import com.einyun.app.pms.main.core.model.UCUserDetailsBean;
import com.einyun.app.pms.main.core.model.UserStarsBean;
import com.einyun.app.pms.main.core.repository.DataSourceFactory;
import com.einyun.app.pms.main.core.repository.MainRepository;
import com.einyun.app.pms.main.core.viewmodel.contract.MineViewModelContract;
import com.google.gson.Gson;

import java.util.List;

import static com.einyun.app.pms.main.core.repository.URLS.URL_GET_ENVIROMENT_TYPE;
import static com.einyun.app.pms.main.core.repository.URLS.URL_GET_PATROL_BASIC;


public class MineViewModel extends BasePageListViewModel<ScanResItemModel> implements MineViewModelContract {
    UserCenterService userCenterService;
    UCService ucService;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    public MineViewModel() {
//        mUsersRepo = new UserRepository();
        userCenterService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_USER_CENTER);
        ucService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_UC);
    }
    @Override
    public LiveData<String> getWorkState() {
        return userCenterService.getWorkStatus(getUserId(), new CallBack<String>() {
            @Override
            public void call(String data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<String> updateWorkState(String status) {
        return userCenterService.updateWorkStatus(getUserId(), userModuleService.getUserName(), status,"","","","",new CallBack<String>() {
            @Override
            public void call(String data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    @Override
    public LiveData<UserInfoModel> getUserInfoByUserId() {
        return ucService.userById(getUserId(), new CallBack<UserInfoModel>() {
            @Override
            public void call(UserInfoModel data) {

            }

            @Override
            public void onFaild(Throwable throwable) {
                ThrowableParser.onFailed(throwable);
            }
        });
    }

    /**
     * ????????????Id
     *
     * @return
     */
    @Override
    public String getUserId() {
        return userModuleService.getUserId();
    }

    /**
     * ??????????????????
     *
     * **/
    MainRepository repository=new MainRepository();
    private MutableLiveData<UCUserDetailsBean> detialStars=new MutableLiveData<>();
    public LiveData<UCUserDetailsBean> getStars(UserStarsBean bean){
        showLoading();
        repository.queryStars(bean, new CallBack<UCUserDetailsBean>() {
            @Override
            public void call(UCUserDetailsBean data) {
                hideLoading();
                detialStars.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return detialStars;
    }
    private MutableLiveData<HasReadModel> hasReadModel=new MutableLiveData<>();
    public LiveData<HasReadModel> hadRead(){
        showLoading();
        repository.hasRead(new CallBack<HasReadModel>() {
            @Override
            public void call(HasReadModel data) {
                hideLoading();
                hasReadModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return hasReadModel;
    }
    private MutableLiveData<ScanResModel> getResModel=new MutableLiveData<>();
    public LiveData<ScanResModel> getRes(String id){
        showLoading();
        repository.getRes(URL_GET_PATROL_BASIC+"/"+id,new CallBack<ScanResModel>() {
            @Override
            public void call(ScanResModel data) {
                hideLoading();
                getResModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getResModel;
    }

    private MutableLiveData<ScanResModel> getClassPathModel=new MutableLiveData<>();
    public LiveData<ScanResModel> getClassPath(){
        showLoading();
        repository.getClassPath(URL_GET_ENVIROMENT_TYPE,new CallBack<ScanResModel>() {
            @Override
            public void call(ScanResModel data) {
                hideLoading();
                getClassPathModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getClassPathModel;
    }

    private MutableLiveData<ScanPatrolModel> getPatrolModel=new MutableLiveData<>();
    public LiveData<ScanPatrolModel> getPatrol(String id){
        showLoading();
        repository.getPatrol(URL_GET_PATROL_BASIC+"/"+id,new CallBack<ScanPatrolModel>() {
            @Override
            public void call(ScanPatrolModel data) {
                hideLoading();
                getPatrolModel.postValue(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });
        return getPatrolModel;
    }

    /**
     * ??????Paging LiveData
     * @return LiveData
     */
    public LiveData<PagedList<ScanResItemModel>> loadPadingData(ScanRequest requestBean, String tag,String type){

        pageList = new LivePagedListBuilder(new DataSourceFactory(requestBean,tag,type), config)
//                .setBoundaryCallback(null)
//                .setFetchExecutor(null)
                .build();

        return pageList;
    }
    public List<LineListModel.DataBean> getLineList(){
        List<LineListModel.DataBean> data = new Gson().fromJson("{\n" +
                "    \"msg\": \"SUCCESS\",\n" +
                "    \"code\": 0,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"498243\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ld\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"483534\",\n" +
                "            \"open\": \"true\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498250\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gq\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"483534\",\n" +
                "            \"open\": \"true\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498254\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldgq\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498243\",\n" +
                "            \"open\": \"true\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498258\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"lddtj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498262\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjgq\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498250\",\n" +
                "            \"open\": \"true\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498266\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swgq\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498250\",\n" +
                "            \"open\": \"true\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498270\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkgq\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498250\",\n" +
                "            \"open\": \"true\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498274\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjdtj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498278\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjggzd\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498282\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gc\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498286\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"lh\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498290\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dxtcc\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498295\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldggzd\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498299\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldxftd\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498303\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"lddt\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498307\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldjkc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498312\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"wmtt\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498316\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"lddtjd\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498325\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldqdj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498329\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldrdj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498333\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldsngj\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498337\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldxfsbf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498341\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldxfsjf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498345\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldqtmhs\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498349\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldxfwybf\",\n" +
                "            \"name\": \"??????????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498353\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldxfjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498357\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldshsbf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498361\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldzsjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498365\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gljf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498369\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ktjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498373\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ldjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498377\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"jsk\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498381\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"hfc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498385\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"fdjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498389\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gypds\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498393\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dypds\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498397\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dtjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498401\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"rdjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498405\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"zbs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498409\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"jkzx\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498413\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498417\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"wsj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498421\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"xyf\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498426\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"bgs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498431\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"csj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498435\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"bnc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498439\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gyc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498443\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"pdj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498254\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498447\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"xftd\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498451\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dt\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498458\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"xfsbf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498462\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"xffjf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498466\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"qtmhs\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498470\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"xfwybf\",\n" +
                "            \"name\": \"??????????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498474\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"xfjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498478\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"shsbf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498482\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"zsjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498487\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkgljf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498491\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkktjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498496\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkldjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498500\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkjsk\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498504\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkhfc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498508\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkfdjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498512\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkgypds\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498516\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkdypds\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498520\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkdtjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498524\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkrdjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498528\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkzbs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498532\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkjkzx\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498536\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkgjj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498540\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkwsj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498544\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkxyf\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498548\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkbgs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498552\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkcsj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498556\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkpdj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498560\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkjqs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498564\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkxdj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498568\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"dkzbjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498270\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498572\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swsj\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498576\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swwq\",\n" +
                "            \"name\": \"??????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498580\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swdstcc\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498584\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swfjdcp\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498588\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swyqcrk\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498592\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swyqdl\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498596\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"swyyc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498600\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"ylc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498266\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498604\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjjkc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498623\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjwmtt\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498628\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjzbs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498632\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjjkzx\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498636\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjgjj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498640\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjwsj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498644\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjxyf\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498648\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjbgs\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498653\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjcsj\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498666\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjxfsbf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498673\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjxffjf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498688\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjqtmhs\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498699\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjxfwybf\",\n" +
                "            \"name\": \"??????????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"??????????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498703\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjxfjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498707\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjshsbf\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498714\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjzsjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498718\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjgljf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498722\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjktjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498726\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjldjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498730\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjjhk\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498734\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjhfc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498738\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjfdjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498742\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjgypds\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498746\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjdypds\",\n" +
                "            \"name\": \"???????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"???????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498756\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjdtjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498764\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjrdjf\",\n" +
                "            \"name\": \"????????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"????????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498768\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjbnc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"498772\",\n" +
                "            \"typeId\": \"483534\",\n" +
                "            \"key\": \"gjgyc\",\n" +
                "            \"name\": \"?????????\",\n" +
                "            \"parentId\": \"498262\",\n" +
                "            \"open\": \"false\",\n" +
                "            \"isParent\": \"false\",\n" +
                "            \"children\": [],\n" +
                "            \"text\": \"?????????\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"state\": true,\n" +
                "    \"request_id\": \"xxx\"\n" +
                "}", LineListModel.class).getData();

        return data;
    }
}
