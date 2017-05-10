package woact.android.zhenik.myreception.view.listener;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;
import woact.android.zhenik.myreception.view.fragment.HotelFragment;
import woact.android.zhenik.myreception.view.fragment.LoginFragment;
import woact.android.zhenik.myreception.view.fragment.ServicesFragment;

public class BottomNavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fm;

    public BottomNavigationListener(FragmentManager fm) {this.fm = fm;}

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                callHotelFragment();
                return true;
            case R.id.navigation_services:
                callServicesFragment();
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

    private void callServicesFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        if (ReceptionAppContext.getUserId() == null) {
            if (fm.findFragmentByTag(LoginFragment.TAG) == null)
                transaction.replace(R.id.content, new LoginFragment(), LoginFragment.TAG);
            transaction.commit();
        } else {
            if (fm.findFragmentByTag(ServicesFragment.TAG) == null)
                transaction.replace(R.id.content, new ServicesFragment(), ServicesFragment.TAG);
            transaction.commit();
        }
    }


}
