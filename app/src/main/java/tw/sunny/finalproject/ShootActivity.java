package tw.sunny.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tw.sunny.finalproject.model.Menu;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.QRCodeReaderView;

public class ShootActivity extends BaseActivity implements QRCodeReaderView.OnQRCodeReadListener, InternetModule.InternetCallback {
    QRCodeReaderView qr;
    File mCurrentPhoto;
    int REQUEST_TAKE_PHOTO = 1;
    String nuId = "", name = "";
    TableLayout infoLayout;
    TextView amount, amountPerPackage,
            calPerPackage, calPerHundred,
            proteinPerPackage, proteinPerHundred,
            fatPerPackage, fatPerHundred,
            saturatedfatPerPackage, saturatedfatPerHundred,
            transfatPerPackage, transfatPerHundred,
            carbohydratePerPackage, carbohydratePerHundred,
            sugarPerPackage, sugarPerHundred,
            sodiumPerPackage, sodiumPerHundred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_main);
        qr = (QRCodeReaderView) findViewById(R.id.qr);
        qr.setOnQRCodeReadListener(this);

        init();
    }

    public void dismissLayout(View v) {
        v.setVisibility(View.GONE);
    }


    private void init() {
        infoLayout = (TableLayout) findViewById(R.id.infos);
        amount = (TextView) findViewById(R.id.amont);
        amountPerPackage = (TextView) findViewById(R.id.amountPerPackage);
        calPerPackage = (TextView) findViewById(R.id.calPerPackage);
        calPerHundred = (TextView) findViewById(R.id.calPerHundred);
        proteinPerPackage = (TextView) findViewById(R.id.proteinPerPackage);
        proteinPerHundred = (TextView) findViewById(R.id.proteinPerHundred);
        fatPerPackage = (TextView) findViewById(R.id.fatPerPackage);
        fatPerHundred = (TextView) findViewById(R.id.fatPerHundred);
        carbohydratePerPackage = (TextView) findViewById(R.id.carbohydratePerPackage);
        carbohydratePerHundred = (TextView) findViewById(R.id.carbohydratePerHundred);
        sodiumPerPackage = (TextView) findViewById(R.id.sodiumPerPackage);
        sodiumPerHundred = (TextView) findViewById(R.id.sodiumPerHundred);
        saturatedfatPerPackage = (TextView) findViewById(R.id.saturatedfatPerPackage);
        saturatedfatPerHundred = (TextView) findViewById(R.id.saturatedfatPerHundred);
        transfatPerPackage = (TextView) findViewById(R.id.transfatPerPackage);
        transfatPerHundred = (TextView) findViewById(R.id.transfatPerHundred);
        sugarPerPackage = (TextView) findViewById(R.id.sugarPerPackage);
        sugarPerHundred = (TextView) findViewById(R.id.sugarPerHundred);
    }

    public void btnHasQrCode(View v) {
        if(nuId.isEmpty()) {
            Toast.makeText(this, "請先掃描條碼", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ShootPostActivity.class);
        intent.putExtra("nuid", nuId);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }

    public void onBtnReturnClick(View v) {
        finish();
    }

    public void btnHasntQrCode(View v) {
        new AlertDialog.Builder(this)
                .setMessage("請將一枚硬幣放在餐點旁邊，以利分析師估算熱量")
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
                                mCurrentPhoto = photoFile;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(ShootActivity.this,
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


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if(nuId.equals(text))
            return;

//        for (int i=0; i<text.length(); i++) {
//            if(!Character.isDigit(text.charAt(i)))
//                return;
//        }
//        nuId = text;
//        Map<String, String> map = new HashMap<>();
//        map.put("id", text);
//        showLoadingDialog();
        //new InternetTask(this, "http://120.126.15.112/food/menu.php?act=get", InternetModule.POST, map).execute();
        try {
            JSONObject json = new JSONObject(text);
            Menu menu = new Menu(json);
            infoLayout.setVisibility(View.VISIBLE);
            calPerPackage.setText(menu.getMenu_calories() + " 大卡");
            carbohydratePerPackage.setText(menu.getMenu_carbohydrate() + " 公克");
            sugarPerPackage.setText(menu.getMenu_sugar() + " 公克");
            sodiumPerPackage.setText(menu.getMenu_soduim() + " 毫克");
            fatPerPackage.setText(menu.getMenu_fat() + " 公克");
            saturatedfatPerPackage.setText(menu.getMenu_saturatedfat() + " 公克");
            transfatPerPackage.setText(menu.getMenu_transfat() + " 公克");
            proteinPerPackage.setText(menu.getMenu_protein() + " 公克");
            name = menu.getMenu_name();

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        dismissLoadingDialog();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                qr.stopCamera();
                qr.setVisibility(View.INVISIBLE);
                showLoadingDialog("Loading", "正在處理中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rotatePhoto(mCurrentPhoto.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingDialog();
                                Intent intent = new Intent(ShootActivity.this, ShootPhotoFoodInfActivity.class);
                                intent.putExtra("bmp", mCurrentPhoto.getAbsolutePath());
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }).start();


            }
        }
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONObject json = new JSONObject(data);
            Menu menu = new Menu(json);
            infoLayout.setVisibility(View.VISIBLE);
            calPerPackage.setText(menu.getMenu_calories() + " 大卡");
            carbohydratePerPackage.setText(menu.getMenu_carbohydrate() + " 公克");
            sugarPerPackage.setText(menu.getMenu_sugar() + " 公克");
            sodiumPerPackage.setText(menu.getMenu_soduim() + " 毫克");
            fatPerPackage.setText(menu.getMenu_fat() + " 公克");
            saturatedfatPerPackage.setText(menu.getMenu_saturatedfat() + " 公克");
            transfatPerPackage.setText(menu.getMenu_transfat() + " 公克");
            proteinPerPackage.setText(menu.getMenu_protein() + " 公克");
            name = menu.getMenu_name();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dismissLoadingDialog();
    }

    @Override
    public void onFail(String msg) {
        dismissLoadingDialog();
    }


}
