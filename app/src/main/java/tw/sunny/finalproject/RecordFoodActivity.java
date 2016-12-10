package tw.sunny.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

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

public class RecordFoodActivity extends BaseActivity implements InternetModule.InternetCallback {
    TextView date;
    Calendar calendar;
    ListView listView;
    List<Record> records;
    ListAdapter adapter;
    BarChart barChart;
    RatingBar rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_food_daily);
        date = (TextView) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        listView = (ListView) findViewById(R.id.listView);
        date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        barChart = (BarChart) findViewById(R.id.chart);
        records = new ArrayList<>();
        adapter = new ListAdapter(this, records);
        listView.setAdapter(adapter);


        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("uid", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        new InternetTask(this, "http://120.126.15.112/food/record.php?act=query&simple=" + date.getText(), InternetModule.POST, map).execute();
    }

    public void btnRemindClick(View v) {
        startActivity(new Intent(this, RecordRemindActivity.class));
    }

    public void btnAnalysisClick(View v) {
        Intent intent = new Intent(this, RecordFoodDetailActivity.class);
        intent.putExtra("date", date.getText().toString());
        startActivity(intent);
    }

    public void btnDatePickerClick(View v) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                Map<String, String> map = new HashMap<>();
                map.put("uid", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
                new InternetTask(RecordFoodActivity.this, "http://120.126.15.112/food/record.php?act=query&simple=" + date.getText(), InternetModule.POST, map).execute();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                records.add(new Record(array.getJSONObject(i)));
            }

            adapter.notifyDataSetChanged();
            doBarChartInit();

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            dismissLoadingDialog();
        }
    }

    private void doBarChartInit() {
        float[] values = new float[8];
        int cnt = 0;
        for (Record record : records) {
            if (record.getMenu() == null) {
                cnt++;
                continue;
            }
            values[0] += record.getMenu().getMenu_calories();
            values[1] += record.getMenu().getMenu_carbohydrate();
            values[2] += record.getMenu().getMenu_sugar();
            values[3] += record.getMenu().getMenu_fat();
            values[4] += record.getMenu().getMenu_saturatedfat();
            values[5] += record.getMenu().getMenu_transfat();
            values[6] += record.getMenu().getMenu_protein();
            values[7] += record.getMenu().getMenu_soduim();
        }
        final String[] ls = {"卡路里", "碳水化合物", "糖類", "脂肪", "飽和脂肪", "反式脂肪", "蛋白質", "鈉"};

        List<BarEntry> entry = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            entry.add(new BarEntry(i, values[i]));
        }

        BarDataSet bds = new BarDataSet(entry, "成分表");
        bds.setStackLabels(ls);
        List<IBarDataSet> setlist = new ArrayList<>();
        setlist.add(bds);

        barChart.getXAxis().setLabelCount(ls.length);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(true);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
//        barChart.setScaleMinima(2f, 1f);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getDescription().setEnabled(false);
        barChart.setData(new BarData(setlist));

        barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ls[(int)value];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        barChart.invalidate();
        if (cnt > 0) Toast.makeText(this, "過濾" + cnt + "筆資料", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String msg) {
        dismissLoadingDialog();
    }

    private class Holder {
        public Holder(ImageView photo, TextView title, TextView store, RatingBar rating) {
            this.photo = photo;
            this.title = title;
            this.store = store;
            this.rating = rating;
        }

        public RatingBar rating;
        public ImageView photo;
        public TextView title;
        public TextView store;
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
            Record rec = records.get(position);
            Holder holder = null;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.record_analysis_list_item, null);
                TextView title = (TextView) v.findViewById(R.id.name);
                TextView store = (TextView) v.findViewById(R.id.store);
                ImageView photo = (ImageView) v.findViewById(R.id.photo);
                RatingBar rating = (RatingBar) v.findViewById(R.id.ratingBar);
                rating.setTag("" + rec.getCord_id());
                holder = new Holder(photo, title, store, rating);
                v.setTag(holder);

            } else {
                holder = (Holder) v.getTag();
            }


            holder.title.setText(rec.getMenu_name());
            holder.store.setText(rec.getStore_location());
            Glide.with(RecordFoodActivity.this)
                    .load(rec.getThumbnail())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.default_image)
                    .into(holder.photo);
            holder.rating.setOnRatingBarChangeListener(onRatingBarChangeListener);
            return v;
        }
    }

    private RatingBar.OnRatingBarChangeListener onRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(final RatingBar ratingBar, final float rating, boolean fromUser) {
            new AlertDialog.Builder(RecordFoodActivity.this)
                    .setTitle("確認")
                    .setMessage("是否要上傳評分" + (int) rating + "顆星？")
                    .setPositiveButton("我要上傳", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("m", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
                            map.put("rec", (String) ratingBar.getTag());
                            map.put("r", "" + rating);

                            new InternetTask(new InternetModule.InternetCallback() {
                                @Override
                                public void onSuccess(String data) {
                                    if (data.startsWith("OK"))
                                        Toast.makeText(RecordFoodActivity.this, "上傳成功", Toast.LENGTH_SHORT).show();
                                    else
                                        onFail(data);
                                }

                                @Override
                                public void onFail(String msg) {

                                }
                            }, "http://120.126.15.112/food/rating.php", InternetModule.POST, map).execute();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();

        }
    };


}
