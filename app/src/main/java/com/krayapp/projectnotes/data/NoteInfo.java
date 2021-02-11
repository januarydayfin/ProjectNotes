package com.krayapp.projectnotes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteInfo implements Parcelable {
    private String title;
    private String description;
    private Date date;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public Date getDate() {
        return date;
    }


    public NoteInfo(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    protected NoteInfo(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date.getTime());
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


