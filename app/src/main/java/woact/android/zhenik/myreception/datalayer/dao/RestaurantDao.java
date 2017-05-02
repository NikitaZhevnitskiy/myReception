package woact.android.zhenik.myreception.datalayer.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Restaurant;
import woact.android.zhenik.myreception.datalayer.entities.Room;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_RESTAURANT_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ROOM_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_RESTAURANT;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_RESTAURANTS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;


public class RestaurantDao {

    public List<Restaurant> getHotelRestaurantsList(long hotelId){
        String selectQuery = String.format(
                "SELECT %s.* FROM %s INNER JOIN %s ON %s.%s=%s.%s WHERE %s.%s=?",
                TABLE_RESTAURANTS,TABLE_RESTAURANTS,TABLE_HOTEL_RESTAURANT,TABLE_HOTEL_RESTAURANT,KEY_RESTAURANT_ID,
                TABLE_RESTAURANTS, KEY_ID, TABLE_HOTEL_RESTAURANT,KEY_HOTEL_ID);

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(hotelId)});

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant;
        if (cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                restaurant = new Restaurant();
                restaurant.setId(cursor.getLong(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setDescription(cursor.getString(2));

                // Adding contact to list
                restaurants.add(restaurant);

            } while (cursor.moveToNext());
        }
        DatabaseManager.getInstance().closeDatabase();
        return restaurants;
    }
}
