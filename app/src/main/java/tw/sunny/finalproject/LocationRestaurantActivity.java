package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by nagiy on 2016/4/12.
 */
public class LocationRestaurantActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_gps_common);

        ((ListView)findViewById(R.id.listView)).setAdapter(new ArrayAdapter<String>(this, R.layout.listitem_location_gps_restaurant, R.id.title, new String[] {"餐廳A","餐廳B","餐廳C"}));
        ((ListView)findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(LocationRestaurantActivity.this, LocationCommonActivity.class));
            }
        });
    }
}
