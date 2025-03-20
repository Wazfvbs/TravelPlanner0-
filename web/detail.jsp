<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${attraction.name} - 景点详情</title>

    <link rel="stylesheet" href="css/detail.css">

</head>
<body>
<%@ include file="head.jsp" %>
<div class="container">
    <div class="header">
        <h1>${attraction.name}</h1>
        <p>排名：${attraction.rank} | 星级评分：1</p>
    </div>
    <div class="content">
        <div class="attraction-info">
            <h2>${attraction.cityName}</h2>
            <p><strong>简介：</strong>${attraction.description}</p>
            <p><strong>评论数：</strong>${attraction.commentsCount}</p>
            <p><strong>喜爱数：</strong>${attraction.guidesCount}</p>
        </div>
        <div class="actions">
            <button id="like-btn" class="btn like-btn">点赞</button>
            <button id="dislike-btn" class="btn dislike-btn">拒绝</button>
            <button class="wish-btn" action="search" method="post" onclick="window.location.href='search?query=${attraction.cityName}'">
                我想去
            </button>

        </div>
        <div class="comments">
            <h3>用户评论：</h3>
            <c:forEach var="comment" items="${comments}">
                <div class="comment">
                    <p>${comment}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
<script src="js/detail.js"></script>

</html>
