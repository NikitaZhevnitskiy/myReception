package woact.android.zhenik.myreception.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.CacheDataLoader;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;


public class ServicesFragment extends Fragment {

    public static final String TAG = "ServicesFragment:> ";
    private View view;
    private LinearLayout servicesLayout;
    private TextView hotelName;
    private TextView userName;
    private CacheDao cacheDao;

    public ServicesFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        setHasOptionsMenu(true);
        this.view = inflater.inflate(R.layout.fragment_services, null);
        cacheDao = CacheDao.getInstance();
        initServicesFeature();
        initNames();
        return view;
    }


    private void initServicesFeature() {
        servicesLayout = (LinearLayout) view.findViewById(R.id.services_fragment_screen_navigation);
        servicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.services_fragment_screen_navigation) {
                    Toasty.info(getContext(), "Functionality mocked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initNames() {
        hotelName = (TextView) view.findViewById(R.id.services_fragment_hotel_name);
        userName = (TextView) view.findViewById(R.id.services_fragment_user_name);
        hotelName.setText(cacheDao.getHotelDao().getHotel(ReceptionAppContext.getHotelId()).getName());
        userName.setText(cacheDao.getUserDao().getUser(ReceptionAppContext.getUserId()).getName());
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
