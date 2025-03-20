package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import dao.AttractionDAO;
import model.Attraction;
import service.TripService;

public class TripServlet extends HttpServlet {
    private TripService tripService = new TripService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("selectedAttractions".equals(action)) {
            // 获取用户选择的景点 ID
            String[] idArray = request.getParameterValues("ids");
            if (idArray != null) {
                try {
                    // 将 String[] 转换为 List<Integer>
                    List<Integer> ids = Arrays.stream(idArray)
                            .map(Integer::parseInt) // 将每个 String 转换为 Integer
                            .collect(Collectors.toList());

                    // 调用 DAO 获取景点信息
                    List<Attraction> selectedAttractions = AttractionDAO.getAttractionsByIds(ids);

                    // 设置响应类型为 JSON，并写入数据
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(selectedAttractions));
                } catch (NumberFormatException e) {
                    // 如果转换失败，返回 400 状态码
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid ID format");
                }
            } else {
                // 如果未提供 ID，返回 400 状态码
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("No IDs provided");
            }
        } else {
            // 未知的 action，返回 400 状态码
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action");
        }
    }

}
