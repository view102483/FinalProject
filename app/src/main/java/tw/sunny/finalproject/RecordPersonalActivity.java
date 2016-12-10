package tw.sunny.finalproject;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class RecordPersonalActivity extends BaseActivity implements InternetModule.InternetCallback {
    ListView listView;
    TextView txtDate, txtCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_person);
        listView = (ListView) findViewById(R.id.listView);
        txtDate = (TextView) findViewById(R.id.date);
        txtCount = (TextView) findViewById(R.id.count);


        Map<String, String> data = new HashMap<>();
        data.put("uid", getSharedPreferences("member", MODE_PRIVATE).getString("member_id", "0"));
        showLoadingDialog("", "正在讀取中...");
        new InternetTask(this, "http://120.126.15.112/food/record.php?act=query", InternetModule.POST, data).execute();
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONObject jObject = new JSONObject(data);
            Iterator<?> keys = jObject.keys();
            List<ListViewModel> models = new ArrayList<>();
            int cnt = 0;
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONArray jArray = jObject.getJSONArray(key);
                List<Record> list = new ArrayList<>();

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject rec = jArray.getJSONObject(i);
                    list.add(new Record(rec));
                    cnt++;
                }
                models.add(new ListViewModel(key, list));
            }

            if (models.size() > 0) {
                txtDate.setText("從 " + models.get(0).getDate() + "\n開始記錄");
                txtCount.setText("已有 " + cnt + "則");
            }

            LinearLayout frame = (LinearLayout) findViewById(R.id.frame);
            for (ListViewModel model : models) {
                LinearLayout item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.record_listview_item, null);
                TextView title = (TextView) item.findViewById(R.id.title);
                title.setText(model.getDate());
                GridLayout grid = (GridLayout) item.findViewById(R.id.gridlayout);
                ImageView image = null;
                for (final Record rec : model.getRecords()) {
                    image = new ImageView(this);
                    image.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    image.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.drawable.btn_selector));
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RecordPersonalActivity.this, RecordDetailActivity.class);
                            intent.putExtra("record", rec);
                            startActivity(intent);
                        }
                    });
                    Glide.with(this).load(rec.getThumbnail()).placeholder(R.drawable.loading).error(R.drawable.default_image).into(image);
                    grid.addView(image);
                }
                frame.addView(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dismissLoadingDialog();
        }
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    private class ListHolder {
        private TextView title;
        private GridLayout gridLayout;

        public ListHolder(TextView title, GridLayout gridLayout) {
            this.title = title;
            this.gridLayout = gridLayout;
        }

        public TextView getTitle() {
            return title;
        }

        public GridLayout getGridLayout() {
            return gridLayout;
        }
    }

    private class ListAdapter extends BaseAdapter {

        private Context context;
        private List<ListViewModel> models;

        public ListAdapter(Context context, List<ListViewModel> models) {
            this.context = context;
            this.models = models;
        }

        @Override
        public int getCount() {
            return models.size();
        }

        @Override
        public Object getItem(int position) {
            return models.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ListHolder holder;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.record_listview_item, null);

                TextView title = (TextView) v.findViewById(R.id.title);
                GridLayout layout = (GridLayout) v.findViewById(R.id.gridlayout);
                holder = new ListHolder(title, layout);
                v.setTag(holder);
            } else {
                holder = (ListHolder) v.getTag();
            }

//            GridView gridView = (GridView) v.findViewById(R.id.gallery);
//            TextView title = (TextView) v.findViewById(R.id.title);
            ListViewModel model = models.get(position);
//            if (model != null) {
//                title.setText(model.getDate());
//                gridView.setAdapter(new ImageAdapter(context, model.getRecords()));
//            }
            holder.getTitle().setText(model.getDate());
            for (final Record rec : model.getRecords()) {
                ImageView imageView = new ImageView(context);
                imageView.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.drawable.btn_selector));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RecordPersonalActivity.this, RecordDetailActivity.class);
                        intent.putExtra("record", rec);
                        startActivity(intent);
                    }
                });
                Glide.with(context)
                        .load(rec.getThumbnail())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.default_image)
                        .into(imageView);
                holder.getGridLayout().addView(imageView);
            }
            return v;
        }
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<Record> records;

        public ImageAdapter(Context mContext, List<Record> records) {
            this.mContext = mContext;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            String path = records.get(position).getThumbnail();

            Glide.with(mContext).load(path)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecordPersonalActivity.this, RecordDetailActivity.class);
                    intent.putExtra("record", records.get(position));
                    startActivity(intent);
                }
            });
            return imageView;
        }
    }

    private class ListViewModel {
        String date;
        List<Record> records;

        public ListViewModel(String date, List<Record> records) {
            this.date = date;
            this.records = records;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<Record> getRecords() {
            return records;
        }

        public void setRecords(List<Record> records) {
            this.records = records;
        }
    }
}
