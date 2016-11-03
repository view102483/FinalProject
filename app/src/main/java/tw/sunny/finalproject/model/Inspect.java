package tw.sunny.finalproject.model;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class Inspect {
    public Inspect(JSONObject json) {
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
    private String inspect_date;
    private String member_weight;
    private String inspect_HbA1c;
    private String inspect_Tcholesterol;
    private String inspect_TG;
    private String inspect_LDLC;
    private String inspect_HDLC;
    private String inspect_Cr;
    private String inspect_PRO;
    private String inspect_ACR;
    private String inspect_sbp;
    private String inspect_dbp;

    public String getInspect_date() {
        return inspect_date;
    }

    public void setInspect_date(String inspect_date) {
        this.inspect_date = inspect_date;
    }

    public String getMember_weight() {
        return member_weight;
    }

    public void setMember_weight(String member_weight) {
        this.member_weight = member_weight;
    }

    public String getInspect_HbA1c() {
        return inspect_HbA1c;
    }

    public void setInspect_HbA1c(String inspect_HbA1c) {
        this.inspect_HbA1c = inspect_HbA1c;
    }

    public String getInspect_Tcholesterol() {
        return inspect_Tcholesterol;
    }

    public void setInspect_Tcholesterol(String inspect_Tcholesterol) {
        this.inspect_Tcholesterol = inspect_Tcholesterol;
    }

    public String getInspect_TG() {
        return inspect_TG;
    }

    public void setInspect_TG(String inspect_TG) {
        this.inspect_TG = inspect_TG;
    }

    public String getInspect_LDLC() {
        return inspect_LDLC;
    }

    public void setInspect_LDLC(String inspect_LDLC) {
        this.inspect_LDLC = inspect_LDLC;
    }

    public String getInspect_HDLC() {
        return inspect_HDLC;
    }

    public void setInspect_HDLC(String inspect_HDLC) {
        this.inspect_HDLC = inspect_HDLC;
    }

    public String getInspect_Cr() {
        return inspect_Cr;
    }

    public void setInspect_Cr(String inspect_Cr) {
        this.inspect_Cr = inspect_Cr;
    }

    public String getInspect_PRO() {
        return inspect_PRO;
    }

    public void setInspect_PRO(String inspect_PRO) {
        this.inspect_PRO = inspect_PRO;
    }

    public String getInspect_ACR() {
        return inspect_ACR;
    }

    public void setInspect_ACR(String inspect_ACR) {
        this.inspect_ACR = inspect_ACR;
    }

    public String getInspect_sbp() {
        return inspect_sbp;
    }

    public void setInspect_sbp(String inspect_sbp) {
        this.inspect_sbp = inspect_sbp;
    }

    public String getInspect_dbp() {
        return inspect_dbp;
    }

    public void setInspect_dbp(String inspect_dbp) {
        this.inspect_dbp = inspect_dbp;
    }
}
