package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tw.sunny.finalproject.model.Record;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class MenuDetailActivity extends BaseActivity {
    ImageView photo;
    TextView desc, location;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        photo = (ImageView) findViewById(R.id.photo);
        desc = (TextView) findViewById(R.id.desc);
        location = (TextView) findViewById(R.id.location);


        Record record = getIntent().getParcelableExtra("menu");
        if(record.getRecord_image() != null && !record.getRecord_image().isEmpty())
            Glide.with(this).load(record.getRecord_image()).into(photo);
        desc.setText(record.getPhoto_description());
        location.setText(record.getStore_location());
    }
}
