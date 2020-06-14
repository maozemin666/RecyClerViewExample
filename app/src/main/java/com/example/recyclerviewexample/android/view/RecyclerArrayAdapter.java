package com.example.recyclerviewexample.android.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerArrayAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<InterItemView> headers = new ArrayList<>();
    private List<InterItemView> footers = new ArrayList<>();
    private List<T> mObjects = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if (headers.size() != 0) {
            if (position < headers.size()) {
                return headers.get(position).hashCode();
            }
        }
        if (footers.size() != 0) {
            position = position - headers.size() - mObjects.size();
            if (position >= 0) {
                return footers.get(position).hashCode();
            }
        }
        return getViewType(position - headers.size());
    }

    private int getViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = onCreateViewByType(viewGroup, viewType);
        if (view != null) {
            return new BaseViewHolder(view);
        }
        final BaseViewHolder baseViewHolder = OnCreateViewHolder(viewGroup, viewType);
        return baseViewHolder;
    }

    public abstract BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int viewType);

    private View onCreateViewByType(ViewGroup parent, int viewType) {
        if (headers.size() > 0) {
            for (InterItemView header : headers) {
                if (header.hashCode() == viewType) {
                    return header.onCreateView(parent);
                }
            }
        }
        if (footers.size() > 0) {
            for (InterItemView footer : footers) {
                if (footer.hashCode() == viewType) {
                    return footer.onCreateView(parent);
                }
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return headers.size() + footers.size() + mObjects.size();
    }

    public int getCount() {
        return mObjects.size();
    }
}
