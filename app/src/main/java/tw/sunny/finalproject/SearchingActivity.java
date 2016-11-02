package tw.sunny.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.sunny.finalproject.model.Record;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Created by lixinting on 2016/4/12.
 */
public class SearchingActivity extends BaseActivity implements InternetModule.InternetCallback {
    GridView gridView;
    List<Record> records;
    EditText keyword;
    ImageAdapter adapter;
    ImageView location, time;
    int[] locationRes = {R.drawable.search_loc_none,
            R.drawable.search_loc_north,
            R.drawable.search_loc_mid,
            R.drawable.search_loc_south,
            R.drawable.search_loc_east,
            R.drawable.search_loc_island
    };
    int idxLoc = 0, idxTime = 0;

    int[] timeRes = {R.drawable.search_time_none,
            R.drawable.search_time_hr,
            R.drawable.search_time_d,
            R.drawable.search_time_w,
            R.drawable.search_time_m};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);
        gridView = (GridView) findViewById(R.id.gallery);
        records = new ArrayList<>();
        adapter = new ImageAdapter(this, records);
        gridView.setAdapter(adapter);
        keyword = (EditText) findViewById(R.id.keyword);
        location = (ImageView) findViewById(R.id.location);
        time = (ImageView) findViewById(R.id.time);
    }
    public void btnSearchingToPhoto(View view){
        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        if(keyword.getText().length() > 0)
            map.put("kw", keyword.getText().toString());
        map.put("loc", String.valueOf(idxLoc));
        map.put("tm", String.valueOf(idxTime));
        new InternetTask(this, "http://120.126.15.112/food/record.php?act=recfind", InternetModule.POST, map).execute();
    }


    public void btnLocationClick(View v) {
        idxLoc = idxLoc + 1 >= locationRes.length ? 0 : idxLoc + 1;
        location.setImageBitmap(BitmapFactory.decodeResource(getResources(), locationRes[idxLoc]));
    }

    public void btnTimeClick(View v) {
        idxTime = idxTime + 1 >= timeRes.length ? 0 : idxTime + 1;
        time.setImageBitmap(BitmapFactory.decodeResource(getResources(), timeRes[idxTime]));

    }


    @Override
    public void onSuccess(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            records.clear();
            for(int i=0; i<jsonArray.length(); i++) {
                records.add(new Record(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

        dismissLoadingDialog();
    }

    @Override
    public void onFail(String msg) {
        dismissLoadingDialog();
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
                    Intent intent = new Intent(SearchingActivity.this, RecordDetailActivity.class);
                    intent.putExtra("record", records.get(position));
                    startActivity(intent);
                }
            });
            return imageView;
        }
    }




}
