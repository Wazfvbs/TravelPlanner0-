
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>旅游推荐系统</title>
    <link rel="stylesheet" href="css/stylecpy.css">
</head>
<body>
<header class="navbar">
    <div class="logo">
        <a href="index.jsp">旅游推荐系统</a>
    </div>
    <nav class="nav-links">
        <a href="index.jsp">首页</a>
        <a href="hot.jsp">热门推荐</a>
        <a href="map.jsp">地图查询</a>
        <a href="comments.jsp">用户评论</a>
    </nav>
    <div class="user-actions">
        <%
            session = request.getSession(false);
            if (session != null && session.getAttribute("username") != null) {
        %>
        <a href="profile_old.jsp">我的</a>
        <a href="UserServlet?action=logout">退出</a>
        <% } else { %>
        <a href="login.jsp">登录/注册</a>
        <% } %>
    </div>
</header>

<section class="header">
    <h1>去哪儿？</h1>
    <div class="search-bar">
        <input type="text" placeholder="景点玩乐、酒店..." />
        <button>查询</button>
    </div>
</section>

<main class="main-content">
    <img src="sc/caption.jpg" alt="旅游景点" />
    <div class="overlay">
        <h2>以您的方式规划您的旅行</h2>
        <p>使用“旅行”收藏您的最爱，制定行程等。</p>
    </div>
</main>
</body>
</html>
