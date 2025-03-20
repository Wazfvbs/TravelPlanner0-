
$(document).ready(function () {
    function loadUserProfile() {

        $.ajax({
            url: "UserServlet?action=viewProfile",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data) {
                    $("#id").val(data.id);
                    $("#username").val(data.username);
                    $("#name").val(data.name);
                    $("#gender").val(data.gender);
                    $("#hobbies").val(data.hobbies);
                    $("#description").val(data.description);
                    const avatarPath = `sc/tx/${data.avatar || 'default-avatar.png'}`;
                    $("#avatar").attr("src", avatarPath);
                } else {
                    alert("No user data received.");
                }
            },
            error: function (xhr, status, error) {
                console.error("Error loading profile:", status, error);
                alert("Failed to load user profile.");
            }
        });



    }


    // 更新用户数据
    $("#update-profile").click(function () {
        const userData = {
            id: $("#id").val(),
            username: $("#username").val(),
            name: $("#name").val(),
            gender: $("#gender").val(),
            hobbies: $("#hobbies").val(),
            description: $("#description").val(),
        };

        const password = $("#password").val();
        if (password) {
            userData.password = password; // 只有在密码非空时才添加密码字段
        }

        $.ajax({
            url: "UserServlet?action=update",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(userData),
            success: function () {
                alert("Profile updated successfully!");
            },
            error: function () {

            }
        });
    });
    function loadUserComments() {
        $.ajax({
            url: "CommentServlet?action=viewProfile",
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data && data.comments && data.comments.length > 0) {
                    $("#comments-container").empty();
                    data.comments.forEach(comment => {
                        const commentBox = `
                        <div class="comment-box">
                            <p><strong>评分：</strong>${comment.rating}</p>
                            <p><strong>内容：</strong>${comment.content}</p>
                            <p><strong>时间：</strong>${comment.time}</p>
                            <p><strong>景点ID：</strong>${comment.attractionId}</p>
                        </div>
                    `;
                        $("#comments-container").append(commentBox);
                    });
                } else {
                    $("#comments-container").html("<p>暂无评论。</p>");
                }
            },
            error: function () {
                alert("加载评论失败！");
            }
        });
    }


    // 初始化加载
    loadUserProfile();
    loadUserComments();
});
