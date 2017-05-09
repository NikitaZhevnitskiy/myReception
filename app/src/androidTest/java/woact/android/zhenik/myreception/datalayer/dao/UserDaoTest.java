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

import static junit.framework.Assert.*;

import woact.android.zhenik.myreception.datalayer.CacheDataLoader;
import woact.android.zhenik.myreception.datalayer.DatabaseHelper;
import woact.android.zhenik.myreception.datalayer.DatabaseManager;
import woact.android.zhenik.myreception.datalayer.entities.User;

import static woact.android.zhenik.myreception.datalayer.DatabaseHelper.TABLE_USERS;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private static final String TAG = "UserDaoTest:> ";
    private CacheDataLoader cdf;
    private UserDao userDao;
    private long userId1;
    private long userId2;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(context);
        DatabaseManager.initializeInstance(mDatabaseHelper);
        cdf = new CacheDataLoader();
        userDao = new UserDao();
        createTestData();
    }

    private void createTestData() {
        userId1 = cdf.createUser(new User("foo", "bar@bar.no"));
        userId2 = cdf.createUser(new User("foo1", "bar@bar.no1"));
    }

    @After
    public void tearDown() {
        userId1 = -1;
        userId2 = -1;
        cdf = null;
        userDao = null;
        clearTable(TABLE_USERS);

    }

    private void clearTable(String tableName) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(tableName, null, null);
        DatabaseManager.getInstance().closeDatabase();
        Log.d(TAG, "---table clean---\n" + tableName);
    }

    @Test
    public void getUser_UserExists() {
        // Act
        User user = userDao.getUser(userId1);

        // Assert
        assertTrue(user != null);
        assertEquals(userId1, user.getId());
    }

    @Test
    public void getUser_UserNOTExists() {
        // Act
        User user = userDao.getUser(342342);

        // Assert
        assertTrue(user == null);

    }


}
