<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>个性化推荐</title>
  <link rel="stylesheet" href="css/style_old.css">
</head>
<body>
<header>
  <div class="navbar">
    <a href="index.jsp">首页</a>
    <a href="hot.jsp">热门推荐</a>
    <a href="map.jsp">地图查询</a>
    <a href="comments.jsp">用户评论</a>
    <a href="profile_old.jsp">我的</a>
  </div>
</header>
<main>
  <h2>推荐内容</h2>
  <ul>
    <!-- 动态推荐内容 -->
    <li>推荐1：<%= request.getParameter("query") %></li>
    <li>推荐2：其他相关内容</li>
  </ul>
</main>
</body>
</html>
