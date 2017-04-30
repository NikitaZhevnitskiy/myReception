package woact.android.zhenik.myreception.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.datalayer.dao.HotelDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Room;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ROOM_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;

/**
 * CacheDataFactory uses only
 * for development mode
 * to fill database with data
 * */

public class CacheDataFactory {
    private static final String TAG = "DDF:> ";
    private HotelDao hotelDao;

    public CacheDataFactory() {}

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

    public long createRoomType(RoomType roomType){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, roomType.getType());
        values.put(KEY_DESCRIPTION, roomType.getDescription());
        long roomTypeId = db.insert(TABLE_ROOM_TYPES, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return roomTypeId;
    }

    public long createRoom(long roomTypeId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_ID, roomTypeId);
        long roomId = db.insert(TABLE_ROOMS, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return roomId;
    }
    public long createHotelRoom(long hotelId, long roomId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HOTEL_ID, hotelId);
        values.put(KEY_ROOM_ID, roomId);
        long hotelRoomId = db.insert(TABLE_HOTEL_ROOM, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return hotelRoomId;
    }


    public List<Room> getRoomList(long hotelId){
        String selectQuery = String.format(
                "SELECT %s.* FROM %s INNER JOIN %s ON %s.%s=%s.%s WHERE %s.%s=?",
                TABLE_ROOMS,TABLE_ROOMS,TABLE_HOTEL_ROOM,TABLE_HOTEL_ROOM,KEY_ROOM_ID,
                TABLE_ROOMS, KEY_ID, TABLE_HOTEL_ROOM,KEY_HOTEL_ID);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(hotelId)});

        List<Room> rooms = new ArrayList<>();
        Room room;
        if (cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                room = new Room();
                room.setId(cursor.getLong(0));
                room.setRoomTypeId(cursor.getLong(1));

                // Adding contact to list
                rooms.add(room);

            } while (cursor.moveToNext());
        }
        return rooms;
    }

}
