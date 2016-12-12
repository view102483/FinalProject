package tw.sunny.finalproject;

import android.animation.AnimatorInflater;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootPostActivity extends BaseActivity implements InternetModule.InternetCallback {
    private static final int REQUEST_TAKE_PHOTO = 1;
    EditText desc, txtLocation, count;
    private CallbackManager callbackManager;
    LocationManager locationManager;
    Bitmap image;
    Uri imageUri;
    String path;
    String name, store;
    private SharedPreferences sp;
    private ImageView photo;
    String nu_id;
    Criteria criteria;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("facebook", MODE_PRIVATE);
        setContentView(R.layout.shoot_post);
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        photo = (ImageView) findViewById(R.id.imageView5);
        desc = (EditText) findViewById(R.id.editText13);
        txtLocation = (EditText) findViewById(R.id.editText14);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        count = (EditText) findViewById(R.id.count);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(ShootPostActivity.this, "成功！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ShootPostActivity.this, "已取消", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(ShootPostActivity.this, "on error!", Toast.LENGTH_LONG).show();
            }
        });
        if (getIntent().hasExtra("bmp")) {
            path = getIntent().getStringExtra("bmp");
            imageUri = Uri.parse(path);
            Glide.with(this).load(path).placeholder(R.drawable.default_image).into(photo);
        } else {
            photo.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.drawable.btn_selector));
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto(v);
                }
            });
        }

        if (getIntent().hasExtra("name")) {
            name = getIntent().getStringExtra("name");
        }
        if (getIntent().hasExtra("store")) {
            name = getIntent().getStringExtra("store");
        }

        if (getIntent().hasExtra("nuid")) {
            nu_id = getIntent().getStringExtra("nuid");
        }
        initLocation();
    }

    private Location getLastKnownLocation() {
        if(criteria == null) {
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(true);
            criteria.setCostAllowed(false);
            criteria.setSpeedRequired(false);
            criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
            criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
            criteria.setSpeedAccuracy(Criteria.ACCURACY_FINE);
            criteria.setHorizontalAccuracy(Criteria.ACCURACY_FINE);
            criteria.setVerticalAccuracy(Criteria.ACCURACY_FINE);
        }
        return locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true));
    }

    private void initLocation()  {
        showLoadingDialog("Updating", "正在更新地理資訊中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Location location = getLastKnownLocation();
                    Geocoder gc = new Geocoder(ShootPostActivity.this, Locale.TRADITIONAL_CHINESE);
                    List<Address> addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                    Address address = addresses.get(0);
                    final String txtAddress = address.getAdminArea() + address.getLocality() + address.getThoroughfare() + address.getFeatureName() + "號";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtLocation.setText(txtAddress);
                            dismissLoadingDialog();
                        }
                    });
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void btnShootPhotoPost(View view) {
        if (path != null && !path.isEmpty()) {
            showLoadingDialog("Uploading", "上傳照片中...");
            new InternetTask(this, "http://120.126.15.112/food/photo.php?act=upload", path).execute();
        } else {
            onSuccess("photo_ok");
        }
    }

    public void btnFacebookShare(View v) {

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(BitmapFactory.decodeFile(path))
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

//        ShareApi.share(content, new FacebookCallback<Sharer.Result>() {
//            @Override
//            public void onSuccess(Sharer.Result result) {
//                Toast.makeText(ShootPostActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(ShootPostActivity.this, "分享失敗：" + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(content);
        } else {
            Toast.makeText(this, "some thing wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnLineShare(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "This is a share message");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setType("image/png");
        intent.setPackage("jp.naver.line.android");
        startActivity(intent);
    }

    public void btnTwitterShare(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setPackage("com.twitter.android");
        intent.setType("image/png");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (responseCode == RESULT_OK) {
                imageUri = Uri.parse(path);
                showLoadingDialog("Loading", "正在處理中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rotatePhoto(path);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(ShootPostActivity.this).load(path).placeholder(R.drawable.loading).error(R.drawable.default_image).into(photo);
                                photo.setOnClickListener(null);
                                photo.setStateListAnimator(null);
                                dismissLoadingDialog();
                            }
                        });
                    }
                }).start();

            }
        } else {
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }

    }

    public void btnHome(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(String data) {
        showLoadingDialog("Uploading", "上傳資料中...");
        Map<String, String> map = new HashMap<>();
        map.put("member_id", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        map.put("photo_description", desc.getText().toString());
        map.put("store_location", txtLocation.getText().toString());
        if(name != null)
        map.put("menu_name", name);
        if(store != null)
        map.put("store_name", store);
        map.put("count", count.getText().toString());
        if (nu_id != null && !nu_id.isEmpty())
            map.put("nu_id", nu_id);
        if (path != null && !path.isEmpty())
            map.put("record_image", new File(path).getName());
        else
            map.put("record_image", "");
        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                Toast.makeText(ShootPostActivity.this, "上傳成功", Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(ShootPostActivity.this, "上傳失敗:" + msg, Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
            }
        }, "http://120.126.15.112/food/record.php?act=upload", InternetModule.POST, map).execute();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, "上傳失敗:" + msg, Toast.LENGTH_SHORT).show();
        dismissLoadingDialog();
    }

    public void takePhoto(View v) {
        new AlertDialog.Builder(this)
                .setMessage("接下來會開啟照相機畫面，拍一張食物的照片上傳吧！")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            File photoFile = null;

                            try {
                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                String imageFileName = "eating_" + timeStamp;
                                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                photoFile = File.createTempFile(
                                        imageFileName,  /* prefix */
                                        ".png",         /* suffix */
                                        storageDir      /* directory */
                                );
                                path = photoFile.getAbsolutePath();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(ShootPostActivity.this,
                                        "com.example.android.fileprovider",
                                        photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                            }
                        }
                    }
                })
                .show();
    }

    public void onLocationRequireClick(View v) {
        initLocation();
    }


}
