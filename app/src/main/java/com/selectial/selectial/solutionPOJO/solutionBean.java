package com.selectial.selectial.solutionPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class solutionBean {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("correct")
    @Expose
    private String correct;

    @SerializedName("not")
    @Expose
    private String not;
    @SerializedName("wrong")
    @Expose
    private String wrong;

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getNot() {
        return not;
    }

    public String getWrong() {
        return wrong;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }
}
