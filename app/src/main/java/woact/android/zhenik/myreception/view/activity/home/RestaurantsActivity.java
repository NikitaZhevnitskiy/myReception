package woact.android.zhenik.myreception.view.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;
import woact.android.zhenik.myreception.view.adapter.RestaurantRecyclerAdapter;
import woact.android.zhenik.myreception.view.adapter.RoomTypesRecyclerAdapter;

public class RestaurantsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CacheDao cacheDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cacheDao = CacheDao.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.restaurant_recyclerList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        fillRecyclerView();
    }


    private void fillRecyclerView() {
        Hotel hotel = ReceptionAppContext.getHotel();
        if (hotel != null) {
            recyclerView.setAdapter(
                    new RestaurantRecyclerAdapter(
                            cacheDao.getRestaurantDao().getHotelRestaurantsList(hotel.getId())));
        } else {
            Toasty.error(getApplicationContext(), "Hotel not found", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

    // Back btn in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
