
package com.example.androidtaskeslammohamed.model.findPlace.response;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFindPlaces implements Parcelable
{

    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates = new ArrayList<Candidate>();
    @SerializedName("debug_log")
    @Expose
    private DebugLog debugLog;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<ResponseFindPlaces> CREATOR = new Creator<ResponseFindPlaces>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseFindPlaces createFromParcel(Parcel in) {
            return new ResponseFindPlaces(in);
        }

        public ResponseFindPlaces[] newArray(int size) {
            return (new ResponseFindPlaces[size]);
        }

    }
    ;

    protected ResponseFindPlaces(Parcel in) {
        in.readList(this.candidates, (Candidate.class.getClassLoader()));
        this.debugLog = ((DebugLog) in.readValue((DebugLog.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ResponseFindPlaces() {
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public DebugLog getDebugLog() {
        return debugLog;
    }

    public void setDebugLog(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(candidates);
        dest.writeValue(debugLog);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
