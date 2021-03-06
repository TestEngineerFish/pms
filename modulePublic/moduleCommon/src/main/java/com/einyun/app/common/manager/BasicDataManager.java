package com.einyun.app.common.manager;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.einyun.app.base.db.entity.BasicDataDb;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.Constants;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.library.core.api.DictService;
import com.einyun.app.library.core.api.MdmService;
import com.einyun.app.library.core.api.ResourceService;
import com.einyun.app.library.core.api.ResourceWorkOrderService;
import com.einyun.app.library.core.api.ServiceManager;
import com.einyun.app.library.core.api.WorkOrderService;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.mdm.model.GridModel;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.model.LineType;
import com.einyun.app.library.resource.workorder.model.PreviewSelectModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.workorder.model.AreaModel;
import com.einyun.app.library.workorder.model.TypeAndLine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.einyun.app.common.manager.BasicDataTypeEnum.COMPLAIN_PROPERTYS;
import static com.einyun.app.common.manager.BasicDataTypeEnum.COMPLAIN_TYPES;
import static com.einyun.app.common.manager.BasicDataTypeEnum.LINE;
import static com.einyun.app.common.manager.BasicDataTypeEnum.LINE_TYPES;
import static com.einyun.app.common.manager.BasicDataTypeEnum.PREVIEW_SELECT;
import static com.einyun.app.common.manager.BasicDataTypeEnum.REPAIR_AREA;
import static com.einyun.app.common.manager.BasicDataTypeEnum.RESOURCE;

/**
 * ??????????????????(????????????)?????????????????????????????????????????????
 */
public class BasicDataManager {
    private static BasicDataManager instance;
    private BasicDataRepository repository;
    private static Lock lock = new ReentrantLock();
    private BasicData basicData;
    private ResourceWorkOrderService resourceWorkOrderService;
    private DictService dictService;
    private ResourceService resourceService;
    private WorkOrderService workOrderService;
    private MdmService mdmService;
    private CountDownLatch latch;
    private ExecutorService fixedThreadPool;
    private volatile boolean reload = false;//

    private BasicDataManager() {
        basicData = new BasicData();
        repository = new BasicDataRepository();
        fixedThreadPool = Executors.newFixedThreadPool(8);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        resourceService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE);
        mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
    }

    /**
     * ??????
     *
     * @return
     */
    public static BasicDataManager getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new BasicDataManager();
            }
            lock.unlock();
        }
        return instance;
    }

