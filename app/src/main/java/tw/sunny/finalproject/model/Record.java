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

public class Record implements Parcelable {
    private String cord_id;
    private String member_id;
    private String menu_name;
    private String photo_description;
    private String store_location;
    private String record_image;
    private String record_datetime;
    private String thumbnail;
    private String nu_id;
    private Menu menu;


    protected Record(Parcel in) {
        cord_id = in.readString();
        member_id = in.readString();
        menu_name = in.readString();
        photo_description = in.readString();
        store_location = in.readString();
        record_image = in.readString();
        record_datetime = in.readString();
        thumbnail = in.readString();
        nu_id = in.readString();
        menu = in.readParcelable(Menu.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cord_id);
        dest.writeString(member_id);
        dest.writeString(menu_name);
        dest.writeString(photo_description);
        dest.writeString(store_location);
        dest.writeString(record_image);
        dest.writeString(record_datetime);
        dest.writeString(thumbnail);
        dest.writeString(nu_id);
        dest.writeParcelable(menu, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }




    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getCord_id() {
        return cord_id;
    }

    public void setCord_id(String cord_id) {
        this.cord_id = cord_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getPhoto_description() {
        return photo_description;
    }

    public void setPhoto_description(String photo_description) {
        this.photo_description = photo_description;
    }

    public String getStore_location() {
        return store_location;
    }

    public void setStore_location(String store_location) {
        this.store_location = store_location;
    }

    public String getRecord_image() {
        return record_image;
    }

    public void setRecord_image(String record_image) {
        this.record_image = record_image;
    }

    public String getRecord_datetime() {
        return record_datetime;
    }

    public void setRecord_datetime(String record_datetime) {
        this.record_datetime = record_datetime;
    }


    public Record(JSONObject json) {
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

            if(nu_id != null) {
                menu = new Menu(json);
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
}
