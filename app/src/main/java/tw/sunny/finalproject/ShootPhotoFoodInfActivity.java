package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootPhotoFoodInfActivity extends BaseActivity  {
    private ImageView camera;
    private String photoPath;
    private EditText name;
    private final int REQ_HELP = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_wait);
        camera = (ImageView) findViewById(R.id.photo);
        name = (EditText) findViewById(R.id.name);
        photoPath = getIntent().getStringExtra("bmp");
        Glide.with(this)
                .load(photoPath)
                .placeholder(R.drawable.default_image)
                .into(camera);
    }



    public void onNotHelpClick(View v) {
        if(name.getText().length() == 0) {
            Toast.makeText(this, "請填入名稱", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        }
        Intent intent = new Intent(this, ShootPostActivity.class);
        intent.putExtra("bmp", photoPath);
        intent.putExtra("name", name.getText().toString());
        startActivity(intent);
        finish();
    }

    public void onHelpClick(View v) {
        if(name.getText().length() == 0) {
            Toast.makeText(this, "請填入名稱", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return;
        }
        Intent intent = new Intent(this, ShootFoodInfoDetailActivity.class);
        intent.putExtra("bmp", photoPath);
        intent.putExtra("name", name.getText().toString());
        startActivityForResult(intent, REQ_HELP);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_HELP && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, ShootPostActivity.class);
            intent.putExtra("bmp", photoPath);
            intent.putExtra("name", name.getText().toString());
            startActivity(intent);
            finish();
        }
    }

}
