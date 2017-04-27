package woact.android.zhenik.myreception.datalayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import woact.android.zhenik.myreception.datalayer.entities.Hotel;

import static org.junit.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;

public class DummyDataFactoryTest {

    private static final String TAG="DummyDataFactoryTest:> ";
    private DummyDataFactory ddf;
    private Hotel hotel1;

    @Before
    public void setUp() {
        // In case you need the context in your test
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
//        DatabaseManager mDatabaseManager=DatabaseManager.getInstance();
        ddf = new DummyDataFactory();
        hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
    }

    @After
    public void tearDown(){
        ddf.clearHotelTable();
        hotel1= null;
    }

    @Test
    public void checkDatabaseColumns(){
        // Arrange
        String selectQuery = "SELECT  * FROM " + TABLE_HOTELS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // log
        Log.d(TAG, "TABLE NAME: "+ Arrays.asList(cursor.getColumnNames()).toString());
        // Assert
        assertEquals(6, cursor.getColumnCount());
        assertEquals(KEY_ID, cursor.getColumnName(0));
        assertEquals(KEY_HOTEL_NAME, cursor.getColumnName(1));
        assertEquals(KEY_DESCRIPTION, cursor.getColumnName(2));
        assertEquals(KEY_ADDRESS, cursor.getColumnName(3));
        assertEquals(KEY_PHONE, cursor.getColumnName(4));
        assertEquals(KEY_EMAIL, cursor.getColumnName(5));

    }

    @Test
    public void createHotel_ValidData(){

        // Act
        long id = ddf.createHotel(hotel1);

        // Assert
        assertTrue(id!=-1);
    }
    @Test
    public void createHotel_NotValidData(){

        // Act
        long id = ddf.createHotel(new Hotel());

        // Assert
        assertTrue(id==-1);
    }
    @Test
    public void createHotel_NameIsNotUnique(){
        // Arrange
        long id1 = ddf.createHotel(hotel1);

        // Act
        long id2 = ddf.createHotel(hotel1);

        // Assert
        assertTrue(id1!=-1);
        assertTrue(id2==-1);

    }
}