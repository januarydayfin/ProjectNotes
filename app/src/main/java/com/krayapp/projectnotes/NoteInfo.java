package com.krayapp.projectnotes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteInfo implements Parcelable {
    private String title;
    private String description;
    private String date;
    private final int index;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public NoteInfo(String title, String description, String date, int index) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.index = index;
    }

    protected NoteInfo(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
        index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeInt(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteInfo> CREATOR = new Creator<NoteInfo>() {
        @Override
        public NoteInfo createFromParcel(Parcel in) {
            return new NoteInfo(in);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };
}


