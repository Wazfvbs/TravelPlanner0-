package dao;

import model.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripDAO {
    public boolean addTrip(Trip trip) {
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO Trips (userId, attractionId, date) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, trip.getUserId());
            preparedStatement.setInt(2, trip.getAttractionId());
            preparedStatement.setString(3, trip.getDate());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return false;
    }

    public List<Trip> getTripsByUserId(int userId) {
        List<Trip> trips = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Trips WHERE userId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Trip trip = new Trip(
                        resultSet.getInt("id"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("attractionId"),
                        resultSet.getString("date")
                );
                trips.add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return trips;
    }
    public String recommendTrip(String preferences) {
        // 将用户偏好分割成关键词数组
        String[] keywords = preferences.split(",");
        List<String> attractions = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        // SQL 查询语句，基于标签进行匹配
        String sql = "SELECT name FROM Attractions WHERE tags LIKE ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 遍历关键词，根据每个关键词查询景点
            for (String keyword : keywords) {
                preparedStatement.setString(1, "%" + keyword.trim() + "%");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    // 将匹配的景点名称添加到列表
                    attractions.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection); // 关闭数据库连接
        }

        // 如果没有找到任何景点，返回提示
        if (attractions.isEmpty()) {
            return "未找到匹配的景点，请尝试修改偏好。";
        }

        // 将推荐景点拼接成字符串返回
        StringBuilder recommendation = new StringBuilder("根据您的偏好推荐以下景点：\n");
        for (String attraction : attractions) {
            recommendation.append("- ").append(attraction).append("\n");
        }
        return recommendation.toString();
    }

}
