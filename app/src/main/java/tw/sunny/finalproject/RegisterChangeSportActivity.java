package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RegisterChangeSportActivity extends AppCompatActivity {



        ImageView sportlight;
        ImageView sportmid;
        ImageView sportheavy;
        TextView tw;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register03);


            sportlight = (ImageView) findViewById(R.id.imageView_sportlight);
            sportmid = (ImageView) findViewById(R.id.imageView_sportmid);
            sportheavy = (ImageView) findViewById(R.id.imageView_sportheavy);
            tw = (TextView) findViewById(R.id.textsporttw);

        }

        public void sportClick(View v) {
            if(v == sportlight) {
                sportlight.setImageResource(R.drawable.mild);
                sportmid.setImageResource(R.drawable.moderatenochoice);
                sportheavy.setImageResource(R.drawable.severenochoice);
                tw.setText("輕度：長時間坐著(學生、上班族)");

            }else if (v == sportmid){
                sportlight.setImageResource(R.drawable.mildnochoice);
                sportmid.setImageResource(R.drawable.moderate);
                sportheavy.setImageResource(R.drawable.severenochoice);
                tw.setText("中度：長時間站立、走動(服務生、專櫃人員)");

            }else {
                sportlight.setImageResource(R.drawable.mildnochoice);
                sportmid.setImageResource(R.drawable.moderatenochoice);
                sportheavy.setImageResource(R.drawable.severe);
                tw.setText("重度：每週固定時間運動，每次長達1小時以上");

            }

        }






}
