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
import com.selectial.selectial.forgotpojo.ForgotBean;
import com.selectial.selectial.getHomePOJO.getHomeBean;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.testListPOJO.testListBean;

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
            @Part("email") String email,
            @Part("password") String password
    );


    @Multipart
    @POST("selectial/api/forgotPassword.php")
    Call<ForgotBean> forgot
            (@Part("email") String email);

    @Multipart
    @POST ("selectial/api/updateProfilePic.php")
    Call<ForgotBean> updatebean(
            @Part("userId") String userid,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("selectial/api/getStreams.php")
    Call<GetStreamBean> steam
            (@Part("class_id") String cls);

    @Multipart
    @POST("selectial/api/updateStream.php")
    Call<UpdateStreamBean> udatestream
            (@Part("stream_id") String cls,
            @Part("userId") String user
             );

    @Multipart
    @POST("selectial/api/getProfile.php")
    Call<GetProfileBean> profilee
            (@Part("userId") String ft);

    @Multipart
    @POST("selectial/api/updateProfile.php")
    Call<EditProfileBean> edit
            (@Part("userId") String e,
             @Part("name") String usrrer ,
             @Part("gender") String r ,
             @Part("age") String fds
            );

    @Multipart
    @POST("selectial/api/changePassword.php")
    Call<ChangePasswordBean> cpp
            (@Part("userId") String e,
             @Part("password") String usrrer
            );

    @Multipart
    @POST("selectial/api/getInstitutes.php")
    Call<GetInsititudeBean> insi
            (@Part("userId") String e);




    @Multipart
    @POST("selectial/api/rateInstitute.php")
    Call<RateBean> rate
            (@Part("userId") String e ,
            @Part("instId") String insd ,
            @Part("rating") String rat
             );



    @Multipart
    @POST("selectial/api/likeInstitute.php")
    Call<GetLikeInsitituteBean> likee
            (@Part("userId") String e ,
            @Part("instId") String ajsdjdsa
             );


    @Multipart
    @POST("selectial/api/getInstitutesDetails.php")
    Call<InsititutedetailsBean> insi
            (@Part("instId") String e ,
             @Part("userId") String ajsdjdsa
            );



    @Multipart
    @POST("selectial/api/addToCompare.php")
    Call<AddCompareBean> addd
            (@Part("instId") String e ,
             @Part("userId") String ajsdjdsa
            );

    @Multipart
    @POST("selectial/api/getHome.php")
    Call<getHomeBean> getHome
            (@Part("stream_id") String streamId,
             @Part("userId") String userId,
             @Part("class_id") String classId
            );

    @Multipart
    @POST("selectial/api/getTestsBySubjects.php")
    Call<testListBean> getTestBySubjects
            (@Part("subject_id") String subjectId,
             @Part("userId") String userId
            );

}
