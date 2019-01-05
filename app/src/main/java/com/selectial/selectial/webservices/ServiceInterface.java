package com.selectial.selectial.webservices;


import com.selectial.selectial.AddComparePOJO.AddCompareBean;
import com.selectial.selectial.ChangePasswordPOJo.ChangePasswordBean;
import com.selectial.selectial.EditProfilePOJO.EditProfileBean;
import com.selectial.selectial.GetInsititudePOJO.GetInsititudeBean;
import com.selectial.selectial.GetProfilePOJO.GetProfileBean;
import com.selectial.selectial.GetStreamPOJO.GetStreamBean;
import com.selectial.selectial.InsitituteDetailsPOJO.InsititutedetailsBean;
import com.selectial.selectial.LikeInsititutePOJO.GetLikeInsitituteBean;
import com.selectial.selectial.RatePOJO.RateBean;
import com.selectial.selectial.UpdateStreamPOJO.UpdateStreamBean;
import com.selectial.selectial.classesPOJO.classesBean;
import com.selectial.selectial.comparePOJO.compareBean;
import com.selectial.selectial.forgotpojo.ForgotBean;
import com.selectial.selectial.getHomePOJO.getHomeBean;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.testListPOJO.testListBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {

   /* @Multipart
    @POST("admin/signup.php")
    Call<SignupResponse> signup(
            @Part("class") RequestBody mclass,
            @Part("username") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );*/

   /* @Multipart
    @POST ("admin/signin.php")
    Call<SigninResponse> signin(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );*/


    @Multipart
    @POST("admin/api/sign_up.php")
    Call<SignupResp> signup(
            @Part("class") RequestBody mclass,
            @Part("name") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("phone") RequestBody phone
    );

    @Multipart
    @POST("admin/api/verifyOTP.php")
    Call<SignupResp> verifyOTP(
            @Part("userId") RequestBody userId,
            @Part("otp") RequestBody otp
    );


    @Multipart
    @POST("admin/api/resendOTP.php")
    Call<SignupResp> resendOTP(
            @Part("userId") RequestBody userId,
            @Part("phone") RequestBody phone
    );

    @Multipart
    @POST ("admin/api/login.php")
    Call<SigninResp> signin(
            @Part("email") String email,
            @Part("password") String password
    );


    @Multipart
    @POST("admin/api/forgotPassword.php")
    Call<ForgotBean> forgot
            (@Part("email") String email);

    @Multipart
    @POST ("admin/api/updateProfilePic.php")
    Call<ForgotBean> updatebean(
            @Part("userId") String userid,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("admin/api/getStreams.php")
    Call<GetStreamBean> steam
            (@Part("class_id") String cls);

    @Multipart
    @POST("admin/api/updateStream.php")
    Call<UpdateStreamBean> udatestream
            (@Part("stream_id") String cls,
            @Part("userId") String user
             );

    @Multipart
    @POST("admin/api/getProfile.php")
    Call<GetProfileBean> profilee
            (@Part("userId") String ft);

    @Multipart
    @POST("admin/api/updateProfile.php")
    Call<EditProfileBean> edit
            (@Part("userId") String e,
             @Part("name") String usrrer ,
             @Part("gender") String r ,
             @Part("age") String fds
            );

    @Multipart
    @POST("admin/api/changePassword.php")
    Call<ChangePasswordBean> cpp
            (@Part("userId") String e,
             @Part("password") String usrrer
            );

    @Multipart
    @POST("admin/api/getInstitutes.php")
    Call<GetInsititudeBean> insi
            (@Part("userId") String e);




    @Multipart
    @POST("admin/api/rateInstitute.php")
    Call<RateBean> rate
            (@Part("userId") String e ,
            @Part("instId") String insd ,
            @Part("rating") String rat
             );



    @Multipart
    @POST("admin/api/likeInstitute.php")
    Call<GetLikeInsitituteBean> likee
            (@Part("userId") String e ,
            @Part("instId") String ajsdjdsa
             );


    @Multipart
    @POST("admin/api/getInstitutesDetails.php")
    Call<InsititutedetailsBean> insi
            (@Part("instId") String e ,
             @Part("userId") String ajsdjdsa
            );



    @Multipart
    @POST("admin/api/addToCompare.php")
    Call<AddCompareBean> addd
            (@Part("instId") String e ,
             @Part("userId") String ajsdjdsa
            );

    @Multipart
    @POST("admin/api/getHome.php")
    Call<getHomeBean> getHome
            (@Part("stream_id") String streamId,
             @Part("userId") String userId,
             @Part("class_id") String classId
            );

    @Multipart
    @POST("admin/api/getTestsBySubjects.php")
    Call<testListBean> getTestBySubjects
            (@Part("subject_id") String subjectId,
             @Part("userId") String userId
            );

    @Multipart
    @POST("admin/api/getCompareList.php")
    Call<compareBean> getCompare
            (
             @Part("userId") String userId
            );

    @GET("admin/api/getClasses.php")
    Call<classesBean> getClasses();

}
