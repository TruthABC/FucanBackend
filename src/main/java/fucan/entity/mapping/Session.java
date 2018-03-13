package fucan.entity.mapping;

import java.sql.Timestamp;

public class Session {

    private int id;
    private String userId;
    private Timestamp time;

    public Session() {}

    public Session(int id, String userId, Timestamp time) {
        this.id = id;
        this.userId = userId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
