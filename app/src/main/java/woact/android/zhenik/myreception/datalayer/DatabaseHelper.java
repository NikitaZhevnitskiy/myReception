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
    public static final String TABLE_ROOM_TYPES = "room_types";
    public static final String TABLE_ROOMS = "rooms";
    public static final String TABLE_HOTEL_ROOM = "hotel_room";
    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String TABLE_HOTEL_RESTAURANT = "hotel_restaurants";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_USER_HOTEL_ROOM = "user_hotel_room";
    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_DESCRIPTION = "description";
    // USERS Table - column names
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    // ROOMS_TYPES Table - columns
    public static final String KEY_TYPE = "type";
    // ROOMS Table - columns
    public static final String KEY_TYPE_ID = "type_id";
    // HOTEL_ROOM Table - columns
    public static final String KEY_HOTEL_ID = "hotel_id";
    public static final String KEY_ROOM_ID = "room_id";
    // RESTAURANTS Table - columns
    // id, name, description already exists
    // HOTEL_RESTAURANT Table - columns
    public static final String KEY_RESTAURANT_ID="restaurant_id";
    // USERS Table - columns
    // id, name, email - have already exists
    // USER_HOTEL_ROOM Table - columns
    // id - has already exists
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_HOTEL_ROOM_ID = "hotel_room_id";
    public static final String KEY_CODE = "code";




    // users table create statement
    private static final String CREATE_TABLE_HOTELS = "CREATE TABLE "
            + TABLE_HOTELS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_DESCRIPTION + " TEXT NOT NULL, "
            + KEY_ADDRESS + " TEXT NOT NULL, "
            + KEY_PHONE + " INTEGER, "
            + KEY_EMAIL + " TEXT)";

    // room_types table create statement
    private static final String CREATE_TABLE_ROOM_TYPES = "CREATE TABLE "
            + TABLE_ROOM_TYPES + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TYPE + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NOT NULL)";

    // rooms table create statement
    private static final String CREATE_TABLE_ROOMS = "CREATE TABLE "
            + TABLE_ROOMS + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TYPE_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY ("+ KEY_TYPE_ID+") REFERENCES "+TABLE_ROOM_TYPES+"("+KEY_ID+"))";

    // hotel_room table create statement
    private static final String CREATE_TABLE_HOTEL_ROOM = "CREATE TABLE "
            + TABLE_HOTEL_ROOM + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_HOTEL_ID + " INTEGER NOT NULL, "
            + KEY_ROOM_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY ("+ KEY_HOTEL_ID+") REFERENCES "+TABLE_HOTELS+"("+KEY_ID+"),"
            + "FOREIGN KEY ("+ KEY_ROOM_ID+") REFERENCES "+TABLE_ROOMS+"("+KEY_ID+"))";

    // restaurants table create statement
    private static final String CREATE_TABLE_RESTAURANTS = "CREATE TABLE "
            + TABLE_RESTAURANTS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_DESCRIPTION + " TEXT NOT NULL)";

    // hotel_restaurant table create statement
    private static final String CREATE_TABLE_HOTEL_RESTAURANT = "CREATE TABLE "
            + TABLE_HOTEL_RESTAURANT + " ("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_HOTEL_ID + " INTEGER NOT NULL, "
            + KEY_RESTAURANT_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY ("+ KEY_HOTEL_ID+") REFERENCES "+TABLE_HOTELS+"("+KEY_ID+"),"
            + "FOREIGN KEY ("+ KEY_RESTAURANT_ID+") REFERENCES "+TABLE_RESTAURANTS+"("+KEY_ID+"))";

    // users table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_EMAIL + " TEXT)";

    // user_hotel_room table create statement
    private static final String CREATE_TABLE_USER_HOTEL_ROOM = "CREATE TABLE "
            + TABLE_USER_HOTEL_ROOM + " ("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_ID + " INTEGER NOT NULL, "
            + KEY_HOTEL_ROOM_ID + " INTEGER NOT NULL, "
            + KEY_CODE + " TEXT NOT NULL UNIQUE, "
            + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
            + "FOREIGN KEY (" + KEY_HOTEL_ROOM_ID + ") REFERENCES " + TABLE_HOTEL_ROOM + "(" + KEY_ID + "))";


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
        db.execSQL(CREATE_TABLE_HOTELS);
        Log.d(TAG, " hotels was created");
        db.execSQL(CREATE_TABLE_ROOM_TYPES);
        Log.d(TAG, " room_types was created");
        db.execSQL(CREATE_TABLE_ROOMS);
        Log.d(TAG, " rooms was created");
        db.execSQL(CREATE_TABLE_HOTEL_ROOM);
        Log.d(TAG, " hotel_room was created");
        db.execSQL(CREATE_TABLE_RESTAURANTS);
        Log.d(TAG, " restaurants was created");
        db.execSQL(CREATE_TABLE_HOTEL_RESTAURANT);
        Log.d(TAG, " hotel_restaurant was created");
        db.execSQL(CREATE_TABLE_USERS);
        Log.d(TAG, " users was created");
        db.execSQL(CREATE_TABLE_USER_HOTEL_ROOM);
        Log.d(TAG, " user_hotel_room was created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HOTEL_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
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

