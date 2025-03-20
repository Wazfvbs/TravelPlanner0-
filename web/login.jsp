<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录</title>
    <link rel="stylesheet" href="css/login.css">
    <script>
        function switchToRegister() {
            document.getElementById('authTitle').textContent = "注册";
            document.getElementById('authButton').textContent = "注册";
            document.getElementById('action').value = "register";
            document.getElementById('extraText').innerHTML = '已有账号？<a href="#" onclick="switchToLogin()">去登录</a>';
        }

        function switchToLogin() {
            document.getElementById('authTitle').textContent = "登录";
            document.getElementById('authButton').textContent = "登录";
            document.getElementById('action').value = "login";
            document.getElementById('extraText').innerHTML = '没有账号？<a href="#" onclick="switchToRegister()">去注册</a>';
        }
    </script>
</head>
<body>
<div class="auth-container">
    <form action="UserServlet" method="post" class="auth-form">
        <input type="hidden" id="action" name="action" value="login">
        <h2 id="authTitle">登录</h2>
        <input type="text" name="username" placeholder="用户名" required>
        <input type="password" name="password" placeholder="密码" required>
        <button type="submit" id="authButton">登录</button>
        <p id="extraText">没有账号？<a href="#" onclick="switchToRegister()">去注册</a></p>
    </form>
</div>
</body>
</html>
