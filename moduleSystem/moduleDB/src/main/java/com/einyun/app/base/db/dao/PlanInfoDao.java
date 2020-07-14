package com.einyun.app.base.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.PlanInfo;
import com.einyun.app.base.db.entity.PlanLocal;

@Dao
public interface PlanInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlanInfo(PlanInfo info);
//
//
    @Query("SELECT * FROM PLAN_INFO WHERE id=:orderId and userId=:userId")
    PlanInfo load(String orderId, String userId);

    @Query("delete from plan_info where taskId=:orderId and userId=:userId")
    void deletePlanInfo(String orderId, String userId);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlanLocal(PlanLocal local);

    @Query("delete from plan_local where orderId=:orderId and userId=:userId")
    void deletePlanLocal(String orderId, String userId);

    @Query("SELECT * FROM PLAN_LOCAL WHERE orderId=:orderId and userId=:userId")
    PlanLocal loadPlanLocal(String orderId, String userId);

//    @Query("delete from patrols_info where id not in(:taskIds) and userId=:userId")
//    void sync(String userId, String... taskIds);
//
//    @Query("delete from patrol_local where orderId not in(:ids) and userId=:userId")

//    void syncLocal(String userId, String... ids);
}
