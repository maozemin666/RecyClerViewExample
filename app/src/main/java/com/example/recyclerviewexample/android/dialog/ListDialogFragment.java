package com.example.recyclerviewexample.android.dialog;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewexample.R;

import java.util.List;

/**
 * @Date 2020/10/13 22:40
 * @Created by zemin
 */
public class ListDialogFragment extends DialogFragment {
    private View rootView;
    private String title;
    private List<String> data;

    private OnSelectListener onSelectListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.center_impl_list, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView titleTextView = view.findViewById(R.id.tv_title);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        if (!TextUtils.isEmpty(title)) {
            titleTextView.setText(title);
        }
        ListDialogAdapter listDialogAdapter = new ListDialogAdapter(data);
        listDialogAdapter.setOnItemClickListener((itemView, viewHolder, position) -> {
            if (onSelectListener != null) {
                if (position >= 0 && position < listDialogAdapter.getData().size()) {
                    onSelectListener.onSelect(position, listDialogAdapter.getData().get(position));
                }
            }
            dismiss();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(listDialogAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(Color.parseColor("#EEEEEE"));
        drawable.setSize(10, dp2Px(0.4f));
        decoration.setDrawable(drawable);
        recyclerView.addItemDecoration(decoration);
    }

    public ListDialogFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public ListDialogFragment setData(List<String> data) {
        this.data = data;
        return this;
    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    private int dp2Px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    public interface OnSelectListener {
        void onSelect(int position, String text);
    }
}
