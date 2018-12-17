package com.selectial.selectial;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.selectial.selectial.forgotpojo.ForgotBean;
import com.selectial.selectial.util.Constant;
import com.selectial.selectial.util.SharePreferenceUtils;
import com.selectial.selectial.webservices.ServiceInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SetProfileImage extends AppCompatActivity {

    Button takephoto;

    static final int RC_TAKE_PHOTO = 1;

    private final int PICK_IMAGE_REQUEST = 2;

    File file;

    Uri fileUri;

    String mCurrentPhotoPath = "";

    TextView proceed;

    ProgressBar bar;

    CircularImageView circularImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_image);

        takephoto = findViewById(R.id.button4);

        proceed = findViewById(R.id.textView85);
        circularImageView = findViewById(R.id.view2);

        bar = findViewById(R.id.progressBar4);

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SetProfileImage.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.show();

                TextView profile = (TextView) dialog.findViewById(R.id.profile);

                TextView cap = (TextView) dialog.findViewById(R.id.capture);

                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
                        dialog.dismiss();


                    }
                });

                cap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file = new File(getExternalCacheDir(),
                                String.valueOf(System.currentTimeMillis()) + ".jpg");

                        fileUri = FileProvider.getUriForFile(
                                SetProfileImage.this,
                                SetProfileImage.this.getApplicationContext()
                                        .getPackageName() + ".fileprovider", file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                        startActivityForResult(intent, RC_TAKE_PHOTO);

                        dialog.dismiss();

                    }
                });


                // Intent intent = new Intent(SetProfileImage.this , Interest.class);
                //  startActivity(intent);

            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MultipartBody.Part body = null;

                RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

                Log.d("image", file.getName());
                Log.d("userid", SharePreferenceUtils.getInstance().getString(Constant.USER_id));

                bar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())

                        .build();

                ServiceInterface cr = retrofit.create(ServiceInterface.class);

                Call<ForgotBean> call = cr.updatebean(SharePreferenceUtils.getInstance().getString(Constant.USER_id), body);
                call.enqueue(new Callback<ForgotBean>() {
                    @Override
                    public void onResponse(Call<ForgotBean> call, Response<ForgotBean> response) {

                        if (Objects.equals(response.body().getStatus(), "1")) {

                            //Toast.makeText(SetProfileImage.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            if (SharePreferenceUtils.getInstance().getString(Constant.CLS_id).equals("1"))
                            {

                                Intent intent = new Intent(SetProfileImage.this , MainActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            }
                            else
                            {

                                Intent intent = new Intent(SetProfileImage.this , Interest.class);
                                startActivity(intent);
                                finishAffinity();

                            }

                        } else {
                            Toast.makeText(SetProfileImage.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<ForgotBean> call, Throwable t) {

                        bar.setVisibility(View.GONE);

                    }
                });


            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_TAKE_PHOTO && resultCode == RESULT_OK) {

            try {

                Log.d("asdasasd", fileUri.getPath());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                circularImageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                Log.d("asdasasd", e.toString());
                e.printStackTrace();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {

            fileUri = data.getData();

            Log.d("mukul", fileUri.toString());

            String mCurrentPhotoPath = getPath(SetProfileImage.this, fileUri);

            file = new File(mCurrentPhotoPath);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            circularImageView.setImageBitmap(bitmap);


        }
    }

    private static String getPath(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = true;

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
