package com.example.recyclerviewexample.android.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.recyclerviewexample.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity  extends AppCompatActivity {
    private static final String TAG = "maomao1";
    
    private List<String> mDatas;
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;

    private Button mOnclickButton;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_single_recyclerview);
        initViews();
        initData();
        mRecyclerView = findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHomeAdapter = new HomeAdapter());
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void initViews() {
        mOnclickButton = findViewById(R.id.btn_onclick);
        final Intent intent = new Intent(this,SecondActivity.class);
        mOnclickButton.setOnClickListener(v -> HomeActivity.this.startActivity(intent));
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

        @NonNull
        @Override
        public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ViewGroup itemView = (ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_home,null);
            HomeViewHolder homeViewHolder = new HomeViewHolder(itemView);
            return homeViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, int i) {
            homeViewHolder.tv.setText(mDatas.get(i));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    
        private class HomeViewHolder extends RecyclerView.ViewHolder {
            private TextView tv;

            public HomeViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.id_num);
            }
        }

        @Override
        public void onViewRecycled(@NonNull HomeViewHolder holder) {
            super.onViewRecycled(holder);
        }
    }
}
