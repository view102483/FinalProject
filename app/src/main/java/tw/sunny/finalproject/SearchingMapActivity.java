package tw.sunny.finalproject;

import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Description here
 *
 * @author nagi
 * @version 1.0
 */

public class SearchingMapActivity extends BaseActivity implements OnMapReadyCallback {
    Criteria criteria;
    LocationManager locationManager;
    GoogleMap map;
    EditText txtFrom, txtTo;
    Marker mkFrom, mkTo;
    String tmpFrom = "", tmpTo = "";
    boolean moveFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_map);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);
        txtFrom = (EditText) findViewById(R.id.from);
        txtTo = (EditText) findViewById(R.id.to);


        txtFrom.setOnEditorActionListener(onEditorActionListener);
        txtTo.setOnEditorActionListener(onEditorActionListener);
    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                moveFlag = true;
                if (txtTo.getText() != null && txtTo.getText().length() > 0) {
                    String to = txtTo.getText().toString();
                    if (!tmpTo.equals(to)) {
                        tmpTo = to;
                        LatLng latlng = getLatLngFromAddress(to);
                        if (latlng != null) {
                            mkTo = mkTo == null ? pinMapMarker(latlng, "目的地", BitmapDescriptorFactory.HUE_RED) : moveMapMarker(mkTo, latlng);
                        }
                    }
                } else {
                    if(!tmpTo.isEmpty()) {
                        mkTo.remove();
                        mkTo = null;
                        tmpTo = "";
                    }
                }
                if (txtFrom.getText() != null && txtFrom.getText().length() > 0) {
                    String from = txtFrom.getText().toString();
                    if (!txtFrom.equals(from)) {
                        tmpFrom = from;
                        LatLng latlng = getLatLngFromAddress(from);
                        if (latlng != null) {
                            mkFrom = mkFrom == null ? pinMapMarker(latlng, "起始點", BitmapDescriptorFactory.HUE_YELLOW) : moveMapMarker(mkFrom, latlng);
                        }
                    }
                } else {
                    if(!tmpFrom.isEmpty()) {
                        mkFrom.remove();
                        mkFrom = null;
                        tmpFrom = "";
                    }
                }
            }
            return false;
        }
    };

    private LatLng getLatLngFromAddress(String addrStr) {
        Geocoder gc = new Geocoder(this);
        LatLng latlng = null;
        try {
            List<Address> address = gc.getFromLocationName(addrStr, 1);
            Address addr = address.get(0);
            latlng = new LatLng(addr.getLatitude(), addr.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return latlng;
    }

    private Location getLastKnownLocation() {
        if (criteria == null) {
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(true);
            criteria.setCostAllowed(false);
            criteria.setSpeedRequired(false);
            criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
            criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
            criteria.setSpeedAccuracy(Criteria.ACCURACY_FINE);
            criteria.setHorizontalAccuracy(Criteria.ACCURACY_FINE);
            criteria.setVerticalAccuracy(Criteria.ACCURACY_FINE);
        }
        return locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
    }

    public Marker pinMapMarker(LatLng latlng, String title, float icon) {
        MarkerOptions opt = new MarkerOptions();
        opt.position(latlng);
        opt.title(title);
        opt.icon(BitmapDescriptorFactory.defaultMarker(icon));
        Marker marker = map.addMarker(opt);
        marker.showInfoWindow();
        if (moveFlag) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
            moveFlag = false;
        }
        return marker;
    }

    public Marker moveMapMarker(Marker marker, LatLng latlng) {
        marker.hideInfoWindow();
        marker.setPosition(latlng);
        marker.showInfoWindow();
        if (moveFlag) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
            moveFlag = false;
        }
        return marker;
    }

    public void btnSearch(View v) {
        if(mkTo == null) {
            Toast.makeText(this, "必須輸入目的地", Toast.LENGTH_SHORT).show();
            txtTo.requestFocus();
            return;
        }


        Intent intent = new Intent(this, SearchingMapResultActivity.class);
        intent.putExtra("to", mkTo.getPosition());
        if(mkFrom != null) {
            intent.putExtra("from", mkFrom.getPosition());
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && requestCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        Location location = getLastKnownLocation();
        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
        map.animateCamera(center);
    }
}
