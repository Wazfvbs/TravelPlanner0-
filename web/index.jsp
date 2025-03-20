<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>首页 - 旅游推荐系统</title>
  <link rel="stylesheet" href="css/style_old.css">
  <script src="js/script.js" defer></script>
</head>
<body>
<!-- 顶部导航栏 -->
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
    <a href="profile.jsp">我的</a>
    <a href="UserServlet?action=logout">退出</a>
    <% } else { %>
    <a href="login.jsp">登录/注册</a>
    <% } %>
  </div>
</header>

<!-- 主内容 -->
<main>
  <!-- 背景轮播图 -->
  <section class="hero">
    <div class="hero-background">
      <img id="background" src="sc/VCG211375909688.jpg" alt="背景图片" style="width: 100%; height: auto;">

    </div>

    <div class="hero-content">
      <h1>探索你的兴趣与目的地</h1>
      <form action="search" method="post" accept-charset="UTF-8" class="search-form">
        <input type="text" name="query" placeholder="输入兴趣或目的地" required>
        <button type="submit">搜索</button>
      </form>

    </div>
  </section>

  <!-- 功能介绍 -->
  <section class="info">
    <h2>热门功能</h2>
    <div class="features">
      <div class="feature-item">
        <h3>推荐景点</h3>
        <p>根据你的兴趣推荐最佳旅游景点。</p>
      </div>
      <div class="feature-item">
        <h3>个性化分析</h3>
        <p>定制你的专属旅行路线。</p>
      </div>
      <div class="feature-item">
        <h3>快速查询</h3>
        <p>快速获取景点和城市信息。</p>
      </div>
    </div>
  </section>
</main>
</body>

</html>
