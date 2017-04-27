package woact.android.zhenik.myreception.datalayer.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class CacheDao {

    private static final String TAG = "CacheDao:> ";

    private HotelDao hotelDao;

    private static CacheDao instance;

    private CacheDao(){
        this(new HotelDao());
    }

    private CacheDao(HotelDao hotelDao){
        this.hotelDao=hotelDao;
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
}
