package servlet;

import com.google.gson.Gson;
import dao.DBConnection;
import model.Attraction;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/AttractionDetailServlet")
public class AttractionDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DBConnection.getConnection()) {
            // 查询景点详细信息
            String attractionQuery = "SELECT * FROM attractions WHERE id = ?";
            Attraction attraction = null;
            try (PreparedStatement stmt = conn.prepareStatement(attractionQuery)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        attraction = new Attraction(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("city_name"),
                                rs.getInt("guides_count"),
                                rs.getInt("comments_count"),
                                rs.getInt("_rank"),
                                rs.getDouble("rating"),
                                rs.getString("description")
                        );
                    }
                }
            }

            // 查询相关评论
            String commentsQuery = "SELECT content FROM comments WHERE attraction_id = ?";
            List<String> comments = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement(commentsQuery)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        comments.add(rs.getString("content"));
                    }
                }
            }

            // 将数据存入请求范围
            request.setAttribute("attraction", attraction);
            request.setAttribute("comments", comments);

            // 转发到 detail.jsp
            request.getRequestDispatcher("/detail.jsp").forward(request, response);
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(attraction));
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器错误，请稍后再试。");
        }
    }
}

