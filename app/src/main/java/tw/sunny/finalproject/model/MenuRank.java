package tw.sunny.finalproject.model;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class MenuRank {
    private String nu_id;
    private String menu_name;
    private int menu_calories;
    private float star;
    private String store_name;
    private double store_latitude;
    private double store_longtitude;
    private String store_starttime;
    private String store_lasttime;
    private String store_image;

    public String getStore_image() {
        return store_image;
    }

    public void setStore_image(String store_image) {
        this.store_image = store_image;
    }

    public MenuRank(JSONObject json) {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (json.has(field.getName())) {
                    field.setAccessible(true);
                    String value = String.valueOf(json.get(field.getName()));
                    if(value.equals("null"))
                        continue;
                    if(field.getType().isAssignableFrom(Integer.TYPE) || field.getType().isAssignableFrom(Integer.class)) {
                        field.set(this, Integer.parseInt(value));
                    } else if(field.getType().isAssignableFrom(Double.TYPE)||field.getType().isAssignableFrom(Double.class)) {
                        field.set(this, Double.parseDouble(value));
                    } else if(field.getType().isAssignableFrom(Float.TYPE)||field.getType().isAssignableFrom(Float.class)) {
                        field.set(this, Float.parseFloat(value));
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

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public double getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(double store_latitude) {
        this.store_latitude = store_latitude;
    }

    public double getStore_longtitude() {
        return store_longtitude;
    }

    public void setStore_longtitude(double store_longtitude) {
        this.store_longtitude = store_longtitude;
    }

    public String getStore_starttime() {
        return store_starttime;
    }

    public void setStore_starttime(String store_starttime) {
        this.store_starttime = store_starttime;
    }

    public String getStore_lasttime() {
        return store_lasttime;
    }

    public void setStore_lasttime(String store_lasttime) {
        this.store_lasttime = store_lasttime;
    }
}
