package servlet;

import com.google.gson.Gson;
import dao.DBConnection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/MapServlet")
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String jsonResponse = "";

        if ("provinces".equals(action)) {
            jsonResponse = getProvinces(); // 获取省份数据
        } else if ("cities".equals(action)) {
            String provinceId = request.getParameter("province_id");
            jsonResponse = getCities(provinceId); // 获取城市数据
        } else if ("attractions".equals(action)) {
            String cityName = request.getParameter("city");
            String limitParam = request.getParameter("limit");
            int limit = (limitParam != null) ? Integer.parseInt(limitParam) : 10; // 默认显示10条
            jsonResponse = getAttractions(cityName, limit); // 获取景点数据
        }

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonResponse);
        out.close();
    }

    private String getProvinces() {
        List<Area> provinces = new ArrayList<>();
        String query = "SELECT area_id, name FROM dou_area WHERE parent_id = 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                provinces.add(new Area(rs.getInt("area_id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Gson().toJson(provinces);
    }

    private String getCities(String provinceId) {
        List<Area> cities = new ArrayList<>();
        String query = "SELECT area_id, name FROM dou_area WHERE parent_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(provinceId));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cities.add(new Area(rs.getInt("area_id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Gson().toJson(cities);
    }

    private String getAttractions(String cityName, int limit) {
        List<Attraction> attractions = new ArrayList<>();
        String query = "SELECT name, latitude, longitude, comments_count FROM attractions WHERE city_name = ? ORDER BY comments_count DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cityName);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    attractions.add(new Attraction(
                            rs.getString("name"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude"),
                            rs.getInt("comments_count") // 这里保持一致
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Gson().toJson(attractions);
    }

    // 内部类：表示区域（省或市）
    private static class Area {
        private int id;
        private String name;

        public Area(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    // 内部类：表示景点
    private static class Attraction {
        private String name;
        private double latitude;
        private double longitude;
        private int comments_count;

        public Attraction(String name, double latitude, double longitude, int comments_count) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.comments_count = comments_count;
        }

        public String getName() {
            return name;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getCommentsCount() {
            return comments_count;
        }
    }
}
