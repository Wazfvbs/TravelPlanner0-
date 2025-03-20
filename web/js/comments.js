$(document).ready(function () {
    const pageSize = 10; // 每页评论数量
    let currentPage = 1;

    // 加载评论
    function loadComments(page) {
        $.ajax({
            url: "CommentServlet?action=getComments",
            type: "GET",
            data: { page: page, pageSize: pageSize },
            dataType: "json",
            success: function (data) {
                if (data && data.comments.length > 0) {
                    $("#comments-container").empty();
                    data.comments.forEach(comment => {
                        const avatar = comment.avatar ? `sc/tx/${comment.avatar}` : 'sc/tx/default-avatar.png';
                        const nickname = comment.nickname || '匿名用户';
                        const content = comment.content || '无内容';
                        const timestamp = comment.timestamp || '未知时间';
                        const attractionId = `${comment.attractionId}`;
                        const attraction = comment.attractionName ? `<a href="AttractionDetailServlet?id=${comment.attractionId}" class="attraction-link">${comment.attractionName}</a>` : '未知景点';
                        const commentHtml = `
                                <div class="comment-box">
                                    <div class="comment-header">
                                        <img class="avatar" src="${avatar}" alt="用户头像">
                                        <strong>${nickname}</strong>
                                        <span>评论了景区: ${attraction}</span>
                                    </div>
                                    <p>${content}</p>
                                    <span class="timestamp">${timestamp}</span>
                                </div>
                            `;

                        $("#comments-container").append(commentHtml);
                    });
                    generatePagination(data.totalPages, page);
                } else {
                    $("#comments-container").html("<p>暂无评论</p>");
                    $(".pagination").empty();
                }
            },
            error: function () {
                alert("加载评论失败！");
            }
        });
    }

    // 生成分页按钮
    function generatePagination(totalPages, currentPage) {
        $(".pagination").empty();
        for (let i = 1; i <= totalPages; i++) {
            const buttonHtml = `<button class="page-btn ${i === currentPage ? 'active' : ''}" data-page="${i}">${i}</button>`;
            $(".pagination").append(buttonHtml);
        }
    }


    // 分页按钮点击事件
    $(".pagination").on("click", ".page-btn", function () {
        const page = parseInt($(this).data("page"));
        if (page !== currentPage) {
            currentPage = page;
            loadComments(page);
        }
    });
    // 搜索按钮点击事件
    $("#search-btn").on("click", function () {
        const searchQuery = $("#search-input").val().trim();

        if (searchQuery) {
            searchComments((searchQuery), 1); // 从第1页加载搜索结果
        } else {
            alert("请输入景点名称！");
        }
    });

    // 搜索评论
    function searchComments(query, page) {
        $.ajax({
            url: "CommentServlet?action=searchComments",
            type: "GET",
            data: { query: decodeURIComponent(decodeURI(query)), page: page, pageSize: pageSize },
            dataType: "json",
            success: function (data) {
                if (data && data.comments.length > 0) {
                    $("#comments-container").empty();
                    data.comments.forEach(comment => {
                        const avatar = comment.avatar ? `sc/tx/${comment.avatar}` : 'sc/tx/default-avatar.png';
                        const nickname = comment.nickname || '匿名用户';
                        const attraction = comment.attractionName
                            ? `<a href="AttractionDetailServlet?id=${comment.attractionId}" class="attraction-link">${comment.attractionName}</a>`
                            : '未知景点';
                        const content = comment.content || '无内容';
                        const timestamp = comment.timestamp || '未知时间';

                        const commentHtml = `
                            <div class="comment-box">
                                <div class="comment-header">
                                    <img class="avatar" src="${avatar}" alt="用户头像">
                                    <strong>${nickname}</strong>
                                    <span>评论了景区: ${attraction}</span>
                                </div>
                                <p>${content}</p>
                                <span class="timestamp">${timestamp}</span>
                            </div>
                        `;
                        $("#comments-container").append(commentHtml);
                    });
                    generatePagination(data.totalPages, page);
                } else {
                    $("#comments-container").html("<p>未找到相关评论</p>");
                    $(".pagination").empty();
                }
            },
            error: function () {
                alert("搜索评论失败！");
            }
        });
    }
    // 初始化加载第一页
    loadComments(currentPage);
});