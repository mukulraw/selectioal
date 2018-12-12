package com.selectial.selectial.webservices;


import com.selectial.selectial.GetStreamPOJO.GetStreamBean;
import com.selectial.selectial.UpdateprofilePOJO.UpdateprofileBean;
import com.selectial.selectial.forgotpojo.ForgotBean;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.response.SigninResponse;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.response.SignupResponse;

import okhttp3.MultipartBody;
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


    @Multipart
    @POST ("selectial/api/login.php")
    Call<SigninResp> signin(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );


    @Multipart
    @POST("selectial/api/updateProfilePic.php")
    Call<ForgotBean> forgot
            (@Part("email") String email);

    @Multipart
    @POST ("selectial/api/updateProfilePic.php")
    Call<UpdateprofileBean> updatebean(
            @Part("userId") String userid,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("selectial/api/getStreams.php")
    Call<GetStreamBean> steam
            (@Part("class_id") String cls);






}
