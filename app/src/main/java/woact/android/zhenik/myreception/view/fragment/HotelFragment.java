package woact.android.zhenik.myreception.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import woact.android.zhenik.myreception.R;


public class HotelFragment extends Fragment {
    public static final String TAG = "HotelFragment";
    private View view;

    public HotelFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        setHasOptionsMenu(true);
        this.view=inflater.inflate(R.layout.fragment_hotel, null);
        return view;
    }

}
