package com.selectial.selectial.getScholarshipPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getScholarshipBean {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("trial")
    @Expose
    private List<Trial> trial = null;
    @SerializedName("scholarship")
    @Expose
    private List<Scholarship> scholarship = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Trial> getTrial() {
        return trial;
    }

    public void setTrial(List<Trial> trial) {
        this.trial = trial;
    }

    public List<Scholarship> getScholarship() {
        return scholarship;
    }

    public void setScholarship(List<Scholarship> scholarship) {
        this.scholarship = scholarship;
    }
}
