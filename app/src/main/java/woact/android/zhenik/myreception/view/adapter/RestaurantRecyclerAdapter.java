package woact.android.zhenik.myreception.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.R;
import woact.android.zhenik.myreception.datalayer.entities.Restaurant;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;
import woact.android.zhenik.myreception.view.holder.RestaurantHolder;
import woact.android.zhenik.myreception.view.holder.RoomTypesHolder;

/**
 * Created by NIK on 02/05/2017.
 */

public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantHolder> {

    private List<Restaurant> restaurants;
    public RestaurantRecyclerAdapter(List<Restaurant> restaurants) {
        this.restaurants = new ArrayList<Restaurant>();
        this.restaurants.addAll(restaurants);
    }

    @Override
    public RestaurantHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext())
                .inflate(R.layout.card_restaurant, viewGroup, false);

        return new RestaurantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantHolder restaurantHolder, int i) {
        Restaurant restaurant = restaurants.get(i);
        restaurantHolder.getTitleText().setText(restaurant.getName());
        restaurantHolder.getContentText().setText(restaurant.getDescription());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


}
