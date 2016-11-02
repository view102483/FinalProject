package tw.sunny.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tw.sunny.finalproject.model.Menu;
import tw.sunny.finalproject.model.Record;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordDetailActivity extends BaseActivity implements InternetModule.InternetCallback {
    ImageView photo;
    TextView desc, location,
            calPerPackage,
            proteinPerPackage,
            fatPerPackage,
            saturatedfatPerPackage,
            transfatPerPackage,
            carbohydratePerPackage,
            sugarPerPackage,
            sodiumPerPackage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        photo = (ImageView) findViewById(R.id.photo);
        desc = (TextView) findViewById(R.id.desc);
        location = (TextView) findViewById(R.id.location);
        calPerPackage = (TextView) findViewById(R.id.calPerPackage);
        proteinPerPackage = (TextView) findViewById(R.id.proteinPerPackage);
        fatPerPackage = (TextView) findViewById(R.id.fatPerPackage);
        carbohydratePerPackage = (TextView) findViewById(R.id.carbohydratePerPackage);
        sodiumPerPackage = (TextView) findViewById(R.id.sodiumPerPackage);
        saturatedfatPerPackage = (TextView) findViewById(R.id.saturatedfatPerPackage);
        transfatPerPackage = (TextView) findViewById(R.id.transfatPerPackage);
        sugarPerPackage = (TextView) findViewById(R.id.sugarPerPackage);

        Record record = getIntent().getParcelableExtra("record");

        Glide.with(this).load(record.getRecord_image())
                .placeholder(R.drawable.loading)
                .error(R.drawable.default_image)
                .into(photo);
        desc.setText(record.getPhoto_description());
        location.setText(record.getStore_location());


        Map<String, String> map = new HashMap<>();
        if(record.getNu_id() != null && !record.getNu_id().isEmpty())
            map.put("nu_id", record.getNu_id());
        else if(record.getMenu_name() != null && !record.getMenu_name().isEmpty())
            map.put("name", record.getMenu_name());
        else
            return;
        new InternetTask(this, "http://120.126.15.112/food/menu.php?act=find", InternetModule.POST, map).execute();
    }

    @Override
    public void onSuccess(String data) {
        try {
            JSONObject json = new JSONObject(data);
            Menu menu = new Menu(json);
            calPerPackage.setText(menu.getMenu_calories() + " 大卡");
            carbohydratePerPackage.setText(menu.getMenu_carbohydrate() + " 公克");
            sugarPerPackage.setText(menu.getMenu_sugar() + " 公克");
            sodiumPerPackage.setText(menu.getMenu_soduim() + " 毫克");
            fatPerPackage.setText(menu.getMenu_fat() + " 公克");
            saturatedfatPerPackage.setText(menu.getMenu_saturatedfat() + " 公克");
            transfatPerPackage.setText(menu.getMenu_transfat() + " 公克");
            proteinPerPackage.setText(menu.getMenu_protein() + " 公克");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFail(String msg) {

    }
}
