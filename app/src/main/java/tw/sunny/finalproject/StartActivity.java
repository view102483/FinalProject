package tw.sunny.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSignin(View view) {
//        startActivity(new Intent(this, LoginActivity.class));
        startActivity(new Intent(this, MainActivity.class));
    }
    public void btnRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
