package woact.android.zhenik.myreception.view.listener;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.utils.ReceptionAppContext;
import woact.android.zhenik.myreception.view.activity.home.InformationActivity;
import woact.android.zhenik.myreception.view.activity.home.RestaurantsActivity;
import woact.android.zhenik.myreception.view.activity.home.RoomTypesActivity;

public class ScreenNavigationListener implements View.OnClickListener {

    private Context context;
    public ScreenNavigationListener(Context context){
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.screen_navigation_information:
                Intent intentInfo = new Intent(context, InformationActivity.class);
                context.startActivity(intentInfo);
                break;
            case R.id.screen_navigation_contacts:
                Hotel hotel = ReceptionAppContext.getHotel();
                if (hotel!=null){
                    String phone = String.valueOf(hotel.getPhone()).trim();
                    if (phone.length()==0){
                        Toasty.error(context, "Telephone not found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    context.startActivity(intentCall);
                }
                else {
                    Toasty.error(context, "Hotel not found", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.screen_navigation_rooms:
                Intent intentRoomTypes = new Intent(context, RoomTypesActivity.class);
                context.startActivity(intentRoomTypes);
                break;
            case R.id.screen_navigation_restaurants:
                Intent intentRestaurants = new Intent(context, RestaurantsActivity.class);
                context.startActivity(intentRestaurants);
                break;
        }
    }
}
