package com.selectial.selectial;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.selectial.selectial.response.ImageResp;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetProfileImage extends AppCompatActivity {

    Button capture;
    CircularImageView circularImageView;
    String userId;
    Retrofit retrofit;
    ServiceInterface serviceInterface;
    File compressedImageFile;
    TextView proceed;
    ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_image);

        setUpWidget();

        userId = SharePreferenceUtils.getInstance().getString(Constant.USER_id);

        //checking the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }


        //Retrofit
        pBar.setVisibility(View.GONE);

        // Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startTest = new Intent(SetProfileImage.this, StartTest.class);
                startActivity(startTest);
                finish();
            }
        });


        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //opening file chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);

               /* Intent intent = new Intent(SetProfileImage.this , Interest.class);
                startActivity(intent);*/

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            Uri selectedImage = data.getData();
            Log.i("image", selectedImage.toString());

            setImage(selectedImage);
/*
            //calling the upload file method after choosing the file
            uploadFile(selectedImage, "My Image");*/
        }
    }

    public void setImage(Uri selectedImage) {
        circularImageView.setImageURI(selectedImage);
        //calling the upload file method after choosing the file

        //capture.setVisibility(View.INVISIBLE);
        uploadFile(userId, selectedImage);

    }

    private void uploadFile(String userId, Uri fileUri) {

        pBar.setVisibility(View.VISIBLE);

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));
        try {
            compressedImageFile = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), compressedImageFile);
        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), userId);

        Call<ImageResp> call = serviceInterface.uploadProfileImage(userid, requestFile);
        call.enqueue(new Callback<ImageResp>() {
            @Override
            public void onResponse(Call<ImageResp> call, Response<ImageResp> response) {
                pBar.setVisibility(View.GONE);
                if (response.body().getStatus().equals("1")) {
                    Toast.makeText(SetProfileImage.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SetProfileImage.this, StartTest.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SetProfileImage.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ImageResp> call, Throwable t) {
                pBar.setVisibility(View.GONE);
                Log.e("error", "" + t);

            }
        });
    }

    private void setUpWidget() {

        capture = findViewById(R.id.button4);
        circularImageView = findViewById(R.id.view2);
        proceed = findViewById(R.id.textView85);
        pBar = findViewById(R.id.progressBar4);
    }


    //This method is fetching the absolute path of the image file
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
