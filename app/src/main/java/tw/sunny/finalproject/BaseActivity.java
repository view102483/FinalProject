package tw.sunny.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.FileOutputStream;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class BaseActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_setting) {
            startActivity(new Intent(this, SettingMainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoadingDialog(String title, String message) {
        if(dialog == null)
            dialog = ProgressDialog.show(this, title, message, true);
        else {
            dialog.setTitle(title);
            dialog.setMessage(message);
        }

    }

    public void showLoadingDialog() {
        showLoadingDialog("Loading", "讀取中");
    }

    public void dismissLoadingDialog() {
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void rotatePhoto(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        try {
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    break;
            }
            FileOutputStream out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

}
