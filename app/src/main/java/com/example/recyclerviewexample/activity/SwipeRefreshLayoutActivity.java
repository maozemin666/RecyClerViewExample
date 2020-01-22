package com.example.recyclerviewexample.activity;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recyclerviewexample.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private SwipeRefreshLayout swiperefreshlayout;
    private List<Integer> mData = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiperefreshlayout);
        initView();
    }

    private void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        swiperefreshlayout = findViewById(R.id.swiperefreshlayout);
    }


    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static final int FOOT = 0;
        public static final int CONTENT = 1;
        private LayoutInflater inflate;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            inflate = LayoutInflater.from(viewGroup.getContext());
            if (viewType == FOOT) {
                View foot = inflate.inflate(R.layout.swipe_foot_list_item, null);
                return new FootViewHolder(foot);
            }
            View content = inflate.inflate(R.layout.swipe_content_list_item, null);
            return new COntentViewHolder(content);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            int viewType = getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mData.size() + 1;

        }

        @Override
        public int getItemViewType(int position) {
            if (mData.size() == position) {
                return FOOT;
            }
            return CONTENT;
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pb_progress);
        }
    }

    private class COntentViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public COntentViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textItem);
        }
    }
}
