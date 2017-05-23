package woact.android.zhenik.myreception.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import woact.android.zhenik.myreception.datalayer.dao.HotelDao;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Restaurant;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;
import woact.android.zhenik.myreception.datalayer.entities.User;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.*;


/**
 * CacheDataLoader uses only
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
        values.put(KEY_NAME, hotel.getName());
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

    public long createRestaurant(Restaurant restaurant){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_DESCRIPTION, restaurant.getDescription());
        long restaurantId = db.insert(TABLE_RESTAURANTS, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return restaurantId;
    }

    public long createHotelRestaurant(long hotelId, long restaurantId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HOTEL_ID, hotelId);
        values.put(KEY_RESTAURANT_ID, restaurantId);
        long hotelRestaurantId = db.insert(TABLE_HOTEL_RESTAURANT, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return hotelRestaurantId;
    }

    public long createUser(User user) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        long userId = db.insert(TABLE_USERS, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return userId;
    }

    public long createUserHotelRoom(long userId, long hotelRoomId, String code) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_HOTEL_ROOM_ID, hotelRoomId);
        values.put(KEY_CODE, code);
        long userHotelRoomId = db.insert(TABLE_USER_HOTEL_ROOM, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return userHotelRoomId;
    }

    // 2 request in one transaction
    public User login(long hotelId, String code) {
        User user = null;
        String selectQuery = String.format(
                "SELECT %s.%s FROM %s "
                        + "INNER JOIN %s ON %s.%s=%s.%s "
                        + "WHERE %s.%s=? AND %s.%s=?",
                TABLE_USER_HOTEL_ROOM, KEY_USER_ID, TABLE_USER_HOTEL_ROOM, TABLE_HOTEL_ROOM,
                TABLE_HOTEL_ROOM, KEY_ID, TABLE_USER_HOTEL_ROOM, KEY_HOTEL_ROOM_ID, TABLE_HOTEL_ROOM,
                KEY_HOTEL_ID, TABLE_USER_HOTEL_ROOM, KEY_CODE);
        try {
            // 1 request
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{
                    String.valueOf(hotelId),
                    String.valueOf(code)}
            );

            if (cursor != null && cursor.getCount() > 0)
                cursor.moveToFirst();
            else
                return null;
            long userId = cursor.getLong(0);

            if (userId == -1) {
                return null;
            }

            // 2 request
            Cursor cursor1 = db.query(
                    TABLE_USERS,
                    new String[]{KEY_ID, KEY_NAME, KEY_EMAIL},
                    KEY_ID + "=?",
                    new String[]{String.valueOf(userId)},
                    null, null, null, null);

            if (cursor1 != null && cursor1.getCount() > 0)
                cursor1.moveToFirst();
            else
                return null;

            user = new User(cursor1.getLong(0),
                            cursor1.getString(1),
                            cursor1.getString(2));

        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
        return user;
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
                                  "Smeltedigelen 2, 0195 Oslo",
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

        // create restaurants
        long restaurantId1 = createRestaurant(new Restaurant("Full moon", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, s"));
        long restaurantId2 = createRestaurant(new Restaurant("B12", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, s"));

        // register restaurants to hotels
        long hotel_restaurant1 = createHotelRestaurant(hotelId1, restaurantId1);
        long hotel_restaurant2 = createHotelRestaurant(hotelId1, restaurantId2);
        long hotel_restaurant3 = createHotelRestaurant(hotelId2, restaurantId2);

        // create user & register in hotelRoom
        long userId1 = createUser(new User("Nikita Zhevnitskiy", "zhenik15@student.westerdals.no"));
        long userHotelRoomId = createUserHotelRoom(userId1, hotel_room1, "code");

    }

    /**
     * Database loader
     * Download to cache (SQLite)
     * ------- stab ---------
     * */
    public void databaseLoad(Hotel hotel){}
}
