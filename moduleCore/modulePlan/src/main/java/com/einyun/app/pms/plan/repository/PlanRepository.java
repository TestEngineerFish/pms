package com.einyun.app.pms.plan.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.dao.PatrolInfoDao;
import com.einyun.app.base.db.dao.PlanDao;
import com.einyun.app.base.db.dao.PlanInfoDao;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.db.entity.Plan;
import com.einyun.app.base.db.entity.PlanInfo;
import com.einyun.app.base.db.entity.PlanLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.repository.DatabaseRepo;
import com.einyun.app.library.core.net.EinyunHttpService;

import java.util.List;

public class PlanRepository extends DatabaseRepo<Plan> {
    PlanDao dao;
    PlanInfoDao infoDao;
    AppDatabase db;
    public PlanRepository() {
        db = AppDatabase.getInstance(CommonApplication.getInstance());
        dao = db.planDao();
        infoDao = db.planInfoDao();
    }
    @Override
    public void deleteAll(String userId,int listType) {
        dao.deleteAll(userId,listType);
    }

    @Override
    public void insert(List<Plan> rows) {
        dao.insert(rows);
    }

    /**
     * 计划工单详情插入
     */
    public void insertPlanInfo(PlanInfo rows) {
        infoDao.insertPlanInfo(rows);
    }
    /**
     * 计划工单详情加载
     */
    public PlanInfo loadPlanInfo(String orderId,String userId) {
       return infoDao.load(orderId,userId);
    }
    /**
     * 计划工单详情缓存删除
     */
    public void deletePlanInfo(String orderId,String userId) {
        infoDao.deletePlanInfo(orderId,userId);
    }
    /**
     * 计划工单本地输入插入
     */
    public void insertPlanLocal(PlanLocal rows) {
        infoDao.insertPlanLocal(rows);
    }
    /**
     * 计划工单本地输入加载
     */
    public void loadPlanLocal(String orderId, String userId, CallBack<PlanLocal> callBack) {
        db.runInTransaction(() -> {
            PlanLocal local = infoDao.loadPlanLocal(orderId,userId);
            callBack.call(local);
        });
//        return infoDao.loadPlanLocal(orderId,userId);
    }
    /**
     * 计划工单详情本地输入shanchu
     */
    public void deletePlanLocal(String orderId,String userId) {
        infoDao.deletePlanLocal(orderId,userId);
    }
    /**
     * 更新缓存状态为已缓存
     *
     * @param orderId
     */
    public void updatePlanCached(String orderId,String userId) {
        dao.updateCachedState(orderId,userId);
    }
    /**
     * 删除任务，完成关闭
     *
     * @param orderId
     */
    public void deleteTask(String orderId,String userId,CallBack<Boolean> callBack) {
        db.runInTransaction(new Runnable() {
            @Override
            public void run() {
                infoDao.deletePlanLocal(orderId,userId);
                infoDao.deletePlanLocal(orderId,userId);
                callBack.call(true);
            }
        });
    }
    @Override
    public DataSource.Factory<Integer, Plan> queryAll(@NonNull String userId,int listType) {
        return dao.queryAll(userId,listType);
    }
}
