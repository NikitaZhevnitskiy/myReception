package woact.android.zhenik.myreception.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import woact.android.zhenik.myreception.R;


public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment:> ";
    private View view;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        setHasOptionsMenu(true);
        this.view = inflater.inflate(R.layout.fragment_login, null);
        return view;
    }


}