package dao;

import model.Attraction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDAO {
    private Connection conn;
    public AttractionDAO(Connection conn) {
        this.conn = conn;
    }
    public AttractionDAO() {

    }
    public void updateAttractionCount(int id, int delta) throws SQLException {
        String query = "UPDATE attractions SET guides_count = guides_count + ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, delta);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // 获取景点列表（可选按城市过滤）
    public List<Attraction> getAttractions(String cityName, Integer limit) {
        List<Attraction> attractions = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Attractions");
        if (cityName != null && !cityName.isEmpty()) {
            query.append(" WHERE city_name = ?");
        }
        query.append(" ORDER BY comments_count DESC");
        if (limit != null && limit > 0) {
            query.append(" LIMIT ?");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (cityName != null && !cityName.isEmpty()) {
                stmt.setString(paramIndex++, cityName);
            }
            if (limit != null && limit > 0) {
                stmt.setInt(paramIndex, limit);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attractions.add(mapResultSetToAttraction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }
    public Attraction getAttractions(String cityName) {
        String query = "SELECT * FROM attractions WHERE city_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, cityName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToAttraction(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 映射结果集到 Attraction 对象
    private Attraction mapResultSetToAttraction(ResultSet rs) throws SQLException {
        Attraction attraction = new Attraction();
        attraction.setId(rs.getInt("id"));
        attraction.setName(rs.getString("name"));
        attraction.setCityName(rs.getString("city_name"));
        attraction.setCommentsCount(rs.getInt("comments_count"));
        attraction.setGuidesCount(rs.getInt("guides_count"));
        attraction.setRank(rs.getInt("_rank"));
        attraction.setRating(rs.getDouble("rating"));
        attraction.setLongitude(rs.getDouble("longitude"));
        attraction.setLatitude(rs.getDouble("latitude"));
        attraction.setDescription(rs.getString("description"));
        return attraction;
    }

    // 获取景点详情
    public static Attraction getAttractionById(int id) {
        String sql = "SELECT * FROM attractions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Attraction attraction = new Attraction();
                attraction.setId(rs.getInt("id"));
                attraction.setName(rs.getString("name"));
                attraction.setCityName(rs.getString("cityName"));
                attraction.setDescription(rs.getString("description"));
                attraction.setRank(rs.getInt("rank"));
                attraction.setGuidesCount(rs.getInt("guidesCount"));
                attraction.setCommentsCount(rs.getInt("commentsCount"));
                return attraction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 获取景点详情
    public Attraction getAttractionByName(String attractionName) {
        String query = "SELECT * FROM attractions WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, attractionName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToAttraction(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 增加点赞数
    public static boolean incrementLikes(int id) {
        String sql = "UPDATE Attraction SET guidesCount = guidesCount + 1 WHERE id = ?";
        return executeUpdate(sql, id);
    }
    // 增加拒绝数
    public static boolean incrementDislikes(int id) {
        String sql = "UPDATE Attraction SET guidesCount = guidesCount - 1 WHERE id = ?";
        return executeUpdate(sql, id);
    }
    // 通用更新方法
    private static boolean executeUpdate(String sql, int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Attraction> getAttractionsByCityName(String cityName) {
        List<Attraction> attractions = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM attractions WHERE city_name = ?")) {
            stmt.setString(1, cityName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attractions.add(new Attraction(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString ("city_name"),
                        rs.getInt ("comments_count"),
                        rs.getInt ("guides_count"),
                        rs.getInt ("_rank"),
                        rs.getDouble ("rating"),
                        rs.getDouble ("longitude"),
                        rs.getDouble ("latitude"),
                        rs.getString ("description")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractions;
    }
    public static List<Attraction> getAttractionsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<Attraction> attractions = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM attractions WHERE id IN (");
        query.append(String.join(",", ids.stream().map(id -> "?").toArray(String[]::new)));
        query.append(")");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }

            ResultSet rs = ps.executeQuery();
            AttractionDAO dao = new AttractionDAO(); // 创建实例
            while (rs.next()) {
                attractions.add(dao.mapResultSetToAttraction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }


}
