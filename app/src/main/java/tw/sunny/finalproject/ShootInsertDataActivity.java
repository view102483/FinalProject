package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootInsertDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_photo_insertdata);
    }
    public void btnShootPhotoPostInInsert(View view){
        Intent intent = new Intent();
        intent.setClass(this, ShootPostActivity.class);
        startActivity(intent);
        finish();
    }
}