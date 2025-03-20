<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户评论</title>
    <link rel="stylesheet" href="css/comments.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>用户评论</h1>
        <div class="search-container">
            <input type="text" id="search-input" placeholder="请输入景点名称">
            <button id="search-btn">搜索</button>
        </div>
    </div>
    <div class="content">
        <div id="comments-container">
            <!-- 评论内容动态加载 -->
        </div>
        <div class="pagination">
            <!-- 分页按钮动态生成 -->
        </div>
    </div>
</div>

<script src="js/comments.js"></script>
</body>
</html>
