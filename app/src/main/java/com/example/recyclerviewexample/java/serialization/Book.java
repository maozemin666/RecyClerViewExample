package com.example.recyclerviewexample.java.serialization;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public abstract class Book implements Parcelable {
    private int id;
    protected String name;
    public List<Book> mBooks;

    public Book() {
    }

    protected Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mBooks = in.createTypedArrayList(Book.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(mBooks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in) {
                @Override
                void Job() {

                }
            };
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    abstract void Job();

    public static class Father extends Book {

        protected Father(Parcel in) {
            super(in);
        }

        @Override
        void Job() {

        }
    }

}
