package woact.android.zhenik.myreception.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.CacheDataLoader;
import woact.android.zhenik.myreception.datalayer.entities.User;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;
import woact.android.zhenik.myreception.view.activity.MyReceptionHome;


public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment:> ";
    private View view;
    private EditText codeInput;
    private Button loginBtn;
    private CacheDataLoader cdf;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        setHasOptionsMenu(true);
        this.view = inflater.inflate(R.layout.fragment_login, null);
        cdf = new CacheDataLoader();
        initLoginFeature();

        return view;
    }

    private void initLoginFeature() {
        codeInput = (EditText) view.findViewById(R.id.login_code_field);
        loginBtn = (Button) view.findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.login_login_btn) {
                    String code = codeInput.getText().toString();
                    User user = cdf.login(ReceptionAppContext.getHotelId(), code);

                    if (user == null) {
                        Toasty.error(getContext(), "code is incorrect", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.success(getContext(), "welcome " + user.getName(), Toast.LENGTH_LONG).show();
                        ReceptionAppContext.setUserId(user.getId());
                        ((MyReceptionHome) getActivity()).clickServices();
                    }
                }
            }
        });

    }
}