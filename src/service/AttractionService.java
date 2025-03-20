package service;

import dao.AttractionDAO;
import model.Attraction;
import java.util.List;

public class AttractionService {
    private final AttractionDAO attractionDAO = new AttractionDAO();

    // 根据城市名获取景点列表（支持分页或条数限制）
    public List<Attraction> getAttractionsByCity(String cityName, Integer limit) {
        return attractionDAO.getAttractions(cityName, limit);
    }
}
