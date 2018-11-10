package com.leading.roomwordsample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * @author Zj
 * @package com.leading.roomwordsample
 * @fileName WordRoomDatabase
 * @date 2018/11/9 10:39
 * @describe TODO
 * @org Leading.com(北京理正软件)
 * @email 2856211755@qq.com
 * @computer Administrator
 * <p>
 * 什么是Room数据库？
 * Room是在SQLite之上的数据库层。Room用于处理我们曾经用SQLiteOpenHelper来处理任务。
 * <p>
 * Room通过DAO向数据库发送查询
 * 默认情况下，为了避免降低UI线程的性能，Room不允许在主线程中执行数据库操作
 * Room提供了编译时的SQL语句检查
 * 创建的Room类必须是抽象的，并且继承RoomDatabase
 * 通常，在整体应用中只需要一个Room数据库实例，即单例。
 * 实现Room数据库
 * 创建一个public abstract类WordRoomDatabase，并继承RoomDatabase。即public abstract class WordRoomDatabase extends RoomDatabase {}
 * 标注其为一个Room数据库，@Database(entities = {Word.class}, version = 1)，声明其在数据库中的实体，并指定版本号。实体可以声明多个，声明的实体将在数据库中创建对应的表。
 * 定义使用数据库的DAO。给每一个@Dao提供get方法。public abstract WordDao wordDao();
 */
@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    // 使WordRoomDatabase作为单例   volatile /'vɒlətaɪl/
    private static volatile WordRoomDatabase INSTANCE;

    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WordDao wordDao();

    /**
     * 由于不能对UI线程执行Room数据库操作，因此onOpen()创建并执行AsyncTask来向数据库添加内容
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * 它删除数据库的内容，然后用两个单词“Hello”和“World”填充数据库
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao mDao;

        public PopulateDbAsync(WordRoomDatabase wordRoomDatabase) {
            this.mDao = wordRoomDatabase.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }
}
