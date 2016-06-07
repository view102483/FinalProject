package tw.sunny.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootPostActivity extends AppCompatActivity {
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_post);
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
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
        });
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
        Log.i("Nagi", "start facebook share");
        Bitmap image = (Bitmap) getIntent().getExtras().get("bmp");
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(content);
        } else {
            Toast.makeText(this, "some thing wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
