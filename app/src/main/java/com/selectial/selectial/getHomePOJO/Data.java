package com.selectial.selectial.getHomePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("package")
    @Expose
    private List<Package> _package = null;
    @SerializedName("scholarship")
    @Expose
    private List<Scholarship> scholarship = null;
    @SerializedName("sucjects")
    @Expose
    private List<Sucject> sucjects = null;

    public List<Package> getPackage() {
        return _package;
    }

    public void setPackage(List<Package> _package) {
        this._package = _package;
    }

    public List<Scholarship> getScholarship() {
        return scholarship;
    }

    public void setScholarship(List<Scholarship> scholarship) {
        this.scholarship = scholarship;
    }

    public List<Sucject> getSucjects() {
        return sucjects;
    }

    public void setSucjects(List<Sucject> sucjects) {
        this.sucjects = sucjects;
    }
}
