package com.einyun.app.pms.patrol.repository;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.dao.PatrolInfoDao;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.common.application.CommonApplication;

public class PatrolRepo {
    PatrolDao dao;
    PatrolInfoDao infoDao;

    public PatrolRepo(){
        dao= AppDatabase.getInstance(CommonApplication.getInstance()).patrolDao();
        infoDao=AppDatabase.getInstance(CommonApplication.getInstance()).patrolInfoDao();
    }

    /**
     * 插入巡查详情
     * @param info
     */
    public void insertPatrolInfo(PatrolInfo info){
        infoDao.insertPatrolInfo(info);
    }

    /**
     * 保存巡查内容
     * @param info
     */
    public void savePatrolInfo(PatrolInfo info){
        infoDao.insertPatrolInfo(info);
    }

    /**
     * 获取本地巡查详情
     * @param taskId
     * @return
     */
    public PatrolInfo loadPatrolInfo(String taskId){
        return infoDao.load(taskId);
    }

    /**
     * 更新缓存状态为已缓存
     * @param taskId
     */
    public void updatePatrolCached(String taskId){
        dao.updateCachedState(taskId);
    }
}
