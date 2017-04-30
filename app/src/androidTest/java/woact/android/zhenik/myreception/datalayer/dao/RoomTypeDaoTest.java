package woact.android.zhenik.myreception.datalayer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import woact.android.zhenik.myreception.datalayer.CacheDataFactory;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.RoomType;

import static junit.framework.Assert.*;
import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_ROOM_TYPES;

public class RoomTypeDaoTest {
    private static final String TAG="RoomTypeDaoTest:> ";
    private CacheDataFactory ddf;
    private RoomTypeDao roomTypeDao;
    private long id1;
    private long id2;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
        ddf = new CacheDataFactory();
        roomTypeDao = new RoomTypeDao();
        createTestData();
    }

    private void createTestData() {
        id1=ddf.createRoomType(new RoomType("Standard", "Nice room"));
        id2=ddf.createRoomType(new RoomType("Luxus", "Super room"));
    }

    @After
    public void tearDown(){
        clearTable(TABLE_ROOM_TYPES);
        id1=-1;
        id2=-1;
        ddf=null;
        roomTypeDao=null;
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
}