<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <title>${city.name} - 城市介绍</title>
</head>
<body>
<h1>${city.name}</h1>
<p>区域 ID：${city.area_id}</p>
<p>所属省份：${city.parent_id}</p>
</body>
</html>
