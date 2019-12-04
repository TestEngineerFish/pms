package com.einyun.app.base.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;

import java.util.List;

@Dao
public interface PatrolInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatrolInfo(PatrolInfo info);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatrolInfos(List<PatrolInfo> info);

    @Query("SELECT * FROM PATROLS_INFO WHERE taskId=:taskId")
    PatrolInfo load(String taskId);
}
