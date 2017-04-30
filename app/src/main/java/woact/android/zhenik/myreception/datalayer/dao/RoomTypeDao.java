package woact.android.zhenik.myreception.datalayer.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE;
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
}
