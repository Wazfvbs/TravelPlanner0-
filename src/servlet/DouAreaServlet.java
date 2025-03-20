// DouAreaServlet.java
package servlet;

import service.DouAreaService;
import model.DouArea;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/douArea")
public class DouAreaServlet extends HttpServlet {
    private DouAreaService douAreaService;

    @Override
    public void init() {
        Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
        douAreaService = new DouAreaService(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String provinceId = request.getParameter("provinceId");
            if (provinceId == null) {
                // 获取所有省份
                List<DouArea> provinces = douAreaService.getAllProvinces();
                request.setAttribute("provinces", provinces);
                request.getRequestDispatcher("provinces.jsp").forward(request, response);
            } else {
                // 获取指定省份的城市
                List<DouArea> cities = douAreaService.getCitiesByProvinceId(Integer.parseInt(provinceId));
                request.setAttribute("cities", cities);
                request.getRequestDispatcher("cities.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving areas", e);
        }
    }
}
