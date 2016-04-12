package tw.sunny.finalproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSignin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void btnRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
