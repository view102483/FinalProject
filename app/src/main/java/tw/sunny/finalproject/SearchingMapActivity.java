package tw.sunny.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import tw.sunny.finalproject.model.MenuRank;
import tw.sunny.finalproject.module.InternetModule;
import tw.sunny.finalproject.module.InternetTask;

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
    List<MenuRank> menuRanks;
    Marker pinStore;
    AlertDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_map);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);
        txtFrom = (EditText) findViewById(R.id.from);
        txtTo = (EditText) findViewById(R.id.to);
        menuRanks = new ArrayList<>();

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
                    if (!tmpTo.isEmpty()) {
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
                    if (!tmpFrom.isEmpty()) {
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
        if (mkTo == null) {
            Toast.makeText(this, "必須輸入目的地", Toast.LENGTH_SHORT).show();
            txtTo.requestFocus();
            return;
        }

    /*
        Intent intent = new Intent(this, SearchingMapResultActivity.class);
        intent.putExtra("to", mkTo.getPosition());
        if(mkFrom != null) {
            intent.putExtra("from", mkFrom.getPosition());
        }
        startActivityForResult(intent, 1);
    */
        String tos = mkTo.getPosition().latitude + "," + mkTo.getPosition().longitude;
        String froms = "";
        if (mkFrom != null)
            froms = mkFrom.getPosition().latitude + "," + mkFrom.getPosition().longitude;
        new InternetTask(new InternetModule.InternetCallback() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONArray array = new JSONArray(data);
                    menuRanks.clear();
                    for(int i=0; i<array.length(); i++) {
                        MenuRank rank = new MenuRank(array.getJSONObject(i));
                        menuRanks.add(rank);
                    }

                    ListView listView = new ListView(SearchingMapActivity.this);
                    listView.setAdapter(new MenuListAdapter(SearchingMapActivity.this, menuRanks));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            moveFlag = true;
                            ListHolder holder = (ListHolder) view.getTag();
                            if (pinStore == null)
                                pinStore = pinMapMarker(new LatLng(holder.menuRank.getStore_latitude(), holder.menuRank.getStore_longtitude()), holder.menuRank.getStore_name(), BitmapDescriptorFactory.HUE_GREEN);
                            else
                                pinStore = moveMapMarker(pinStore, new LatLng(holder.menuRank.getStore_latitude(), holder.menuRank.getStore_longtitude()));

                            if(dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                            }
                        }
                    });

                    dialog = new AlertDialog.Builder(SearchingMapActivity.this)
                        .setTitle("選餐")
                        .setView(listView)
                        .create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String msg) {

            }
        }, "http://120.126.15.112/food/locsearch.php?to=" + tos + "&lng=" + froms + "&calories=" + getSharedPreferences("member", MODE_PRIVATE).getInt("member_calories_left", 9999)).execute();
    }

    private class MenuListAdapter extends BaseAdapter {
        Context context;
        List<MenuRank> menuRanks;

        public MenuListAdapter(Context context, List<MenuRank> menuRanks) {
            this.context = context;
            this.menuRanks = menuRanks;
        }

        @Override
        public int getCount() {
            return menuRanks.size();
        }

        @Override
        public Object getItem(int position) {
            return menuRanks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ListHolder holder;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(R.layout.listitem_location_menu_rank_list, null);
                ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView subtitle = (TextView) v.findViewById(R.id.subtitle);
                RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
                holder = new ListHolder(imageView, title, subtitle, ratingBar);
                v.setTag(holder);
            } else {
                holder = (ListHolder) v.getTag();
            }

            MenuRank rank = menuRanks.get(position);
            Glide.with(context).load(R.drawable.default_image)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.default_image)
                    .into(holder.image);
            holder.title.setText(rank.getMenu_name());
            holder.subtitle.setText(rank.getMenu_calories() + " cal. 店名：" + rank.getStore_name());
            holder.ratingBar.setRating(rank.getStar());
            holder.menuRank = rank;
            return v;
        }

    }

    private class ListHolder {
        public ImageView image;
        public TextView title;
        public TextView subtitle;
        public RatingBar ratingBar;
        public MenuRank menuRank;

        public ListHolder(ImageView image, TextView title, TextView subtitle, RatingBar ratingBar) {
            this.image = image;
            this.title = title;
            this.subtitle = subtitle;
            this.ratingBar = ratingBar;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && requestCode == RESULT_OK) {
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
