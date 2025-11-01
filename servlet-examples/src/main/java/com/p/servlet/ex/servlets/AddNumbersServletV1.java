package com.p.servlet.ex.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/v1/add/*")
public class AddNumbersServletV1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the path info
        String pathInfo = request.getPathInfo(); // /num1/num2
        if (pathInfo != null && pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1); // remove leading slash
        }
        
        // Split the path info to get the parameters
        String[] parts = pathInfo.split("/");
        if (parts.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path parameters");
            return;
        }
        
        // Convert the parameters to integers
        int num1;
        int num2;
        try {
            num1 = Integer.parseInt(parts[0]);
            num2 = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            return;
        }

        // Add the numbers
        int sum = num1 + num2;

        // Set the response content type
        response.setContentType("text/html");

        // Get the PrintWriter to write the response
        PrintWriter out = response.getWriter();
        
        // Write the result as HTML
        out.println("<html><body>");
        out.println("<h1>Sum from V1: " + sum + "</h1>");
        out.println("</body></html>");
    }
}
