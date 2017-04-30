package woact.android.zhenik.myreception.datalayer.entities;


import java.io.Serializable;

public class Room implements Serializable{

    private long id;
    private RoomType type;
    private long roomTypeId;

    public Room() {
    }

    public Room(long id, RoomType type) {
        this.id = id;
        this.type = type;
    }

    public Room(long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Room(RoomType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
}
