package tw.sunny.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class Entry implements Parcelable{

    private float x;
    private float y;

    public Entry(float y, float x) {
        this.y = y;
        this.x = x;
    }

    protected Entry(Parcel in) {
        x = in.readFloat();
        y = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(x);
        dest.writeFloat(y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public float getX() {

        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
