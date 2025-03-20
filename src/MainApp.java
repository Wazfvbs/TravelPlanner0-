/*

import model.User;
import service.UserService;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用旅游管理系统");
        System.out.println("1. 注册用户");
        System.out.println("2. 登录用户");
        System.out.println("3. 查看所有用户");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 吸收换行符

        switch (choice) {
            case 1:
                System.out.println("请输入用户名：");
                String username = scanner.nextLine();
                System.out.println("请输入密码：");
                String password = scanner.nextLine();
                System.out.println("请输入偏好：");
                String preferences = scanner.nextLine();

                User newUser = new User(0, username, password, preferences);
                if (userService.registerUser(newUser)) {
                    System.out.println("注册成功！");
                } else {
                    System.out.println("注册失败！");
                }
                break;

            case 2:
                System.out.println("请输入用户名：");
                String username1 = scanner.nextLine();
                System.out.println("请输入密码：");
                String password1 = scanner.nextLine();
                if (userService.validateUser(username1, password1)) {
                    System.out.println("登录成功！");
                } else {
                    System.out.println("登录失败！");
                }
                break;
            case 3:
                System.out.println("用户列表：");
                userService.getAllUsers().forEach(user -> {
                    System.out.println("ID: " + user.getId() + ", 用户名: " + user.getUsername() + ", 偏好: " + user.getPreferences());
                });
                break;

            default:
                System.out.println("无效选项！");
                break;
        }
    }
}
*/
