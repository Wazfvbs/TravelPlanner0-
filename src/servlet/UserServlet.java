package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;
import dao.DBConnection;
import dao.UserDAO;
import model.User;
import service.UserService;
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            handleUpdate(request, response);
        } else if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            handleLogout(request, response);
        } else if ("viewProfile".equals(action)){
            handleViewProfile(request, response);
        } else if ("viewUsers".equals(action)) {
            handleViewUsers(request, response);
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        User updatedUser = gson.fromJson(reader, User.class);

        UserDAO userDAO = new UserDAO();
        User existingUser = userDAO.getUserById(updatedUser.getId());

        if (existingUser == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\":\"User not found!\"}");
            return;
        }

        // 如果密码为空，则保留原有密码
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existingUser.getPassword());
        }

        boolean success = userDAO.updateUser(updatedUser);

        if (success) {
            response.getWriter().write("{\"message\":\"User updated successfully!\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Failed to update user!\"}");
        }
    }



    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.getWriter().write("用户名或密码不能为空！");
            return;
        }

        if (userService.isUserExists(username)) {
            response.getWriter().write("注册失败！用户名已存在。");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        User newUser = new User(0, username, password, null, null, null, null, null);
        boolean success = userService.registerUser(newUser);

        response.getWriter().write(success ? "注册成功！" : "注册失败！");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userService.validateUser(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("index.jsp");
        } else {
            response.getWriter().write("登录失败，用户名或密码错误！");
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("index.jsp");
    }

    private User Profile(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    System.out.println( user.getId());
                    user.setUsername(rs.getString("username"));
                    user.setName(rs.getString("name"));
                    user.setGender(rs.getString("gender"));
                    user.setHobbies(rs.getString("hobbies"));
                    user.setDescription(rs.getString("description"));
                    user.setAvatar(rs.getString("avatar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    private void handleViewProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        HttpSession session = request.getSession(false);

        String username = (String) session.getAttribute("username");
        if (username == null || username.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"用户未登录，请先登录！\"}");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.findByUsername(username);
        if (user != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user); // 转换用户数据为 JSON 包括密码
            response.getWriter().write(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\":\"未找到用户信息！\"}");
        }
    }



    private void handleViewUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = userService.getAllUsers();

        Gson gson = new Gson();
        String json = gson.toJson(users);
        response.getWriter().write(json);
    }
}
