package woact.android.zhenik.myreception.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.CacheDataLoader;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;

public class MainActivity extends AppCompatActivity {

    private Button mStartBtn;
    private CacheDataLoader cdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
//        getSupportActionBar().hide();

        // db manager init
        DatabaseManager.initializeInstance(DatabaseHelper.getHelper(getApplicationContext()));
        cdf = new CacheDataLoader();
        databaseSetup();

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 2 seconds // freeze: emulate loading screen
                } catch (Exception e) {

                } finally {
                    Intent i = new Intent(getApplicationContext(), ChooseHotelActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }

    /**
     * dummy data load
     * */
    private void databaseSetup(){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if (!prefs.getBoolean("firstTime", false)) {
//            cdf.createDummyData();
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("firstTime", true);
////            editor.commit();
//            editor.apply();
//        }
        if (CacheDao.getInstance().getHotelDao().getHotels().size() < 1) {
            cdf.createDummyData();
        }
    }



}
