package com.einyun.app.base.db.dao;

import androidx.annotation.NonNull;
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

    @Query("delete from patrol_digests where userId=:userId and listType=:listType")
    void deleteAll(@NonNull String userId,int listType);

    @Query("delete from patrol_digests where ID_=:orderId and userId=:userId")
    void deletePatrol(@NonNull String orderId,String userId);

    @Query("select * from patrol_digests where userId=:userId ORDER BY F_creation_date DESC LIMIT :current,:pageSize")
    List<Patrol> pageDigest(int current, int pageSize,String userId);

    @Query("select * from patrol_digests where userId=:userId and listType=:listType")
    DataSource.Factory<Integer,Patrol> queryAll(@NonNull String userId, @NonNull int listType);


    @Query("update patrol_digests set isCached=1 where ID_=:orderId and userId=:userId")
    void updateCachedState(String orderId,String userId);

    @Query("update patrol_digests set isCached=1 where ID_ in (select id from patrols_info where userId=:userId) and userId=:userId")
    void updateCachedStates(@NonNull String userId);

    @Query("delete from patrol_digests where ID_ not in(:ids) and userId=:userId" )
    void sync(String userId,String... ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLocal(PatrolLocal local);
}
