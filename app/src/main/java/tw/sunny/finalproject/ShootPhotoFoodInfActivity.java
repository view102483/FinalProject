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
public class ShootPhotoFoodInfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_wait);
    }
    public void btnShootPhotoPost(View view){
        Intent intent = new Intent();
        intent.setClass(this, ShootPostActivity.class);
//        intent.putExtra("bmp", this.getIntent().getParcelableExtra("bmp"));
        intent.putExtra("bmp", this.getIntent().getStringExtra("bmp"));
        startActivity(intent);
    }
    public void btnSecondSol(View view){
        Toast.makeText(this, "未有資料，需等待營養分析師的分析", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent();
        intent.setClass(this, ShootInsertDataActivity.class);
        startActivity(intent);
    }

    public void btnHome(View view){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
