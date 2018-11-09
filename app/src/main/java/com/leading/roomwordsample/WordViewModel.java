package com.leading.roomwordsample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Zj
 * @package com.leading.roomwordsample
 * @fileName WordViewModel
 * @date 2018/11/9 11:39
 * @describe TODO
 * @org Leading.com(北京理正软件)
 * @email 2856211755@qq.com
 * @computer Administrator
 */
public class WordViewModel extends AndroidViewModel {

    /**
     * 添加一个私有成员变量来保存对存储库的引用
     */
    private WordRepository mRepository;
    /**
     * 添加一个私有LiveData成员变量来缓存单词列表
     */
    private LiveData<List<Word>> mAllWords;

    /**
     * 添加一个构造函数，该构造函数获取对存储库的引用，并从存储库获取单词列表。
     *
     * @param application
     */
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    /**
     * 为所有单词添加一个get方法。这完全隐藏了对UI的实现
     *
     * @return
     */
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * 创建一个调用Repository的insert()方法的包装器insert()方法。这样，insert()的实现对于UI就完全透明了
     *
     * @param word
     */
    public void insert(Word word) {
        mRepository.insert(word);
    }
}
