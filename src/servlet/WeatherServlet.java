package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import service.WeatherService;

public class WeatherServlet extends HttpServlet {
    private WeatherService weatherService = new WeatherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("location");
        String weatherData = weatherService.getWeather(location);
        response.getWriter().write(weatherData);
    }
}
