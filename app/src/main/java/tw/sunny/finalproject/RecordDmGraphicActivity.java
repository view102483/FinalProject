package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import tw.sunny.finalproject.module.RecordDmAnalysisActivity;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordDmGraphicActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_dm_graphic);
    }

    public void btnCheckClick(View v) {
        startActivity(new Intent(this, RecordDmAnalysisActivity.class));
    }
}
