// DouAreaService.java
package service;

import dao.DouAreaDAO;
import model.DouArea;

import java.sql.Connection;
import java.util.List;

public class DouAreaService {
    private DouAreaDAO douAreaDAO;

    public DouAreaService(Connection connection) {
        this.douAreaDAO = new DouAreaDAO(connection);
    }

    // 获取所有省份
    public List<DouArea> getAllProvinces() throws Exception {
        return douAreaDAO.getAllProvinces();
    }

    // 获取指定省份的城市
    public List<DouArea> getCitiesByProvinceId(int provinceId) throws Exception {
        return douAreaDAO.getCitiesByProvinceId(provinceId);
    }
}
