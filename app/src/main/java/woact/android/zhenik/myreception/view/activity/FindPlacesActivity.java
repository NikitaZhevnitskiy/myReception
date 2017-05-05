package woact.android.zhenik.myreception.view.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;

public class FindPlacesActivity extends FragmentActivity implements OnMapReadyCallback {


    public static final String TAG = "FindPlacesActivity:> ";
    private Button btnFindPlaces;
    /**
     * Request code passed to the PlacePicker intent to identify its result when it returns.
     */
    private static final int PLACE_PICKER_REQUEST = 1;
    private GoogleMap map;
    private Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_places);
        setActionBar(new Toolbar(this));
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setTitle("Find near by");
        //Handle when activity is recreated like on orientation Change
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.find_places_map);
        mapFragment.getMapAsync(this);
       hotel = CacheDao.getInstance().getHotelDao().getHotel(ReceptionAppContext.getHotelId());
        initFindButton();
    }
    // Back btn in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void initFindButton(){
        btnFindPlaces=(Button)findViewById(R.id.find_places_btn);
        btnFindPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.find_places_btn){
                    onPick();
                }
            }
        });
    }

        private void onPick(){
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses=new ArrayList<>();
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            addresses = geocoder.getFromLocationName(hotel.getAddress(), 1);
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();

                LatLngBounds bounds =
                        new LatLngBounds(
                                new LatLng(latitude-0.0015, longitude-0.0015),
                                new LatLng(latitude+0.0015, longitude+0.0015));
                builder.setLatLngBounds(bounds);
            }
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                // do smth with place - make a route f.example
                String toastMsg = String.format("Place: %s", place.toString());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                addMarker(place);
//                initCardPlace(place);
            }
        }
    }

    private void addMarker(Place place) {
        LatLng placeLatLng = place.getLatLng();
        map.addMarker(new MarkerOptions().position(placeLatLng).title(place.getName()+""));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
//        googleMap.addMarker(new MarkerOptions()
//                              .position(new LatLng(0, 0))
//                              .title("Marker"));

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses=new ArrayList<>();
        try {
//            addresses = geocoder.getFromLocationName("Chr. Krohgs gate 32, 0186 Oslo", 1);
            addresses = geocoder.getFromLocationName(hotel.getAddress(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses.size() > 0) {
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            LatLng place = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(place).title(hotel.getName()));
//            map.moveCamera(CameraUpdateFactory.newLatLng(westerdals));


            CameraPosition googlePlex = CameraPosition.builder()
                    .target(place)
                    .zoom(16)
                    .bearing(0)
                    .tilt(45)
                    .build();

            map.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }
    }


//
//    private void displayPlace( Place place ) {
//        if( place == null )
//            return;
//
//        String content = "";
//        if( !TextUtils.isEmpty(place.getName() ) ) {
//            content += "Name: " + place.getName() + "\n";
//        }
//        if( !TextUtils.isEmpty( place.getAddress() ) ) {
//            content += "Address: " + place.getAddress() + "\n";
//        }
//        if( !TextUtils.isEmpty( place.getPhoneNumber() ) ) {
//            content += "Phone: " + place.getPhoneNumber();
//        }
//    }

//    private void initCardPlace(Place place){
//        CardView card = (CardView)findViewById(R.id.place_card);
//        TextView placeName = (TextView) card.findViewById(R.id.place_name);
//        TextView paceAddress = (TextView) card.findViewById(R.id.place_address);
//        TextView website = (TextView) card.findViewById(R.id.place_web);
//        TextView phone = (TextView) card.findViewById(R.id.place_phone);
//        placeName.setText(String.valueOf(place.getName()));
//        paceAddress.setText(String.valueOf(place.getAddress()));
//        website.setText(String.valueOf(place.getWebsiteUri()));
//        phone.setText(String.valueOf(place.getPhoneNumber()));
//    }
}
