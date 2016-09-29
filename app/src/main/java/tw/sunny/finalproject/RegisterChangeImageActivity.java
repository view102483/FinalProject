package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by lixinting on 2016/8/9.
 */
public class RegisterChangeImageActivity extends AppCompatActivity {


    ImageView boy;
    ImageView girl;
    EditText height;
    EditText weight;
    String gender;
    String email;
    String pass;
    String name;
    String EditText;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register02);

            Bundle bundle = getIntent().getExtras();
            email = bundle.getString("email");
            pass = bundle.getString("pass");
            name = bundle.getString("name");
            EditText = bundle.getString("birthdat");

            boy = (ImageView) findViewById(R.id.imageView_boy);
            girl = (ImageView) findViewById(R.id.imageView_gril);
            height = (EditText) findViewById(R.id.editHeight);
            weight = (EditText) findViewById(R.id.editWeight);

            genderClick(girl);
        }

    public void genderClick(View v) {
        if(v == boy) {
            boy.setImageResource(R.drawable.boy);
            girl.setImageResource(R.drawable.girlnochoice);
            gender = "man";

        }else {
            girl.setImageResource(R.drawable.girl);
            boy.setImageResource(R.drawable.boynochoice);
            gender = "woman";
                }
    }



    public void btnNext(View view){

        dataActivity();
    }

    public  void  dataActivity(){
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        bundle.putString("pass",pass);
        bundle.putString("name",name);
        bundle.putString("birthdat",EditText);
        bundle.putString("gender",gender);
        bundle.putString("height",height.getText().toString());
        bundle.putString("weight",weight.getText().toString());


        Intent intent = new Intent();
        intent.setClass(this,RegisterChangeSportActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }




}
