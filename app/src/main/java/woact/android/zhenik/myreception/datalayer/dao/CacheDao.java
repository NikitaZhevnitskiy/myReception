package woact.android.zhenik.myreception.datalayer.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import woact.android.zhenik.myreception.datalayer.entities.Restaurant;


public class CacheDao {

    private static final String TAG = "CacheDao:> ";

    private HotelDao hotelDao;
    private RoomTypeDao roomTypeDao;
    private RoomDao roomDao;
    private RestaurantDao restaurantDao;
    private UserDao userDao;

    private static CacheDao instance;

    private CacheDao(){
        this(new HotelDao(), new RoomTypeDao(), new RoomDao(), new RestaurantDao(), new UserDao());
    }

    private CacheDao(HotelDao hotelDao, RoomTypeDao roomTypeDao, RoomDao roomDao, RestaurantDao restaurantDao, UserDao userDao) {
        this.hotelDao=hotelDao;
        this.roomTypeDao=roomTypeDao;
        this.roomDao=roomDao;
        this.restaurantDao=restaurantDao;
        this.userDao = userDao;
    }

    public static synchronized CacheDao getInstance() {
        if (instance == null) {
            instance = new CacheDao();
            Log.d(TAG, "--- init instance ---");
        }
        return instance;
    }

    public HotelDao getHotelDao() {
        return hotelDao;
    }

    public RoomTypeDao getRoomTypeDao() {return roomTypeDao;}

    public RoomDao getRoomDao() {return roomDao;}

    public RestaurantDao getRestaurantDao() {
        return restaurantDao;
    }

    public UserDao getUserDao() {return userDao;}
}
