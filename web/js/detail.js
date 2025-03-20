
function getAttractionIdFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get("id");
}
document.addEventListener("DOMContentLoaded", () => {
    loadAttractionDetails();
    loadComments();
    const likeBtn = document.getElementById("like-btn");
    const dislikeBtn = document.getElementById("dislike-btn");
    const wishBtn = document.getElementById("wish-btn");

    let activeButton = null; // 记录当前激活的按钮

    // 跳转到城市详细页
    wishBtn.addEventListener("click", () => {
        const cityName = wishBtn.getAttribute("data-city-name"); // 从按钮的自定义属性获取城市名
        window.location.href = `SearchServlet?query=${encodeURIComponent(cityName)}`;
    });

    // 动态调整按钮样式和状态
    function toggleButton(buttonToShow, buttonToHide) {
        if (activeButton === buttonToShow) {
            // 如果按钮已激活，则恢复原状
            buttonToShow.style.width = "initial";
            buttonToShow.style.flex = "initial";
            buttonToShow.classList.remove("active");
            buttonToHide.style.display = "inline-block"; // 恢复隐藏按钮
            activeButton = null;
        } else {
            // 激活当前按钮
            buttonToShow.style.width = "200px"; // 横向增大
            buttonToShow.style.flex = "2"; // 占据两个位置
            buttonToShow.classList.add("active");
            buttonToHide.style.display = "none"; // 隐藏另一个按钮
            activeButton = buttonToShow;
        }
    }

    // 监听“喜欢”按钮点击
    likeBtn.addEventListener("click", () => {
        toggleButton(likeBtn, dislikeBtn);
    });

    // 监听“拒绝”按钮点击
    dislikeBtn.addEventListener("click", () => {
        toggleButton(dislikeBtn, likeBtn);
    });

    // 更新景区计数
    async function updateAttractionCount(action, id) {
        try {
            const response = await fetch(`${action}Servlet`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `id=${id}`
            });

            const result = await response.json();
            if (result.success) {
                alert(result.message);
            } else {
                alert("操作失败！");
            }
        } catch (error) {
            console.error("更新计数失败:", error);
        }
    }
});

async function loadAttractionDetails() {
    const attractionId = getAttractionIdFromURL();

    try {
        const response = await fetch(`AttractionDetailServlet?id=${attractionId}`);
        const data = await response.json();

        if (data) {
            document.getElementById("guides-count").innerText = data.guidesCount;
        }
    } catch (error) {
        console.error("加载景区详情失败：", error);
    }
}

async function handleLike(attractionId) {
    try {
        const response = await fetch(`/LikeServlet`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ attractionId }),
        });

        const result = await response.json();
        if (result.success) {
            document.getElementById("like-btn").classList.add("liked");
            document.getElementById("dislike-btn").style.display = "none";
        } else {
            alert(result.message || "点赞失败");
        }
    } catch (error) {
        console.error("点赞失败：", error);
    }
}

async function handleDislike(attractionId) {
    try {
        const response = await fetch(`/DislikeServlet`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ attractionId }),
        });

        const result = await response.json();
        if (result.success) {
            document.getElementById("dislike-btn").classList.add("disliked");
            document.getElementById("like-btn").style.display = "none";
        } else {
            alert(result.message || "拒绝失败");
        }
    } catch (error) {
        console.error("拒绝失败：", error);
    }
}

async function loadComments() {
    const attractionId = getAttractionIdFromURL();
    const page = 1; // 假设是第 1 页
    const pageSize = 10; // 每页显示 10 条评论
    try {
        const response = await fetch(`CommentServlet?action=searchComments&id=${attractionId}&page=${page}&pageSize=${pageSize}`);
        if (!response.ok) throw new Error("请求失败");
        const data = await response.json();

        const container = document.getElementById("comments-container");
        container.innerHTML = data.comments.map(comment => `
            <div class="comment">
                <p>${comment}</p>
            </div>
        `).join("");
    } catch (error) {
        console.error("加载评论失败：", error);
    }
}

function redirectToWish() {
    window.location.href = "/WishPageServlet";
}
