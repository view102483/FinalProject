package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_code_infor);
    }

    public void btnShootInfoNext(View view) {
        Intent intent = new Intent();
        intent.setClass(this,ShootInsertDataActivity.class);
        startActivity(intent);
    }

}
