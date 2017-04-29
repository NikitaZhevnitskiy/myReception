package woact.android.zhenik.myreception.view.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        initHotel();
        initBottomNavigation();
    }

    private void initBottomNavigation() {
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavListener=new BottomNavigationListener(getSupportFragmentManager());
        mNavigation.setOnNavigationItemSelectedListener(mBottomNavListener);
        mNavigation.performClick();
        clickHome();
    }

    private void initHotel(){hotel = (Hotel)getIntent().getSerializableExtra(ReceptionAppContext.HOTEL);}

    private void clickHome(){((View)findViewById(R.id.navigation_home)).performClick();}

}
