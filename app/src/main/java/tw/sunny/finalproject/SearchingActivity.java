package tw.sunny.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

/**
 * Created by lixinting on 2016/4/12.
 */
public class SearchingActivity extends AppCompatActivity {
    @Override



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        Spinner SpinnerArea = (Spinner) findViewById(R.id.SpinnerArea);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterarea = ArrayAdapter.createFromResource(this,
                R.array.SpinnerArea, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterarea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        SpinnerArea.setAdapter(adapterarea);

        Spinner SpinnerTime = (Spinner) findViewById(R.id.SpinnerTime);
        ArrayAdapter<CharSequence> adaptertime = ArrayAdapter.createFromResource(this,
                R.array.SpinnerTime, android.R.layout.simple_spinner_item);
        adaptertime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerTime.setAdapter(adaptertime);

        Spinner Spinner = (Spinner) findViewById(R.id.Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
    }
    public void btnSearchingToPhoto(View view){
        Intent intent = new Intent();
        intent.setClass(this, SearchingPhotoActivity.class);
        startActivity(intent);
    }

    public void SpinnerArea(){

    }


}
