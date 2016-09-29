package tw.sunny.finalproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by lixinting on 2016/3/29.
 */
public class RegisterActivity extends AppCompatActivity {

    EditText EditText;
    EditText email;
    EditText pass;
    EditText name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register01);
        EditText = (EditText) findViewById(R.id.editbirthday);
        email = (EditText) findViewById(R.id.editmail);
        pass = (EditText) findViewById(R.id.editPassword);
        name = (EditText) findViewById(R.id.editName);

    }
    public void btnRegister(View view){

        dataActivity();

    }

    public void datePick(View v){
        DatePickerDialog mDatePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                EditText.setText(year + "/"
                        + (monthOfYear+1) + "/" + dayOfMonth);
            }
        },1980,0,01);

        mDatePickerDialog.show();
    }
    public void dataActivity(){
        Bundle bundle = new Bundle();
        bundle.putString("email",email.getText().toString());
        bundle.putString("pass",pass.getText().toString());
        bundle.putString("name",name.getText().toString());
        bundle.putString("birthdat",EditText.getText().toString());
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, RegisterChangeImageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }


}
