package tw.sunny.finalproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RecordDMBActivity extends BaseActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm);

        ((TextView)findViewById(R.id.date)).setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void onDatePicker(View v) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ((EditText)findViewById(R.id.date)).setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void onSubmit(View v) {
        if(((TextView)findViewById(R.id.date)).getText().length() == 0) {
            Toast.makeText(this, "需要時間", Toast.LENGTH_SHORT).show();
            findViewById(R.id.date).requestFocus();
            return;
        }

        if(((TextView)findViewById(R.id.weight)).getText().length() == 0) {
            Toast.makeText(this, "需要體重", Toast.LENGTH_SHORT).show();
            findViewById(R.id.weight).requestFocus();
            return;
        }

        showLoadingDialog("Uploading", "資料上傳中");
        Map<String, String> map = new HashMap<>();
        map.put("mber_id", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        map.put("date", ((TextView)findViewById(R.id.date)).getText().toString());
        map.put("member_weight", ((TextView)findViewById(R.id.weight)).getText().toString());
        map.put("hba1c", ((TextView)findViewById(R.id.hba1c)).getText().toString());
        map.put("tcholesterol", ((TextView)findViewById(R.id.tcholesterol)).getText().toString());
        map.put("tg", ((TextView)findViewById(R.id.tg)).getText().toString());
        map.put("ldlc", ((TextView)findViewById(R.id.ldlc)).getText().toString());
        map.put("hdlc", ((TextView)findViewById(R.id.hdlc)).getText().toString());
        map.put("cr", ((TextView)findViewById(R.id.cr)).getText().toString());
        map.put("pro", ((TextView)findViewById(R.id.pro)).getText().toString());
        map.put("acr", ((TextView)findViewById(R.id.acr)).getText().toString());
        map.put("sbp", ((TextView)findViewById(R.id.sbp)).getText().toString());
        map.put("dbp", ((TextView)findViewById(R.id.dbp)).getText().toString());

        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                if(!data.equals("ok")) {
                    onFail("上傳失敗");
                    return;
                }
                Toast.makeText(RecordDMBActivity.this, "記錄成功", Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {
                dismissLoadingDialog();
                Toast.makeText(RecordDMBActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, "http://120.126.15.112/food/dmb.php", InternetModule.POST, map).execute();
    }
}
