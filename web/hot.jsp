<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>热门推荐</title>
  <style>
    /* 基础样式 */
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f9;
      color: #333;
    }

    /* 容器样式 */
    .container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
    }

    /* 标题样式 */
    .header {
      text-align: center;
      padding: 30px 0;
      background: linear-gradient(135deg, #007bff, #0056b3);
      color: white;
      border-radius: 10px;
      margin-bottom: 30px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .header h1 {
      margin: 0;
      font-size: 2.5em;
    }

    /* 卡片布局 */
    .hot-list {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
      justify-content: center;
    }

    /* 卡片样式 */
    .hot-item {
      background: white;
      border-radius: 10px;
      overflow: hidden;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      transition: transform 0.2s, box-shadow 0.3s;
      width: calc(33.333% - 20px);
      min-width: 300px;
    }

    .hot-item:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    }

    .hot-item img {
      width: 100%;
      height: 200px;
      object-fit: cover;
    }

    .hot-item .content {
      padding: 20px;
    }

    .hot-item h2 {
      font-size: 1.5em;
      margin: 0 0 10px;
      color: #1e88e5;
    }

    .hot-item p {
      margin: 0 0 15px;
      color: #555;
      line-height: 1.6;
    }

    .hot-item .btn {
      display: inline-block;
      padding: 10px 20px;
      background-color: #007bff;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      font-weight: bold;
      transition: background-color 0.3s ease;
    }

    .hot-item .btn:hover {
      background-color: #0056b3;
    }

    /* 响应式调整 */
    @media (max-width: 768px) {
      .hot-item {
        width: calc(50% - 20px);
      }
    }

    @media (max-width: 480px) {
      .hot-item {
        width: 100%;
      }
    }
  </style>
</head>
<body>
<div class="overlay">
  <div class="container">
    <div class="header">
      <h1>热门推荐</h1>
    </div>

    <div class="hot-list">
      <!-- 景点卡片 -->
      <div class="hot-item">
        <img src="sc/jd/VCG211322807582.jpg" alt="阳光海岸">
        <div class="content">
          <h2>阳光海岸</h2>
          <p>迷人的沙滩与湛蓝的海水，是度假与放松的理想之地。</p>
          <a href="#" class="btn">了解更多</a>
        </div>
      </div>

      <div class="hot-item">
        <img src="sc/jd/VCG41N1363888736.jpg" alt="梦幻森林">
        <div class="content">
          <h2>梦幻森林</h2>
          <p>探索自然的奥秘，享受远离城市喧嚣的宁静时光。</p>
          <a href="#" class="btn">了解更多</a>
        </div>
      </div>

      <div class="hot-item">
        <img src="sc/jd/VCG211378353619.jpg" alt="神秘古堡">
        <div class="content">
          <h2>神秘古堡</h2>
          <p>一座充满历史与传说的城堡，等您来一探究竟。</p>
          <a href="#" class="btn">了解更多</a>
        </div>
      </div>

      <div class="hot-item">
        <img src="sc/jd/VCG211397198167.jpg" alt="金色沙漠">
        <div class="content">
          <h2>金色沙漠</h2>
          <p>体验壮丽的沙漠风光，与日出日落的绝美景色。</p>
          <a href="#" class="btn">了解更多</a>
        </div>
      </div>

      <div class="hot-item">
        <img src="sc/jd/VCG211418125706.jpg" alt="静谧湖泊">
        <div class="content">
          <h2>静谧湖泊</h2>
          <p>清澈的湖水与倒映的山影，宛若一幅绝美的画卷。</p>
          <a href="#" class="btn">了解更多</a>
        </div>
      </div>

      <div class="hot-item">
        <img src="sc/jd/VCG211307513086.jpg" alt="巍峨雪山">
        <div class="content">
          <h2>巍峨雪山</h2>
          <p>挑战极限，征服高峰，感受大自然的雄伟壮丽。</p>
          <a href="#" class="btn">了解更多</a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
