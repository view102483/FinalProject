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

public class CareInfo implements Parcelable{
    private int id;
    private String Cateogry;
    private String Info;

    public CareInfo(JSONObject json) {
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

    protected CareInfo(Parcel in) {
        id = in.readInt();
        Cateogry = in.readString();
        Info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Cateogry);
        dest.writeString(Info);
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

    public String getCateogry() {
        return Cateogry;
    }

    public void setCateogry(String cateogry) {
        Cateogry = cateogry;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
