package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lixinting on 2016/8/9.
 */
public class RegisterChangeImageActivity extends AppCompatActivity {


    ImageView boy;
    ImageView girl;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register02);


            boy = (ImageView) findViewById(R.id.imageView_boy);
            girl = (ImageView) findViewById(R.id.imageView_gril);

        }

            public void genderClick(View v) {
                if(v == boy) {
                    boy.setImageResource(R.drawable.boy);
                    girl.setImageResource(R.drawable.girlnochoice);

                }else {
                    girl.setImageResource(R.drawable.girl);
                    boy.setImageResource(R.drawable.boynochoice);
                }
            }

    public void btnNext(View view){
        Intent intent = new Intent();
        intent.setClass(this,RegisterChangeSportActivity.class);
        startActivity(intent);
    }




}
