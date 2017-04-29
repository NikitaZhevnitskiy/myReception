package woact.android.zhenik.myreception.datalayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String TAG = "DatabaseHelper:> ";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "reception";
    // Table Names
    public static final String TABLE_HOTELS = "hotels";
    // Common column names
    public static final String KEY_ID = "id";
    // USERS Table - column names
    public static final String KEY_HOTEL_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";

    // users table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_HOTELS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_HOTEL_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_DESCRIPTION + " TEXT NOT NULL, "
            + KEY_ADDRESS + " TEXT NOT NULL, "
            + KEY_PHONE + " INTEGER, "
            + KEY_EMAIL + " TEXT)";
    /**
     * Synchronized singleton
     */
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.d(TAG, "db was created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTELS);
        Log.d(TAG, "drop tables, change vers from " + oldVersion + " to new " + newVersion);
        onCreate(db);
    }



    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

}

