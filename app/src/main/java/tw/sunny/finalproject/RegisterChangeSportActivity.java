package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RegisterChangeSportActivity extends AppCompatActivity {


    ImageView sportLight;
    ImageView sportMid;
    ImageView sportHeavy;
    ImageView sportVeryHeavy;
    EditText kcalText;
    TextView tw;
    double sport;
    double kcal;
    String gender;
    String email;
    String pass;
    String name;
    String birthdat;
    String height;
    String weight;

    CheckBox dm, hbp, sz, ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register03);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        pass = bundle.getString("pass");
        name = bundle.getString("name");
        birthdat = bundle.getString("birthdat");
        gender = bundle.getString("gender");
        height = bundle.getString("height");
        weight = bundle.getString("weight");

        sportLight = (ImageView) findViewById(R.id.sh_light);
        sportMid = (ImageView) findViewById(R.id.sh_middle);
        sportHeavy = (ImageView) findViewById(R.id.sh_heavy);
        sportVeryHeavy = (ImageView) findViewById(R.id.sh_veryheavy);
        tw = (TextView) findViewById(R.id.textsporttw);
        kcalText = (EditText) findViewById(R.id.editcal);
        dm = (CheckBox) findViewById(R.id.dm);
        hbp = (CheckBox) findViewById(R.id.hbp);
        sz = (CheckBox) findViewById(R.id.sh);
        ds = (CheckBox) findViewById(R.id.ds);
        sportClick(sportLight);
    }

    public void sportClick(View v) {
        if (v == sportLight) {
            sportLight.setImageResource(R.drawable.mild);
            sportMid.setImageResource(R.drawable.walknochoice);
            sportHeavy.setImageResource(R.drawable.moderatenochoice);
            sportVeryHeavy.setImageResource(R.drawable.severenochoice);
            tw.setText("輕度：長時間坐著(學生、上班族)");
            sport = 1.4;

        } else if (v == sportMid) {
            sportLight.setImageResource(R.drawable.mildnochoice);
            sportMid.setImageResource(R.drawable.walk);
            sportHeavy.setImageResource(R.drawable.moderatenochoice);
            sportVeryHeavy.setImageResource(R.drawable.severenochoice);
            tw.setText("中度：長時間站立、走動(服務生、專櫃人員)");
            sport = 1.7;

        } else if (v == sportHeavy) {
            sportLight.setImageResource(R.drawable.mildnochoice);
            sportMid.setImageResource(R.drawable.walknochoice);
            sportHeavy.setImageResource(R.drawable.moderate);
            sportVeryHeavy.setImageResource(R.drawable.severenochoice);
            tw.setText("尚未設定");
            sport = 1.7;
        } else {
            sportLight.setImageResource(R.drawable.mildnochoice);
            sportMid.setImageResource(R.drawable.walknochoice);
            sportHeavy.setImageResource(R.drawable.moderatenochoice);
            sportVeryHeavy.setImageResource(R.drawable.severe);
            tw.setText("重度：每週固定時間運動，每次長達1小時以上");
            sport = 1.9;
        }
        try {
            kcal = calcCalEating(
                    Double.parseDouble(height) / 100,
                    Double.parseDouble(weight),
                    sport,
                    gender,
                    calcAge(birthdat)
            );

            kcalText.setText("" + Math.round(kcal));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public double calcCalEating(double height, double weight, double sport, String gender, int age) {
        double rmr = (gender.equals("man") ? 25.44 : 23.1811)
                - 0.01807 * age
                - 0.1448 * weight
                + 0.03797 * height * 100;
        double healthWeight = 22 * (height * height);
        return sport * rmr * healthWeight;
    }

    public int calcAge(String brndat) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        Date brnDate = null;
        try {
            brnDate = sdf.parse(brndat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        long diffInSeconds = now.getTime() - brnDate.getTime();
        diffInSeconds /= 1000L;
        long yy = diffInSeconds / (365 * 24 * 60 * 60);
        Long y = Long.valueOf(yy);
        return y.intValue();
    }

    public void btnRegisterClick(View v) {
        double k;
        try {
            k = Double.parseDouble(kcalText.getText().toString());
            if (kcal - k > 1200) {
                Toast.makeText(this, "熱量差距太大！(>1200)", Toast.LENGTH_SHORT).show();
                return;
            } else if (kcal < 1000) {
                Toast.makeText(this, "熱量過低！(<1000)", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> map = new HashMap<>();
            map.put("user", email);
            map.put("pass", pass);
            if (!name.isEmpty()) map.put("name", name);
            if (!birthdat.isEmpty()) map.put("birthdat", birthdat);
            if (!gender.isEmpty()) map.put("gender", gender);
            if (!email.isEmpty()) map.put("email", email);
            if (!height.isEmpty()) map.put("height", height);
            if (!weight.isEmpty()) map.put("weight", weight);
            if (sport != 0) map.put("exercise", "" + sport);
            if (k != 0) map.put("calories", "" + k);
            map.put("dm", dm.isChecked() ? "是" : "否");
            map.put("hbp", hbp.isChecked() ? "是" : "否");
            map.put("sz", sz.isChecked() ? "是" : "否");
            map.put("ds", ds.isChecked() ? "是" : "否");

            new InternetTask(new InternetModule.InternetCallback() {
                @Override
                public void onSuccess(String data) {
                    if (data.startsWith("OK")) {
                        setResult(RESULT_OK, new Intent());
                        Toast.makeText(RegisterChangeSportActivity.this, "註冊完成", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFail(String msg) {

                }
            }, "http://120.126.15.112/food/member.php?act=register", InternetModule.POST, map).execute();

        } catch (Exception e) {
            Toast.makeText(this, "輸入框錯誤！", Toast.LENGTH_SHORT).show();
        }
    }


}
