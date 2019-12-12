package com.einyun.app.base.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolLocal;

import java.util.List;

@Dao
public interface PatrolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDigest(List<Patrol> list);

    @Query("delete from patrol_digests where userId=:userId")
    void deleteAll(String userId);

    @Query("delete from patrol_digests where taskId=:taskId and userId=:userId")
    void deletePatrol(String taskId,String userId);

    @Query("select * from patrol_digests where userId=:userId ORDER BY F_creation_date DESC LIMIT :current,:pageSize")
    List<Patrol> pageDigest(int current, int pageSize,String userId);

    @Query("select * from patrol_digests where userId=:userId")
    DataSource.Factory<Integer,Patrol> queryAll(String userId);

    @Query("update patrol_digests set isCached=1 where taskId=:taskId and userId=:userId")
    void updateCachedState(String taskId,String userId);

    @Query("update patrol_digests set isCached=1 where taskId in (select taskId from patrols_info) and userId=:userId")
    void updateCachedStates(String userId);

    @Query("delete from patrol_digests where taskId not in(:taskIds) and userId=:userId" )
    void sync(String userId,String... taskIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocal(PatrolLocal local);
}
