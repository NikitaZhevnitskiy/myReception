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
import woact.android.zhenik.myreception.datalayer.entities.Room;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static org.junit.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;

public class RoomDaoTest {

    private static final String TAG="RoomDaoTest:> ";
    private CacheDataLoader cdf;
    private RoomDao roomDao;
    private Hotel hotel1;

    @Before
    public void setUp() {
        // In case you need the context in your test
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
//        DatabaseManager mDatabaseManager=DatabaseManager.getInstance();
        cdf = new CacheDataLoader();
        roomDao=new RoomDao();
        hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
    }

    @After
    public void tearDown(){
        clearTable(TABLE_HOTEL_ROOM);
        clearTable(TABLE_ROOMS);
        clearTable(TABLE_ROOM_TYPES);
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
    public void getRoomList_DataExist_1Room(){
        // Arrange
        long hotelId = cdf.createHotel(new Hotel(1,"foo","bar","address", 41, "foo@bar.no"));
        long roomTypeId = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId = cdf.createRoom(roomTypeId);
        long hotelRoomId = cdf.createHotelRoom(hotelId, roomId);

        // Act
        List<Room> hotelRooms = roomDao.getHotelRoomsList(hotelId);

        // Assert
        assertEquals(1, hotelRooms.size());
    }

    @Test
    public void getRoomList_DataExist_2Rooms(){
        // Arrange
        long hotelId = cdf.createHotel(hotel1);
        long roomTypeId = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId1 = cdf.createRoom(roomTypeId);
        long roomId2 = cdf.createRoom(roomTypeId);
        long hotelRoomId1 = cdf.createHotelRoom(hotelId, roomId1);
        long hotelRoomId2 = cdf.createHotelRoom(hotelId, roomId2);

        // Act
        List<Room> hotelRooms = roomDao.getHotelRoomsList(hotelId);

        // Assert
        assertEquals(2, hotelRooms.size());
    }

    @Test
    public void getRoomList_DataNOTExist(){
        // Act
        List<Room> hotelRooms = roomDao.getHotelRoomsList(12312321l);

        // Assert
        assertEquals(0, hotelRooms.size());
    }
}