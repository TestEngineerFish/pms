package com.einyun.app.pms.patrol.repository;

import androidx.paging.DataSource;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.common.application.CommonApplication;

import java.util.List;

public class PatrolListRepo {
    PatrolDao patrolDao;
    AppDatabase db;

    public PatrolListRepo(){
        db=AppDatabase.getInstance(CommonApplication.getInstance());
        patrolDao = db.patrolDao();
    }

    /**
     * 分页查询
     * @param current
     * @param pageSize
     * @return
     */
    public List<Patrol> queryPage(int current, int pageSize) {
        return patrolDao.pageDigest(current,pageSize);
    }

    public DataSource.Factory<Integer,Patrol> queryAll(){
        return patrolDao.queryAll();
    }

    public void initData(List<Patrol> patrols){
        db.runInTransaction(() -> {
            patrolDao.deleteAll();
            patrolDao.insertDigest(patrols);
        });

    }
}
