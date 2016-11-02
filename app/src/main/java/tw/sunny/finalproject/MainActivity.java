package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by lixinting on 2016/4/12.
 */
public class MainActivity extends BaseActivity {
    ProgressBar progressBar = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        progressBar = (ProgressBar) findViewById(R.id.pgbar);
        progressBar.setMax(100);
        progressBar.setProgress(30);
    }
    public void btnShoot(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ShootActivity.class);
        startActivity(intent);
    }

    public void btnSearching(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SearchingMainActivity.class);
        startActivity(intent);
    }
    public void btnLocation(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LocationMainActivity.class);
        startActivity(intent);
    }
    public void btnRecord(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, RecordMainActivity.class);
        startActivity(intent);
    }

    public void btnMedicalManage(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, RecordDmManageActivity.class);
        startActivity(intent);
    }
}
