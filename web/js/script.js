// 动态表单验证
document.getElementById('tripForm').addEventListener('submit', function (e) {
    e.preventDefault(); // 防止表单提交刷新页面
    const destination = document.getElementById('destination').value.trim();
    const budget = document.getElementById('budget').value.trim();
    const travelDate = document.getElementById('travelDate').value.trim();

    if (!destination || !budget || !travelDate) {
        alert('请填写所有字段！');
        return;
    }

    alert(`行程已生成！目的地：${destination}，预算：${budget} 元，出行日期：${travelDate}`);
});
function displayTripResults(destination, budget, travelDate) {
    const resultsDiv = document.getElementById('tripResults');
    resultsDiv.innerHTML = `
        <div class="card">
            <div class="card-header">推荐行程</div>
            <div class="card-body">
                <p><strong>目的地：</strong> ${destination}</p>
                <p><strong>预算：</strong> ${budget} 元</p>
                <p><strong>出行日期：</strong> ${travelDate}</p>
                <p>建议游览以下景点：...</p>
            </div>
        </div>
    `;
}

// 修改表单提交事件处理逻辑
document.getElementById('tripForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const destination = document.getElementById('destination').value.trim();
    const budget = document.getElementById('budget').value.trim();
    const travelDate = document.getElementById('travelDate').value.trim();

    if (!destination || !budget || !travelDate) {
        alert('请填写所有字段！');
        return;
    }

    displayTripResults(destination, budget, travelDate);
});
// 动态检查登录状态
$(document).ready(function () {
    $.get("UserServlet?action=checkLoginStatus", function (data) {
        if (data.isLoggedIn) {
            $("#login-register").html('<a href="my.jsp">我的</a>');
        }
    });

    // 动态切换背景图片
    const images = [
        "sc/VCG211374915873.jpg",
        "sc/VCG211375909688.jpg",
        "sc/VCG211381398334.jpg"
    ];
    let currentIndex = 0;
    setInterval(() => {
        currentIndex = (currentIndex + 1) % images.length;
        $("#background").attr("src", images[currentIndex]);
    }, 5000);

    // 点击背景弹出模态框
    $("#background").click(function () {
        $("#modal").css("display", "block");
    });

    // 关闭模态框
    $(".close").click(function () {
        $("#modal").css("display", "none");
    });

    // “我想去”按钮功能
    $("#go-to-spot").click(function () {
        $.get("UserServlet?action=checkLoginStatus", function (data) {
            if (data.isLoggedIn) {
                alert("选择已保存！");
            } else {
                alert("请先登录！");
                location.href = "login.jsp";
            }
        });
    });
});
document.addEventListener("DOMContentLoaded", () => {
    const heroBackground = document.querySelector(".hero-background");
    const images = [
        "sc/VCG211374915873.jpg",
        "sc/VCG211375909688.jpg",
        "sc/VCG211381398334.jpg",
        "sc/VCG211391025815.jpg"
    ];

    let currentIndex = 0;

    function changeBackground() {
        heroBackground.style.backgroundImage = `url(${images[currentIndex]})`;
        currentIndex = (currentIndex + 1) % images.length;
    }

    setInterval(changeBackground, 5000); // 每 5 秒切换图片
    changeBackground(); // 初始化显示第一张图片
});

