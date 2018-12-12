package com.selectial.selectial.webservices;


import com.selectial.selectial.response.ImageResp;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.response.SigninResponse;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.response.SignupResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {

   /* @Multipart
    @POST("selectial/signup.php")
    Call<SignupResponse> signup(
            @Part("class") RequestBody mclass,
            @Part("username") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );*/

   /* @Multipart
    @POST ("selectial/signin.php")
    Call<SigninResponse> signin(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );*/

//Register new user
    @Multipart
    @POST("selectial/api/sign_up.php")
    Call<SignupResp> signup(
            @Part("class") RequestBody mclass,
            @Part("name") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );

// login
    @Multipart
    @POST("selectial/api/login.php")
    Call<SigninResp> signin(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );

    //uploading profile image
    @Multipart
    @POST("selectial/api/updateProfilePic.php")
    Call<ImageResp> uploadProfileImage(
            @Part("userId") RequestBody userid,
            @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file
    );



}