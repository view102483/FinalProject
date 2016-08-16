package tw.sunny.finalproject;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RecordMainActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_main);

    }

    public void btnDmManage(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordMainActivity.class);
        startActivity(intent);


    }
}
