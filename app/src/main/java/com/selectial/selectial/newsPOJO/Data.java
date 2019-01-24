package com.selectial.selectial.newsPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("scholarship")
    @Expose
    private List<Scholarship> scholarship = null;

    public List<Scholarship> getScholarship() {
        return scholarship;
    }

    public void setScholarship(List<Scholarship> scholarship) {
        this.scholarship = scholarship;
    }
}
