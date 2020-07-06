package com.einyun.app.base.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;
import com.einyun.app.base.db.entity.PlanInfo;



import java.util.List;

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
//
//    @Query("delete from patrol_local where orderId=:orderId and userId=:userId")
//    void deletePatrolLocal(String orderId, String userId);
//
//    @Query("delete from patrols_info where id not in(:taskIds) and userId=:userId")
//    void sync(String userId, String... taskIds);
//
//    @Query("delete from patrol_local where orderId not in(:ids) and userId=:userId")
//    void syncLocal(String userId, String... ids);
//
//    @Query("SELECT * FROM PATROL_LOCAL WHERE orderId=:orderId and userId=:userId")
//    PatrolLocal loadByTaskId(String orderId, String userId);
}
