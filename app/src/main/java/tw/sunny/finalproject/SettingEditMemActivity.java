package tw.sunny.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Created by lixinting on 2016/8/5.
 */

public class SettingEditMemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_editmember);

    }

    public void btnResetPass(View v) {
        final EditText edit = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("請輸入新密碼")
                .setView(edit)
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(edit.getText().length() <= 0) {
                            Toast.makeText(SettingEditMemActivity.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Map<String, String> map = new HashMap<>();
                        map.put("uid", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
                        map.put("pass", edit.getText().toString());
                        new InternetTask(new InternetModule.InternetCallback() {
                            @Override
                            public void onSuccess(String data) {
                                if(data.startsWith("OK"))
                                    Toast.makeText(SettingEditMemActivity.this, "修改完成", Toast.LENGTH_SHORT).show();
                                else
                                    onFail(data);
                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        }, "http://120.126.15.112/food/member.php?act=cp", InternetModule.POST, map).execute();

                        Toast.makeText(SettingEditMemActivity.this, "修改完成", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    public void btnClearRecords(View v) {
        new AlertDialog.Builder(this)
                .setTitle("確認")
                .setMessage("是否要清除所有資料？（無法復原）")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("uid", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
                        new InternetTask(new InternetModule.InternetCallback() {
                            @Override
                            public void onSuccess(String data) {
                                if(data.startsWith("OK"))
                                    Toast.makeText(SettingEditMemActivity.this, "清除完畢", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        }, "http://120.126.15.112/food/record.php?act=clear", InternetModule.POST, map).execute();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    public void btnEditPersonalData(View v) {
        startActivity(new Intent(this, SettingPersonalPage1.class));
    }

    public void btnHome(View view){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


