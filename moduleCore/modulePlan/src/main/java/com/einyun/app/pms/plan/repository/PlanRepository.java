package com.einyun.app.pms.plan.repository;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.einyun.app.base.db.dao.PlanDao;
import com.einyun.app.base.db.entity.Plan;
import com.einyun.app.common.repository.DatabaseRepo;

import java.util.List;

public class PlanRepository extends DatabaseRepo<Plan> {
    PlanDao dao=getDb().planDao();

    @Override
    public void deleteAll(String userId,int listType) {
        dao.deleteAll(userId,listType);
    }

    @Override
    public void insert(List<Plan> rows) {
        dao.insert(rows);
    }

    @Override
    public DataSource.Factory<Integer, Plan> queryAll(@NonNull String userId,int listType) {
        return dao.queryAll(userId,listType);
    }
}
