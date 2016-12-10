package tw.sunny.finalproject.model;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class Bs {
    private String mber_id;
    private String BS_date;
    private int BS_id;
    private int BS_befb;
    private int BS_afterb;
    private int insulin_b;
    private int BS_b;
    private int BS_befl;
    private int BS_afterl;
    private int insulin_l;
    private int BS_l;
    private int BS_befd;
    private int BS_afterd;
    private int insulin_d;
    private int BS_d;
    private int BS_befsleep;
    private int insulin_befsleep;
    private int bp_befsleep;

    public Bs(JSONObject json) {
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

    public String getMber_id() {
        return mber_id;
    }

    public void setMber_id(String mber_id) {
        this.mber_id = mber_id;
    }

    public String getBS_date() {
        return BS_date;
    }

    public void setBS_date(String BS_date) {
        this.BS_date = BS_date;
    }

    public int getBS_id() {
        return BS_id;
    }

    public void setBS_id(int BS_id) {
        this.BS_id = BS_id;
    }

    public int getBS_befb() {
        return BS_befb;
    }

    public void setBS_befb(int BS_befb) {
        this.BS_befb = BS_befb;
    }

    public int getBS_afterb() {
        return BS_afterb;
    }

    public void setBS_afterb(int BS_afterb) {
        this.BS_afterb = BS_afterb;
    }

    public int getInsulin_b() {
        return insulin_b;
    }

    public void setInsulin_b(int insulin_b) {
        this.insulin_b = insulin_b;
    }

    public int getBS_b() {
        return BS_b;
    }

    public void setBS_b(int BS_b) {
        this.BS_b = BS_b;
    }

    public int getBS_befl() {
        return BS_befl;
    }

    public void setBS_befl(int BS_befl) {
        this.BS_befl = BS_befl;
    }

    public int getBS_afterl() {
        return BS_afterl;
    }

    public void setBS_afterl(int BS_afterl) {
        this.BS_afterl = BS_afterl;
    }

    public int getInsulin_l() {
        return insulin_l;
    }

    public void setInsulin_l(int insulin_l) {
        this.insulin_l = insulin_l;
    }

    public int getBS_l() {
        return BS_l;
    }

    public void setBS_l(int BS_l) {
        this.BS_l = BS_l;
    }

    public int getBS_befd() {
        return BS_befd;
    }

    public void setBS_befd(int BS_befd) {
        this.BS_befd = BS_befd;
    }

    public int getBS_afterd() {
        return BS_afterd;
    }

    public void setBS_afterd(int BS_afterd) {
        this.BS_afterd = BS_afterd;
    }

    public int getInsulin_d() {
        return insulin_d;
    }

    public void setInsulin_d(int insulin_d) {
        this.insulin_d = insulin_d;
    }

    public int getBS_d() {
        return BS_d;
    }

    public void setBS_d(int BS_d) {
        this.BS_d = BS_d;
    }

    public int getBS_befsleep() {
        return BS_befsleep;
    }

    public void setBS_befsleep(int BS_befsleep) {
        this.BS_befsleep = BS_befsleep;
    }

    public int getInsulin_befsleep() {
        return insulin_befsleep;
    }

    public void setInsulin_befsleep(int insulin_befsleep) {
        this.insulin_befsleep = insulin_befsleep;
    }

    public int getBp_befsleep() {
        return bp_befsleep;
    }

    public void setBp_befsleep(int bp_befsleep) {
        this.bp_befsleep = bp_befsleep;
    }
}
