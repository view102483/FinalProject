package tw.sunny.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.io.IOException;

/**
 * Created by lixinting on 2016/4/12.
 */
public class ShootActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    QRCodeReaderView qr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoot_main);
        qr = (QRCodeReaderView) findViewById(R.id.qr);
        qr.setOnQRCodeReadListener(this);
    }

    public void btnCameraOnClick(View v) {

    }

    public void btnCodeOnClick(View v) {
        qr.startCamera();
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Toast.makeText(this, "Tst QR", Toast.LENGTH_SHORT).show();

    }
}
