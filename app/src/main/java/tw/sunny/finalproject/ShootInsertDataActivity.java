package tw.sunny.finalproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootInsertDataActivity extends BaseActivity {
    ImageView photo;
    EditText foodname;
    private final int REQ_PICKUP = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_photo_insertdata);
        photo = (ImageView) findViewById(R.id.imageView5);
        foodname = (EditText) findViewById(R.id.textfood);

        photo.setImageBitmap(BitmapFactory.decodeFile(getIntent().getStringExtra("bmp")));
    }


    public void takeFoodInfo(View v) {
        startActivityForResult(new Intent(this, ShootFoodInfoDetailActivity.class), REQ_PICKUP);
    }


}
