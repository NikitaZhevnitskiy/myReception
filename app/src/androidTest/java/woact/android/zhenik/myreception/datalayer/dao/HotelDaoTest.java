package woact.android.zhenik.myreception.datalayer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.CacheDataFactory;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;

import static org.junit.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;

public class HotelDaoTest {

    private static final String TAG="CacheDataFactoryTest:> ";
    private CacheDataFactory ddf;
    private HotelDao hotelDao;
    private long id1;
    private long id2;


    @Before
    public void setUp() {
        // In case you need the context in your test
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
        ddf = new CacheDataFactory();
        hotelDao = new HotelDao();
        createTestData();
    }

    private void createTestData() {
        Hotel hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
        Hotel hotel2 = new Hotel("doo","dar","address", 41, "foo@bar.no");
        id1=ddf.createHotel(hotel1);
        id2=ddf.createHotel(hotel2);
    }

    @After
    public void tearDown(){
        clearTable(TABLE_HOTELS);
        id1=-1;
        id2=-1;
        ddf=null;
        hotelDao=null;
    }
    private void clearTable(String tableName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(tableName, null, null);
        DatabaseManager.getInstance().closeDatabase();
        Log.d(TAG, "---table clean---\n"+tableName);
    }

    @Test
    public void getHotel_HotelExists(){
        // Act
        Hotel hotel = hotelDao.getHotel(id1);

        // Assert
        assertNotNull(hotel);
        assertEquals(1, hotel.getId());
        assertEquals("foo", hotel.getName());
    }

    @Test
    public void getHotel_HotelNotExists(){
        // Act
        Hotel hotel = hotelDao.getHotel(1123123123);

        // Assert
        assertNull(hotel);
    }

    @Test
    public void getHotels_2HotelExist(){
        // Act
        List<Hotel> hotels = hotelDao.getHotels();

        // Assert
        assertEquals(2, hotels.size());
    }

}