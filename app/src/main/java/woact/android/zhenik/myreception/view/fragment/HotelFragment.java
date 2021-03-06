package woact.android.zhenik.myreception.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.view.listener.ScreenNavigationListener;


public class HotelFragment extends Fragment {

    public static final String TAG = "HotelFragment:> ";
    private View view;

    public HotelFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        setHasOptionsMenu(true);
        this.view=inflater.inflate(R.layout.fragment_hotel, null);
        initNavigationListeners();
        return view;
    }

    private void initNavigationListeners(){

        ScreenNavigationListener screenNavigationListener = new ScreenNavigationListener(getContext());
        ((LinearLayout)view.findViewById(R.id.screen_navigation_information))
                .setOnClickListener(screenNavigationListener);
        ((LinearLayout)view.findViewById(R.id.screen_navigation_contacts))
                .setOnClickListener(screenNavigationListener);
        ((LinearLayout)view.findViewById(R.id.screen_navigation_rooms))
                .setOnClickListener(screenNavigationListener);
        ((LinearLayout)view.findViewById(R.id.screen_navigation_restaurants))
                .setOnClickListener(screenNavigationListener);
    }

}
