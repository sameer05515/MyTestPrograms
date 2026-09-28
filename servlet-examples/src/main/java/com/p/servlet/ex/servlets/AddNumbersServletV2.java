package com.p.servlet.ex.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/v2/add")
public class AddNumbersServletV2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the two numbers from the query parameters
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");

        // Convert the numbers from String to int
        int num1 = Integer.parseInt(num1Str);
        int num2 = Integer.parseInt(num2Str);

        // Add the numbers
        int sum = num1 + num2;

        // Set the response content type
        response.setContentType("text/html");

        // Get the PrintWriter to write the response
        PrintWriter out = response.getWriter();
        
        // Write the result as HTML
        out.println("<html><body>");

        out.println("<h1>Sum from V2: " + sum + "</h1>");
        out.println("</body></html>");
    }
}
