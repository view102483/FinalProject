package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class SearchingMainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_main);
    }

    public void onYummyClick(View v) {
        startActivity(new Intent(this, SearchingActivity.class));
    }

    public void onMapClick(View v) {
        startActivity(new Intent(this, SearchingMapActivity.class));
    }
}
