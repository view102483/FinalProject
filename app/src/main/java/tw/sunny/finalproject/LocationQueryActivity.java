package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by nagiy on 2016/4/12.
 */
public class LocationQueryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_unknow_map);

        ((Spinner) findViewById(R.id.spinner)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[] {"漢堡", "下午茶", "素食"}));
    }

    public void btnMap(View v) {
        startActivity(new Intent(this, LocationSuggestActivity.class));
    }
}
