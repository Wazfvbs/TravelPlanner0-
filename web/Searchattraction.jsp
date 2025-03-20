<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <title>景点列表</title>
</head>
<body>
<h1>景点列表</h1>
<ul>
  <c:forEach var="attraction" items="${attractions}">
    <li>${attraction.name} - ${attraction.cityName}</li>
  </c:forEach>
</ul>
<div>
  <c:choose>
    <c:when test="${currentPage > 1}">
      <a href="search?type=attraction&page=${currentPage - 1}&cityName=${param.cityName}">上一页</a>
    </c:when>
    <c:otherwise>
      <span>上一页</span>
    </c:otherwise>
  </c:choose>
  <a href="search?type=attraction&page=${currentPage + 1}&cityName=${param.cityName}">下一页</a>
</div>
</body>
</html>
