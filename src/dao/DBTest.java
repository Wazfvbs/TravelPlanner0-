package dao;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        // 测试数据库连接
        Connection connection = DBConnection.getConnection();
        if (connection != null) {
            System.out.println("数据库连接成功！");
            DBConnection.closeConnection(connection);
        } else {
            System.out.println("数据库连接失败！");
        }
    }
}
