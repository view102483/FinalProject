package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RecordMainActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_main);

    }


    public void btnPersonalData(View v) {
        startActivity(new Intent(this, RecordPersonalActivity.class));
    }

    public void btnAnalysis(View v) {
        startActivity(new Intent(this, RecordFoodActivity.class));
    }



}
