package servlet;

import com.google.gson.Gson;
import dao.AttractionDAO;
import dao.DBConnection;
import dao.DouAreaDAO;
import model.Attraction;
import model.DouArea;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private DouAreaDAO douAreaDAO;
    private AttractionDAO attractionDAO;

    @Override
    public void init() {
        // 创建 DAO 类实例
        douAreaDAO = new DouAreaDAO();
        attractionDAO = new AttractionDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 确保请求按 UTF-8 解码
        response.setContentType("application/json; charset=UTF-8");
        String searchQuery = request.getParameter("query");

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            request.setAttribute("error", "搜索内容不能为空！");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        searchQuery = searchQuery.trim();
        DouArea city = douAreaDAO.getCityByName(searchQuery);
        if (city != null) {
        int cityId = douAreaDAO.getCityByName(searchQuery).getAreaId();
        System.out.println(cityId);


            request.setAttribute("cityId", cityId);
            request.getRequestDispatcher("city.jsp").forward(request, response);
            return;
        }

        Attraction attraction = attractionDAO.getAttractionByName(searchQuery);
        int attractionId = attraction.getId();
        if (attraction != null) {
        try (Connection conn = DBConnection.getConnection()) {
            // 查询景点详细信息
            String attractionQuery = "SELECT * FROM attractions WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(attractionQuery)) {
                stmt.setInt(1, attractionId);
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
                stmt.setInt(1, attractionId);
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
        }
        catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器错误，请稍后再试。");
        }
        request.setAttribute("error", "未找到匹配的城市或景点！");
        request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}