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
import java.util.List;

import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Room;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static org.junit.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_HOTEL_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;

public class CacheDataFactoryTest {

    private static final String TAG="CacheDataFactoryTest:> ";
    private CacheDataFactory ddf;
    private Hotel hotel1;

    @Before
    public void setUp() {
        // In case you need the context in your test
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
//        DatabaseManager mDatabaseManager=DatabaseManager.getInstance();
        ddf = new CacheDataFactory();
        hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
    }

    @After
    public void tearDown(){
        clearTable(TABLE_HOTEL_ROOM);
        clearTable(TABLE_ROOMS);
        clearTable(TABLE_ROOM_TYPES);
        clearTable(TABLE_HOTELS);
        hotel1= null;
    }


    private void clearTable(String tableName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(tableName, null, null);
        DatabaseManager.getInstance().closeDatabase();
        Log.d(TAG, "---table clean---\n"+tableName);
    }

    private int rawCount(String tableName){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor  cursor = db.rawQuery("select * from "+tableName,null);
        int rows=cursor.getCount();
        DatabaseManager.getInstance().closeDatabase();
        return rows;
    }


    @Test
    public void checkDatabaseColumns_HotelTable(){
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
    public void checkDatabaseColumns_RoomTypesTable(){
        // Arrange
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM_TYPES;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // log
        Log.d(TAG, "TABLE NAME: "+ Arrays.asList(cursor.getColumnNames()).toString());
        // Assert
        assertEquals(3, cursor.getColumnCount());
        assertEquals(KEY_ID, cursor.getColumnName(0));
        assertEquals(KEY_TYPE, cursor.getColumnName(1));
        assertEquals(KEY_DESCRIPTION, cursor.getColumnName(2));
    }

    @Test
    public void checkDatabaseColumns_RoomsTable(){
        // Arrange
        String selectQuery = "SELECT  * FROM " + TABLE_ROOMS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // log
        Log.d(TAG, "TABLE NAME: "+ Arrays.asList(cursor.getColumnNames()).toString());
        // Assert
        assertEquals(2, cursor.getColumnCount());
        assertEquals(KEY_ID, cursor.getColumnName(0));
        assertEquals(KEY_TYPE_ID, cursor.getColumnName(1));
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

    @Test
    public void createRoomType_DataValid(){
        // Arrange
        String type = "Standard";
        String description = "Rooms are on floors 22 - 35 and offer views of the city";
        RoomType roomType = new RoomType(type, description);

        // Act
        long id = ddf.createRoomType(roomType);

        // Assert
        assertTrue(id!=-1);
    }

    @Test
    public void createRoom_ValidData(){
        // Arrange
        String type = "Standard";
        String description = "Rooms are on floors 22 - 35 and offer views of the city";
        RoomType roomType = new RoomType(type, description);
        long roomTypeId = ddf.createRoomType(roomType);
        Log.d(TAG, " roomId = "+roomTypeId);

        // Act
        long roomId = ddf.createRoom(roomTypeId);
        Log.d(TAG, " roomId = "+roomId);

        // Assert
        assertTrue(roomId!=-1);
        assertEquals(1, rawCount(TABLE_ROOMS));
        assertEquals(1, rawCount(TABLE_ROOM_TYPES));
    }

    // check that foreign key constraints works
    @Test
    public void createRoom_NOTValidData(){
        // Act
        long roomId = ddf.createRoom(12121l);
        Log.d(TAG, " roomId = "+roomId);

        // Assert
        assertTrue(roomId==-1);
        assertEquals(0, rawCount(TABLE_ROOMS));
        assertEquals(0, rawCount(TABLE_ROOM_TYPES));
    }

    @Test
    public void createHotelRoom_ValidData(){
        //Arrange
        long hotelId = ddf.createHotel(hotel1);
        long roomTypeId = ddf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId = ddf.createRoom(roomTypeId);

        // Act
        long hotelRoomId = ddf.createHotelRoom(hotelId, roomId);
        Log.d("MYTAG:",
              "\nhotelId: " +hotelId+"\nroomTypeId: "+roomTypeId+"\nroomId: "+roomId+"\nhotelRoomId: "+hotelRoomId);
        // Assert
        assertTrue(hotelRoomId!=-1);
    }

    @Test
    public void getRoomList_DataExist_1Room(){
        // Arrange
        long hotelId = ddf.createHotel(hotel1);
        long roomTypeId = ddf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId = ddf.createRoom(roomTypeId);
        long hotelRoomId = ddf.createHotelRoom(hotelId, roomId);

        // Act
        List<Room> hotelRooms = ddf.getRoomList(hotelId);

        // Assert
        assertEquals(1, hotelRooms.size());
    }

    @Test
    public void getRoomList_DataExist_2Rooms(){
        // Arrange
        long hotelId = ddf.createHotel(hotel1);
        long roomTypeId = ddf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId1 = ddf.createRoom(roomTypeId);
        long roomId2 = ddf.createRoom(roomTypeId);
        long hotelRoomId1 = ddf.createHotelRoom(hotelId, roomId1);
        long hotelRoomId2 = ddf.createHotelRoom(hotelId, roomId2);

        // Act
        List<Room> hotelRooms = ddf.getRoomList(hotelId);

        // Assert
        assertEquals(2, hotelRooms.size());
    }

    @Test
    public void getRoomList_DataNOTExist(){
        // Act
        List<Room> hotelRooms = ddf.getRoomList(123123l);

        // Assert
        assertEquals(0, hotelRooms.size());
    }
}