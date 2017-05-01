package woact.android.zhenik.myreception.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import woact.android.zhenik.myreception.datalayer.dao.HotelDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Room;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.*;


/**
 * CacheDataLoader uses only
 * for development mode
 * to fill database with data
 * */

public class CacheDataLoader {
    private static final String TAG = "CDF:> ";
    private HotelDao hotelDao;

    public CacheDataLoader() {}

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



    /**
     * Dummy data for local.
     * */
    public void createDummyData() {
        // create hotels
        long hotelId1 = createHotel(new Hotel(1,
                                  "Dream Oslo Hotel",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                                  "Chr. Krohgs gate 32, 0186 Oslo",
                                  22057550,
                                  "post@westerdals.no"));
        long hotelId2 = createHotel(new Hotel(2,
                                  "Luxury Bergen Hotel",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                                  "Some street",
                                  321314142,
                                  "post@westerdals.no"));

        // create room types
        long roomTypeId1 = createRoomType(new RoomType("Standard", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod exercitation"));
        long roomTypeId2 = createRoomType(new RoomType("Double","do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));

        // create rooms
        long roomId1 = createRoom(roomTypeId1);
        long roomId2 = createRoom(roomTypeId1);
        long roomId3 = createRoom(roomTypeId2);

        // register rooms to hotels
        long hotel_room1=createHotelRoom(hotelId1, roomId1);
        long hotel_room2=createHotelRoom(hotelId1, roomId2);
        long hotel_room3=createHotelRoom(hotelId1, roomId3);
        long hotel_room4=createHotelRoom(hotelId2, roomId1);
        long hotel_room5=createHotelRoom(hotelId2, roomId2);

    }

    /**
     * Database loader
     * Download to cache (SQLite)
     * ------- stab ---------
     * */
    public void databaseLoad(Hotel hotel){}
}
