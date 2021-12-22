package com.example.estate.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "change", value = "/change")
public class ChangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String row = request.getParameter("row");
        String column = request.getParameter("column");
        String name = request.getParameter("name");

        PrintWriter writer = response.getWriter();
        RequestDispatcher requestDispatcher;
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        request.getRequestDispatcher("header.html").include(request, response);
        writer.println("<body>");
        writer.println("<div class=\"header\">");
        writer.println("<div class=\"header_name\">" + name + "<br>" + "ROW: " + row + "<br>" + "COLUMN: " + column + "</div>");
        writer.println("<form action=\"/view?name=" + name + "\" method=\"post\" class=\"form_new-answer\">");
        writer.println("<div class=\"form\">");
        writer.println("<label class=\"label\">Change<br>");
        writer.println("<input type=\"text\" name=\"text\" class=\"input\" placeholder=\"Enter the text\" required></input>");
        writer.println("</label>");
        writer.println("</div>");
        writer.println("<input type=\"hidden\" name=\"changeRow\" value=\"" + row + "\"/>");
        writer.println("<input type=\"hidden\" name=\"changeColumn\" value=\"" + column + "\"/>");
        writer.println("<div class=\"form_submit\">");
        writer.println("<input type=\"submit\" value=\"new text\" class=\"menu_button\"/>");
        writer.println("</div>");
        writer.println("</form>");
        writer.println("</div>");
        writer.println("</body>");
        writer.println("</html>");
    }

}
