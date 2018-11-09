package com.leading.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * @author Zj
 * @package com.leading.roomwordsample
 * @fileName WordRepository
 * @date 2018/11/9 11:14
 * @describe TODO
 * @org Leading.com(北京理正软件)
 * @email 2856211755@qq.com
 * @computer Administrator
 *
 * Repository是一个可访问多数据源的类。
 * 它并非构架组件库中的一部分，但它是代码分离和体系结构的最佳实践建议。Repository用于处理数据操作，它为应用提供数据访问接口。
 *
 * Repository管理查询线程，并允许您使用多个后端。
 * 在最常见的示例中，Repository实现了决定是从网络获取数据还是从本地缓存中获取结果的逻辑。
 */
public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    /**
     * 为getAllWords()添加一个包装器。Room在单独的线程上执行所有查询。观察到LiveData数据更改时，将通知观察者。
     * @return
     */
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * 为insert()方法添加一个包装器。使用AsyncTask来执行，确保其是在非UI线程中执行。
     * @param word
     */
    public void insert(Word word) {
        new InsertAsyncTask(mWordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        public InsertAsyncTask(WordDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
