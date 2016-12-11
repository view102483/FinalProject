package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import tw.sunny.finalproject.model.CareInfo;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class CareInfoDetailActivity extends BaseActivity {
    TextView title, info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careinfo_detail);

        title = (TextView) findViewById(R.id.title);
        info = (TextView) findViewById(R.id.info);
        CareInfo inf = getIntent().getParcelableExtra("info");
        title.setText(inf.getTitle());
        info.setText(inf.getInfo());
    }
}
