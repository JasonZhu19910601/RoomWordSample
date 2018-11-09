package com.leading.roomwordsample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Zj
 * @package com.leading.roomwordsample
 * @fileName Word
 * @date 2018/11/9 10:13
 * @describe 创建一个Word类，并为其创建构造函数与必要的get方法。这样Room才可以实例化对象。
 * @org Leading.com(北京理正软件)
 * @email 2856211755@qq.com
 * @computer Administrator
 *
 *
 * 为了使Word类对Room库有意义，我们需要为它加注解。注解用了将实体与数据库相关联，Room根据相应的注解信息去生成对应的代码。
 *
 * @Entity(tableName = "word_table") 每一个@Entity类代表数据库中的一张表。tableName为生成表的表名。
 * @PrimaryKey 每个实体需要一个主键。
 * @NonNull 表示参数、字段或返回值不能为null。
 * @ColumnInfo(name = "word") 指定与成员变量对应的列名。
 * 为一个字段需要是public的或提供get方法。
 */
@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {
        this.mWord = word;
    }

    public String getWord() {
        return mWord;
    }
}
