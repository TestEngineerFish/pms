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
 * 基础数据获取(内存缓存)，线程池管理，后期迁移至数据库
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
        fixedThreadPool = Executors.newFixedThreadPool(5);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        resourceService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE);
        mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
        dictService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
        workOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
    }

    /**
     * 单例
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
//     * 刷新基础数据/从新获取基础数据
//     *
//     * @param callBack
//     */
//    public void reload(CallBack<BasicDataDb> callBack) {
//        reload = true;
//        loadBasicData(callBack);
//    }

    /**
     * 获取基础数据，并缓存内存,如果有缓存直接返回缓存数据
     */
    public void loadBasicData(CallBack<BasicData> callBack, BasicDataTypeEnum... typeEnum) {
        if (typeEnum == null) {
            callBack.onFaild(new Throwable("请传入指定枚举"));
            return;
        }
        /**
         * 判断数据是否已经初始化
         */
        if (hasInit(typeEnum) && !reload) {
            callBack.call(basicData);
        } else {
            latch = new CountDownLatch(typeEnum.length);
            for (BasicDataTypeEnum basicDataTypeEnum : typeEnum) {
                switch (basicDataTypeEnum) {
                    case LINE:
                        //所有条线
                        loadLines();
                    case RESOURCE:
                        //基础资源
                        loadResources();
                        break;
                    case LINE_TYPES:
                        //所有分类
                        loadLineTypes();
                        break;
                    case REPAIR_AREA:
                        //报修区域
                        loadRepairArea();
                        break;
                    case COMPLAIN_TYPES:
                        //投诉类型
                        loadComplainTypes();
                        break;
                    case COMPLAIN_PROPERTYS:
                        //投诉性质
                        loadComplainPropertys();
                        break;
                    case PREVIEW_SELECT:
                        //获取工单预览筛选
                        loadPreviewSelect();
                        break;
                }
            }
            //获取结果
            loadResult(callBack);
        }
    }

    /**
     * 获取基础数据，并缓存内存,如果有缓存直接返回缓存数据
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
            if (callBack != null) {
                callBack.call(dictDataModel);
            }
        }
    }

    /**
     * 获取基础数据，并缓存内存,如果有缓存直接返回缓存数据
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
     * 获取工单预览筛选条线
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
     * 获取基础数据
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
     * 获取分类 客服，工程，秩序，客服
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
     * 加载所有条线数据
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
     * 加载所有资源数据
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
     * 投诉性质
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
     * 投诉性质
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
     * 获取分期下面的网格信息
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
     * 获取报修区域
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
     * 判断是否初始化有数据
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
