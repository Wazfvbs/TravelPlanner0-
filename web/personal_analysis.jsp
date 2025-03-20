<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*, org.json.JSONObject" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个性化分析 - 旅游推荐系统</title>
    <link rel="stylesheet" href="css/style_old.css">
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

<main>
    <h1>欢迎进入个性化分析页面</h1>
    <section class="personalized-content">
        <%
            // 获取当前用户的会话信息
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("username") == null) {
                out.println("<p>请先登录后访问此页面。</p>");
            } else {
                String username = (String) session.getAttribute("username");
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    // 连接数据库
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");

                    // 获取用户偏好信息
                    String sql = "SELECT preferences FROM user_preferences WHERE username = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, username);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        String preferencesJSON = rs.getString("preferences");
                        JSONObject preferences = new JSONObject(preferencesJSON);

                        // 动态生成个性化内容
        %>
        <h2>您的个性化推荐</h2>
        <ul>
            <li>最感兴趣的类别：<%= preferences.getString("favoriteCategory") %></li>
            <li>最近搜索的目的地：<%= preferences.getString("recentSearch") %></li>
            <li>推荐景点：<%= preferences.getString("recommendedSpot") %></li>
        </ul>
        <h3>个性化旅行路线建议</h3>
        <p>基于您的兴趣，我们为您定制了以下路线：</p>
        <ul>
            <%
                for (Object route : preferences.getJSONArray("recommendedRoutes")) {
                    out.println("<li>" + route.toString() + "</li>");
                }
            %>
        </ul>
        <%
                    } else {
                        out.println("<p>未找到您的个性化数据，请完善您的兴趣信息。</p>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                }
            }
        %>
    </section>
</main>
</body>
</html>
