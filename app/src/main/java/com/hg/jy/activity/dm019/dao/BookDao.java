package com.hg.jy.activity.dm019.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hg.jy.activity.dm019.entity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(BookInfo... book);

    @Delete
    void delete(BookInfo... book);

    // 删除所有书籍信息
    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(BookInfo... book);

    // 加载所有书籍信息
    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    // 根据名字加载书籍
    @Query("SELECT * FROM BookInfo WHERE name = :name ORDER BY id DESC limit 1")
    BookInfo queryByName(String name);
}
