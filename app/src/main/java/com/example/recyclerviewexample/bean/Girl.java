package com.example.recyclerviewexample.bean;

import android.text.TextUtils;

import com.example.recyclerviewexample.utils.PinYinUtil;

public class Girl implements Comparable<Girl> {

    private String mName;
    private String mPinyin;

    public Girl(String mName) {
        this.mName = mName;
        this.mPinyin = PinYinUtil.toPinyin(mName);
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPinyin() {
        return mPinyin;
    }

    public void setmPinyin(String mPinyin) {
        this.mPinyin = mPinyin;
    }


    @Override
    public int compareTo(Girl o) {
        //不能传入null，会跑出nullexception，因为不能比较
        String name = o.mName;
        String pinyin = o.mPinyin;
        if (TextUtils.isEmpty(mPinyin)) {
            return mName.compareToIgnoreCase(TextUtils.isEmpty(pinyin) ? name : pinyin);
        } else {
            return mPinyin.compareToIgnoreCase(TextUtils.isEmpty(pinyin) ? name : pinyin);
        }
    }
}
