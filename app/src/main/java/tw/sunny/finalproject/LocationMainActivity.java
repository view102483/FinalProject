package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by nagiy on 2016/4/12.
 */
public class LocationMainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_main);
    }

    public void btnGpsPlan(View v) {
        startActivity(new Intent(this, LocationRestaurantActivity.class));
    }


    public void btnUnknownPlan(View v) {
        startActivity(new Intent(this, LocationQueryActivity.class));
    }
}
