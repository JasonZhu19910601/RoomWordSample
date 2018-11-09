package com.leading.roomwordsample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Zj
 * @package com.leading.roomwordsample
 * @fileName WordListAdapter
 * @date 2018/11/9 15:10
 * @describe TODO
 * @org Leading.com(北京理正软件)
 * @email 2856211755@qq.com
 * @computer Administrator
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mLayoutInflater;

    /**
     * Cached copy of words
     */
    private List<Word> mWords;

    public WordListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder wordViewHolder, int i) {
        if (mWords != null) {
            Word current = mWords.get(i);
            wordViewHolder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            wordViewHolder.wordItemView.setText("No Word");
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return mWords == null ? 0 : mWords.size();
    }

    public void setWords(List<Word> mWords) {
        this.mWords = mWords;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
