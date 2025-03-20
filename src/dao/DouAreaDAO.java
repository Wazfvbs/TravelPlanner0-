// DouAreaDAO.java
package dao;

import model.DouArea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DouAreaDAO {
    private Connection connection;

    public DouAreaDAO(Connection connection) {
        this.connection = connection;
    }

    public DouAreaDAO() {

    }

    // 获取所有省份（假设中国的 parent_id 为 0）
    public List<DouArea> getAllProvinces() throws SQLException {
        List<DouArea> provinces = new ArrayList<>();
        String query = "SELECT area_id, parent_id, name FROM dou_area WHERE parent_id = 0";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                DouArea area = new DouArea(
                        rs.getInt("area_id"),
                        rs.getInt("parent_id"),
                        rs.getString("name")
                );
                provinces.add(area);
            }
        }
        return provinces;
    }

    // 获取某省份的所有城市
    public List<DouArea> getCitiesByProvinceId(int provinceId) throws SQLException {
        List<DouArea> cities = new ArrayList<>();
        String query = "SELECT area_id, parent_id, name FROM dou_area WHERE parent_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, provinceId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    DouArea area = new DouArea(
                            rs.getInt("area_id"),
                            rs.getInt("parent_id"),
                            rs.getString("name")
                    );
                    cities.add(area);
                }
            }
        }
        return cities;
    }

    public List<DouArea> searchCities(String keyword) {
        List<DouArea> cities = new ArrayList<>();
        String query = "SELECT area_id, parent_id, name FROM dou_area WHERE name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    DouArea area = new DouArea(
                            rs.getInt("area_id"),
                            rs.getInt("parent_id"),
                            rs.getString("name")
                    );
                    cities.add(area);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
    public List<DouArea> getPagedCities(int parentId, int offset, int limit) {
        List<DouArea> cities = new ArrayList<>();
        String query = "SELECT area_id, parent_id, name FROM dou_area WHERE parent_id = ? LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, parentId);
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DouArea city = new DouArea(
                            rs.getInt("area_id"),
                            rs.getInt("parent_id"),
                            rs.getString("name")
                    );
                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
    public DouArea getCityByName(String cityName) {
        String query = "SELECT * FROM dou_area WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DouArea(
                            rs.getInt("area_id"),
                            rs.getInt("parent_id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public DouArea getCityById(int cityId) {
        String query = "SELECT * FROM dou_area WHERE area_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DouArea(
                            rs.getInt("area_id"),
                            rs.getInt("parent_id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取所有顶级区域（parentId = 0）
    public static List<DouArea> getAllTopLevelAreas() {
        List<DouArea> areas = new ArrayList<>();
        String sql = "SELECT * FROM dou_area WHERE parent_id = 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DouArea area = new DouArea(
                        rs.getInt("areaId"),
                        rs.getInt("parentId"),
                        rs.getString("name")
                );
                areas.add(area);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return areas;
    }
    //获取指定 parentId 的子区域
    public static List<DouArea> getSubAreas(int parentId) {
        List<DouArea> subAreas = new ArrayList<>();
        String sql = "SELECT * FROM dou_area WHERE parent_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DouArea area = new DouArea(
                        rs.getInt("areaId"),
                        rs.getInt("parentId"),
                        rs.getString("name")
                );
                subAreas.add(area);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subAreas;
    }


    private DouArea mapResultSetToDouArea(ResultSet rs) throws SQLException {
        DouArea city = new DouArea();
        city.setAreaId(rs.getInt("id"));
        city.setName(rs.getString("name"));
        city.setParentId(rs.getInt("parent_id"));
        return city;
    }
}
