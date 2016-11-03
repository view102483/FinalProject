package tw.sunny.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.sunny.finalproject.model.Ingredient;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class ShootFoodInfoDetailActivity extends BaseActivity implements InternetModule.InternetCallback {
    EditText keyword;
    ListView listView;
    List<Ingredient> ingredients;
    List<Ingredient> queryIngredients;
    ListAdapter adapter;
    Map<String, Boolean> selected;
    Map<String, String> amount;
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_food_details);
        name = getIntent().getStringExtra("name");
        keyword = (EditText) findViewById(R.id.keyword);
        listView = (ListView) findViewById(R.id.listView);
        ingredients = new ArrayList<>();
        queryIngredients = new ArrayList<>();
        selected = new HashMap<>();
        amount = new HashMap<>();
        adapter = new ListAdapter(this);
        listView.setAdapter(adapter);
        showLoadingDialog();
        new InternetTask(this, "http://120.126.15.112/food/ingredient.php?act=load").execute();

    }

    public void btnHome(View v) {
        finish();
    }

    public void btnQueryClick(View v) {

        if (keyword.getText() == null || keyword.getText().length() == 0) {
            Toast.makeText(this, "請輸入關鍵字", Toast.LENGTH_SHORT).show();
            return;
        }
        queryIngredients.clear();
        for (Ingredient ing : ingredients) {
            if (ing.getIngre_name().contains(keyword.getText().toString())) {
                queryIngredients.add(ing);
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void btnFilterClick(View v) {
        queryIngredients.clear();
        for (Ingredient ing : ingredients) {
            switch (v.getId()) {
                case R.id.vegetable:
                    if (ing.getVegetables() != 0)
                        queryIngredients.add(ing);
                    break;
                case R.id.grain:
                    if (ing.getGrains() != 0)
                        queryIngredients.add(ing);
                    break;
                case R.id.fruit:
                    if (ing.getFruits() != 0)
                        queryIngredients.add(ing);
                    break;
                case R.id.oil:
                    if (ing.getOilsandsweets() != 0)
                        queryIngredients.add(ing);
                    break;
                case R.id.daily:
                    if (ing.getDairy() != 0)
                        queryIngredients.add(ing);
                    break;
                case R.id.meat:
                    if (ing.getMeetandbeans() != 0)
                        queryIngredients.add(ing);
                    break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    ingredients.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Ingredient ingredient = new Ingredient(jsonArray.getJSONObject(i));
                        ingredients.add(ingredient);
                        selected.put(ingredient.getGre_id(), false);
                        amount.put(ingredient.getGre_id(), "");
                    }
                    queryIngredients.addAll(ingredients);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            dismissLoadingDialog();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, "Fail:" + msg, Toast.LENGTH_SHORT).show();
        dismissLoadingDialog();
    }

    private class ListHolder {
        public ListHolder(CheckBox checkBox, TextView textView, EditText editText) {
            this.checkBox = checkBox;
            this.textView = textView;
            this.editText = editText;
        }
        public CheckBox checkBox;
        public TextView textView;
        public EditText editText;
    }

    private class ListAdapter extends BaseAdapter {
        Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return queryIngredients.size();
        }

        @Override
        public Object getItem(int position) {
            return queryIngredients.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final ListHolder holder;

            if (v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.ingredient_item, null);
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox);
                TextView title = (TextView) v.findViewById(R.id.title);
                EditText amount = (EditText) v.findViewById(R.id.amount);
                holder = new ListHolder(checkBox, title, amount);
                v.setTag(holder);
            } else {
                holder = (ListHolder) v.getTag();
            }

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selected.put(queryIngredients.get(position).getGre_id(), isChecked);
                }
            });

            holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        amount.put(queryIngredients.get(position).getGre_id(), v.getText().toString());
                    }
                    return false;
                }
            });
            Ingredient ing = queryIngredients.get(position);
            holder.textView.setText(ing.getIngre_name());
            holder.checkBox.setChecked(selected.get(ing.getGre_id()));
            holder.editText.setText(amount.get(ing.getGre_id()));
            return v;
        }
    }

    public void btnOkClick(View v) {
        Map<String, String> map = new HashMap<>();
        String data = "";
        for(Ingredient ing : ingredients) {
            if(selected.get(ing.getGre_id())) {
                data += String.format("%s=%s,", ing.getIngre_name(), amount.get(ing.getGre_id()));
            }
        }
        data = data.substring(0, data.length() - 1);
        map.put("scratchpad", data);
        map.put("name", name);
        showLoadingDialog();
        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                if(!data.equals("scratchpad_ok")) {
                    onFail(data);
                    return;
                }
                dismissLoadingDialog();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {
                dismissLoadingDialog();
                Toast.makeText(ShootFoodInfoDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }, "http://120.126.15.112/food/ingredient.php?act=scratchpad", InternetModule.POST, map).execute();
    }
}
