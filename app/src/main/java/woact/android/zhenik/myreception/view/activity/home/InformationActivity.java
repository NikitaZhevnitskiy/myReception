package woact.android.zhenik.myreception.view.activity.home;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polyak.iconswitch.IconSwitch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;

import static woact.android.zhenik.myreception.utils.ReceptionAppContext.hotel;

public class InformationActivity extends AppCompatActivity implements OnMapReadyCallback,
        IconSwitch.CheckedChangeListener, ValueAnimator.AnimatorUpdateListener
{

    private TextView hotelName;
    private TextView hotelDescription;
    private TextView hotelTelephone;
    private TextView hotelEmail;
    private Hotel currentHotel;
    private ImageView layoutPic;
    private GoogleMap map;

    private static final int DURATION_COLOR_CHANGE_MS = 400;

    private int[] toolbarColors;
    private int[] statusBarColors;
    private ValueAnimator statusBarAnimator;
    private Interpolator contentInInterpolator;
    private Interpolator contentOutInterpolator;
    private Point revealCenter;

    private Window window;
    private Toolbar toolbar;
    private View content;
    private IconSwitch iconSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        layoutPic = (ImageView)findViewById(R.id.information_activity_pic);

        window = getWindow();
        initColors();
        initAnimationRelatedFields();
        content = findViewById(R.id.content);
        initToolbar();
        iconSwitch = (IconSwitch) findViewById(R.id.icon_switch);
        iconSwitch.setCheckedChangeListener(this);
        updateColors(false);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = new SupportMapFragment();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }
        fragment.getMapAsync(this);

        new PictureLoader().execute(layoutPic);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(color(R.color.colorAccent));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(color(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    protected void onPostResume() {
        initInformation();
        super.onPostResume();
    }

    private void initInformation(){
        // user chosen hotel
        currentHotel= CacheDao.getInstance().getHotelDao().getHotel(ReceptionAppContext.getHotelId());
        // define views
        hotelName=(TextView)findViewById(R.id.information_activity_hotel_name);
        hotelDescription=(TextView)findViewById(R.id.information_activity_hotel_description);
        hotelTelephone=(TextView)findViewById(R.id.information_activity_phone_txt);
        hotelEmail=(TextView)findViewById(R.id.information_activity_email_txt);

        if (currentHotel!=null){
            hotelName.setText(currentHotel.getName());
            hotelDescription.setText(currentHotel.getDescription());
            hotelTelephone.setText(String.valueOf(currentHotel.getPhone()));
            hotelEmail.setText(currentHotel.getEmail());
        }
    }

    private void updateColors(boolean animated) {
        int colorIndex = iconSwitch.getChecked().ordinal();
        toolbar.setBackgroundColor(toolbarColors[colorIndex]);
        if (animated) {
            switch (iconSwitch.getChecked()) {
                case LEFT:
                    statusBarAnimator.reverse();
                    break;
                case RIGHT:
                    statusBarAnimator.start();
                    break;
            }
            revealToolbar();
        } else {
            window.setStatusBarColor(statusBarColors[colorIndex]);
        }
    }

    private void revealToolbar() {
        iconSwitch.getThumbCenter(revealCenter);
        moveFromSwitchToToolbarSpace(revealCenter);
        ViewAnimationUtils.createCircularReveal(toolbar,
                                                revealCenter.x, revealCenter.y,
                                                iconSwitch.getHeight(), toolbar.getWidth())
                .setDuration(DURATION_COLOR_CHANGE_MS)
                .start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animator) {
        if (animator == statusBarAnimator) {
            int color = (Integer) animator.getAnimatedValue();
            window.setStatusBarColor(color);
        }
    }

    private void changeContentVisibility() {
        int targetTranslation = 0;
        Interpolator interpolator = null;
        switch (iconSwitch.getChecked()) {
            case LEFT:
                targetTranslation = 0;
                interpolator = contentInInterpolator;
                break;
            case RIGHT:
                targetTranslation = content.getHeight();
                interpolator = contentOutInterpolator;
                break;
        }
        content.animate().cancel();
        content.animate()
                .translationY(targetTranslation)
                .setInterpolator(interpolator)
                .setDuration(DURATION_COLOR_CHANGE_MS)
                .start();
    }

    @Override
    public void onCheckChanged(IconSwitch.Checked current) {
        updateColors(true);
        changeContentVisibility();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> addresses = new ArrayList<>();
        try {
//            addresses = geocoder.getFromLocationName("Chr. Krohgs gate 32, 0186 Oslo", 1);
            addresses = geocoder.getFromLocationName(hotel.getAddress(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() > 0) {
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();
            LatLng place = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(place).title(hotel.getName()));

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(place)
                    .zoom(16)
                    .bearing(0)
                    .tilt(45)
                    .build();

            map.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }
    }

    private void initAnimationRelatedFields() {
        revealCenter = new Point();
        statusBarAnimator = createArgbAnimator(
                statusBarColors[IconSwitch.Checked.LEFT.ordinal()],
                statusBarColors[IconSwitch.Checked.RIGHT.ordinal()]);
        contentInInterpolator = new OvershootInterpolator(0.5f);
        contentOutInterpolator = new DecelerateInterpolator();
    }

    private void initColors() {
        toolbarColors = new int[IconSwitch.Checked.values().length];
        statusBarColors = new int[toolbarColors.length];
//        toolbarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.informationPrimary);
//        statusBarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.informationPrimaryDark);
//        toolbarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.mapPrimary);
//        statusBarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.mapPrimaryDark);


        toolbarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.colorPrimary);
        statusBarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.informationPrimaryDark);
        toolbarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.mapPrimary);
        statusBarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.mapPrimaryDark);
    }

    private ValueAnimator createArgbAnimator(int leftColor, int rightColor) {
        ValueAnimator animator = ValueAnimator.ofArgb(leftColor, rightColor);
        animator.setDuration(DURATION_COLOR_CHANGE_MS);
        animator.addUpdateListener(this);
        return animator;
    }

    private void moveFromSwitchToToolbarSpace(Point point) {
        point.set(point.x + iconSwitch.getLeft(), point.y + iconSwitch.getTop());
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    // Back btn in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    class PictureLoader extends AsyncTask<ImageView, Void, Bitmap> {
        ImageView imageView = null;
        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return uploadImage();
        }
        @Override
        protected void onPostExecute(Bitmap result) {imageView.setImageBitmap(result);}
        private Bitmap uploadImage() {
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.info_description1);
                if (null != bmp)
                    return bmp;
            }
            catch (Exception e) {e.printStackTrace();}

            return bmp;
        }

    }
}
