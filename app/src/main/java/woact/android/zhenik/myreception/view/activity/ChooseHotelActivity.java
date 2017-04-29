package woact.android.zhenik.myreception.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ApplicationContex;
import woact.android.zhenik.myreception.view.adapter.HotelAdapter;

public class ChooseHotelActivity extends AppCompatActivity {
    private static final String TAG = "CH-activity:> ";
    private HotelAdapter mHotelAdapter;
    private ListView mHotelsList;
    private ProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hotel);
        mProgressWheel = (ProgressWheel) findViewById(R.id.choose_hotel_progress_wheel);
        initAdapter();
    }

    @Override
    protected void onResume() {
        initWheel();
        super.onResume();
    }

    private void initAdapter() {
        mHotelsList = (ListView) findViewById(R.id.choose_hotel_list);
        List<Hotel> hotels = CacheDao.getInstance().getHotelDao().getHotels();
        mHotelAdapter = new HotelAdapter(this, R.layout.item_list_hotel, hotels);
        Log.d(TAG, hotels.size() + "");
        if (mHotelsList != null)
            mHotelsList.setAdapter(mHotelAdapter);
        initListViewListener();
    }

    private void initWheel() {
        if (mProgressWheel.isSpinning()) {
            mProgressWheel.setVisibility(View.INVISIBLE);
        }
    }

    private void initListViewListener() {
        mHotelsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hotel hotel = ((Hotel) mHotelsList.getItemAtPosition(position));
                final Intent intent = new Intent(getApplicationContext(), MyReceptionHome.class);
                intent.putExtra(ApplicationContex.HOTEL, hotel);
                mProgressWheel.setVisibility(View.VISIBLE);

                // emulate download database to cache
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        startActivity(intent);
                    }
                }, 1500);
            }
        });
    }


}
