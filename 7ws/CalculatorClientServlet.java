package org.calculator.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.calculator.server.CalculatorService;

@WebServlet("/CalculatorClientServlet")
public class CalculatorClientServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Get user inputs from JSP form
        double num1 = Double.parseDouble(request.getParameter("num1"));
        double num2 = Double.parseDouble(request.getParameter("num2"));
        String operation = request.getParameter("operation");

        // Call the backend service
        CalculatorService service = new CalculatorService();
        double result = service.calculate(num1, num2, operation);

        // Send data to JSP page
        request.setAttribute("num1", num1);
        request.setAttribute("num2", num2);
        request.setAttribute("operation", operation);
        request.setAttribute("result", result);

        // Forward request to result.jsp
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}