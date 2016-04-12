package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lixinting on 2016/3/29.
 */
public class ForgetPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
    }


    public void btnContinue(View v) {
        //do something
        Toast.makeText(ForgetPasswordActivity.this, "已寄信完成", Toast.LENGTH_SHORT).show();
        finish();
    }
}
