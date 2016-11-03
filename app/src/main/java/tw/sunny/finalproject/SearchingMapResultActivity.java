package tw.sunny.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.sunny.finalproject.model.Store;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class SearchingMapResultActivity extends BaseActivity implements InternetModule.InternetCallback {
    ListView listView;
    List<Store> stores;
    ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_map_result);
        listView = (ListView) findViewById(R.id.listView);
        stores = new ArrayList<>();
        adapter = new ListAdapter(this, stores);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + stores.get(position).getStore_latitude() + "," + stores.get(position).getStore_longtitude() + "(" + stores.get(position).getStore_name() +")")));
            }
        });
        LatLng to = getIntent().getParcelableExtra("to");
        LatLng from = null;
        if (getIntent().hasExtra("from"))
            getIntent().getParcelableExtra("from");

        showLoadingDialog();
        Map<String, String> map = new HashMap<>();
        map.put("to", to.latitude + "," + to.longitude);
        if (from != null)
            map.put("from", from.latitude + "," + from.longitude);
        new InternetTask(this, "http://120.126.15.112/food/store.php", InternetModule.POST, map).execute();
    }

    public void onHomeClick(View v) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONArray array = new JSONArray(data);
            stores.clear();
            for (int i = 0; i < array.length(); i++) {
                stores.add(new Store(array.getJSONObject(i)));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        dismissLoadingDialog();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, "找不到資料", Toast.LENGTH_SHORT).show();
        dismissLoadingDialog();
    }

    private class ListAdapter extends BaseAdapter {
        Context context;
        List<Store> stores;

        public ListAdapter(Context context, List<Store> stores) {
            this.context = context;
            this.stores = stores;
        }

        @Override
        public int getCount() {
            return stores.size();
        }

        @Override
        public Object getItem(int position) {
            return stores.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final ListHolder holder;

            if (v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.searching_map_result_item, null);
                ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView location = (TextView) v.findViewById(R.id.location);
                TextView howlong = (TextView) v.findViewById(R.id.howlong);
                holder = new ListHolder(imageView, title, location, howlong);
                v.setTag(holder);
            } else {
                holder = (ListHolder) v.getTag();
            }

            Store store = stores.get(position);
            Glide.with(context).load(store.getStore_image())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.default_image)
                    .into(holder.getImageView());
            holder.getTitle().setText(store.getStore_name());
            holder.getLocaiton().setText(store.getStore_location());

            return v;
        }

        private class ListHolder {
            private ImageView imageView;
            private TextView title;

            public TextView getLocaiton() {
                return locaiton;
            }

            public void setLocaiton(TextView locaiton) {
                this.locaiton = locaiton;
            }

            public ImageView getImageView() {
                return imageView;
            }

            public void setImageView(ImageView imageView) {
                this.imageView = imageView;
            }

            public TextView getTitle() {
                return title;
            }

            public void setTitle(TextView title) {
                this.title = title;
            }

            public TextView getHowlong() {
                return howlong;
            }

            public void setHowlong(TextView howlong) {
                this.howlong = howlong;
            }

            private TextView locaiton;
            private TextView howlong;

            public ListHolder(ImageView imageView, TextView title, TextView locaiton, TextView howlong) {
                this.imageView = imageView;
                this.title = title;
                this.locaiton = locaiton;
                this.howlong = howlong;
            }
        }
    }
}
