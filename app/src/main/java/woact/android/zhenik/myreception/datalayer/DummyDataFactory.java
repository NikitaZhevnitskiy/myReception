package woact.android.zhenik.myreception.datalayer;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import woact.android.zhenik.myreception.datalayer.entities.Hotel;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;

/**
 * DummyDataFactory uses only
 * for development mode
 * to fill database with data
 * */

public class DummyDataFactory {
    private static final String TAG = "DDF:> ";
    public DummyDataFactory() {}

    public long createHotel(Hotel hotel) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, hotel.getId());
        values.put(KEY_HOTEL_NAME, hotel.getName());
        values.put(KEY_DESCRIPTION, hotel.getDescription());
        values.put(KEY_ADDRESS, hotel.getAddress());
        values.put(KEY_PHONE, hotel.getPhone());
        values.put(KEY_EMAIL, hotel.getEmail());
        long hotelId = db.insert(TABLE_HOTELS, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return hotelId;
    }


    public void clearHotelTable() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE_HOTELS, null, null);
        DatabaseManager.getInstance().closeDatabase();
        Log.d(TAG, "---clean db---");
    }
}
