
package service;

import dao.CommentDAO;
import dao.DBConnection;
import model.Comment;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
    private CommentDAO commentDAO = new CommentDAO();
    public boolean addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }
    public List<Comment> getCommentsByAttractionId(int attractionId) {
        return commentDAO.getCommentsByAttractionId(attractionId);
    }
    public boolean deleteComment(int commentId) {
        return commentDAO.deleteComment(commentId);
    }
    public List<Comment> getAllComments() {
        return commentDAO.getAllComments();
    }
    public List<Comment> searchComments(String keyword) {
        return commentDAO.searchComments(keyword);
    }
    public List<Comment> getCommentsWithPagination(int page, int pageSize) {
        return commentDAO.getCommentsWithPagination(page, pageSize);
    }
    public List<Comment> getCommentsByUserId(int userId){
        return commentDAO.getCommentsByUserId(userId);
    }
    // 插入评论
    public boolean addCommenta(Comment comment) {
        return commentDAO.insertComment(comment);
    }

    // 分页获取评论
    public List<Comment> getCommentsByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return commentDAO.getComments(offset, pageSize);
    }

    // 获取评论总数
    public int getTotalCommentCount() {
        return commentDAO.getCommentCount();
    }
    public List<Comment> searchComments(String query, int page, int pageSize) {
        return commentDAO.searchComments(query, page, pageSize);
    }

    public int getSearchTotalPages(String query, int pageSize) {
        return commentDAO.getSearchTotalPages(query, pageSize);
    }

}
