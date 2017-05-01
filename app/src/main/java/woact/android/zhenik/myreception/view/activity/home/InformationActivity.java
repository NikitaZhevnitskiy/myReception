package woact.android.zhenik.myreception.view.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.dao.CacheDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;

public class InformationActivity extends AppCompatActivity {

    private TextView hotelName;
    private TextView hotelDescription;
    private TextView hotelTelephone;
    private TextView hotelEmail;
    private Hotel currentHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("INFORMATION");

    }

    @Override
    protected void onPostResume() {
        initInformation();
        super.onPostResume();
    }

    private void initInformation(){
        // user chosen hotel
        currentHotel= CacheDao.getInstance().getHotelDao().getHotel(ReceptionAppContext.getHotelId());
        // define views
        hotelName=(TextView)findViewById(R.id.information_activity_hotel_name);
        hotelDescription=(TextView)findViewById(R.id.information_activity_hotel_description);
        hotelTelephone=(TextView)findViewById(R.id.information_activity_phone_txt);
        hotelEmail=(TextView)findViewById(R.id.information_activity_email_txt);

        if (currentHotel!=null){
            hotelName.setText(currentHotel.getName());
            hotelDescription.setText(currentHotel.getDescription());
            hotelTelephone.setText(String.valueOf(currentHotel.getPhone()));
            hotelEmail.setText(currentHotel.getEmail());
        }
    }

    // Back btn in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


}
