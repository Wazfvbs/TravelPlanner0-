// 加载景区详情
async function loadAttractionDetails(attractionId) {
    try {
        const response = await fetch(`AttractionDetailServlet?id=${attractionId}`);
        const data = await response.json();

        // 填充景区信息
        document.getElementById('attraction-name').innerText = data.name;
        document.getElementById('attraction-city').innerText = `城市：${data.cityName}`;
        document.getElementById('attraction-description').innerText = `描述：${data.description}`;
        document.getElementById('attraction-guides').innerText = `攻略数：${data.guidesCount}`;
        document.getElementById('attraction-comments').innerText = `评论数：${data.commentsCount}`;
        document.getElementById('attraction-rank').innerText = `排名：${data.rank}`;
        document.getElementById('attraction-rating').innerText = `星级：${data.starRating}`;

        // 填充评论
        const commentsList = document.getElementById('comments-list');
        commentsList.innerHTML = ""; // 清空旧评论
        data.comments.forEach(comment => {
            const li = document.createElement('li');
            li.innerText = comment;
            commentsList.appendChild(li);
        });
    } catch (error) {
        console.error('加载景区详情时出错:', error);
    }
}
function likeAttraction(id) {
    fetch(`/likeAttraction?id=${id}`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            alert(data.message || "点赞成功！");
            location.reload();
        })
        .catch(err => alert("点赞失败！"));
}
// 点赞功能
document.getElementById('like-btn').addEventListener('click', () => {
    updateAttractionCount('like');
});

// 拒绝功能
document.getElementById('dislike-btn').addEventListener('click', () => {
    updateAttractionCount('dislike');
});
function dislikeAttraction(id) {
    fetch(`/dislikeAttraction?id=${id}`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            alert(data.message || "操作成功！");
            location.reload();
        })
        .catch(err => alert("操作失败！"));
}


// 更新计数
async function updateAttractionCount(action) {
    try {
        const response = await fetch(`${action}Servlet`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `id=${attractionId}`
        });

        if (response.ok) {
            loadAttractionDetails(attractionId); // 刷新详情
        } else {
            console.error('更新计数时出错:', response.statusText);
        }
    } catch (error) {
        console.error('请求失败:', error);
    }
}
