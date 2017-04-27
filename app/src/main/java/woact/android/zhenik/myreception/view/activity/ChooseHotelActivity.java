package woact.android.zhenik.myreception.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.dao.HotelDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.view.adapter.HotelAdapter;

public class ChooseHotelActivity extends AppCompatActivity {
    private static final String TAG = "CH-activity:> ";
    private HotelAdapter hotelAdapter;
    private ListView hotelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hotel);
//        DatabaseManager.initializeInstance(DatabaseHelper.getHelper(getApplicationContext()));
        initAdapter();
    }

    private void initAdapter(){
        hotelsList = (ListView)findViewById(R.id.choose_hotel_list);
        List<Hotel> hotels = CacheDao.getInstance().getHotelDao().getHotels();
//        List<Hotel> hotels = (new HotelDao()).getHotels();
        hotelAdapter = new HotelAdapter(this, R.layout.hotel_item, hotels);
        Log.d(TAG, hotels.size()+"");
        if (hotelsList!=null)
            hotelsList.setAdapter(hotelAdapter);
    }


}
