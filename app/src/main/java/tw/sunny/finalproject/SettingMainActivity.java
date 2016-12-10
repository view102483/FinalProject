package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/4/12.
 */
public class SettingMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }

    public void btnHome(View view){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void btnSettingToSettingEditMem(View view){
        Intent intent = new Intent();
        intent.setClass(SettingMainActivity.this, SettingEditMemActivity.class);
        startActivity(intent);

    }
    public void btnSettingToSettingRequest(View view){
        Intent intent = new Intent();
        intent.setClass(SettingMainActivity.this, SettingRequestActivity.class);
        startActivity(intent);

    }

    public void btnLogout(View v) {
        getSharedPreferences("member", MODE_PRIVATE).edit().clear().commit();
        ExitActivity.exitApplication(this);
    }


}
