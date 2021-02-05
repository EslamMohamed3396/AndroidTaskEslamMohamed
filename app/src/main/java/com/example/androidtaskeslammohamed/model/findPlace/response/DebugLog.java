
package com.example.androidtaskeslammohamed.model.findPlace.response;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebugLog implements Parcelable
{

    @SerializedName("line")
    @Expose
    private List<Object> line = new ArrayList<Object>();
    public final static Creator<DebugLog> CREATOR = new Creator<DebugLog>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DebugLog createFromParcel(Parcel in) {
            return new DebugLog(in);
        }

        public DebugLog[] newArray(int size) {
            return (new DebugLog[size]);
        }

    }
    ;

    protected DebugLog(Parcel in) {
        in.readList(this.line, (Object.class.getClassLoader()));
    }

    public DebugLog() {
    }

    public List<Object> getLine() {
        return line;
    }

    public void setLine(List<Object> line) {
        this.line = line;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(line);
    }

    public int describeContents() {
        return  0;
    }

}
