package woact.android.zhenik.myreception.datalayer.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.User;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_USERS;


public class UserDao {

    public User getUser(long id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{KEY_ID, KEY_NAME, KEY_EMAIL},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            return null;

        User user = new User(cursor.getLong(0),
                             cursor.getString(1),
                             cursor.getString(2));
        DatabaseManager.getInstance().closeDatabase();
        return user;

    }
}
