package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by nagiy on 2016/4/12.
 */
public class LocationSuggestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_gps_common);

        ((ListView)findViewById(R.id.listView)).setAdapter(new ArrayAdapter<String>(this, R.layout.listitem_location_gps_restaurant, R.id.title, new String[] {"餐廳A","餐廳B","餐廳C"}));
    }
}
