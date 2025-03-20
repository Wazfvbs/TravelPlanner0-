package servlet;

import com.google.gson.Gson;
import dao.GsonUtil;
import model.Attraction;
import model.City;
import model.DouArea;
import dao.DouAreaDAO;
import dao.AttractionDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
public class CityServlet extends HttpServlet {
    private DouAreaDAO douAreaDAO;
    private AttractionDAO attractionDAO;

    @Override
    public void init() {
        douAreaDAO = new DouAreaDAO();
        attractionDAO = new AttractionDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String areaIdParam = request.getParameter("cityId");

        // 参数校验
        if (areaIdParam == null || areaIdParam.trim().isEmpty()) {
            response.getWriter().write("{\"error\": \"城市ID不能为空！\"}");
            return;
        }

        try {
            int areaId = Integer.parseInt(areaIdParam);

            // 根据 areaId 查询城市信息
            DouArea city = douAreaDAO.getCityById(areaId);

            if (city != null) {
                DouArea province = douAreaDAO.getCityById(city.getParentId());
                List<Attraction> attractions = attractionDAO.getAttractionsByCityName(city.getName());
                City cityResponse = new City(city, province,attractions);

                // 转换为 JSON 格式
                Gson gson = GsonUtil.getGson();
                response.getWriter().write(gson.toJson(cityResponse));
                return;
            }

            // 未找到城市
            response.getWriter().write("{\"error\": \"未找到匹配的城市！\"}");
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"error\": \"无效的城市ID！\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器错误，请稍后再试。");
        }
    }

}
