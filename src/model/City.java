package model;

import java.util.List;

public class City {
    private DouArea city; // 城市信息
    private DouArea province; //
    private List<Attraction> attractions; // 景点列表

    // 构造函数
    public City(DouArea city, DouArea province,List<Attraction> attractions) {
        this.city = city;
        this.province = province;
        this.attractions = attractions;
    }

    // Getter 和 Setter
    public DouArea getCity() {
        return city;
    }

    public void setCity(DouArea city) {
        this.city = city;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
}
