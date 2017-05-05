package woact.android.zhenik.myreception.view.activity.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
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
    private ImageView layoutPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        layoutPic = (ImageView)findViewById(R.id.information_activity_pic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("INFORMATION");
        new PictureLoader().execute(layoutPic);
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

    class PictureLoader extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return download_Image();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        private Bitmap download_Image() {

            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.info_description1);
                if (null != bmp)
                    return bmp;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

    }
}
