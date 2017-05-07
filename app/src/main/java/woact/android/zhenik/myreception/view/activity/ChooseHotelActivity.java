package woact.android.zhenik.myreception.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;
import woact.android.zhenik.myreception.view.adapter.HotelArrayAdapter;

public class ChooseHotelActivity extends AppCompatActivity {
    private static final String TAG = "CH-activity:> ";
    private HotelArrayAdapter mHotelArrayAdapter;
    private ListView mHotelsList;
    private ProgressWheel mProgressWheel;
    private EditText mTxtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hotel);
        mProgressWheel = (ProgressWheel) findViewById(R.id.choose_hotel_progress_wheel);
        mTxtSearch = (EditText)findViewById(R.id.choose_hotel_search);

//        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initAdapter();
        mHotelArrayAdapter.getFilter().filter(mTxtSearch.getText());
        mTxtSearch.addTextChangedListener(new TextChangeListener());
    }

    @Override
    protected void onResume() {
        initWheel();
        super.onResume();
    }

    private void initAdapter() {
        mHotelsList = (ListView) findViewById(R.id.choose_hotel_list);
        List<Hotel> hotels = CacheDao.getInstance().getHotelDao().getHotels();
        mHotelArrayAdapter = new HotelArrayAdapter(this, R.layout.item_list_hotel, hotels);
        Log.d(TAG, hotels.size() + "");
        if (mHotelsList != null)
            mHotelsList.setAdapter(mHotelArrayAdapter);
        initListViewListener();
        mHotelsList.setTextFilterEnabled(true);
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
                intent.putExtra(ReceptionAppContext.HOTEL_IN_SYSTEM, hotel);
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


    class TextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mHotelArrayAdapter.getFilter().filter(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
