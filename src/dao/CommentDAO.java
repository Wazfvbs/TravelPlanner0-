package dao;

import model.Comment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentDAO {
    // 添加评论
    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO Comments (user_id, attraction_id, content, rating, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getAttractionId());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setInt(4, comment.getRating());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(comment.getTimestamp()));

            int result = preparedStatement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 根据景点ID获取评论
    public List<Comment> getCommentsByAttractionId(int attractionId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.attraction_id, c.content, c.rating, c.created_at, " +
                "u.name AS nickname, u.avatar, a.name AS attraction_name " +
                "FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN attractions a ON c.attraction_id = a.id " +
                "WHERE a.id LIKE ? " +
                "ORDER BY c.created_at";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, attractionId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setAttractionId(rs.getInt("attraction_id"));
                comment.setContent(rs.getString("content"));
                comment.setRating(rs.getInt("rating"));
                comment.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());
                comment.setNickname(rs.getString("nickname"));
                comment.setAvatar(rs.getString("avatar"));
                comment.setAttractionName(rs.getString("attraction_name"));
                comments.add(comment);
            }
            return comments;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    // 删除评论
    public boolean deleteComment(int commentId) {
        String sql = "DELETE FROM Comments WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, commentId);
            int result = preparedStatement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 获取所有评论
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT id, user_id, attraction_id, content, rating, created_at FROM Comments";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("attraction_id"),
                        rs.getString("content"),
                        rs.getInt("rating"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }
    //搜索评论
    public List<Comment> searchComments(String keyword) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT id, user_id, attraction_id, content, rating, created_at FROM Comments WHERE content LIKE ? OR attraction_id IN (SELECT id FROM Attractions WHERE name LIKE ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("attraction_id"),
                        resultSet.getString("content"),
                        resultSet.getInt("rating"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    public List<Comment> searchComments(String query, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT c.id, c.user_id, c.attraction_id, c.content, c.rating, c.created_at, " +
                "u.name AS nickname, u.avatar, a.name AS attraction_name " +
                "FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN attractions a ON c.attraction_id = a.id " +
                "WHERE a.name LIKE ? " +
                "ORDER BY c.created_at DESC " +
                "LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setAttractionId(rs.getInt("attraction_id"));
                    comment.setContent(rs.getString("content"));
                    comment.setRating(rs.getInt("rating"));
                    comment.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());
                    comment.setNickname(rs.getString("nickname"));
                    comment.setAvatar(rs.getString("avatar"));
                    comment.setAttractionName(rs.getString("attraction_name"));
                    comments.add(comment);
                }
                return comments;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    //根据页码显示评论
    public List<Comment> getCommentsWithPagination(int page, int pageSize) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT id, user_id, attraction_id, content, rating, created_at FROM Comments LIMIT ?, ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (page - 1) * pageSize);
            preparedStatement.setInt(2, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("attraction_id"),
                        resultSet.getString("content"),
                        resultSet.getInt("rating"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );
                comments.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    //根据用户id搜索评论
    public List<Comment> getCommentsByUserId(int userId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setAttractionId(rs.getInt("attraction_id"));
                    comment.setContent(rs.getString("content"));
                    comment.setRating(rs.getInt("rating"));
                    comment.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime()); // 映射到 created_at
                    comments.add(comment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }
    //插入评论
    public boolean insertComment(Comment comment) {
        String sql = "INSERT INTO comments (user_id, attraction_id, content, rating, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, comment.getUserId());
            ps.setInt(2, comment.getAttractionId());
            ps.setString(3, comment.getContent());
            ps.setInt(4, comment.getRating());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(comment.getTimestamp()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 获取分页评论
    public List<Comment> getComments(int offset, int pageSize) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.attraction_id, c.content, c.rating, c.created_at, " +
                "u.name, u.avatar, a.name AS attraction_name " +
                "FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "JOIN attractions a ON c.attraction_id = a.id " +
                "ORDER BY c.created_at DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setAttractionId(rs.getInt("attraction_id"));
                    comment.setContent(rs.getString("content"));
                    comment.setRating(rs.getInt("rating"));
                    comment.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());
                    comment.setNickname(rs.getString("name")); // 从 ResultSet 中获取昵称
                    comment.setAvatar(rs.getString("avatar")); // 从 ResultSet 中获取头像
                    comment.setAttractionName(rs.getString("attraction_name"));
                    comments.add(comment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }
    // 获取评论总数
    public int getCommentCount() {
        String sql = "SELECT COUNT(*) AS total FROM comments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSearchTotalPages(String query, int pageSize) {
        String sql = "SELECT COUNT(*) FROM comments c " +
                "JOIN attractions a ON c.attraction_id = a.id " +
                "WHERE a.name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int totalComments = rs.getInt(1);
                    return (int) Math.ceil(totalComments / (double) pageSize);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
