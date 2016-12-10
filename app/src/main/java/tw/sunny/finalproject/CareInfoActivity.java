package tw.sunny.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import tw.sunny.finalproject.model.CareInfo;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class CareInfoActivity extends BaseActivity {

    ListView listView;
    List<CareInfo> infos;
    ListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careinfo);
        listView = (ListView) findViewById(R.id.listView);
        infos = new ArrayList<>();
        adapter = new ListAdapter(this, infos);
        listView.setAdapter(adapter);

        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                try {
                    infos.clear();
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++)
                        infos.add(new CareInfo(array.getJSONObject(i)));
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        }, "").execute();
    }

    private class ListAdapter extends BaseAdapter {
        Context context;
        List<CareInfo> list;

        public ListAdapter(Context context, List<CareInfo> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
