package com.selectial.selectial.scholQuesDataPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("scholar_id")
    @Expose
    private String scholarId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("qtype")
    @Expose
    private String qtype;
    @SerializedName("opt1")
    @Expose
    private String opt1;
    @SerializedName("type1")
    @Expose
    private String type1;
    @SerializedName("opt2")
    @Expose
    private String opt2;
    @SerializedName("type2")
    @Expose
    private String type2;
    @SerializedName("opt3")
    @Expose
    private String opt3;
    @SerializedName("type3")
    @Expose
    private String type3;
    @SerializedName("opt4")
    @Expose
    private String opt4;
    @SerializedName("type4")
    @Expose
    private String type4;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("yrans")
    @Expose
    private String yrans;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScholarId() {
        return scholarId;
    }

    public void setScholarId(String scholarId) {
        this.scholarId = scholarId;
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

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getType4() {
        return type4;
    }

    public void setType4(String type4) {
        this.type4 = type4;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getYrans() {
        return yrans;
    }

    public void setYrans(String yrans) {
        this.yrans = yrans;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
