<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>城市详情</title>
    <link rel="stylesheet" href="css/city.css">
    <script src="js/city.js" defer></script>
</head>
<body>

<header>
    <button id="back-btn">返回</button>
    <h1 id="city-name">加载中...</h1>
    <p>所在省份：<span id="province-name">加载中...</span></p>
</header>

<section id="attractions">
    <h2 id="visit-status">我想去：</h2>
    <div id="attraction-list">
        <!-- 景点卡片动态加载 -->
    </div>
    <button id="submit-btn">提交</button>
</section>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const cityId = "<%= request.getAttribute("cityId") %>"; // 从请求属性中获取城市ID
        fetchCityDetails(cityId);
    });
</script>
</body>
</html>
