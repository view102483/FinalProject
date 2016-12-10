package tw.sunny.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.sunny.finalproject.model.Bs;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordDmGraphicActivity extends BaseActivity implements InternetModule.InternetCallback {
    LineChart chart;
    List<Bs> bslist;
    public static final int BREAKFAST = 0;
    public static final int LAUNCH = 1;
    public static final int DINNER = 2;
    public static final int BEFORESLEEP = 3;

    private int timePicked = 0;
    ImageView breakfast, launch, dinner, beforesleep;
    LinearLayout before, after;
    TextView tbefore, tafter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_dm_graphic);
        chart = (LineChart) findViewById(R.id.chart);
        bslist = new ArrayList<>();

        breakfast = (ImageView) findViewById(R.id.imgBreakfast);
        launch = (ImageView) findViewById(R.id.imgLaunch);
        dinner = (ImageView) findViewById(R.id.imgDinner);
        beforesleep = (ImageView) findViewById(R.id.imgBeforeSleep);

        initLineChart();
        showLoadingDialog();
        new InternetTask(this, "http://120.126.15.112/food/bs.php?m=" + getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0")).execute();
    }


    public void btnTimeChange(View v) {
        breakfast.setImageResource(R.drawable.breakfastblue);
        launch.setImageResource(R.drawable.lunchblue);
        dinner.setImageResource(R.drawable.dinnerblue);
        if (v == breakfast) {
            timePicked = BREAKFAST;
            breakfast.setImageResource(R.drawable.breakfastred);
        } else if (v == launch) {
            timePicked = LAUNCH;
            launch.setImageResource(R.drawable.lunchred);
        } else if (v == dinner) {
            timePicked = DINNER;
            dinner.setImageResource(R.drawable.dinnerred);
        } else if (v == beforesleep) {
            timePicked = BEFORESLEEP;
        }

        try {
            showLoadingDialog();
            doChangeLineChart();
            dismissLoadingDialog();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void btnCheckClick(View v) {
        startActivity(new Intent(this, RecordDmAnalysisActivity.class));
    }

    public void initLineChart() {
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        LineData linedata = new LineData();
        chart.setData(linedata);
        chart.invalidate();
    }

    public void doChangeLineChart() throws ParseException {

        List<Entry> entrySugarBefore = new ArrayList<>();
        List<Entry> entrySugarAfter = new ArrayList<>();
        List<Entry> entryInsulin = new ArrayList<>();
        List<Entry> entryBs = new ArrayList<>();

        Date firstDay = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (bslist.size() > 0)
            firstDay = sdf.parse(bslist.get(0).getBS_date());

        for (Bs bs : bslist) {
            int xVal = getDateDiffDay(firstDay, sdf.parse(bs.getBS_date()));
            switch (timePicked) {
                case BREAKFAST:
                    entrySugarBefore.add(new Entry(xVal, bs.getBS_befb()));
                    entrySugarAfter.add(new Entry(xVal, bs.getBS_afterb()));
                    entryInsulin.add(new Entry(xVal, bs.getInsulin_b()));
                    entryBs.add(new Entry(xVal, bs.getBS_b()));
                    break;
                case LAUNCH:
                    entrySugarBefore.add(new Entry(xVal, bs.getBS_befl()));
                    entrySugarAfter.add(new Entry(xVal, bs.getBS_afterl()));
                    entryInsulin.add(new Entry(xVal, bs.getInsulin_l()));
                    entryBs.add(new Entry(xVal, bs.getBS_l()));
                    break;
                case DINNER:
                    entrySugarBefore.add(new Entry(xVal, bs.getBS_befd()));
                    entrySugarAfter.add(new Entry(xVal, bs.getBS_afterd()));
                    entryInsulin.add(new Entry(xVal, bs.getInsulin_d()));
                    entryBs.add(new Entry(xVal, bs.getBS_d()));

                    break;
                case BEFORESLEEP:
                    entrySugarBefore.add(new Entry(xVal, bs.getBp_befsleep()));
                    entryInsulin.add(new Entry(xVal, bs.getInsulin_befsleep()));
                    entryBs.add(new Entry(xVal, bs.getBS_befsleep()));
                    break;
            }
        }


        LineData data = chart.getData();
        if (data != null||data.getDataSetCount() == 0) {
            while (data.getDataSetCount() > 0)
                data.removeDataSet(0);

            LineDataSet before = new LineDataSet(entrySugarBefore, timePicked != BEFORESLEEP ? "飯前" : "睡前");
            before.setColor(Color.RED);
            before.setLineWidth(2);
            before.setCircleColor(Color.RED);
            data.addDataSet(before);

            if (timePicked != BEFORESLEEP) {
                LineDataSet after = new LineDataSet(entrySugarAfter, "飯後");
                after.setColor(Color.BLUE);
                after.setLineWidth(2);
                after.setCircleColor(Color.BLUE);
                data.addDataSet(after);
            }

            LineDataSet insulin= new LineDataSet(entryInsulin, "胰島素");
            insulin.setColor(Color.GREEN);
            insulin.setCircleColor(Color.GREEN);
            data.addDataSet(insulin);
            data.addDataSet(new LineDataSet(entryBs, "血壓"));


            chart.notifyDataSetChanged();
            chart.getXAxis().setAxisMinimum(0f);
            chart.getXAxis().setLabelCount(5, true);
            chart.animateXY(50 * bslist.size(),50 * bslist.size());
        }
    }

    public int getDateDiffDay(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    @Override
    public void onSuccess(String data) {
        JSONArray array = null;
        try {
            bslist.clear();
            array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Bs bs = new Bs(obj);
                bslist.add(bs);

            }


            doChangeLineChart();
            dismissLoadingDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(String msg) {

    }
}
