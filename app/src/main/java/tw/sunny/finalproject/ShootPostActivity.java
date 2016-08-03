package tw.sunny.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootPostActivity extends AppCompatActivity {
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    Bitmap image;
    Uri imageUri;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("facebook", MODE_PRIVATE);
        setContentView(R.layout.shoot_post);
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
/*
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(ShootPostActivity.this, "success!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ShootPostActivity.this, "cencel!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(ShootPostActivity.this, "on error!", Toast.LENGTH_LONG).show();
            }
        });*/
        String path = getIntent().getStringExtra("bmp");
        imageUri = Uri.parse(path);
        try {
            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ((ImageView)findViewById(R.id.imageView5)).setImageBitmap(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void btnShootPhotoPost(View view){
//        Intent intent = new Intent();
//        intent.setClass(this, ShootRating.class);
//        startActivity(intent);
//        finish();
//        String uri = getIntent().getExtras().get("bmp");
//        if(uri == null || uri.isEmpty()) {
//            return;
//        }

    }

    public void btnFacebookShare(View v) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(ShootPostActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(ShootPostActivity.this, "分享失敗：" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /*
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(content);
        } else {
            Toast.makeText(this, "some thing wrong", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void btnTwitterShare(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "This is a share message");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setType("image/jpeg");
        intent.setPackage("com.twitter.android");
        startActivity(intent);
    }

    public void btnInstagramShare(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setPackage("com.instagram.android");
        intent.setType("image/jpeg");
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
