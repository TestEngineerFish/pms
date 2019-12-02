package com.einyun.app.base.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.einyun.app.base.db.entity.Patrol;

import java.util.List;

@Dao
public interface PatrolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDigest(List<Patrol> list);

    @Query("delete from patrol_digests ")
    void deleteAll();

    @Query("select * from patrol_digests ORDER BY F_creation_date DESC LIMIT :current,:pageSize")
    List<Patrol> pageDigest(int current, int pageSize);

    @Query("select * from patrol_digests ")
    DataSource.Factory<Integer,Patrol> queryAll();

    @Query("update patrol_digests set isCached=1 where taskId=:taskId")
    void updateCachedState(String taskId);
}
