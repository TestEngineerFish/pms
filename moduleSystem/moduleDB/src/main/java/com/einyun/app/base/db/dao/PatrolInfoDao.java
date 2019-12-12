package com.einyun.app.base.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.db.entity.PatrolLocal;

import java.util.List;

@Dao
public interface PatrolInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatrolInfo(PatrolInfo info);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatrolInfos(List<PatrolInfo> info);

    @Query("SELECT * FROM PATROLS_INFO WHERE taskId=:taskId and userId=:userId")
    PatrolInfo load(String taskId,String userId);

    @Query("delete from patrols_info where taskId=:taskId and userId=:userId")
    void deletePatrolInfo(String taskId,String userId);

    @Query("delete from patrol_local where taskId=:taskId and userId=:userId")
    void deletePatrolLocal(String taskId,String userId);

    @Query("delete from patrols_info where taskId not in(:taskIds) and userId=:userId")
    void sync(String userId,String...taskIds);

    @Query("delete from patrol_local where taskId not in(:taskIds) and userId=:userId")
    void syncLocal(String userId,String... taskIds);

    @Query("SELECT * FROM PATROL_LOCAL WHERE taskId=:taskId and userId=:userId")
    PatrolLocal loadByTaskId(String taskId,String userId);
}
