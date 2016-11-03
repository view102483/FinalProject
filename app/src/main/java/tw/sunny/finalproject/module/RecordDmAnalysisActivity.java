package tw.sunny.finalproject.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tw.sunny.finalproject.BaseActivity;
import tw.sunny.finalproject.R;
import tw.sunny.finalproject.model.Inspect;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class RecordDmAnalysisActivity extends BaseActivity {
    TableRow rowDate, rowWeight, rowHba1c, rowTcholesterol, rowTg, rowLdlc, rowHdlc, rowCr, rowPro, rowAcr, rowSbp, rowDbp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_dm_analysis);

        rowDate = (TableRow) findViewById(R.id.rowDate);
        rowWeight = (TableRow) findViewById(R.id.rowWeight);
        rowHba1c = (TableRow) findViewById(R.id.rowHba1c);
        rowTcholesterol = (TableRow) findViewById(R.id.rowTcholesterol);
        rowTg = (TableRow) findViewById(R.id.rowTg);
        rowLdlc = (TableRow) findViewById(R.id.rowLdlc);
        rowHdlc = (TableRow) findViewById(R.id.rowHdlc);
        rowCr = (TableRow) findViewById(R.id.rowCr);
        rowPro = (TableRow) findViewById(R.id.rowPro);
        rowAcr = (TableRow) findViewById(R.id.rowAcr);
        rowSbp = (TableRow) findViewById(R.id.rowSbp);
        rowDbp = (TableRow) findViewById(R.id.rowDbp);

        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Inspect ins = new Inspect(obj);

                        rowDate.addView(genTextView(ins.getInspect_date()));
                        rowWeight.addView(genTextView(ins.getMember_weight()));
                        rowHba1c.addView(genTextView(ins.getInspect_HbA1c()));
                        rowTcholesterol.addView(genTextView(ins.getInspect_Tcholesterol()));
                        rowTg.addView(genTextView(ins.getInspect_TG()));
                        rowLdlc.addView(genTextView(ins.getInspect_LDLC()));
                        rowHdlc.addView(genTextView(ins.getInspect_HDLC()));
                        rowCr.addView(genTextView(ins.getInspect_Cr()));
                        rowPro.addView(genTextView(ins.getInspect_PRO()));
                        rowAcr.addView(genTextView(ins.getInspect_ACR()));
                        rowSbp.addView(genTextView(ins.getInspect_sbp()));
                        rowDbp.addView(genTextView(ins.getInspect_dbp()));

                    }
                    int offset = 0;
                    while (offset + array.length() < 3) {
                        rowDate.addView(genTextView("　　"));
                        rowWeight.addView(genTextView("　　"));
                        rowHba1c.addView(genTextView("　　"));
                        rowTcholesterol.addView(genTextView("　　"));
                        rowTg.addView(genTextView("　　"));
                        rowLdlc.addView(genTextView("　　"));
                        rowHdlc.addView(genTextView("　　"));
                        rowCr.addView(genTextView("　　"));
                        rowPro.addView(genTextView("　　"));
                        rowAcr.addView(genTextView("　　"));
                        rowSbp.addView(genTextView("　　"));
                        rowDbp.addView(genTextView("　　"));
                        offset++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(RecordDmAnalysisActivity.this, "連線失敗", Toast.LENGTH_SHORT).show();
            }
        }, "http://120.126.15.112/food/dm.php?m=1").execute();
    }

    public TextView genTextView(String message) {
        TextView tv = new TextView(this);
        int padding = dp2px(10);
        tv.setPadding(padding, padding, padding, padding);
        tv.setBackgroundResource(R.drawable.cell);
        tv.setText(message == null || message.isEmpty() ? "　" : message);
        return tv;
    }
    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}