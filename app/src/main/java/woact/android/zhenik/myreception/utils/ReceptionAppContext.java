package woact.android.zhenik.myreception.utils;

import android.app.Application;

import woact.android.zhenik.myreception.datalayer.entities.Hotel;

public class ReceptionAppContext extends Application {
    public static final String HOTEL_IN_SYSTEM = "HOTEL_IN_SYSTEM";

    public static Hotel hotel = null;
    public static Hotel getHotel() {return hotel;}
    public static void setHotel(Hotel hotel) {ReceptionAppContext.hotel = hotel;}

    public static Long hotelId=null;
    public static Long getHotelId() {return hotelId;}
    public static void setHotelId(Long hotelId) {ReceptionAppContext.hotelId = hotelId;}

    private static Long userId = null;

    public static Long getUserId() {return userId;}

    public static void setUserId(Long userId) {ReceptionAppContext.userId = userId;}
}
