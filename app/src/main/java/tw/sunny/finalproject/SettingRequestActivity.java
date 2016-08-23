package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/8/23.
 */
public class SettingRequestActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_reqest);
    }

    public void btnHome(View view){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