//    /**
//     * ??????????????????/????????????????????????
//     *
//     * @param callBack
//     */
//    public void reload(CallBack<BasicDataDb> callBack) {
//        reload = true;
//        loadBasicData(callBack);
//    }

    /**
     * ????????????????????????????????????,???????????????????????????????????????
     */
    public void loadBasicData(CallBack<BasicData> callBack, BasicDataTypeEnum... typeEnum) {
        if (typeEnum == null) {
            callBack.onFaild(new Throwable("?????????????????????"));
            return;
        }
        /**
         * ?????????????????????????????????
         */
        if (hasInit(typeEnum) && !reload) {
            callBack.call(basicData);
        } else {
            latch = new CountDownLatch(typeEnum.length);
            for (BasicDataTypeEnum basicDataTypeEnum : typeEnum) {
                switch (basicDataTypeEnum) {
                    case LINE:
                        //????????????
                        loadLines();
                    case RESOURCE:
                        //????????????
                        loadResources();
                        break;
                    case LINE_TYPES:
                        //????????????
                        loadLineTypes();
                        break;
                    case REPAIR_AREA:
                        //????????????
                        loadRepairArea();
                        break;
                    case COMPLAIN_TYPES:
                        //????????????
                        loadComplainTypes();
                        break;
                    case COMPLAIN_PROPERTYS:
                        //????????????
                        loadComplainPropertys();
                        break;
                    case PREVIEW_SELECT:
                        //????????????????????????
                        loadPreviewSelect();
                        break;
                }
            }
            //????????????
            loadResult(callBack);
        }
    }

    /**
     * ????????????????????????????????????,???????????????????????????????????????
     */
    public void loadBasicDataByTypeKey(CallBack<List<DictDataModel>> callBack, String typeKey) {
        List<DictDataModel> dictDataModel = basicData.getDictDataModelMap().get(typeKey);
        if (dictDataModel == null || dictDataModel.isEmpty()) {
            repository.queryData("typeKey" + typeKey, new CallBack<BasicDataDb>() {
                @Override
                public void call(BasicDataDb basicDataDb) {
                    if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                        List<DictDataModel> model = gson.fromJson(basicDataDb.getBasicData(), new TypeToken<List<DictDataModel>>() {
                        }.getType());
                        basicData.getDictDataModelMap().put(typeKey, model);
                        ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.call(model);
                            }
                        });
                    }
                    dictService.getByTypeKey(typeKey, new CallBack<List<DictDataModel>>() {
                        @Override
                        public void call(List<DictDataModel> data) {
                            basicData.getDictDataModelMap().put(typeKey, data);
                            if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                                ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.call(data);
                                    }
                                });
                            }
                            repository.insertData("typeKey" + typeKey, data);
                        }

                        @Override
                        public void onFaild(Throwable throwable) {
                            ThrowableParser.onFailed(throwable);
                        }
                    });
                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });
        } else {
            dictService.getByTypeKey(typeKey, new CallBack<List<DictDataModel>>() {
                @Override
                public void call(List<DictDataModel> data) {
                    basicData.getDictDataModelMap().put(typeKey, data);
//                    if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                        ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.call(data);
                            }
                        });
//                    }
                    repository.insertData("typeKey" + typeKey, data);
                }

                @Override
                public void onFaild(Throwable throwable) {
                    ThrowableParser.onFailed(throwable);
                }
            });
            if (callBack != null) {
                callBack.call(dictDataModel);
            }
        }
    }

    /**
     * ????????????????????????????????????,???????????????????????????????????????
     */
    public void loadBasicDataTypesListKey(CallBack<List<DictDataModel>> callBack, String typeListKey) {
        List<DictDataModel> dictDataModel = basicData.getTypesListKeyMap().get(typeListKey);
        if (dictDataModel == null || dictDataModel.isEmpty()) {
            repository.queryData("typeListKey" + typeListKey, new CallBack<BasicDataDb>() {
                @Override
                public void call(BasicDataDb basicDataDb) {
                    if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                        List<DictDataModel> model = gson.fromJson(basicDataDb.getBasicData(), new TypeToken<List<DictDataModel>>() {
                        }.getType());
                        basicData.getTypesListKeyMap().put(typeListKey, model);
                        ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.call(model);
                            }
                        });
                    }
                    dictService.getTypesListByKey(typeListKey, new CallBack<List<DictDataModel>>() {
                        @Override
                        public void call(List<DictDataModel> data) {
                            basicData.getTypesListKeyMap().put(typeListKey, data);
                            if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                                ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.call(data);
                                    }
                                });
                            }
                            repository.insertData("typeListKey" + typeListKey, data);
                        }

                        @Override
                        public void onFaild(Throwable throwable) {
                            ThrowableParser.onFailed(throwable);
                        }
                    });
                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });
        } else {
            if (callBack != null) {
                callBack.call(dictDataModel);
            }
        }
    }


    Gson gson = new Gson();

    /**
     * ??????????????????????????????
     */
    protected void loadPreviewSelect() {
        repository.queryData(PREVIEW_SELECT.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setPreviewSelect(gson.fromJson(basicDataDb.getBasicData().toString(), new TypeToken<List<PreviewSelectModel>>() {
                    }.getType()));
                    latch.countDown();
                }
                fixedThreadPool.execute(() -> resourceWorkOrderService.getOrderPreviewSelect(new CallBack<List<PreviewSelectModel>>() {
                    @Override
                    public void call(List<PreviewSelectModel> data) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        basicData.setPreviewSelect(data);
                        repository.insertData(PREVIEW_SELECT.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

    }


    /**
     * ??????????????????
     *
     * @param callBack
     */
    protected void loadResult(CallBack<BasicData> callBack) {
        fixedThreadPool.execute(() -> {
            try {
                latch.await();
                reload = false;
                Single.just(0)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>(){

                            @Override
                            public void accept(Integer integer) throws Exception {
                                callBack.call(basicData);
                            }
                        });
            } catch (InterruptedException e) {
                callBack.onFaild(e);
                e.printStackTrace();
            }
        });
    }

    /**
     * ???????????? ?????????????????????????????????
     */
    protected void loadLineTypes() {
        repository.queryData(LINE_TYPES.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setListLineTypes(gson.fromJson(basicDataDb.getBasicData(), new TypeToken<List<LineType>>() {
                    }.getType()));
                    latch.countDown();
                }
                fixedThreadPool.execute(() -> resourceService.getLineType(new CallBack<List<LineType>>() {
                    @Override
                    public void call(List<LineType> data) {
                        basicData.setListLineTypes(data);
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        repository.insertData(LINE_TYPES.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * ????????????????????????
     */
    protected void loadLines() {
        repository.queryData(LINE.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setLines(gson.fromJson(basicDataDb.getBasicData().toString(), new TypeToken<List<WorkOrderTypeModel>>() {
                    }.getType()));
                    latch.countDown();
                }
                fixedThreadPool.execute(() -> resourceWorkOrderService.getWorkOrderType(new CallBack<List<WorkOrderTypeModel>>() {
                    @Override
                    public void call(List<WorkOrderTypeModel> data) {
                        basicData.setLines(data);
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        repository.insertData(LINE.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

    }

    /**
     * ????????????????????????
     */
    protected void loadResources() {
        repository.queryData(RESOURCE.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setResources(gson.fromJson(basicDataDb.getBasicData(), new TypeToken<List<ResourceTypeBean>>() {
                    }.getType()));
                    latch.countDown();
                }
                fixedThreadPool.execute(() -> resourceWorkOrderService.getTiaoXian(new CallBack<List<ResourceTypeBean>>() {
                    @Override
                    public void call(List<ResourceTypeBean> data) {
                        basicData.setResources(data);
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        repository.insertData(RESOURCE.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });

    }

    /**
     * ????????????
     */
    protected void loadComplainPropertys() {
        repository.queryData(COMPLAIN_PROPERTYS.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setComplainPropertys(gson.fromJson(basicDataDb.getBasicData(), new TypeToken<List<DictDataModel>>() {
                    }.getType()));
                    latch.countDown();
                }
                fixedThreadPool.execute(() -> dictService.getByTypeKey(Constants.COMPLAIN_WAY, new CallBack<List<DictDataModel>>() {
                    @Override
                    public void call(List<DictDataModel> data) {
                        basicData.setComplainPropertys(data);
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        repository.insertData(COMPLAIN_PROPERTYS.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * ????????????
     */
    protected void loadComplainTypes() {
        repository.queryData(COMPLAIN_TYPES.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setComplainTypes(gson.fromJson(basicDataDb.getBasicData(), new TypeToken<List<TypeAndLine>>() {
                    }.getType()));
                    latch.countDown();
                }
                fixedThreadPool.execute(() -> workOrderService.typeAndLineList(new CallBack<List<TypeAndLine>>() {
                    @Override
                    public void call(List<TypeAndLine> data) {
                        basicData.setComplainTypes(data);
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        repository.insertData(COMPLAIN_TYPES.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * ?????????????????????????????????
     *
     * @param divideId
     * @param callBack
     */
    public void loadDivideGrid(String divideId, CallBack<DivideGrid> callBack) {
        if (basicData.getDivideGridMap().get(divideId) != null) {
            if (callBack != null) {
                callBack.call(basicData.getDivideGridMap().get(divideId));
            }
            return;
        }
        repository.queryData("divideGrid" + divideId, new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    DivideGrid divideGrid = gson.fromJson(basicDataDb.getBasicData(), new TypeToken<DivideGrid>() {
                    }.getType());
                    basicData.getDivideGridMap().put(divideId, divideGrid);
                    ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.call(divideGrid);
                            }
                        }
                    });
                }
                fixedThreadPool.execute(() ->
                        mdmService.gridPage(divideId, new CallBack<PageResult<GridModel>>() {
                            @Override
                            public void call(PageResult<GridModel> data) {
                                DivideGrid gridData = new DivideGrid(data);
                                basicData.getDivideGridMap().put(divideId, gridData);
                                if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                                    ActivityUtil.getLastActivty().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (callBack != null) {
                                                callBack.call(gridData);
                                            }
                                        }
                                    });
                                }
                                repository.insertData("divideGrid" + divideId, gridData);
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                if (callBack != null) {
                                    callBack.call(null);
                                }
                            }
                        }));
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * ??????????????????
     */
    protected void loadRepairArea() {
        repository.queryData(REPAIR_AREA.getTypeName(), new CallBack<BasicDataDb>() {
            @Override
            public void call(BasicDataDb basicDataDb) {
                if (basicDataDb != null && StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                    basicData.setRepairArea(gson.fromJson(basicDataDb.getBasicData(), new TypeToken<AreaModel>() {
                    }.getType()));
                    if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                        latch.countDown();
                    }
                }
                workOrderService.getAreaType(new CallBack<AreaModel>() {
                    @Override
                    public void call(AreaModel data) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                        basicData.setRepairArea(data);
                        repository.insertData(REPAIR_AREA.getTypeName(), data);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        if (basicDataDb == null || !StringUtil.isNullStr(String.valueOf(basicDataDb.getBasicData()))) {
                            latch.countDown();
                        }
                    }
                });
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        });
    }

    /**
     * ??????????????????????????????
     *
     * @param typeEnum
     * @return
     */
    public boolean hasInit(BasicDataTypeEnum[] typeEnum) {
        if (basicData != null) {
            for (BasicDataTypeEnum basicDataTypeEnum : typeEnum) {
                switch (basicDataTypeEnum) {
                    case LINE:
                        if (basicData.getLines() == null || basicData.getLines().size() == 0) {
                            return false;
                        }
                        break;
                    case RESOURCE:
                        if (basicData.getResources() == null || basicData.getResources().size() == 0) {
                            return false;
                        }
                        break;
                    case LINE_TYPES:
                        if (basicData.getListLineTypes() == null || basicData.getListLineTypes().size() == 0) {
                            return false;
                        }
                        break;
                    case REPAIR_AREA:
                        if (basicData.getRepairArea() == null) {
                            return false;
                        }
                        break;
                    case COMPLAIN_TYPES:
                        if (basicData.getComplainTypes() == null || basicData.getComplainTypes().size() == 0) {
                            return false;
                        }
                        break;
                    case COMPLAIN_PROPERTYS:
                        if (basicData.getComplainPropertys() == null || basicData.getComplainPropertys().size() == 0) {
                            return false;
                        }
                        break;
                    case PREVIEW_SELECT:
                        if (basicData.getPreviewSelect() == null || basicData.getPreviewSelect().size() == 0) {
                            return false;
                        }
                        break;
                }
            }
            return true;
        }
        return false;
    }


}
