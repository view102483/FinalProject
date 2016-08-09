package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lixinting on 2016/3/29.
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register01);

    }
    public void btnRegister(View view){
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, registerchangeimage.class);
        startActivity(intent);
    }

}
