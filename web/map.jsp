<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>旅游地图</title>
    <link rel="stylesheet" href="css/map.css">
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: "",
        };
    </script>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script src="https://webapi.amap.com/maps?v=2.0&key="></script>
</head>
<body>
<div class="map-container">
    <!-- 地图 -->
    <div id="map"></div>

    <!-- 半透明控件 -->
    <div class="controls-overlay">
        <select id="province" onchange="loadCities(this.value)">
            <option value="">选择省份</option>
        </select>
        <select id="city" onchange="locateCity()">
            <option value="">选择城市</option>
        </select>
        <input type="number" id="limit" placeholder="显示条数" value="10" min="1" max="100">
        <button id="showAttractionsBtn" onclick="showAttractions()">显示景点</button>
        <div id="attractionsList">
            <!-- 景点数据将在这里动态加载 -->
        </div>
    </div>
</div>
<script src="js/map.js"></script>
</body>
</html>
