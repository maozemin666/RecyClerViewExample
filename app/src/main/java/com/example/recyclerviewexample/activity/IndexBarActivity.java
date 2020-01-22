package com.example.recyclerviewexample.activity;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.bean.Girl;
import com.example.recyclerviewexample.bean.Girls;
import com.example.recyclerviewexample.customveiw.IndexBar;
import com.example.recyclerviewexample.utils.DigitalUtil;
import com.lovejjfg.powerrecycle.PowerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndexBarActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private TextView mTvLetter;
    private List<Girl> mPersons = new ArrayList<>();
    private List<String> mLetters = new ArrayList<>();

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_bar);

        mRv = findViewById(R.id.rv);
        IndexBar indexBar = findViewById(R.id.index_bar);
        mTvLetter = findViewById(R.id.tv_letter);

        fillNameAndSort();
        indexBar.setLetters(mLetters);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setHasFixedSize(true);
        IndexApapter indexApapter = new IndexApapter();
        indexApapter.setList(mPersons);
        mRv.setAdapter(indexApapter);

        indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(int position, String letter) {
                showTextView(letter);

                if (letter.equalsIgnoreCase("#")) {
                    mRv.smoothScrollToPosition(0);
                    return;
                }

                for (int i = 0; i < mPersons.size(); i++) {
                    Girl girl = mPersons.get(i);
                    String pinyin = girl.getmPinyin();
                    String name = girl.getmName();
                    String firstPinyin =  String.valueOf(TextUtils.isEmpty(pinyin) ? name.charAt(0) : pinyin.charAt(0));
                    if (firstPinyin.equalsIgnoreCase(letter)) {
                        mRv.smoothScrollToPosition(i);
                        return;
                    }
                }
            }
        });

    }

    private void showTextView(String letter) {
        mTvLetter.setVisibility(View.VISIBLE);
        mTvLetter.setText(letter);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(() -> {
            mTvLetter.setVisibility(View.INVISIBLE);
        },600);
    }

    private void fillNameAndSort() {
        for (int i = 0; i < Girls.NAMES.length; i++) {
            Girl girl = new Girl(Girls.NAMES[i]);
            mPersons.add(girl);

            if (DigitalUtil.isDigital(girl.getmName())) {
                if (!mLetters.contains("#")) {
                    mLetters.add("#");
                }
                continue;
            }

            String letter;
            if (!TextUtils.isEmpty(girl.getmPinyin())) {
                letter = girl.getmPinyin().substring(0, 1).toUpperCase();
            } else {
                letter = girl.getmName().substring(0, 1).toUpperCase();
            }
            if (!mLetters.contains(letter)) {
                mLetters.add(letter);
            }

            Collections.sort(mPersons);
            Collections.sort(mLetters);
        }
    }

    static class IndexApapter extends PowerAdapter<Girl> {

        @Override
        public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.index_list_item, parent, false);
            return new IndexViewHolder(viewGroup);
        }

        @Override
        public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
            ((IndexViewHolder) holder).onBind(list, position);
        }
    }

    static class IndexViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvPinyin;
        private final TextView mTvName;

        public IndexViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvPinyin = itemView.findViewById(R.id.tv_pinyin);
            mTvName = itemView.findViewById(R.id.tv_name);
        }

        public void onBind(List<Girl> list, int position) {
            Girl girl = list.get(position);

            String pinyin = girl.getmPinyin();
            String name = girl.getmName();

            String firstPinyin = String.valueOf(TextUtils.isEmpty(pinyin) ? name.charAt(0) : pinyin.charAt(0));
            String preFirstPinyin;
            if (position == 0) {
                preFirstPinyin = "-";
            } else {
                Girl preGirl = list.get(position-1);
                String prePinyin = preGirl.getmPinyin();
                String preName = preGirl.getmName();
                preFirstPinyin = String.valueOf(TextUtils.isEmpty(pinyin) ? name.charAt(0) : pinyin.charAt(0));
            }

            if (DigitalUtil.isDigital(firstPinyin)) {
                firstPinyin ="#";
            }
            if (DigitalUtil.isDigital(preFirstPinyin)) {
                preFirstPinyin ="#";
            }

            mTvPinyin.setVisibility(firstPinyin.equalsIgnoreCase(preFirstPinyin) ? View.GONE:View.VISIBLE);
            if (mTvPinyin.getVisibility()!= View.GONE) {
                mTvPinyin.setText(firstPinyin);
            }
            mTvName.setText(name);
        }
    }
}
