package woact.android.zhenik.myreception.datalayer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import woact.android.zhenik.myreception.datalayer.CacheDataLoader;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Room;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static junit.framework.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;

@RunWith(AndroidJUnit4.class)
public class RoomTypeDaoTest {
    private static final String TAG="RoomTypeDaoTest:> ";
    private CacheDataLoader cdf;
    private RoomTypeDao roomTypeDao;
    private long id1;
    private long id2;
    private Hotel hotel1;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
        cdf = new CacheDataLoader();
        roomTypeDao = new RoomTypeDao();
        createTestData();
    }

    private void createTestData() {
        id1= cdf.createRoomType(new RoomType("Standard", "Nice room"));
        id2= cdf.createRoomType(new RoomType("Luxus", "Super room"));
        hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
    }

    @After
    public void tearDown(){
        id1=-1;
        id2=-1;
        cdf =null;
        roomTypeDao=null;
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

    @Test
    public void checkGetRoomType_ValidData(){
        // Act
        RoomType roomType = roomTypeDao.getRoomType(id1);

        // Assert
        assertEquals("Standard", roomType.getType());
        assertEquals("Nice room", roomType.getDescription());
    }

    //(hotel has 2 rooms - 1 Standard and 1 Lux)
    @Test
    public void checkGetRoomTypeForHotel_2Types2Rooms1Hotel(){

        /**
         * ARRANGE
         * */
        // create hotel
        long hotelId = cdf.createHotel(hotel1);
        // create room types
        long roomTypeId1 = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomTypeId2 = cdf.createRoomType(new RoomType("Lux", "Huge room 24 sqrt"));
        // create rooms
        long roomId1 = cdf.createRoom(roomTypeId1);
        long roomId2 = cdf.createRoom(roomTypeId2);
        // register rooms in hotel
        long hotelRoomId1 = cdf.createHotelRoom(hotelId, roomId1);
        long hotelRoomId2 = cdf.createHotelRoom(hotelId, roomId2);
//        // middle assert
//        List<Room> rooms = cdf.getRoomList(hotelId);
//        assertEquals(2, rooms.size());

        /**
         * ACT
         * */
        List<RoomType> types = roomTypeDao.getRoomTypeForHotel(hotelId);

        /**
         * ASSERT
         * */
        assertEquals(2, types.size());
    }


    //(hotel has 3 rooms - all of them are standard type)
    @Test
    public void checkGetRoomTypeForHotel_1Types3Rooms1Hotel() {

        /**
         * ARRANGE
         * */
        // create hotel
        long hotelId = cdf.createHotel(hotel1);
        // create room types
        long roomTypeId1 = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        // create rooms
        long roomId1 = cdf.createRoom(roomTypeId1);
        long roomId2 = cdf.createRoom(roomTypeId1);
        long roomId3 = cdf.createRoom(roomTypeId1);
        // register rooms in hotel
        long hotelRoomId1 = cdf.createHotelRoom(hotelId, roomId1);
        long hotelRoomId2 = cdf.createHotelRoom(hotelId, roomId2);
        long hotelRoomId3 = cdf.createHotelRoom(hotelId, roomId3);


        /**
         * ACT
         * */
        List<RoomType> types = roomTypeDao.getRoomTypeForHotel(hotelId);

        /**
         * ASSERT
         * */
        assertEquals(1, types.size());

    }
}