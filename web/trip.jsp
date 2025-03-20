        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Attraction" %>
<%@ page import="dao.AttractionDAO" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的旅行路线</title>
    <link rel="stylesheet" href="css/trip.css">
    <script type="text/javascript" src="js/trip.js"></script>
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: "a49fbf8b08748b2a118e058a835f38d2",
        };
    </script>
    <script src="https://webapi.amap.com/maps?v=2.0&key=13fe47fe5e15710c59e1cdc359cc638f"></script>
    <style>
        #map { width: 100%; height: 500px; margin-bottom: 20px; }
        #route-controls { margin: 20px; padding: 10px; background-color: #f9f9f9; border: 1px solid #ccc; border-radius: 5px; }
        #selectedAttractionsList { list-style-type: none; padding: 0; }
        #selectedAttractionsList li { padding: 5px; cursor: pointer; }
        #selectedAttractionsList li:hover { background-color: #e9e9e9; }
    </style>
</head>
<body>
<%@ include file="head.jsp" %>
<div id="map"></div>
<div id="route-controls">
    <h3>已选景点</h3>
    <ul id="selectedAttractionsList">
        <%
            // 接收景点ID
            String[] idArray = request.getParameter("attractions").split(",");
            List<Integer> ids = new ArrayList<>();
            List<Attraction> attractions = null;
            try {
                // 将 String[] 转换为 List<Integer>
                for (String id : idArray) {
                    ids.add(Integer.parseInt(id));
                }
                // 调用 DAO 获取景点列表
                attractions = AttractionDAO.getAttractionsByIds(ids);

                // 将景点数据输出到页面
                for (Attraction attraction : attractions) {
        %>
        <div>
            <h2><%=attraction.getName()%></h2>
            <p><%=attraction.getDescription()%></p>
            <p>城市：<%=attraction.getCityName()%></p>
        </div>
        <%
            }
        } catch (NumberFormatException e) {
        %>
        <p>无效的景点ID参数。</p>
        <%
            }
        %>
        <% if (attractions != null && !attractions.isEmpty()) {
            for (Attraction attraction : attractions) { %>
        <li onclick="focusMarker(<%=attraction.getLatitude()%>, <%=attraction.getLongitude()%>)">
            <%=attraction.getName()%> (<%=attraction.getCityName()%>)
        </li>
        <%  }
        } else { %>
        <li>未选择任何景点</li>
        <% } %>
    </ul>
</div>

<!-- 样式定义 -->
<style>
    html, body {
        margin: 0;
        padding: 0;
        height: 100%; /* 确保页面占满屏幕 */
        overflow: hidden; /* 移除滚动条 */
    }

    #map {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%; /* 地图全屏 */
        z-index: 1; /* 确保地图在底层 */
    }

    #route-controls {
        position: absolute;
        top: 20px;
        right: 20px;
        width: 300px;
        background-color: rgba(255, 255, 255, 0.8); /* 半透明白色背景 */
        border-radius: 8px; /* 圆角效果 */
        padding: 10px; /* 内边距 */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 阴影效果 */
        font-family: Arial, sans-serif; /* 字体样式 */
        z-index: 2; /* 确保控件在地图上方 */
    }

    #route-controls h3 {
        margin: 0;
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        border-bottom: 1px solid #ddd; /* 下边框 */
        padding-bottom: 5px;
    }

    #selectedAttractionsList {
        list-style: none; /* 移除列表符号 */
        padding: 0;
        margin: 10px 0;
    }

    #selectedAttractionsList li {
        padding: 5px;
        margin: 5px 0;
        background-color: #f7f7f7; /* 背景色 */
        border: 1px solid #ccc; /* 边框 */
        border-radius: 4px; /* 圆角 */
        cursor: pointer;
        transition: background-color 0.3s; /* 动效 */
    }

    #selectedAttractionsList li:hover {
        background-color: #eaeaea; /* 悬停效果 */
    }
</style>

<!-- 如果有地图 JS 初始化，请确保调整容器大小 -->
<script>
    var attractionsData = [
        <% if (attractions != null && !attractions.isEmpty()) {
                for (Attraction attraction : attractions) { %>
        { name: "<%=attraction.getName()%>", city: "<%=attraction.getCityName()%>", lat: <%=attraction.getLatitude()%>
            , lng: <%=attraction.getLongitude()%> },
        <%  }
            } %>
    ];
    initializeMap(attractionsData); // 调用 JS 初始化地图
    // 在页面加载完成时初始化地图
    window.onload = function () {
        // 确保地图根据容器大小更新
        if (typeof map !== 'undefined' && map.invalidateSize) {
            map.invalidateSize();
        }
    };


</script>
</body>
</html>
