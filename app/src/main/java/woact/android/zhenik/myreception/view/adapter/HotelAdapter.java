package woact.android.zhenik.myreception.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;

/**
 * Created by NIK on 27/04/2017.
 */

public class HotelAdapter extends ArrayAdapter<Hotel> {
    private List<Hotel> hotels;

    public HotelAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HotelAdapter(Context context, int resource, List<Hotel> items) {
        super(context, resource, items);
        hotels=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.hotel_item, null);
        }
        Hotel hotel = getHotel(position);
        if (hotel != null) {
            TextView tt2 = (TextView) v.findViewById(R.id.item_hotel_name);
            if (tt2 != null) {tt2.setText(hotel.getName());}
        }
        return v;
    }

    public Hotel getHotel(int position) {
        return ((Hotel)getItem(position));
    }

    @Override
    public int getCount() {
        return hotels.size();
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }
}
