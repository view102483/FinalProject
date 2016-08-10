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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register01);
        EditText = (EditText) findViewById(R.id.editbirthday);

    }
    public void btnRegister(View view){
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, RegisterChangeImageActivity.class);
        startActivity(intent);
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


}
