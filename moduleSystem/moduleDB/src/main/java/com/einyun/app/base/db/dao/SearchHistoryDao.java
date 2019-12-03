package com.einyun.app.base.db.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.einyun.app.base.db.entity.SearchHistory;

import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Query("SELECT search_content FROM search_history WHERE type==:type ORDER BY update_time DESC LIMIT 10")
    LiveData<List<String>> loadAllSearchHistory(int type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSearchHistory(SearchHistory... searchHistories);

    @Update
    void updateSearchHistory(SearchHistory searchHistory);

    @Delete
    void deleteSearchHistory(SearchHistory searchHistory);

//    @Query("SELECT * FROM users WHERE user_name == :userName")
//    User selectUserByName(String userName);
}
