<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>地图 - 城市缩略图</title>
    <style>
        .city {
            display: inline-block;
            width: 150px;
            height: 100px;
            margin: 10px;
            background-size: cover;
            background-position: center;
            position: relative;
            text-align: center;
            color: white;
            font-weight: bold;
        }
        .city a {
            text-decoration: none;
            color: white;
        }
        .city:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h1>选择城市</h1>
<div>
    <c:forEach items="${cities}" var="city">
        <div class="city" style="background-image: url('/images/cities/${city.name}.jpg');">
            <a href="/attractions?cityId=${city.areaId}">${city.name}</a>
        </div>
    </c:forEach>
</div>
<div id="mapContainer" style="width: 100%; height: 400px;"></div>
<script src="https://webapi.amap.com/maps?v=2.0&key=YOUR_API_KEY"></script>
<script>
    var map = new AMap.Map('mapContainer', {
        zoom: 10, // 缩放级别
        center: [126.642464, 45.756967] // 默认中心点 (哈尔滨市)
    });

    // 添加标记（根据城市信息动态生成）
    <c:forEach items="${cities}" var="city">
    var marker = new AMap.Marker({
        position: [city.longitude, city.latitude], // 假设数据表有经纬度字段
        title: '${city.name}'
    });
    marker.setMap(map);
    </c:forEach>
</script>

</body>
</html>
