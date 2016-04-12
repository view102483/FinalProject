package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/3/29.
 */
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void btnForgotPassword(View v) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    public void btnLogin(View v) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
