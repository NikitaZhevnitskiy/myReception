package woact.android.zhenik.myreception.view.listener;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.view.fragment.HotelFragment;

public class BottomNavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fm;

    public BottomNavigationListener(FragmentManager fm) {this.fm = fm;}

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                callHotelFragment();
                return true;
            case R.id.navigation_services:
                return true;
            case R.id.navigation_places:
                return true;
            }
        return false;
    }

    private void callHotelFragment(){
        FragmentTransaction transaction = fm.beginTransaction();
        if (fm.findFragmentByTag(HotelFragment.TAG)==null)
            transaction.replace(R.id.content, new HotelFragment(), HotelFragment.TAG);
        transaction.commit();
    }


}
