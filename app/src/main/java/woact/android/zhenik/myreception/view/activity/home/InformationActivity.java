package woact.android.zhenik.myreception.view.activity.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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
    private LinearLayout layoutPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        layoutPic = (LinearLayout)findViewById(R.id.information_activity_pic);
        layoutPic.setBackground(getResources().getDrawable(R.drawable.info_description1));
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

    class MyTask extends AsyncTask<Drawable, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            tvInfo.setText("Begin");
        }

        @Override
        protected Void doInBackground(Drawable... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
//                byte[] decodedString = Base64.decode(getResources().getDrawable(R.drawable.info_description1, ), Base64.NO_WRAP);
//                InputStream inputStream  = new ByteArrayInputStream(decodedString);
//                Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                layoutPic.setBackground(params[0]);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            tvInfo.setText("End");
        }
    }


}
