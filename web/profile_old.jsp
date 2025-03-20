<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人中心</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .profile-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
            margin-bottom: 20px;
        }
        .profile-avatar {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
        }
        .comment, .recommendation {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 15px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <!-- Header Section -->
    <div class="profile-header">
        <div>
            <h2>个人中心</h2>
            <p>欢迎来到你的个人空间！</p>
        </div>
        <div>
            <img id="profile-avatar" class="profile-avatar" src="default-avatar.jpg" alt="头像">
        </div>
    </div>

    <!-- Basic Info -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>基本信息</h5>
        </div>
        <div class="card-body">
            <form id="profile-form">
                <div class="mb-3">
                    <label for="name" class="form-label">姓名</label>
                    <input type="text" class="form-control" id="name" name="name" value="王有强">
                </div>
                <div class="mb-3">
                    <label for="gender" class="form-label">性别</label>
                    <select class="form-select" id="gender" name="gender">
                        <option value="male" selected>男</option>
                        <option value="female">女</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="hobbies" class="form-label">爱好</label>
                    <input type="text" class="form-control" id="hobbies" name="hobbies" value="旅游、摄影、读书">
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">个人介绍</label>
                    <textarea class="form-control" id="description" name="description" rows="3">我是一个喜欢探索世界的旅行者。</textarea>
                </div>
                <div class="mb-3">
                    <label for="avatar" class="form-label">上传头像</label>
                    <input type="file" class="form-control" id="avatar" name="avatar">
                </div>
                <button type="submit" class="btn btn-primary">保存</button>
            </form>
        </div>
    </div>

    <!-- User Comments -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>我的评论</h5>
        </div>
        <div class="card-body">
            <div class="comment">
                <strong>故宫</strong> - 很壮观，值得一去！ (2024-12-01)
            </div>
            <div class="comment">
                <strong>颐和园</strong> - 风景秀丽，拍了好多照片！ (2024-12-15)
            </div>
            <!-- 可动态加载更多评论 -->
        </div>
    </div>

    <!-- Recommended Attractions -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>我推荐的景点</h5>
        </div>
        <div class="card-body">
            <div class="recommendation">
                <strong>八达岭长城</strong> - 长城上风景无限，爬得很尽兴。
            </div>
            <div class="recommendation">
                <strong>古北水镇</strong> - 夜景很美，非常适合拍照。
            </div>
            <!-- 可动态加载更多推荐 -->
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // TODO: 后续可添加动态加载功能
    document.getElementById("profile-form").addEventListener("submit", function (e) {
        e.preventDefault();
        alert("保存成功！");
    });
</script>
</body>
</html>
