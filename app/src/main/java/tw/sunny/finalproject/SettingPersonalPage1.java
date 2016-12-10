package tw.sunny.finalproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class SettingPersonalPage1 extends AppCompatActivity {
    EditText EditText;
    EditText email;
    EditText pass;
    EditText name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register01);
        EditText = (EditText) findViewById(R.id.editbirthday);
        email = (EditText) findViewById(R.id.editmail);
        pass = (EditText) findViewById(R.id.editPassword);
        name = (EditText) findViewById(R.id.editName);
        findViewById(R.id.TextHelloNew).setVisibility(View.INVISIBLE);

    }
    public void btnRegister(View view){
        Bundle bundle = new Bundle();
        bundle.putString("email",email.getText().toString());
        bundle.putString("pass",pass.getText().toString());
        bundle.putString("name",name.getText().toString());
        bundle.putString("birthdat",EditText.getText().toString());
        Intent intent = new Intent();
        intent.setClass(SettingPersonalPage1.this, SettingPersonalPage2.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
