package tw.sunny.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.AccessToken;

import java.util.HashMap;
import java.util.Map;

import tw.sunny.finalproject.model.LoginUser;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;


/**
 * Created by lixinting on 2016/3/29.
 */
public class LoginActivity extends AppCompatActivity implements InternetModule.InternetCallback {

    LoginButton btnFbLogin;
    CallbackManager callbackManager;
    SharedPreferences sp;
    EditText account, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("facebook", MODE_PRIVATE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        account = (EditText) findViewById(R.id.editAccount);
        password = (EditText) findViewById(R.id.editPassword);
        btnFbLogin = (LoginButton)findViewById(R.id.login_button);
        btnFbLogin.setPublishPermissions("publish_actions");
        btnFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "token=" + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                //sp.edit().putString("fb_token", loginResult.getAccessToken().getToken()).apply();
                AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancel login.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "some thing error!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void btnForgotPassword(View v) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    public void btnLogin(View v) {
        // get map data
        Map<String, String> data = new HashMap<>();
        data.put("user", account.getText().toString());
        data.put("pass", password.getText().toString());

        new InternetTask(this, "http://120.126.15.112/food/member.php?act=login", InternetModule.POST, data).execute();
//        Intent intent = new Intent();
//        intent.setClass(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onSuccess(String data) {
//        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, "Fail:" + msg, Toast.LENGTH_SHORT).show();
    }
}
