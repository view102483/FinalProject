package tw.sunny.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

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


        progressBar.setMax(getSharedPreferences("member", MODE_PRIVATE)
                .getInt("member_calories", 100));
        progressBar.setProgress(0);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("uid", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                try {
                    progressBar.setProgress(0);
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        progressBar.setProgress(progressBar.getProgress() + obj.getInt("menu_calories"));
                    }
                    getSharedPreferences("member", MODE_PRIVATE).edit().putInt("member_calories_left", progressBar.getMax() - progressBar.getProgress());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        }, "http://120.126.15.112/food/record.php?act=query&simple=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), InternetModule.POST, map).execute();
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
    public void btnCareInfo(View view){
        startActivity(new Intent(this, CareInfoActivity.class));
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

    public void btnConversation(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/312453062445936/")));
    }
}
