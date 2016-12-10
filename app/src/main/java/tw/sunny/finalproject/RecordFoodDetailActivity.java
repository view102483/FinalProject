package tw.sunny.finalproject;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class RecordFoodDetailActivity extends BaseActivity implements InternetModule.InternetCallback {
    TextView dateBegin, dateFinish;
    LineChart chart;
    List<Record> records;
    Date firstDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_food_detail);

        dateBegin = (TextView) findViewById(R.id.dateBegin);
        dateFinish = (TextView) findViewById(R.id.dateFinish);

        String date = getIntent().getStringExtra("date");
        dateBegin.setText(date);
        dateFinish.setText(date);
        records = new ArrayList<>();
        chart = (LineChart) findViewById(R.id.detail);

        initChart();


        Map<String, String> map = new HashMap<>();
        map.put("member_id", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        map.put("begin", dateBegin.getText().toString());
        map.put("finish", dateFinish.getText().toString());
        showLoadingDialog();
        new InternetTask(this, "http://120.126.15.112/food/record.php?act=getbydate", InternetModule.POST, map).execute();
    }

    public void btnDateBegin(View v) {
        String[] temp = dateBegin.getText().toString().split("-");
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateBegin.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                Map<String, String> map = new HashMap<>();
                map.put("member_id", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
                map.put("begin", dateBegin.getText().toString());
                map.put("finish", dateFinish.getText().toString());
                showLoadingDialog();
                new InternetTask(RecordFoodDetailActivity.this, "http://120.126.15.112/food/record.php?act=getbydate", InternetModule.POST, map).execute();

            }
        }, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        dialog.show();
    }

    public void btnDateFinish(View v) {
        String[] temp = dateFinish.getText().toString().split("-");
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateFinish.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                Map<String, String> map = new HashMap<>();
                map.put("member_id", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
                map.put("begin", dateBegin.getText().toString());
                map.put("finish", dateFinish.getText().toString());
                showLoadingDialog();
                new InternetTask(RecordFoodDetailActivity.this, "http://120.126.15.112/food/record.php?act=getbydate", InternetModule.POST, map).execute();
            }
        }, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        dialog.show();
    }

    public void initChart() {
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        LineData linedata = new LineData();
        chart.setData(linedata);
        chart.getLegend().setWordWrapEnabled(true);
        chart.invalidate();
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (firstDate != null) {
                    Calendar calc = Calendar.getInstance();
                    calc.setTime(firstDate);
                    calc.add(Calendar.DATE, (int) value);
                    return new SimpleDateFormat("MM/dd").format(calc.getTime());
                }

                else
                    return "";

            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

    }

    public void refreshChart() {
        List<Entry> entryCalories = new ArrayList<>();
        List<Entry> entryCarbohydrate = new ArrayList<>();
        List<Entry> entrySugar = new ArrayList<>();
        List<Entry> entryFat = new ArrayList<>();
        List<Entry> entrySaturatedfat = new ArrayList<>();
        List<Entry> entryTransfat = new ArrayList<>();

        List<Entry> entryProtein = new ArrayList<>();
        List<Entry> entrySoduim = new ArrayList<>();

        Map<String, int[]> map = new HashMap<>();
        final String[] ls = {"卡路里", "碳水化合物", "糖類", "脂肪", "飽和脂肪", "反式脂肪", "蛋白質", "鈉"};

        for (Record record : records) {
            String key = record.getRecord_datetime().substring(0, 10);
            if (!map.containsKey(key))
                map.put(key, new int[8]);

            if (record.getMenu() != null) {
                map.get(key)[0] += record.getMenu().getMenu_calories();
                map.get(key)[1] += record.getMenu().getMenu_carbohydrate();
                map.get(key)[2] += record.getMenu().getMenu_sugar();
                map.get(key)[3] += record.getMenu().getMenu_fat();
                map.get(key)[4] += record.getMenu().getMenu_saturatedfat();
                map.get(key)[5] += record.getMenu().getMenu_transfat();
                map.get(key)[6] += record.getMenu().getMenu_protein();
                map.get(key)[7] += record.getMenu().getMenu_soduim();
            }
        }
        int cnt = 0;
        firstDate = null;
        for (String key : map.keySet()) {
            if (map.get(key).length > 0) {
                try {
                    if (firstDate == null)
                        firstDate = new SimpleDateFormat("yyyy-MM-dd").parse(key);
                    int xVal = getDateDiffDay(firstDate, new SimpleDateFormat("yyyy-MM-dd").parse(key));
                    entryCalories.add(new Entry(xVal, map.get(key)[0]));
                    entryCarbohydrate.add(new Entry(xVal, map.get(key)[1]));
                    entrySugar.add(new Entry(xVal, map.get(key)[2]));
                    entryFat.add(new Entry(xVal, map.get(key)[3]));
                    entrySaturatedfat.add(new Entry(xVal, map.get(key)[4]));
                    entryTransfat.add(new Entry(xVal, map.get(key)[5]));
                    entryProtein.add(new Entry(xVal, map.get(key)[6]));
                    entrySoduim.add(new Entry(xVal, map.get(key)[7]));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                cnt++;
            }
        }

        LineData data = chart.getLineData();
        if (data != null) {
            while (data.getDataSetCount() > 0)
                data.removeDataSet(0);

            LineDataSet setCalories = new LineDataSet(entryCalories, ls[0]);
            LineDataSet setCarbohydrate = new LineDataSet(entryCarbohydrate, ls[1]);
            LineDataSet setSugar = new LineDataSet(entrySugar, ls[2]);
            LineDataSet setFat = new LineDataSet(entryFat, ls[3]);
            LineDataSet setSaturatedfat = new LineDataSet(entrySaturatedfat, ls[4]);
            LineDataSet setTransfat = new LineDataSet(entryTransfat, ls[5]);
            LineDataSet setProtein = new LineDataSet(entryProtein, ls[6]);
            LineDataSet setSoduim = new LineDataSet(entrySoduim, ls[7]);

            int idx = 0;
            setCalories.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setCalories.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setCarbohydrate.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setCarbohydrate.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setSugar.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setSugar.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setFat.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setFat.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setSaturatedfat.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setSaturatedfat.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setTransfat.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setTransfat.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setProtein.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setProtein.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            setSoduim.setColor(Color.argb(255, idx * 255/ 8, 0, 192));
            setSoduim.setCircleColor(Color.argb(255, idx++ * 255/ 8, 0, 192));

            data.addDataSet(setCalories);
            data.addDataSet(setCarbohydrate);
            data.addDataSet(setSugar);
            data.addDataSet(setFat);
            data.addDataSet(setSaturatedfat);
            data.addDataSet(setTransfat);
            data.addDataSet(setProtein);
            data.addDataSet(setSoduim);

            chart.notifyDataSetChanged();
            chart.getXAxis().setGranularity(1f);
            chart.animateXY(50 * records.size(), 50 * records.size());
            if (cnt > 0)
                Toast.makeText(this, "SKIP " + cnt + " Records.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONArray array = new JSONArray(data);
            records.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Record rec = new Record(object);
                records.add(rec);

                refreshChart();

            }
        } catch (Exception e) {
            onFail(data);
        } finally {


            dismissLoadingDialog();

        }
    }

    @Override
    public void onFail(String msg) {

    }

    public int getDateDiffDay(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
