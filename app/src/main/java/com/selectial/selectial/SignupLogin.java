package com.selectial.selectial;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.selectial.selectial.response.SigninResp;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SignupLogin extends AppCompatActivity {

    Button login , signup;
    TextView subText;
    ImageButton facebook , google;
    private CallbackManager mCallbackManager;

    ProgressBar pBar;

    GoogleSignInClient mGoogleSignInClient;

    int RC_SIGN_IN = 12;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.i("MainActivity", "@@@response: " + response.toString());

                                        try {

                                            final String name = object.getString("name");
                                            final String id = object.getString("id");
                                            final String email = object.getString("email");



                                            socialSignin(name , id , email);




                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignupLogin.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SignupLogin.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_signup_login);

        login = findViewById(R.id.button2);

        signup = findViewById(R.id.button);

        subText = findViewById(R.id.textView91);

        facebook = findViewById(R.id.imageButton);

        google = findViewById(R.id.imageButton2);

        pBar = findViewById(R.id.progressBar12);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignupLogin.this, Collections.singletonList("public_profile"));
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }

        SpannableString ss = new SpannableString("By entering you will agree to our Privacy Policy and Terms and Conditions");
        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Log.d("clicked" , "privacy");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://selectialindia.com/privacy-policy.php")));

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan terms = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Log.d("clicked" , "terms");
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://selectialindia.com/terms-for-students.php")));

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(privacy, 34, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(terms, 52, 73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        subText.setText(ss);
        subText.setMovementMethod(LinkMovementMethod.getInstance());
        subText.setHighlightColor(Color.TRANSPARENT);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignupLogin.this , Login.class);
                startActivity(intent);

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupLogin.this , Signup.class);
                intent.putExtra("social" , "0");
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    void socialSignup(String name , String id , String email)
    {
        Intent intent = new Intent(SignupLogin.this , Signup.class);
        intent.putExtra("social" , "1");
        intent.putExtra("name" , name);
        intent.putExtra("id" , id);
        intent.putExtra("email" , email);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            socialSignin(account.getDisplayName() , account.getId() , account.getEmail());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Signin", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    void socialSignin(final String name , final String id , final String email)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceInterface serviceInterface = retrofit.create(ServiceInterface.class);


        Call<SigninResp> call = serviceInterface.socialSignin(email, id);
        call.enqueue(new Callback<SigninResp>() {
            @Override
            public void onResponse(Call<SigninResp> call, Response<SigninResp> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {
                        pBar.setVisibility(View.GONE);



                        Toast.makeText(SignupLogin.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_id, response.body().getData().getUserId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_name, response.body().getData().getName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_email, response.body().getData().getEmail());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_phone, response.body().getData().getPhone());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_gender, response.body().getData().getGender());
                        SharePreferenceUtils.getInstance().saveString(Constant.User_age, response.body().getData().getAge());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_class, response.body().getData().getClassName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_class_id, response.body().getData().getClassId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_date, response.body().getData().getCreatedDate());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_image, response.body().getData().getImage());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_isPaid, response.body().getData().getIsPaid());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_status, response.body().getData().getStatus());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_id, response.body().getData().getSubClassId());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_sub_class_name, response.body().getData().getSubClassName());
                        SharePreferenceUtils.getInstance().saveString(Constant.USER_password, response.body().getData().getPassword());

                        if (response.body().getData().getIsVerified().equals("0"))
                        {
                            Toast.makeText(SignupLogin.this, "Your phone number is not verified, please check OTP", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupLogin.this, OTP.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else
                        {
                            Intent intent = new Intent(SignupLogin.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }


                    } else {
                        pBar.setVisibility(View.GONE);
                        socialSignup(name , id , email);
                    }
                }

            }

            @Override
            public void onFailure(Call<SigninResp> call, Throwable t) {
                pBar.setVisibility(View.GONE);

                Log.e("error", "" + t);
                // Toast.makeText(Login.this, "invalid login"+t, Toast.LENGTH_SHORT).show();


            }
        });
    }

}
