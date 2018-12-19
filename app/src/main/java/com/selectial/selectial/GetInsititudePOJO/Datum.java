package com.selectial.selectial.GetInsititudePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("fees")
    @Expose
    private String fees;
    @SerializedName("est_year")
    @Expose
    private String estYear;
    @SerializedName("centres")
    @Expose
    private String centres;
    @SerializedName("air_med")
    @Expose
    private String airMed;
    @SerializedName("air_engg")
    @Expose
    private String airEngg;
    @SerializedName("students")
    @Expose
    private String students;
    @SerializedName("faculties")
    @Expose
    private String faculties;
    @SerializedName("isLiked")
    @Expose
    private String isLiked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getEstYear() {
        return estYear;
    }

    public void setEstYear(String estYear) {
        this.estYear = estYear;
    }

    public String getCentres() {
        return centres;
    }

    public void setCentres(String centres) {
        this.centres = centres;
    }

    public String getAirMed() {
        return airMed;
    }

    public void setAirMed(String airMed) {
        this.airMed = airMed;
    }

    public String getAirEngg() {
        return airEngg;
    }

    public void setAirEngg(String airEngg) {
        this.airEngg = airEngg;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public String getFaculties() {
        return faculties;
    }

    public void setFaculties(String faculties) {
        this.faculties = faculties;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }
}
