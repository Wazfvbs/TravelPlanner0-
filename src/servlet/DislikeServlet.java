package servlet;

import dao.AttractionDAO;
import dao.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/dislikeServlet")
public class DislikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DBConnection.getConnection()) {
            AttractionDAO dao = new AttractionDAO(conn);
            dao.updateAttractionCount(id, -1); // 拒绝 -1
            response.getWriter().write("{\"success\": true, \"message\": \"操作成功！\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"操作失败！\"}");
        }
    }
}

