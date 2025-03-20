<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>地图 - 省份缩略图</title>
    <style>
        .province {
            display: inline-block;
            width: 200px;
            height: 150px;
            margin: 10px;
            background-size: cover;
            background-position: center;
            position: relative;
            text-align: center;
            color: white;
            font-weight: bold;
        }
        .province a {
            text-decoration: none;
            color: white;
        }
        .province:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h1>选择省份</h1>
<div>
    <c:forEach items="${provinces}" var="province">
        <div class="province" style="background-image: url('/images/provinces/${province.name}.jpg');">
            <a href="/map?type=city&id=${province.areaId}">${province.name}</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
