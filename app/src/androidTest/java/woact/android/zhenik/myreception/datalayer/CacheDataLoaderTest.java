package woact.android.zhenik.myreception.datalayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import woact.android.zhenik.myreception.datalayer.entities.Hotel;
import woact.android.zhenik.myreception.datalayer.entities.Restaurant;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;
import woact.android.zhenik.myreception.datalayer.entities.User;

import static org.junit.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ADDRESS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_DESCRIPTION;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_EMAIL;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_NAME;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_PHONE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.KEY_TYPE_ID;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTELS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_RESTAURANT;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_HOTEL_ROOM;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_RESTAURANTS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOMS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_USERS;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_USER_HOTEL_ROOM;

@RunWith(AndroidJUnit4.class)
public class CacheDataLoaderTest {

    private static final String TAG="CacheDataLoaderTest:> ";
    private CacheDataLoader cdf;
    private Hotel hotel1;

    @Before
    public void setUp() {
        // In case you need the context in your test
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
//        DatabaseManager mDatabaseManager=DatabaseManager.getInstance();
        cdf = new CacheDataLoader();
        hotel1 = new Hotel(1,"foo","bar","address", 41, "foo@bar.no");
    }

    @After
    public void tearDown(){
        clearTable(TABLE_USER_HOTEL_ROOM);
        clearTable(TABLE_USERS);
        clearTable(TABLE_HOTEL_ROOM);
        clearTable(TABLE_ROOMS);
        clearTable(TABLE_ROOM_TYPES);
        clearTable(TABLE_HOTEL_RESTAURANT);
        clearTable(TABLE_RESTAURANTS);
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
        assertEquals(KEY_NAME, cursor.getColumnName(1));
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
    public void checkDatabaseColumns_RestaurantTable(){
        // Arrange
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANTS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // log
        Log.d(TAG, "TABLE NAME: "+ Arrays.asList(cursor.getColumnNames()).toString());
        // Assert
        assertEquals(3, cursor.getColumnCount());
        assertEquals(KEY_ID, cursor.getColumnName(0));
        assertEquals(KEY_NAME, cursor.getColumnName(1));
        assertEquals(KEY_DESCRIPTION, cursor.getColumnName(2));

    }

    @Test
    public void checkDatabaseColumns_UsersTable() {
        // Arrange
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // log
        Log.d(TAG, "TABLE NAME: " + Arrays.asList(cursor.getColumnNames()).toString());
        // Assert
        assertEquals(3, cursor.getColumnCount());
        assertEquals(KEY_ID, cursor.getColumnName(0));
        assertEquals(KEY_NAME, cursor.getColumnName(1));
        assertEquals(KEY_EMAIL, cursor.getColumnName(2));

    }

    @Test
    public void createHotel_ValidData(){

        // Act
        long id = cdf.createHotel(hotel1);

        // Assert
        assertTrue(id!=-1);
    }
    @Test
    public void createHotel_NotValidData(){

        // Act
        long id = cdf.createHotel(new Hotel());

        // Assert
        assertTrue(id==-1);
    }

    @Test
    public void createHotel_NameIsNotUnique(){
        // Arrange
        long id1 = cdf.createHotel(hotel1);

        // Act
        long id2 = cdf.createHotel(hotel1);

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
        long id = cdf.createRoomType(roomType);

        // Assert
        assertTrue(id!=-1);
    }

    @Test
    public void createRoom_ValidData(){
        // Arrange
        String type = "Standard";
        String description = "Rooms are on floors 22 - 35 and offer views of the city";
        RoomType roomType = new RoomType(type, description);
        long roomTypeId = cdf.createRoomType(roomType);
        Log.d(TAG, " roomId = "+roomTypeId);

        // Act
        long roomId = cdf.createRoom(roomTypeId);
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
        long roomId = cdf.createRoom(12121l);
        Log.d(TAG, " roomId = "+roomId);

        // Assert
        assertTrue(roomId==-1);
        assertEquals(0, rawCount(TABLE_ROOMS));
        assertEquals(0, rawCount(TABLE_ROOM_TYPES));
    }

    @Test
    public void createHotelRoom_ValidData(){
        //Arrange
        long hotelId = cdf.createHotel(hotel1);
        long roomTypeId = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId = cdf.createRoom(roomTypeId);

        // Act
        long hotelRoomId = cdf.createHotelRoom(hotelId, roomId);
        Log.d("MYTAG:",
              "\nhotelId: " +hotelId+"\nroomTypeId: "+roomTypeId+"\nroomId: "+roomId+"\nhotelRoomId: "+hotelRoomId);
        // Assert
        assertTrue(hotelRoomId!=-1);
    }

    @Test
    public void createRestaurant_ValidData(){

        // Act
        long restaurantId = cdf.createRestaurant(new Restaurant("foo","bar"));

        // Assert
        assertTrue(restaurantId!=-1);

    }
    @Test
    public void createRestaurant_NOTValidData(){

        // Act
        long restaurantId = cdf.createRestaurant(new Restaurant());

        // Assert
        assertTrue(restaurantId==-1);

    }

    @Test
    public void createHotelRestaurant_ValidaData(){
        // Arrange
        long hotelId = cdf.createHotel(hotel1);
        long restaurantId = cdf.createRestaurant(new Restaurant("foo","bar"));

        // Act
        long hotelRestaurantID = cdf.createHotelRestaurant(hotelId, restaurantId);

        // Assert
        assertTrue(hotelRestaurantID!=-1);
    }

    @Test
    public void createHotelRestaurant_NOTValidaData(){
        // Arrange

        long restaurantId = cdf.createRestaurant(new Restaurant("foo","bar"));

        // Act
        long hotelRestaurantID = cdf.createHotelRestaurant(123123l, restaurantId);

        // Assert
        assertTrue(hotelRestaurantID==-1);
    }

    @Test
    public void createUser_ValidData() {
        // Arrange
        User user = new User("Joakim", "ololo@gmail.com");

        // Act
        long userId = cdf.createUser(user);

        // Assert
        assertTrue(userId != -1);
        assertEquals(1, rawCount(TABLE_USERS));
    }

    @Test
    public void createUser_NOTValidData() {
        // Arrange
        User user = new User();

        // Act
        long userId = cdf.createUser(user);

        // Assert
        assertTrue(userId == -1);
        assertEquals(0, rawCount(TABLE_USERS));
    }

    @Test
    public void createUserHotelRoom_ValidData() {
        // Arrange
        User user = new User("Joakim", "ololo@gmail.com");
        long userId = cdf.createUser(user);
        long hotelId = cdf.createHotel(hotel1);
        long roomTypeId = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId = cdf.createRoom(roomTypeId);
        long hotelRoomId = cdf.createHotelRoom(hotelId, roomId);
        Log.d("MYTAG1: ", userId + "|" + hotelRoomId + "|");
        // Act
        long userHotelRoomId = cdf.createUserHotelRoom(userId, hotelRoomId, "code");

        // Assert
        assertTrue(userHotelRoomId != -1);
        assertEquals(1, rawCount(TABLE_USER_HOTEL_ROOM));
    }

    @Test
    public void testLogin_ValidData() {
        // Arrange
        User user = new User("Joakim", "ololo@gmail.com");
        long userId = cdf.createUser(user);
        long hotelId = cdf.createHotel(hotel1);
        long roomTypeId = cdf.createRoomType(new RoomType("Standard", "Huge room 24 sqrt"));
        long roomId = cdf.createRoom(roomTypeId);
        long hotelRoomId = cdf.createHotelRoom(hotelId, roomId);
        long userHotelRoomId = cdf.createUserHotelRoom(userId, hotelRoomId, "code");

        // Act
        User user1 = cdf.login(hotelId, "code");

        // Assert
        assertNotNull(user1);
        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getEmail(), user1.getEmail());
    }



}