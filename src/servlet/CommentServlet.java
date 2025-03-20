package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import model.Comment;
import model.User;
import service.CommentService;
import service.UserService;
import dao.GsonUtil;
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
    private final CommentService commentService = new CommentService();
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addComment".equals(action)) {
            handleAddComment(request, response);
        }
        else if ("add".equals(action)) {
            // 添加评论
            try {
                int userId = Integer.parseInt(request.getParameter("user_id"));
                int attractionId = Integer.parseInt(request.getParameter("attraction_id"));
                String content = request.getParameter("content");
                int rating = Integer.parseInt(request.getParameter("rating"));
                LocalDateTime dateTime = LocalDateTime.now();

                boolean success = commentService.addComment(new Comment(0, userId, attractionId, content, rating, dateTime));
                response.getWriter().write(success ? "评论成功！" : "评论失败！");
            } catch (Exception e) {
                response.getWriter().write("评论失败！参数错误：" + e.getMessage());
            }
        }
        else {
            response.getWriter().write("无效的操作！");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        switch (action) {
            case "search": // 搜索评论
                handleSearchCommentss(request, response);
                break;
            case "filter": // 按景点过滤评论
                handleFilterComments(request, response);
                break;
            case "viewProfile": // 查看用户信息及评论
                handleViewProfile(request, response);
                break;
            case "getComments":
                handleGetComments(request, response);
                break;
            case "searchComments":
                handleSearchComments(request, response);
                break;
            default: // 默认获取所有评论
                handleGetAllComments(response);
                break;
        }
    }

    private void handleSearchCommentss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        List<Comment> comments = commentService.searchComments(keyword);

        writeJsonResponse(response, "comments", comments);
    }

    private void handleFilterComments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int attractionId = Integer.parseInt(request.getParameter("attractionId"));
            List<Comment> comments = commentService.getCommentsByAttractionId(attractionId);

            writeJsonResponse(response, "comments", comments);
        } catch (Exception e) {
            response.getWriter().write("参数错误：" + e.getMessage());
        }
    }

    private void handleViewProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        User user = userService.findByUserName(username);

        try {
            int userId = user.getId();
            List<Comment> comments = commentService.getCommentsByUserId(userId);

            // 转换评论数据为 JSON 格式
            List<Map<String, Object>> commentList = new ArrayList<>();
            for (Comment comment : comments) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("content", comment.getContent());
                commentMap.put("rating", comment.getRating());
                commentMap.put("time", comment.getTimestamp().toString()); // 转为字符串格式
                commentMap.put("attractionId", comment.getAttractionId());
                commentList.add(commentMap);
            }
            Map<String, Object> responseData = new HashMap<>();
            // 响应数据
            Map<String, String> userMap = new HashMap<>();
            userMap.put("username", user.getUsername());
            userMap.put("name", user.getName());
            responseData.put("user", userMap);



            responseData.put("comments", commentList);

            writeJsonResponse(response, responseData);
        } catch (Exception e) {
            response.setContentType("text/plain");
            response.getWriter().write("参数错误：" + e.getMessage());
        }
    }

    // 通用的 JSON 响应方法
    private void writeJsonResponse(HttpServletResponse response, Map<String, Object> data) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new Gson().toJson(data)); // 使用 Gson 库
    }

    private void handleGetAllComments(HttpServletResponse response) throws IOException {
        List<Comment> comments = commentService.getAllComments();
        writeJsonResponse(response, "comments", comments);
    }

    private void writeJsonResponse(HttpServletResponse response, String key, Object value) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(key, value);

        writeJsonResponse(response, responseData);
    }
    private void handleGetComments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        List<Comment> comments = commentService.getCommentsByPage(page, pageSize);
        int totalComments = commentService.getTotalCommentCount();
        int totalPages = (int) Math.ceil((double) totalComments / pageSize);

        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);
        result.put("totalPages", totalPages);

        response.setContentType("application/json;charset=UTF-8");
        Gson gson = GsonUtil.getGson();
        response.getWriter().write(gson.toJson(result));
    }

    private void handleAddComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int attractionId = Integer.parseInt(request.getParameter("attractionId"));
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));
        LocalDateTime timestamp = LocalDateTime.now();

        Comment comment = new Comment(userId, attractionId, content, rating, timestamp);
        boolean isAdded = commentService.addCommenta(comment);

        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("success", isAdded);
        Gson gson = GsonUtil.getGson();
        response.getWriter().write(gson.toJson(result));
    }
    private void handleSearchComments(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String query = request.getParameter("query");

        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        List<Comment> comments = commentService.searchComments(query, page, pageSize);
        int totalPages = commentService.getSearchTotalPages(query, pageSize);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("comments", comments);
        responseMap.put("totalPages", totalPages);
        Gson gson = GsonUtil.getGson();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gson.toJson(responseMap));

    }
}
