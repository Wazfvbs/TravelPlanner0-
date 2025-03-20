<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!-- 集成了 CSS 的 header.jsp -->
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

<style>
  /* 顶部导航栏样式 */
  .navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    background-color: #0056b3;
    color: white;
    height: 60px;
  }

  .navbar .logo a {
    font-size: 1.5rem;
    color: white;
    text-decoration: none;
  }

  .navbar .nav-links a {
    margin: 0 15px;
    color: white;
    text-decoration: none;
  }

  .navbar .user-actions a {
    margin-left: 15px;
    color: white;
    text-decoration: none;
  }
</style>
