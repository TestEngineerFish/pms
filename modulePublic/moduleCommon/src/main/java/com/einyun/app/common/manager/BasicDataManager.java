package com.einyun.app.common.manager;

import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.paging.bean.PageResult;
import com.einyun.app.common.Constants;
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

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基础数据获取(内存缓存)，线程池管理，后期迁移至数据库
 */
public class BasicDataManager {
    private static BasicDataManager instance;
    private static Lock lock = new ReentrantLock();
    private BasicData basicData;
    private ResourceWorkOrderService resourceWorkOrderService;
    private DictService dictService;
    private ResourceService resourceService;
    private WorkOrderService workOrderService;
    private MdmService mdmService;
    private CountDownLatch latch;
    private ExecutorService fixedThreadPool;
    private final int THREADS = 7;
    private volatile boolean reload = false;//

    private BasicDataManager() {
        basicData = new BasicData();
        latch = new CountDownLatch(THREADS);
        fixedThreadPool = Executors.newFixedThreadPool(5);
        resourceWorkOrderService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE_WORK_ORDER);
        resourceService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_RESOURCE);
        mdmService = ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_MDM);
        dictService=ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_DICT);
        workOrderService=ServiceManager.Companion.obtain().getService(ServiceManager.SERVICE_WORK_ORDER);
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

    /**
     * 刷新基础数据/从新获取基础数据
     *
     * @param callBack
     */
    public void reload(CallBack<BasicData> callBack) {
        reload = true;
        latch = new CountDownLatch(THREADS);
        loadBasicData(callBack);
    }

    /**
     * 获取基础数据，并缓存内存,如果有缓存直接返回缓存数据
     *
     * @param callBack
     */
    public void loadBasicData(CallBack<BasicData> callBack) {
        /**
         * 判断数据是否已经初始化
         */
        if (hasInit() && !reload) {
            callBack.call(basicData);
            return;
        }
        loadResources(); //基础资源
        loadLines(); //所有条线
        loadLineTypes(); //所有分类
        loadComplainTypes(); //投诉类型
        loadComplainPropertys(); //投诉性质
        loadRepairArea(); //报修区域
        loadResult(callBack);//获取结果
        loadPreviewSelect();//获取工单预览筛选

//        fixedThreadPool.shutdown();
    }

    /**
     *
     * 获取工单预览筛选条线
     *
     * */
    protected void loadPreviewSelect() {
        resourceWorkOrderService.getOrderPreviewSelect(new CallBack<List<PreviewSelectModel>>() {
            @Override
            public void call(List<PreviewSelectModel> data) {
                latch.countDown();
                basicData.setPreviewSelect(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
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
                callBack.call(basicData);
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
        fixedThreadPool.execute(() -> resourceService.getLineType(new CallBack<List<LineType>>() {
            @Override
            public void call(List<LineType> data) {
                basicData.setListLineTypes(data);
                latch.countDown();
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
            }
        }));
    }

    /**
     * 加载所有条线数据
     */
    protected void loadLines() {
        fixedThreadPool.execute(() -> resourceWorkOrderService.getWorkOrderType(new CallBack<List<WorkOrderTypeModel>>() {
            @Override
            public void call(List<WorkOrderTypeModel> data) {
                basicData.setLines(data);
                latch.countDown();
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
            }
        }));
    }

    /**
     * 加载所有资源数据
     */
    protected void loadResources() {
        fixedThreadPool.execute(() -> resourceWorkOrderService.getTiaoXian(new CallBack<List<ResourceTypeBean>>() {
            @Override
            public void call(List<ResourceTypeBean> data) {
                basicData.setResources(data);
                latch.countDown();
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
            }
        }));
    }

    /**
     * 投诉性质
     */
    protected void loadComplainPropertys() {
        fixedThreadPool.execute(() -> dictService.getByTypeKey(Constants.COMPLAIN_WAY, new CallBack<List<DictDataModel>>() {
            @Override
            public void call(List<DictDataModel> data) {
                basicData.setComplainPropertys(data);
                latch.countDown();
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
            }
        }));
    }

    /**
     * 投诉性质
     */
    protected void loadComplainTypes() {
        fixedThreadPool.execute(() -> workOrderService.typeAndLineList(new CallBack<List<TypeAndLine>>() {
            @Override
            public void call(List<TypeAndLine> data) {
                basicData.setComplainTypes(data);
                latch.countDown();
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
            }
        }));
    }

    /**
     * 获取分期下面的网格信息
     * @param divideId
     * @param callBack
     */
    public void loadDivideGrid(String divideId, CallBack<DivideGrid> callBack) {
        DivideGrid divideGrid = basicData.getDivideGridMap().get(divideId);
        if (divideGrid == null||divideGrid.isEmpty()) {
            mdmService.gridPage(divideId, new CallBack<PageResult<GridModel>>() {
                @Override
                public void call(PageResult<GridModel> data) {
                    DivideGrid gridData=new DivideGrid(data);
                    basicData.getDivideGridMap().put(divideId,gridData);
                    if(callBack!=null){
                        callBack.call(gridData);
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    callBack.call(null);
                }
            });
        } else {
            if(callBack!=null){
                callBack.call(divideGrid);
            }
        }
    }

    /**
     * 获取报修区域
     */
    protected void loadRepairArea() {
        workOrderService.getAreaType(new CallBack<AreaModel>() {
            @Override
            public void call(AreaModel data) {
                latch.countDown();
                basicData.setRepairArea(data);
            }

            @Override
            public void onFaild(Throwable throwable) {
                latch.countDown();
            }
        });
    }


    /**
     * 获取缓存
     *
     * @return
     */
    public BasicData loadCache() {
        return basicData;
    }

    /**
     * 判断是否初始化有数据
     *
     * @return
     */
    public boolean hasInit() {
        if (basicData != null) {
            if (basicData.getLines() != null && basicData.getListLineTypes() != null & basicData.getResources() != null
            &&basicData.getComplainPropertys()!=null&&basicData.getComplainTypes()!=null) {
                return true;
            }
        }
        return false;
    }
}