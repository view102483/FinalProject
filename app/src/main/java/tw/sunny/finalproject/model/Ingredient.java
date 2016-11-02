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

public class Ingredient implements Parcelable {
    private String gre_id;
    private String ingre_name;
    private int ingre_caloris;
    private int ingre_carbohydrate;
    private int ingre_fat;
    private int ingre_protein;
    private int ingre_sodium;
    private int vegetables;
    private int grains;
    private int fruits;
    private int oilsandsweets;
    private int dairy;
    private int meetandbeans;
    private int gram;

    public Ingredient(JSONObject json) {
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

    protected Ingredient(Parcel in) {
        gre_id = in.readString();
        ingre_name = in.readString();
        ingre_caloris = in.readInt();
        ingre_carbohydrate = in.readInt();
        ingre_fat = in.readInt();
        ingre_protein = in.readInt();
        ingre_sodium = in.readInt();
        vegetables = in.readInt();
        grains = in.readInt();
        fruits = in.readInt();
        oilsandsweets = in.readInt();
        dairy = in.readInt();
        meetandbeans = in.readInt();
        gram = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gre_id);
        dest.writeString(ingre_name);
        dest.writeInt(ingre_caloris);
        dest.writeInt(ingre_carbohydrate);
        dest.writeInt(ingre_fat);
        dest.writeInt(ingre_protein);
        dest.writeInt(ingre_sodium);
        dest.writeInt(vegetables);
        dest.writeInt(grains);
        dest.writeInt(fruits);
        dest.writeInt(oilsandsweets);
        dest.writeInt(dairy);
        dest.writeInt(meetandbeans);
        dest.writeInt(gram);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getGre_id() {
        return gre_id;
    }

    public void setGre_id(String gre_id) {
        this.gre_id = gre_id;
    }

    public String getIngre_name() {
        return ingre_name;
    }

    public void setIngre_name(String ingre_name) {
        this.ingre_name = ingre_name;
    }

    public int getIngre_caloris() {
        return ingre_caloris;
    }

    public void setIngre_caloris(int ingre_caloris) {
        this.ingre_caloris = ingre_caloris;
    }

    public int getIngre_carbohydrate() {
        return ingre_carbohydrate;
    }

    public void setIngre_carbohydrate(int ingre_carbohydrate) {
        this.ingre_carbohydrate = ingre_carbohydrate;
    }

    public int getIngre_fat() {
        return ingre_fat;
    }

    public void setIngre_fat(int ingre_fat) {
        this.ingre_fat = ingre_fat;
    }

    public int getIngre_protein() {
        return ingre_protein;
    }

    public void setIngre_protein(int ingre_protein) {
        this.ingre_protein = ingre_protein;
    }

    public int getIngre_sodium() {
        return ingre_sodium;
    }

    public void setIngre_sodium(int ingre_sodium) {
        this.ingre_sodium = ingre_sodium;
    }

    public int getVegetables() {
        return vegetables;
    }

    public void setVegetables(int vegetables) {
        this.vegetables = vegetables;
    }

    public int getGrains() {
        return grains;
    }

    public void setGrains(int grains) {
        this.grains = grains;
    }

    public int getFruits() {
        return fruits;
    }

    public void setFruits(int fruits) {
        this.fruits = fruits;
    }

    public int getOilsandsweets() {
        return oilsandsweets;
    }

    public void setOilsandsweets(int oilsandsweets) {
        this.oilsandsweets = oilsandsweets;
    }

    public int getDairy() {
        return dairy;
    }

    public void setDairy(int dairy) {
        this.dairy = dairy;
    }

    public int getMeetandbeans() {
        return meetandbeans;
    }

    public void setMeetandbeans(int meetandbeans) {
        this.meetandbeans = meetandbeans;
    }


    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }
}
