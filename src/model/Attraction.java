package model;

public class Attraction {
    private int id;
    private String name;
    private String cityName;
    private int commentsCount;
    private int guidesCount;
    private Integer rank;
    private double rating;
    private double longitude;
    private double latitude;
    private String description;

    public Attraction(int id, String name, String cityName, int commentsCount, int guidesCount, Integer rank, double rating, double longitude, double latitude, String description) {
        this.id = id;
        this.name = name;
        this.cityName = cityName;
        this.commentsCount = commentsCount;
        this.guidesCount = guidesCount;
        this.rank = rank;
        this.rating = rating;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
    }

    // 无参构造函数
    public Attraction() {
    }

    // 部分参构造函数（例如不包含经纬度的情况）
    public Attraction(int id, String name, String cityName, int commentsCount, int guidesCount, Integer rank, double rating, String description) {
        this.id = id;
        this.name = name;
        this.cityName = cityName;
        this.commentsCount = commentsCount;
        this.guidesCount = guidesCount;
        this.rank = rank;
        this.rating = rating;
        this.description = description;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getGuidesCount() {
        return guidesCount;
    }

    public void setGuidesCount(int guidesCount) {
        this.guidesCount = guidesCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
