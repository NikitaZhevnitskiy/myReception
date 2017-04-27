package woact.android.zhenik.myreception.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.DummyDataFactory;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;

public class MainActivity extends AppCompatActivity {

    private Button mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartBtn=(Button)findViewById(R.id.main_activity_start_btn);
        initBtnListener();

        // db manager init
        DatabaseManager.initializeInstance(DatabaseHelper.getHelper(getApplicationContext()));
        databaseSetup();

    }

    private void initBtnListener(){
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.main_activity_start_btn){
                    Intent intent = new Intent(getApplicationContext(), ChooseHotelActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * DB setup once only
     * */
    private void databaseSetup(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            createDummyData();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

    private void createDummyData() {
        DummyDataFactory ddf =new DummyDataFactory();
        ddf.createHotel(new Hotel(1,
                                  "Dream Hotel",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                                  "Chr. Krohgs gate 32, 0186 Oslo",
                                  22057550,
                                  "post@westerdals.no"));

    }

}
