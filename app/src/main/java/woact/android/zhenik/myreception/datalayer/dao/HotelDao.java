package woact.android.zhenik.myreception.datalayer.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;

public class HotelDao {


    public Hotel getHotel(long id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query(
                TABLE_HOTELS,
                new String[]{KEY_ID, KEY_HOTEL_NAME, KEY_DESCRIPTION, KEY_ADDRESS, KEY_PHONE, KEY_EMAIL},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            return null;

        Hotel hotel = new Hotel(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getInt(4),
                                cursor.getString(5));
        DatabaseManager.getInstance().closeDatabase();
        return hotel;

    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_HOTELS;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                Hotel hotel = new Hotel();
                hotel.setId(cursor.getInt(0));
                hotel.setName(cursor.getString(1));
                hotel.setDescription(cursor.getString(2));
                hotel.setAddress(cursor.getString(3));
                hotel.setPhone(cursor.getInt(4));
                hotel.setEmail(cursor.getString(5));
                hotels.add(hotel);
            } while (cursor.moveToNext());
        }
        return hotels;
    }
}
