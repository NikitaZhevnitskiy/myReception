package woact.android.zhenik.myreception.datalayer.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import woact.android.zhenik.myreception.datalayer.CacheDataLoader;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Restaurant;

import static org.junit.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_RESTAURANT;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_RESTAURANTS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;


public class RestaurantDaoTest {

    private static final String TAG="RestaurantDaoTest:> ";
    private CacheDataLoader cdf;
    private RestaurantDao restaurantDao;
    private Hotel hotel1;

    @Before
    public void setUp() {
        // In case you need the context in your test
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
//        DatabaseManager mDatabaseManager=DatabaseManager.getInstance();
        cdf = new CacheDataLoader();
        restaurantDao=new RestaurantDao();
        hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
    }

    @After
    public void tearDown(){
        clearTable(TABLE_HOTEL_ROOM);
        clearTable(TABLE_ROOMS);
        clearTable(TABLE_ROOM_TYPES);
        clearTable(TABLE_HOTEL_RESTAURANT);
        clearTable(TABLE_RESTAURANTS);
        clearTable(TABLE_HOTELS);
    }


    private void clearTable(String tableName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(tableName, null, null);
        DatabaseManager.getInstance().closeDatabase();
        Log.d(TAG, "---table clean---\n"+tableName);
    }

    private int rawCount(String tableName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("select * from "+tableName, null);
        int rows=cursor.getCount();
        DatabaseManager.getInstance().closeDatabase();
        return rows;
    }

    @Test
    public void getHotelRestaurants_2Restaurants(){

        /**
         * ARRANGE
         * */
        // create hotel
        long hotelId = cdf.createHotel(hotel1);
        // create restaurants
        long restaurantId1 = cdf.createRestaurant(new Restaurant("foo", "bar"));
        long restaurantId2 = cdf.createRestaurant(new Restaurant("food", "bar"));
        // register restaurants for hotel
        long hotelRestaurantId1 = cdf.createHotelRestaurant(hotelId, restaurantId1);
        long hotelRestaurantId2 = cdf.createHotelRestaurant(hotelId, restaurantId2);

        /**
         * ACT
         * */
        List<Restaurant> restaurants = restaurantDao.getHotelRestaurantsList(hotelId);

        /**
         * ASSERT
         * */
        assertEquals(2, restaurants.size());
    }
}