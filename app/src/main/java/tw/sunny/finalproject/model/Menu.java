package tw.sunny.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class Menu implements Parcelable{
    private String nu_id;
    private String menu_name;
    private int menu_calories;
    private int menu_carbohydrate;
    private int menu_sugar;
    private int menu_fat;
    private int menu_saturatedfat;
    private int menu_transfat;
    private int menu_protein;
    private int menu_soduim;
    private String store_id;
    private String bar_code;

    protected Menu(Parcel in) {
        nu_id = in.readString();
        menu_name = in.readString();
        menu_calories = in.readInt();
        menu_carbohydrate = in.readInt();
        menu_sugar = in.readInt();
        menu_fat = in.readInt();
        menu_saturatedfat = in.readInt();
        menu_transfat = in.readInt();
        menu_protein = in.readInt();
        menu_soduim = in.readInt();
        store_id = in.readString();
        bar_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nu_id);
        dest.writeString(menu_name);
        dest.writeInt(menu_calories);
        dest.writeInt(menu_carbohydrate);
        dest.writeInt(menu_sugar);
        dest.writeInt(menu_fat);
        dest.writeInt(menu_saturatedfat);
        dest.writeInt(menu_transfat);
        dest.writeInt(menu_protein);
        dest.writeInt(menu_soduim);
        dest.writeString(store_id);
        dest.writeString(bar_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };


    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public Menu(){}

    public Menu(JSONObject json) {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (json.has(field.getName())) {
                    field.setAccessible(true);
                    String value = String.valueOf(json.get(field.getName()));
                    if(value.equals("null"))
                        continue;
                    if(field.getType().isAssignableFrom(Integer.TYPE)) {
                        field.set(this, Integer.parseInt(value));
                    } else if(field.getType().isAssignableFrom(Double.TYPE)) {
                        field.set(this, Double.parseDouble(value));
                    }
                    else {
                        field.set(this, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNu_id() {
        return nu_id;
    }

    public void setNu_id(String nu_id) {
        this.nu_id = nu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getMenu_calories() {
        return menu_calories;
    }

    public void setMenu_calories(int menu_calories) {
        this.menu_calories = menu_calories;
    }

    public int getMenu_carbohydrate() {
        return menu_carbohydrate;
    }

    public void setMenu_carbohydrate(int menu_carbohydrate) {
        this.menu_carbohydrate = menu_carbohydrate;
    }

    public int getMenu_sugar() {
        return menu_sugar;
    }

    public void setMenu_sugar(int menu_sugar) {
        this.menu_sugar = menu_sugar;
    }

    public int getMenu_fat() {
        return menu_fat;
    }

    public void setMenu_fat(int menu_fat) {
        this.menu_fat = menu_fat;
    }

    public int getMenu_saturatedfat() {
        return menu_saturatedfat;
    }

    public void setMenu_saturatedfat(int menu_saturatedfat) {
        this.menu_saturatedfat = menu_saturatedfat;
    }

    public int getMenu_transfat() {
        return menu_transfat;
    }

    public void setMenu_transfat(int menu_transfat) {
        this.menu_transfat = menu_transfat;
    }

    public int getMenu_protein() {
        return menu_protein;
    }

    public void setMenu_protein(int menu_protein) {
        this.menu_protein = menu_protein;
    }

    public int getMenu_soduim() {
        return menu_soduim;
    }

    public void setMenu_soduim(int menu_soduim) {
        this.menu_soduim = menu_soduim;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
