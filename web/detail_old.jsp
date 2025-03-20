<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${attraction.name} - 景点详情</title>
    <script src="js/detail_old.js" defer></script>
    <link rel="stylesheet" href="css/detail_old.css">

</head>
<body>
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
            <p><strong>攻略数：</strong>${attraction.guidesCount}</p>
        </div>
        <div class="actions">
            <button class="like-btn" onclick="likeAttraction(${attraction.id})">点赞</button>
            <button class="dislike-btn" onclick="dislikeAttraction(${attraction.id})">拒绝</button>
            <button class="wish-btn">我想去</button>
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
</html>
