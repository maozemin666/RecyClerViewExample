package com.example.recyclerviewexample.designmode.proxy;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/10/8 14:59
 * @Created by zemin
 */
public class VarietyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Proxy<Object, RecyclerView.ViewHolder>> proxyList = new ArrayList<>();

    private final List<Object> dataList = new ArrayList<>();

    public <T, VH extends RecyclerView.ViewHolder> void addProxy(Proxy<T, VH> proxy) {
        proxyList.add((Proxy<Object, RecyclerView.ViewHolder>) proxy);
    }

    public <T, VH extends RecyclerView.ViewHolder> void removeProxy(Proxy<T, VH> proxy) {
        proxyList.remove(proxy);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return proxyList.get(viewType).onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        proxyList.get(getItemViewType(position)).onBindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getProxyIndex(dataList.get(position));
    }

    private int getProxyIndex(Object o) {
        for (int i = 0; i < proxyList.size(); i++) {
            Proxy<?, ?> proxy = proxyList.get(i);
            Type type = proxy.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                String firstTypeParamClassName = ((ParameterizedType) type).getActualTypeArguments()[0].toString();
                String proxyName = proxy.getClass().toString();

                String dataProxyName = "";
                if (o instanceof DataProxyMap) {
                    dataProxyName = ((DataProxyMap)o).toProxy();
                }
                // 首要匹配条件：代理类第一个类型参数和数据类型相同
                // 次要匹配条件：数据类自定义匹配代理名和当前代理名相同
                if (TextUtils.equals(o.getClass().toString(), firstTypeParamClassName)
                        && TextUtils.equals(dataProxyName, proxyName)) {
                    return i;
                }
            }
        }
        return -1;
    }

    // image, imageViewHolder
    public static abstract class Proxy<T, VH extends RecyclerView.ViewHolder> {
        abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        abstract void onBindViewHolder(VH holder, T data, int position);
    }

    // 数据和代理的对应关系
    public interface DataProxyMap {
        /**
         *
         * @return 返回对应proxy的类名
         */
        String toProxy();
    }
}
