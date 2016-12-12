package tw.sunny.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lixinting on 2016/8/16.
 */
public class RecordDmManageActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_dmmanage);
    }

    public void btnDIY(View v) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        String[] cs = {"歐陽英自我檢測", "糖尿病自我檢測站", "糖尿病對話網"};
        ab.setSingleChoiceItems(cs, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.oyoung.com.tw/oypark/oyygame-noback.asp?id=17&title=%E7%B3%96%E5%B0%BF%E7%97%85%E6%AA%A2%E6%B8%AC")));
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://tw.funfunquiz.com/512/1552652138084857")));

                        break;
                    case 2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dawnstudy.tw/examination/examination.asp")));
                        break;
                }
            }
        });
        ab.create().show();
    }
    public void btnDMB(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordDMBActivity.class);
        startActivity(intent);


    }
    public void btnDMCheckRegular(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordDMCheckRegularActivity.class);
        startActivity(intent);


    }
    public void btnDMGraphic(View view){
        Intent intent = new Intent();
        intent.setClass(this, RecordDmGraphicActivity.class);
        startActivity(intent);
    }


}
