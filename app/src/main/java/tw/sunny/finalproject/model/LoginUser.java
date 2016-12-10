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

public class LoginUser implements Parcelable {
    private String member_id;
    private String mber_email;
    private String member_name;
    private String member_gender;
    private int member_calories;

    public LoginUser(JSONObject json) {
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
    protected LoginUser(Parcel in) {
        member_id = in.readString();
        mber_email = in.readString();
        member_name = in.readString();
        member_gender = in.readString();
        member_calories = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(member_id);
        dest.writeString(mber_email);
        dest.writeString(member_name);
        dest.writeString(member_gender);
        dest.writeInt(member_calories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginUser> CREATOR = new Creator<LoginUser>() {
        @Override
        public LoginUser createFromParcel(Parcel in) {
            return new LoginUser(in);
        }

        @Override
        public LoginUser[] newArray(int size) {
            return new LoginUser[size];
        }
    };

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMber_email() {
        return mber_email;
    }

    public void setMber_email(String mber_email) {
        this.mber_email = mber_email;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public void setMember_gender(String member_gender) {
        this.member_gender = member_gender;
    }

    public int getMember_calories() {
        return member_calories;
    }

    public void setMember_calories(int member_calories) {
        this.member_calories = member_calories;
    }
}
