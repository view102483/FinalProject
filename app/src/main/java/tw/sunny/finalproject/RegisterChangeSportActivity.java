package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RegisterChangeSportActivity extends AppCompatActivity {



        ImageView sportlight;
        ImageView sportmid;
        ImageView sportheavy;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register03);


            sportlight = (ImageView) findViewById(R.id.imageView_sportlight);
            sportmid = (ImageView) findViewById(R.id.imageView_sportmid);
            sportheavy = (ImageView) findViewById(R.id.imageView_sportheavy);

        }

        public void genderClick(View v) {
            if(v == sportlight) {
                sportlight.setImageResource(R.drawable.mild);
                sportmid.setImageResource(R.drawable.moderatenochoice);
                sportheavy.setImageResource(R.drawable.severenochoice);

            }if (v == sportmid){
                sportlight.setImageResource(R.drawable.mildnochoice);
                sportmid.setImageResource(R.drawable.moderate);
                sportheavy.setImageResource(R.drawable.severenochoice);
            }else {
                sportlight.setImageResource(R.drawable.mildnochoice);
                sportmid.setImageResource(R.drawable.moderatenochoice);
                sportheavy.setImageResource(R.drawable.severe);
            }

        }






}
