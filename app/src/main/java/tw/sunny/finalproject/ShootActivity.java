package tw.sunny.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootActivity extends AppCompatActivity{
    private int QR_REQ = 0;
    private int CAMERA_REQ = 1;

    private Uri fileUri;

    private int DEFAULT_THUMBNAIL_WIDTH = 200;
    private int DEFAULT_THUMBNAIL_HEIGHT = 200;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_main);
    }
    public void btnShootCode(View view){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE","SCAN_MODE");

        startActivityForResult(intent, QR_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QR_REQ) {
            if (resultCode == RESULT_OK) {
                String code = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(this, "Code:" + code, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(this, ShootCodeActivity.class);
                startActivity(intent);
            }
        } else if(requestCode == CAMERA_REQ) {
            if(resultCode == RESULT_OK) {
                Intent intent = new Intent(this, ShootPhotoFoodInfActivity.class);
//                Bitmap bmp = (Bitmap)data.getExtras().get("data");
                    intent.putExtra("bmp", fileUri.toString());
                    startActivity(intent);
                    finish();

            }
        }
    }

    public void btnShootPhoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Long timestamp = System.currentTimeMillis()/1000;
        File photo = new File(getExternalCacheDir(), timestamp.toString() + ".jpg");
        fileUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_REQ);
    }

}
