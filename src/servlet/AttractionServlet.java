package servlet;

import model.Attraction;
import service.AttractionService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AttractionServlet")
public class AttractionServlet extends HttpServlet {
    private final AttractionService attractionService = new AttractionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型和编码
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 获取请求参数
        String city = request.getParameter("city");
        String limitParam = request.getParameter("limit");
        Integer limit = null;

        // 校验并解析参数
        if (city == null || city.trim().isEmpty()) {
            writeErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "City name is required.");
            return;
        }

        if (limitParam != null) {
            try {
                limit = Integer.parseInt(limitParam);
                if (limit <= 0) {
                    writeErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Limit must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                writeErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid limit parameter.");
                return;
            }
        }

        try {
            // 调用 Service 获取景点列表
            List<Attraction> attractions = attractionService.getAttractionsByCity(city, limit);

            // 判断返回结果是否为空
            if (attractions.isEmpty()) {
                writeErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "No attractions found for the specified city.");
            } else {
                // 转换为 JSON 并返回
                String json = new Gson().toJson(attractions);
                response.getWriter().write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching attractions.");
        }
    }

    /**
     * 写入错误响应
     *
     * @param response HTTP 响应对象
     * @param statusCode HTTP 状态码
     * @param errorMessage 错误消息
     * @throws IOException 写入响应可能抛出 IOException
     */
    private void writeErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write(new Gson().toJson(new ErrorResponse(errorMessage)));
    }

    /**
     * 内部类，用于错误响应的 JSON 格式化
     */
    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
