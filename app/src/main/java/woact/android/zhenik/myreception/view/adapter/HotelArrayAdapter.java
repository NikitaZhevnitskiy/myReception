package woact.android.zhenik.myreception.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;

/**
 *
 * Do not need use synchronization to prevent race condition,
 * because use cant modify table hotel
 *
 * */
public class HotelArrayAdapter extends ArrayAdapter<Hotel> implements Filterable {
    private List<Hotel> hotels;
    private List<Hotel> filteredData = null;
    private ItemFilter mFilter = new ItemFilter();

    public HotelArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public HotelArrayAdapter(Context context, int resource, List<Hotel> items) {
        super(context, resource, items);
        hotels=items;
        filteredData=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_hotel, null);
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

    @Nullable
    @Override
    public Hotel getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase().trim();

            FilterResults results = new FilterResults();

            final List<Hotel> list = hotels;

            int count = list.size();
            final ArrayList<Hotel> nlist = new ArrayList<Hotel>(count);
            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName().toLowerCase().trim();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Hotel>) results.values;
            notifyDataSetChanged();
        }
    }
}
