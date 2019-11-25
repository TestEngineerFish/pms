package com.einyun.app.base.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.einyun.app.base.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    //demo
    @Query("SELECT * from users ORDER BY update_time DESC LIMIT 5")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT user_name FROM users ORDER BY update_time DESC LIMIT 5")
    List<String> loadAllUserName();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users WHERE user_name == :userName")
    User selectUserByName(String userName);

    @Query("SELECT * FROM users ORDER BY update_time DESC LIMIT 1 ")
    User selectUserLastUpdate();
}
