package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RecordDmManageActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_dmmanage);
    }

    public void btnDMB(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordDMBActivity.class);
        startActivity(intent);


    }
    public void btnDMCheckRegular(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordDMCheckRegularActivity.class);
        startActivity(intent);


    }
    public void btnDMTable(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordDMCheckRegularActivity.class);
        startActivity(intent);


    }


}
