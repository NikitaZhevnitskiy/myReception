package woact.android.zhenik.myreception.datalayer.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class CacheDao {

    private static final String TAG = "CacheDao:> ";

    private HotelDao hotelDao;
    private RoomTypeDao roomTypeDao;
    private RoomDao roomDao;

    private static CacheDao instance;

    private CacheDao(){
        this(new HotelDao(), new RoomTypeDao(), new RoomDao());
    }

    private CacheDao(HotelDao hotelDao, RoomTypeDao roomTypeDao, RoomDao roomDao) {
        this.hotelDao=hotelDao;
        this.roomTypeDao=roomTypeDao;
        this.roomDao=roomDao;
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
}
