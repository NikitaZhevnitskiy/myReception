package woact.android.zhenik.myreception.datalayer.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Room;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ROOM_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;


public class RoomDao {

    public RoomDao(){}

    public List<Room> getHotelRoomsList(long hotelId){
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
        DatabaseManager.getInstance().closeDatabase();
        return rooms;
    }
}
