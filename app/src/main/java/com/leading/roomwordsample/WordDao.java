package com.leading.roomwordsample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * @author Zj
 * @package com.leading.roomwordsample
 * @fileName WordDao
 * @date 2018/11/9 10:20
 * @describe TODO
 * @org Leading.com(北京理正软件)
 * @email 2856211755@qq.com
 * @computer Administrator
 *
 * DAO是代码的基础，它用于提供word的增、删、改、查。
 *
 * 创建一个名为WordDao的接口。
 * 为WordDao添加@Dao注解
 * 声明一个插入方法void insert(Word word);
 * 为上述方法添加@Insert注解，并且不需要为其提供SQL语句！（同样的用法还有@Delete and @Update）
 * 声明方法void deleteAll();
 * 这里没有方便的注解可以用于删除多个实体，因此需要用@Query注解
 * 还需要为@Query注解提供SQL语句@Query("DELETE FROM word_table")
 * 创建方法List<Word> getAllWords();
 * 在WordDao中，改变getAllWords()方法的返回值：
 * 为其添加注解与SQL@Query("SELECT * from word_table ORDER BY word ASC")
 */
@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Query("DELETE FROM WORD_TABLE")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}
