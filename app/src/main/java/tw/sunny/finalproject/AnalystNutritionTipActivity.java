package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lixinting on 2016/8/23.
 */
public class AnalystNutritionTipActivity extends AppCompatActivity {

    ImageView standard;
    ImageView breakfast;
    ImageView lunch;
    ImageView dinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyst_nutrition);

        standard = (ImageView) findViewById(R.id.image_standard);
        breakfast = (ImageView) findViewById(R.id.image_breakfast);
        lunch = (ImageView) findViewById(R.id.image_lunch);
        dinner = (ImageView) findViewById(R.id.image_dinner);

    }

    public void mealClick(View v) {
        if (v == standard) {
            standard.setImageResource(R.drawable.standardred);
            breakfast.setImageResource(R.drawable.breakfastblue);
            lunch.setImageResource(R.drawable.lunchblue);
            dinner.setImageResource(R.drawable.dinnerblue);

        } else if (v == breakfast) {
            standard.setImageResource(R.drawable.standardblue);
            breakfast.setImageResource(R.drawable.breakfastred);
            lunch.setImageResource(R.drawable.lunchblue);
            dinner.setImageResource(R.drawable.dinnerblue);

        } else if (v ==lunch){
            standard.setImageResource(R.drawable.standardblue);
            breakfast.setImageResource(R.drawable.breakfastblue);
            lunch.setImageResource(R.drawable.lunchred);
            dinner.setImageResource(R.drawable.dinnerblue);

        } else {
            standard.setImageResource(R.drawable.standardblue);
            breakfast.setImageResource(R.drawable.breakfastblue);
            lunch.setImageResource(R.drawable.lunchblue);
            dinner.setImageResource(R.drawable.dinnerred);
        }

    }
    public void btnHome(View view){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
