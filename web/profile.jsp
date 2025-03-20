<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户个人信息</title>
    <link rel="stylesheet" href="css/profile.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<%@ include file="head.jsp" %>
<div class="container">
    <div class="header">
        <h1>个人中心</h1>
    </div>
    <div class="content">
        <!-- 左侧用户信息 -->
        <div class="user-info">
            <h2>我的信息</h2>
            <form id="profile-form">
                <input type="hidden" id="id" value="viewProfile">
                <div id="avatar-container">
                    <img id="avatar" src="sc/tx/default-avatar.png" alt="用户头像">
                </div>
                <div class="form-group">
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" readonly>
                </div>
                <div class="form-group">
                    <label for="name">昵称:</label>
                    <input type="text" id="name" name="name">
                </div>
                <div class="form-group">
                    <label for="gender">性别:</label>
                    <input type="text" id="gender" name="gender">
                </div>
                <div class="form-group">
                    <label for="hobbies">爱好:</label>
                    <input type="text" id="hobbies" name="hobbies">
                </div>
                <div class="form-group">
                    <label for="description">个人介绍:</label>
                    <textarea id="description" name="description"></textarea>
                </div>
                <button type="button" id="update-profile">保存修改</button>
            </form>
        </div>

        <!-- 右侧用户评论 -->
        <div class="user-comments">
            <h2>我的评论</h2>
            <div id="comments-container">
                <p>加载中...</p>
            </div>
        </div>
    </div>
</div>
<script src="js/profile.js"></script>
</body>



</html>
