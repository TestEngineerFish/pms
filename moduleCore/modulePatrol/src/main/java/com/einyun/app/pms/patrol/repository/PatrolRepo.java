package com.einyun.app.pms.patrol.repository;

import androidx.lifecycle.LiveData;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.dao.PatrolInfoDao;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.CommonApplication;

public class PatrolRepo {
    PatrolDao dao;
    PatrolInfoDao infoDao;
    AppDatabase db;

    public PatrolRepo() {
        db = AppDatabase.getInstance(CommonApplication.getInstance());
        dao = db.patrolDao();
        infoDao = db.patrolInfoDao();
    }

    /**
     * 获取用户本地输入数据
     *
     * @param orderId
     * @return
     */
    public void loadLocalUserData(String orderId,String userId, CallBack<PatrolLocal> callBack) {
        db.runInTransaction(() -> {
            PatrolLocal local = infoDao.loadByTaskId(orderId,userId);
            callBack.call(local);
        });
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
                dao.deletePatrol(orderId,userId);
                infoDao.deletePatrolInfo(orderId,userId);
                infoDao.deletePatrolLocal(orderId,userId);
                callBack.call(true);
            }
        });
    }

    public void saveLocalData(PatrolLocal local) {
        db.runInTransaction(() -> dao.insertLocal(local));
    }

    /**
     * 插入巡查详情
     *
     * @param info
     */
    public void insertPatrolInfo(PatrolInfo info) {
        infoDao.insertPatrolInfo(info);
    }

    /**
     * 保存巡查内容
     *
     * @param info
     */
    public void savePatrolInfo(PatrolInfo info) {
        infoDao.insertPatrolInfo(info);
    }

    /**
     * 获取本地巡查详情
     *
     * @param orderId
     * @return
     */
    public PatrolInfo loadPatrolInfo(String orderId,String userId) {
        return infoDao.load(orderId,userId);
    }

    /**
     * 更新缓存状态为已缓存
     *
     * @param orderId
     */
    public void updatePatrolCached(String orderId,String userId) {
        dao.updateCachedState(orderId,userId);
    }
}
