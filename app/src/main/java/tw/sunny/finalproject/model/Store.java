package tw.sunny.finalproject.model;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class Store {
    private String store_id;
    private String store_name;
    private String store_phone;
    private String store_location;
    private double store_longtitude;
    private double store_latitude;
    private String store_introduction;
    private String store_starttime;
    private String store_lasttime;
    private int store_lowcost;
    private int store_charge;
    private String store_image;

    public Store(JSONObject json) {
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

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getStore_location() {
        return store_location;
    }

    public void setStore_location(String store_location) {
        this.store_location = store_location;
    }

    public double getStore_longtitude() {
        return store_longtitude;
    }

    public void setStore_longtitude(double store_longtitude) {
        this.store_longtitude = store_longtitude;
    }

    public double getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(double store_latitude) {
        this.store_latitude = store_latitude;
    }

    public String getStore_introduction() {
        return store_introduction;
    }

    public void setStore_introduction(String store_introduction) {
        this.store_introduction = store_introduction;
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

    public int getStore_lowcost() {
        return store_lowcost;
    }

    public void setStore_lowcost(int store_lowcost) {
        this.store_lowcost = store_lowcost;
    }

    public int getStore_charge() {
        return store_charge;
    }

    public void setStore_charge(int store_charge) {
        this.store_charge = store_charge;
    }

    public String getStore_image() {
        return store_image;
    }

    public void setStore_image(String store_image) {
        this.store_image = store_image;
    }
}
