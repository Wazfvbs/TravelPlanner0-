package service;

import dao.DBConnection;
import dao.UserDAO;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    public boolean registerUser(User user) {
        return userDAO.addUser(user);
    }
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }
    public boolean validateUser(String username, String password) {
        return userDAO.validateUser(username, password); // 验证用户登录
    }
    public boolean isUserExists(String username) {
        return userDAO.findByUsername(username) != null;
    }
    public User findByUserName(String username) {
        return userDAO.findByUsername(username);
    }
}

