package com.ecocustomerapp.data.local.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ecocustomerapp.data.model.api.Entities;

@Dao
public interface EntityDao {

    @Delete
    void delete(Entities entities);

    @Query("DELETE FROM entities")
    void deleteAllEntity();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Entities entities);

    @Query("SELECT * FROM entities")
    Entities loadAll();
}
