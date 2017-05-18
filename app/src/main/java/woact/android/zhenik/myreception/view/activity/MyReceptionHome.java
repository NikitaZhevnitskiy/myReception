package woact.android.zhenik.myreception.view.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;
import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.view.listener.BottomNavigationListener;

public class MyReceptionHome extends AppCompatActivity {

    private Hotel hotel;
    private BottomNavigationView mNavigation;
    private BottomNavigationListener mBottomNavListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reception_home);
        getSupportActionBar().setTitle("Reception");
        initHotel();
        initBottomNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_navigation, menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
//        icon.setTint(getResources().getColor(android.R.color.white));
        icon.setTint(ContextCompat.getColor(this, android.R.color.white));
//        icon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_find_place:
                startActivity(new Intent(getApplicationContext(), FindPlacesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initBottomNavigation() {
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavListener=new BottomNavigationListener(getSupportFragmentManager());
        mNavigation.setOnNavigationItemSelectedListener(mBottomNavListener);
        mNavigation.performClick();
        clickHome();
    }

    private void initHotel(){
        hotel = (Hotel)getIntent().getSerializableExtra(ReceptionAppContext.HOTEL_IN_SYSTEM);
        if (hotel==null){
            Toasty.error(getApplicationContext(), "Hotel not found", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ChooseHotelActivity.class));
        }
        ReceptionAppContext.setHotel(hotel);
        ReceptionAppContext.setHotelId(hotel.getId());
    }

    private void clickHome(){((View)findViewById(R.id.navigation_home)).performClick();}

    public void clickServices() {((View) findViewById(R.id.navigation_services)).performClick();}

}
