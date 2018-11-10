package com.leading.roomwordsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 要显示数据库的当前内容，添加一个观察者来观察ViewModel中的LiveData。
     * 每当数据更改时，都会调用onchange()回调，该回调调用适配器的setWord()方法，以更新适配器的缓存数据并刷新显示的列表。
     * <p>
     * 在MainActivity中，为ViewModel创建一个成员变量：
     */
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter wordListAdapter = new WordListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordListAdapter);

        // 从ViewModelProvider获取一个ViewModel
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        // 为getAllWords()返回的LiveData添加一个观察者。
        // 当观察到的数据发生变化且activity 位于前台时，onchange()方法就会调用。
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                // Update the cached copy of the words in the adapter.
                wordListAdapter.setWords(words);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 为NewWordActivity添加onActivityResult()代码。
    // 如果activity返回RESULT_OK，则通过调用WordViewModel的insert()方法将返回的单词插入数据库。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }

    }

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    /**
     * 现在你有了一个实用的应用程序，让我们回顾一下你已经构建了什么。这是最开发的应用程序的结构。
     * 您有一个在列表中显示单词的应用程序(MainActivity、ReccyclerView、WordListAdapter)。
     * 您可以向列表中添加单词(NewWordActivity)。
     * 单词是单词实体类的实例。
     * 这些单词作为单词(mWords) List缓存在RecyclerViewAdapter中。当数据库中的单词更改时，此单词列表会自动更新和重新显示。
     *
     * 用于自动UI更新的数据流(反应性UI)
     * 自动更新是可能的，因为我们正在使用LiveData。在MainActivity中，有一个观察者从数据库中观察到LiveData这个词，并在它们更改时得到通知。当发生更改时，将执行观察者的onChange()方法，并更新WordListAdapter中的mWord。
     *
     * 可以观察到数据，因为它是LiveData。观察到的是由WordViewModel对象的getAllWords()方法返回的LiveData<list<word>>。
     *
     * WordViewModel从UI层隐藏关于后端的所有内容。它提供访问数据层的方法，并返回LiveData，以便MainActivity可以设置观察者关系。Activities(和Fragments)仅通过ViewModel与数据交互。因此，数据从何而来并不重要。
     *
     * 在这个demo，数据来自一个存储库。ViewModel不需要知道这个仓库与什么交互。它只需要知道如何通过Repository公开的方法与Repository交互。
     *
     * Repository 管理一个或多个数据源。在WordListSample应用程序中，后端是一个Room数据库。Room是一个包装器，实现了SQLite数据库。Room为你做了很多工作，你以前不得不自己做。例如，Room完成了以前使用SQLiteOpenHelper类所做的一切。
     *
     * DAO映射方法调用数据库查询，以便当Repository调用getAllWords()等方法时，Room可以通过执行SELECT * from word_table ORDER BY word ASC。
     *
     * 因为从查询返回的结果被观察到LiveData，所以每当Room中的数据发生变化时，会执行观察者接口的onChanged()方法，并更新UI。
     */
}
