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
import com.selectial.selectial.SubClassPOJO.SubClassBean;
import com.selectial.selectial.UpdateStreamPOJO.UpdateStreamBean;
import com.selectial.selectial.bannerPOJO.bannerBean;
import com.selectial.selectial.classesPOJO.classesBean;
import com.selectial.selectial.comparePOJO.compareBean;
import com.selectial.selectial.forgotpojo.ForgotBean;
import com.selectial.selectial.getHomePOJO.getHomeBean;
import com.selectial.selectial.getScholarshipPOJO.getScholarshipBean;
import com.selectial.selectial.newsPOJO.newsBean;
import com.selectial.selectial.onlineTestPOJO.onlineTestBean;
import com.selectial.selectial.ordersPOJO.ordersBean;
import com.selectial.selectial.quesDataPOJO.quesDataBean;
import com.selectial.selectial.questionPOJO.questionBean;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.response.SignupResp;
import com.selectial.selectial.scholQuesDataPOJO.scholQuesDataBean;
import com.selectial.selectial.scholarshipQuesPOJO.scholarshipQuesBean;
import com.selectial.selectial.solutionPOJO.solutionBean;
import com.selectial.selectial.submitTestBean;
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
            @Part("stream_id") RequestBody stream_id,
            @Part("name") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("phone") RequestBody phone
    );

    @Multipart
    @POST("admin/api/social_sign_up.php")
    Call<SignupResp> socialSignup(
            @Part("class") RequestBody mclass,
            @Part("stream_id") RequestBody streamId,
            @Part("name") RequestBody username,
            @Part("gender") RequestBody gender,
            @Part("age") RequestBody age,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("pid") RequestBody pid,
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
    @POST ("admin/api/social_login.php")
    Call<SigninResp> socialSignin(
            @Part("email") String email,
            @Part("pid") String pid
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
    @POST("admin/api/removeFromCompare.php")
    Call<AddCompareBean> removeFromCompare
            (
                    @Part("compId") String e
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
    @POST("admin/api/getOnlineTestsBySubjects.php")
    Call<onlineTestBean> getOnlineTestBySubjects
            (@Part("subject_id") String subjectId,
             @Part("userId") String userId
            );

    @Multipart
    @POST("admin/api/getCompareList.php")
    Call<compareBean> getCompare
            (
             @Part("userId") String userId
            );

    @Multipart
    @POST("admin/api/getTestQuestions2.php")
    Call<questionBean> getQuestions
            (
                    @Part("testId") String testId
            );

    @Multipart
    @POST("admin/api/getScholarQues.php")
    Call<scholarshipQuesBean> getScholarshipQuestions
            (
                    @Part("scholarId") String testId
            );

    @Multipart
    @POST("admin/api/submitTestAnswers.php")
    Call<questionBean> submitAnswer
            (
                    @Part("userId") String userId,
                    @Part("quesId") String quesId,
                    @Part("answer") String answer
            );

    @Multipart
    @POST("admin/api/submitScholAnswers.php")
    Call<questionBean> submitScholAnswer
            (
                    @Part("userId") String userId,
                    @Part("quesId") String quesId,
                    @Part("answer") String answer
            );


    @Multipart
    @POST("admin/api/getQuesData.php")
    Call<quesDataBean> getQuesData
            (
                    @Part("userId") String userId,
                    @Part("quesId") String quesId
            );

    @Multipart
    @POST("admin/api/getScholarQuesData.php")
    Call<scholQuesDataBean> getScholQuesData
            (
                    @Part("userId") String userId,
                    @Part("quesId") String quesId
            );

    @Multipart
    @POST("admin/api/submitTest.php")
    Call<submitTestBean> submitTest
            (
                    @Part("userId") String userId,
                    @Part("testId") String testId
            );

    @Multipart
    @POST("admin/api/submitScholarship.php")
    Call<submitTestBean> submitScholarship
            (
                    @Part("userId") String userId,
                    @Part("scholarId") String testId
            );

    @Multipart
    @POST("admin/api/getTestResult2.php")
    Call<solutionBean> viewResult
            (
                    @Part("userId") String userId,
                    @Part("testId") String testId
            );

    @Multipart
    @POST("admin/api/bud_package.php")
    Call<questionBean> buyPackage
            (
                    @Part("userId") String userId,
                    @Part("package_id") String packageId
            );

    @Multipart
    @POST("admin/api/getOrders.php")
    Call<ordersBean> getOrders
            (
                    @Part("userId") String userId
            );





    @Multipart
    @POST("admin/api/getStreams.php")
    Call<SubClassBean> subclass(
                    @Part("class_id") String clsid
            );

    @Multipart
    @POST("admin/api/getScholarshipTests.php")
    Call<getScholarshipBean> getScholarshipTests(
            @Part("userId") String userId
    );



    @GET("admin/api/getClasses.php")
    Call<classesBean> getClasses();

    @GET("admin/api/getBanners.php")
    Call<bannerBean> getBanners();

    @GET("admin/api/getScholarship.php")
    Call<newsBean> getNews();

}
