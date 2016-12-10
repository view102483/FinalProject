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
public class RecordDMCheckRegularActivity extends BaseActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_dm_checkregular);

        ((TextView)findViewById(R.id.date)).setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void onBtnCalendarClick(View v) {
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
        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("m", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        map.put("BS_date", ((TextView)findViewById(R.id.date)).getText().toString());
        map.put("BS_befb", ((TextView)findViewById(R.id.break_sugar_pre)).getText().toString());
        map.put("BS_afterb", ((TextView)findViewById(R.id.break_sugar_post)).getText().toString());
        map.put("insulin_b", ((TextView)findViewById(R.id.break_insulin)).getText().toString());
        map.put("BS_b", ((TextView)findViewById(R.id.break_press)).getText().toString());
        map.put("BS_befl", ((TextView)findViewById(R.id.launch_sugar_pre)).getText().toString());
        map.put("BS_afterl", ((TextView)findViewById(R.id.launch_sugar_post)).getText().toString());
        map.put("insulin_l", ((TextView)findViewById(R.id.launch_insulin)).getText().toString());
        map.put("BS_l", ((TextView)findViewById(R.id.launch_press)).getText().toString());
        map.put("BS_befd", ((TextView)findViewById(R.id.dinner_sugar_pre)).getText().toString());
        map.put("BS_afterd", ((TextView)findViewById(R.id.dinner_sugar_post)).getText().toString());
        map.put("insulin_d", ((TextView)findViewById(R.id.dinner_insulin)).getText().toString());
        map.put("BS_d", ((TextView)findViewById(R.id.dinner_press)).getText().toString());
        map.put("BS_befsleep", ((TextView)findViewById(R.id.sleep_sugar)).getText().toString());
        map.put("insulin_befsleep", ((TextView)findViewById(R.id.sleep_insulin)).getText().toString());
        map.put("bp_befsleep", ((TextView)findViewById(R.id.sleep_press)).getText().toString());
        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                if(!data.equals("OK")) {
                    onFail(data);
                    return;
                }
                dismissLoadingDialog();
                Toast.makeText(RecordDMCheckRegularActivity.this, "記錄成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {
                dismissLoadingDialog();
                Toast.makeText(RecordDMCheckRegularActivity.this, "something error:" + msg, Toast.LENGTH_SHORT).show();
            }
        }, "http://120.126.15.112/food/bsi.php", InternetModule.POST, map).execute();
    }
}
