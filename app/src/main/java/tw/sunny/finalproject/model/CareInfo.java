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

public class CareInfo implements Parcelable {
    private int id;
    private String title;
    private String info;
    private Integer parent_id;


    public CareInfo(JSONObject json) {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (json.has(field.getName())) {
                    field.setAccessible(true);
                    String value = String.valueOf(json.get(field.getName()));
                    if (value.equals("null"))
                        continue;
                    if (field.getType().isAssignableFrom(Integer.TYPE)) {
                        field.set(this, Integer.parseInt(value));
                    } else if (field.getType().isAssignableFrom(Integer.class)) {
                        field.set(this, Integer.parseInt(value));
                    } else if (field.getType().isAssignableFrom(Double.TYPE)) {
                        field.set(this, Double.parseDouble(value));
                    } else {
                        field.set(this, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected CareInfo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        info = in.readString();
        parent_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(info);
        dest.writeInt(parent_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CareInfo> CREATOR = new Creator<CareInfo>() {
        @Override
        public CareInfo createFromParcel(Parcel in) {
            return new CareInfo(in);
        }

        @Override
        public CareInfo[] newArray(int size) {
            return new CareInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
