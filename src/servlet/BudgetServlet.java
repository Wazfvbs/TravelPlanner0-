package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import service.BudgetService;

public class BudgetServlet extends HttpServlet {
    private BudgetService budgetService = new BudgetService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("calculate".equals(action)) {
            // 预算计算
            String data = request.getParameter("data");
            String result = budgetService.calculateBudget(data);
            response.getWriter().write(result);
        }
    }
}
