package com.selectial.selectial.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupResp {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignupResp withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SignupResp withStatus(String status) {
        this.status = status;
        return this;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public SignupResp withData(Data data) {
        this.data = data;
        return this;
    }
    public class Data {

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("class_id")
        @Expose
        private String classId;
        @SerializedName("class_name")
        @Expose
        private String className;
        @SerializedName("sub_class_id")
        @Expose
        private String subClassId;
        @SerializedName("sub_class_name")
        @Expose
        private String subClassName;
        @SerializedName("isPaid")
        @Expose
        private String isPaid;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_date")
        @Expose
        private String createdDate;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Data withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Data withName(String name) {
            this.name = name;
            return this;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Data withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public Data withAge(String age) {
            this.age = age;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Data withEmail(String email) {
            this.email = email;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Data withPassword(String password) {
            this.password = password;
            return this;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public Data withClassId(String classId) {
            this.classId = classId;
            return this;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Data withClassName(String className) {
            this.className = className;
            return this;
        }

        public String getSubClassId() {
            return subClassId;
        }

        public void setSubClassId(String subClassId) {
            this.subClassId = subClassId;
        }

        public Data withSubClassId(String subClassId) {
            this.subClassId = subClassId;
            return this;
        }

        public String getSubClassName() {
            return subClassName;
        }

        public void setSubClassName(String subClassName) {
            this.subClassName = subClassName;
        }

        public Data withSubClassName(String subClassName) {
            this.subClassName = subClassName;
            return this;
        }

        public String getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(String isPaid) {
            this.isPaid = isPaid;
        }

        public Data withIsPaid(String isPaid) {
            this.isPaid = isPaid;
            return this;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Data withImage(String image) {
            this.image = image;
            return this;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data withStatus(String status) {
            this.status = status;
            return this;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Data withCreatedDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

    }

}