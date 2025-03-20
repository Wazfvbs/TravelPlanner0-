package model;

public class Trip {
    private int id;
    private int userId;
    private int attractionId;
    private String date;

    // 构造方法
    public Trip() {}

    public Trip(int id, int userId, int attractionId, String date) {
        this.id = id;
        this.userId = userId;
        this.attractionId = attractionId;
        this.date = date;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
