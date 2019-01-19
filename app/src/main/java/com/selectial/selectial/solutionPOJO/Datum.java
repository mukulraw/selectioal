package com.selectial.selectial.solutionPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("qtype")
    @Expose
    private String qtype;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("atype")
    @Expose
    private String atype;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("yourans")
    @Expose
    private String yourans;
    @SerializedName("ytype")
    @Expose
    private String ytype;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("etype")
    @Expose
    private String etype;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYourans() {
        return yourans;
    }

    public void setYourans(String yourans) {
        this.yourans = yourans;
    }

    public String getYtype() {
        return ytype;
    }

    public void setYtype(String ytype) {
        this.ytype = ytype;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
