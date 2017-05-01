package woact.android.zhenik.myreception.datalayer.entities;

import java.io.Serializable;



public class RoomType implements Serializable {
    private long id;
    private String type;
    private String description;

    public RoomType() {
    }

    public RoomType(String type, String description) {
        this.type = type;
        this.description = description;
    }


    public RoomType(long id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescritpion(String description) {
        this.description = description;
    }
}
