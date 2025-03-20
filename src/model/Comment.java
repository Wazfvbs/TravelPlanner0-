package model;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private int userId;
    private int attractionId;
    private String content;
    private int rating;
    private LocalDateTime timestamp;
    private String nickname; // 用户昵称
    private String avatar; // 用户头像
    private String attractionName; // 景点名称
    private int attractionid;

    // 完整构造函数
    public Comment(int id, int userId, int attractionId, String content, int rating, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.attractionId = attractionId;
        this.content = content;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    // 用于插入的简化构造函数
    public Comment(int userId, int attractionId, String content, int rating, LocalDateTime timestamp) {
        this.userId = userId;
        this.attractionId = attractionId;
        this.content = content;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public Comment() {

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Getter 和 Setter 方法
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }
}
