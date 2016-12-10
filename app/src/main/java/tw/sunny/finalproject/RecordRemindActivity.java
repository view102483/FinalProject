package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordRemindActivity extends BaseActivity implements InternetModule.InternetCallback {
    ImageView std, bfk, lun, din;
    String bfks, luns, dins, suggest;

    TextView note, hey;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_remind);
        std = (ImageView) findViewById(R.id.standard);
        bfk = (ImageView) findViewById(R.id.breakfast);
        lun = (ImageView) findViewById(R.id.lunch);
        din = (ImageView) findViewById(R.id.dinner);
        note = (TextView) findViewById(R.id.note);
        hey = (TextView) findViewById(R.id.hey);

        new InternetTask(this, "http://120.126.15.112/food/catering.php?m=" + getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0")).execute();

    }

    public void btnChangeTime(View v) {
        std.setImageResource(R.drawable.standardblue);
        bfk.setImageResource(R.drawable.breakfastblue);
        lun.setImageResource(R.drawable.lunchblue);
        din.setImageResource(R.drawable.dinnerblue);

        if (v == std) {
            std.setImageResource(R.drawable.standardred);
        } else if (v == bfk) {
            bfk.setImageResource(R.drawable.breakfastred);
            hey.setText(bfks);
        } else if (v == lun) {
            lun.setImageResource(R.drawable.lunchred);
            hey.setText(luns);
        } else if (v == din) {
            din.setImageResource(R.drawable.dinnerred);
            hey.setText(dins);
        }
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONObject obj = new JSONObject(data);
            bfks = obj.isNull("catering_breakfast") ? "" : obj.getString("catering_breakfast").replace("<br>", "\n");
            luns = obj.isNull("catering_lunch") ? "" : obj.getString("catering_lunch").replace("<br>", "\n");
            dins = obj.isNull("catering_dinner") ? "" : obj.getString("catering_dinner").replace("<br>", "\n");
            suggest = obj.isNull("suggestion") ? "" : obj.getString("suggestion");

            note.setText(suggest);
            hey.setText(bfks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(String msg) {

    }


}
