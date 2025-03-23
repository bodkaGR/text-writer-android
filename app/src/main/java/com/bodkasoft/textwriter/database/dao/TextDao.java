package com.bodkasoft.textwriter.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bodkasoft.textwriter.database.entity.Text;

import java.util.List;

@Dao
public interface TextDao {
    @Query("SELECT * FROM text_data")
    LiveData<List<Text>> getAll();

    @Query("SELECT * FROM text_data WHERE id = :id")
    Text getById(int id);

    @Insert
    Long insert(Text textData);

    @Delete
    void delete(Text text);
}
