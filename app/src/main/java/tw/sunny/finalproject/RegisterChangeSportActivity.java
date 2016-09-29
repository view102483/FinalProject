package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.Duration;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RegisterChangeSportActivity extends AppCompatActivity {


    ImageView sportlight;
    ImageView sportmid;
    ImageView sportheavy;
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

        sportlight = (ImageView) findViewById(R.id.imageView_sportlight);
        sportmid = (ImageView) findViewById(R.id.imageView_sportmid);
        sportheavy = (ImageView) findViewById(R.id.imageView_sportheavy);
        tw = (TextView) findViewById(R.id.textsporttw);
        kcalText = (EditText) findViewById(R.id.editcal);

        sportClick(sportlight);
    }

    public void sportClick(View v) {
        if (v == sportlight) {
            sportlight.setImageResource(R.drawable.mild);
            sportmid.setImageResource(R.drawable.moderatenochoice);
            sportheavy.setImageResource(R.drawable.severenochoice);
            tw.setText("輕度：長時間坐著(學生、上班族)");
            sport = 1.4;

        } else if (v == sportmid) {
            sportlight.setImageResource(R.drawable.mildnochoice);
            sportmid.setImageResource(R.drawable.moderate);
            sportheavy.setImageResource(R.drawable.severenochoice);
            tw.setText("中度：長時間站立、走動(服務生、專櫃人員)");
            sport = 1.7;

        } else {
            sportlight.setImageResource(R.drawable.mildnochoice);
            sportmid.setImageResource(R.drawable.moderatenochoice);
            sportheavy.setImageResource(R.drawable.severe);
            tw.setText("重度：每週固定時間運動，每次長達1小時以上");
            sport = 1.9;
        }

        kcal = calcCalEating(
                Double.parseDouble(height) / 100,
                Double.parseDouble(weight),
                sport,
                gender,
                calcAge(birthdat)
        );

        kcalText.setText("" + Math.round(kcal));


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
        long yy = diffInSeconds / (365 * 24 *  60 * 60);
        Long y = Long.valueOf(yy);
        return y.intValue();
    }

    public void btnRegisterClick(View v) {
        double k;
        try {
            k = Double.parseDouble(kcalText.getText().toString());
            if(kcal - k > 1200) {
                Toast.makeText(this, "熱量差距太大！(>1200)", Toast.LENGTH_SHORT).show();
                return;
            } else if(kcal < 1000) {
                Toast.makeText(this, "熱量過低！(<1000)", Toast.LENGTH_SHORT).show();
                return;
            }

            //TODO: next page

        } catch(Exception e) {
            Toast.makeText(this, "輸入框錯誤！", Toast.LENGTH_SHORT).show();
        }
    }


}
