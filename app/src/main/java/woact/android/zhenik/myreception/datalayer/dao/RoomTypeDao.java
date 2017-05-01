package woact.android.zhenik.myreception.datalayer.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Room;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ROOM_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;

public class RoomTypeDao {

    public RoomType getRoomType(long id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query(
                TABLE_ROOM_TYPES,
                new String[]{KEY_ID, KEY_TYPE, KEY_DESCRIPTION},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            return null;

        RoomType roomType = new RoomType(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2));
        DatabaseManager.getInstance().closeDatabase();
        return roomType;
    }

    // I decided to avoid double inner join. I did it for experiment
    // I use instead 2 transactions
    public List<RoomType> getRoomTypeForHotel(long hotelId){
        List<RoomType> types = new ArrayList<>();

        try{
            //
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            // get rooms for hotel- 1 transaction
            List<Room> rooms = getRoomsForHotel(hotelId, db);
            Set<Long> typesIds = new HashSet<>();
            for (Room room:rooms)
                typesIds.add(room.getRoomTypeId());
            String query = String.format("SELECT * FROM %s WHERE %s=?", TABLE_ROOM_TYPES, KEY_ID);
            RoomType roomType = null;

            for (Long id : typesIds){
                Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)});
                if (cursor != null && cursor.getCount()>0)
                    cursor.moveToFirst();

                if (cursor.moveToFirst()) {
                    do {
                        roomType = new RoomType();
                        roomType.setId(cursor.getLong(0));
                        roomType.setType(cursor.getString(1));
                        roomType.setDescritpion(cursor.getString(2));

                        // Adding contact to list
                        types.add(roomType);

                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            DatabaseManager.getInstance().closeDatabase();
        }

        return types;
    }

    // part of transaction, the reason why do not need db connection
    private List<Room> getRoomsForHotel(long hotelId, SQLiteDatabase db){
        String selectQuery = String.format(
                "SELECT %s.* FROM %s INNER JOIN %s ON %s.%s=%s.%s WHERE %s.%s=?",
                TABLE_ROOMS,TABLE_ROOMS,TABLE_HOTEL_ROOM,TABLE_HOTEL_ROOM,KEY_ROOM_ID,
                TABLE_ROOMS, KEY_ID, TABLE_HOTEL_ROOM,KEY_HOTEL_ID);
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
