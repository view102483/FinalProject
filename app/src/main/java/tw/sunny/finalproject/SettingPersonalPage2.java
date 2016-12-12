package tw.sunny.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class SettingPersonalPage2 extends AppCompatActivity {
    ImageView boy;
    ImageView girl;
    android.widget.EditText height;
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
        SharedPreferences sp = getSharedPreferences("member", MODE_PRIVATE);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        pass = bundle.getString("pass");
        name = bundle.getString("name");
        EditText = bundle.getString("birthdat");

        boy = (ImageView) findViewById(R.id.imageView_boy);
        girl = (ImageView) findViewById(R.id.imageView_gril);
        height = (EditText) findViewById(R.id.editHeight);
        weight = (EditText) findViewById(R.id.editWeight);
        if("男".equals(sp.getString("member_gender", "")))
            genderClick(boy);
        else if("女".equals(sp.getString("member_gender", "")))
            genderClick(girl);

        height.setText(sp.getInt("member_height", 0) + "");
        weight.setText(sp.getInt("member_weight", 0) + "");

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
        intent.setClass(this,SettingPersonalPage3.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode == RESULT_OK) {
                setResult(RESULT_OK, data);
                finish();
            }
        }
    }
}
