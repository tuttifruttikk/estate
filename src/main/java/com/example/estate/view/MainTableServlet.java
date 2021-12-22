package com.example.estate.view;

import com.example.estate.model.Table;
import com.example.estate.model.User;
import com.example.estate.model.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "table", value = "/table")
public class MainTableServlet extends HttpServlet {
    private HashMap<String, User> users = null;
    private UserDAO dao = new UserDAO("users.txt");
    private ArrayList<Table> tables;

    @Override
    public void init() throws ServletException {
        users = dao.loadFile();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tableBase.txt"))) {
            tables = (ArrayList<Table>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession(false);
        PrintWriter writer = response.getWriter();
        RequestDispatcher requestDispatcher;
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        request.getRequestDispatcher("header.html").include(request, response);
        writer.println("<body>");
        if (session != null && session.getAttribute("role").equals("teacher")) {
            requestDispatcher = request.getRequestDispatcher("header_for_teacher.html");
        } else if (session != null && session.getAttribute("role").equals("student")){
            requestDispatcher = request.getRequestDispatcher("header_for_student.html");
        } else {
            requestDispatcher = request.getRequestDispatcher("header_for_guests.html");
        }
        requestDispatcher.include(request, response);

        writer.println("<div class=\"tables\">");
        for (int i = 0; i < tables.size(); ++i) {

            writer.println("<a href=\"/view?name=" + tables.get(i).getName() + "\">");
            writer.println("<div class=\"table\">");
            writer.println("<div class=\"table_name\">");
            writer.println(tables.get(i).getName());
            writer.println("</div>");
            writer.println("<div class=\"table_author\">");
            writer.println(tables.get(i).getAuthor());
            writer.println("</div>");
            writer.println("<div class=\"table_date-create\">");
            writer.println(tables.get(i).getDateOfCreation());
            writer.println("</div>");
            writer.println("<div class=\"table_date-modification\">");
            writer.println(tables.get(i).getDateOfLastModification());
            writer.println("</div>");
            if (session != null && session.getAttribute("role").equals("teacher")) {
                writer.println("<div class=\"button\">");
                writer.println("<form action=\"table\" method=\"post\">");
                writer.println("<input type=\"hidden\" name=\"reset\"  value=\"" + i + "\">");
                writer.println("<input type=\"submit\" value=\"delete\" class=\"delete_button\">");
                writer.println("</form>");
                writer.println("</div>");
            }
            writer.println("</div>");
            writer.println("</a>");

        }
        writer.println("</div>");

        writer.println("</body>");
        writer.println("</html>");

        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reset = request.getParameter("reset");

        if (reset != null) {
            int ind = Integer.parseInt(reset);
            tables.remove(ind);
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tableBase.txt"))) {
                oos.writeObject(tables);
                oos.flush();
            }
            response.sendRedirect("/table");
        }


    }
}
