package tw.sunny.finalproject;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    List<CareInfo> alllist;
    ListAdapter adapter;
    Stack<CareInfo> stack;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careinfo);
        listView = (ListView) findViewById(R.id.listView);
        alllist = new ArrayList<>();
        infos = new ArrayList<>();
        stack = new Stack<>();
        adapter = new ListAdapter(this, infos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CareInfo info = infos.get(position);

                if(info.getInfo() == null || info.getInfo().length() == 0) {
                    stack.push(info);
                    infos.clear();
                    for(CareInfo inf : getChildrens(info)) {
                        infos.add(inf);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    startActivity(new Intent(CareInfoActivity.this, CareInfoDetailActivity.class).putExtra("info", info));
                }
            }
        });
        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                try {
                    alllist.clear();
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++)
                        alllist.add(new CareInfo(array.getJSONObject(i)));
                    infos.clear();
                    for (CareInfo inf : getRoots()) {
                        infos.add(inf);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        }, "http://120.126.15.112/food/care.php?all=1").execute();
    }

    @Override
    public void onBackPressed() {
        if (stack.isEmpty())
            finish();
        else {
            CareInfo info = stack.pop();
            infos.clear();
            for(CareInfo inf : getSiblings(info)) {
                infos.add(inf);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private List<CareInfo> getSiblings(CareInfo info) {
        List<CareInfo> list = new ArrayList<>();
        for (CareInfo inf : alllist) {
            if(String.valueOf(inf.getParent_id()).equals( String.valueOf( info.getParent_id()))) {
                list.add(inf);
            }
        }
        return list;
    }

    private List<CareInfo> getChildrens(CareInfo info) {
        List<CareInfo> list = new ArrayList<>();
        for (CareInfo inf : alllist) {
            if(inf.getParent_id() != null && inf.getParent_id() == info.getId()) {
                list.add(inf);
            }
        }
        return list;
    }

    private List<CareInfo> getRoots() {
        List<CareInfo> list = new ArrayList<>();
        for (CareInfo inf : alllist) {
            if(inf.getParent_id() == null) {
                list.add(inf);
            }
        }
        return list;
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
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            CareInfo info = infos.get(position);
            ViewHolder holder = null;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.careinfo_item, null);
                TextView text = (TextView) v.findViewById(R.id.textView);
                ImageView image = (ImageView) v.findViewById(R.id.imageView);

                holder = new ViewHolder(image, text);
                holder.info = info;
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }


            holder.textView.setText(info.getTitle());
            return v;
        }
    }

    private class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public CareInfo info;

        public ViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }
    }
}
