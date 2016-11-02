package tw.sunny.finalproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.sunny.finalproject.model.Record;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordFoodActivity extends BaseActivity implements InternetModule.InternetCallback{
    TextView date;
    Calendar calendar;
    ListView listView;
    List<Record> records;
    ListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_food_daily);
        date = (TextView) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        listView = (ListView) findViewById(R.id.listView);
        date.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));

        records = new ArrayList<>();
        adapter = new ListAdapter(this, records);
        listView.setAdapter(adapter);


        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("uid", "1");
        new InternetTask(this, "http://120.126.15.112/food/record.php?act=query&simple=1", InternetModule.POST, map).execute();
    }

    public void btnRemindClick(View v) {
        startActivity(new Intent(this, RecordRemindActivity.class));
    }

    public void btnAnalysisClick(View v) {

    }

    public void btnDatePickerClick(View v) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONArray array = new JSONArray(data);
            for(int i=0; i<array.length(); i++) {
                records.add(new Record(array.getJSONObject(i)));
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            dismissLoadingDialog();
        }
    }

    @Override
    public void onFail(String msg) {
        dismissLoadingDialog();
    }


    private class ListAdapter extends BaseAdapter {
        Context context;
        List<Record> records;

        public ListAdapter(Context context, List<Record> records) {
            this.context = context;
            this.records = records;
        }

        @Override
        public int getCount() {
            return records.size();
        }

        @Override
        public Object getItem(int position) {
            return records.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.record_analysis_list_item, null);
            }
            return v;
        }
    }

}
