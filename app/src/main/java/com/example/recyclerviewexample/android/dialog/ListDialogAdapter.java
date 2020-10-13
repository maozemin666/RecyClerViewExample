package com.example.recyclerviewexample.android.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/10/13 23:12
 * @Created by zemin
 */
public class ListDialogAdapter extends RecyclerView.Adapter {

    private List<String> data;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ListDialogAdapter(List<String> data) {
        this.data = (data == null ? new ArrayList<>() : data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_text_match, parent, false);
        return new ListDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView text = holder.itemView.findViewById(R.id.tv_text);
        text.setText(data.get(position));

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.itemView, holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<String> getData() {
        return data;
    }

    static class ListDialogViewHolder extends RecyclerView.ViewHolder {
        public ListDialogViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position);
    }
}
