package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordFoodDetailActivity extends BaseActivity {
    TextView dateBegin, dateFinish;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_food_detail);

        dateBegin = (TextView) findViewById(R.id.dateBegin);
        dateFinish = (TextView) findViewById(R.id.dateFinish);

        String date = getIntent().getStringExtra("date");
        dateBegin.setText(date);
        dateFinish.setText(date);
    }
}
