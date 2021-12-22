package com.example.estate.view;

import com.example.estate.model.Table;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "create", value = "/create")
public class CreateTableServlet extends HttpServlet {
    ArrayList<Table> tables = new ArrayList<>();
    HashMap<String, Table> check = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tableBase.txt"))) {
            tables = (ArrayList<Table>) ois.readObject();
            for (int i = 0; i < tables.size(); ++i) {
                check.put(tables.get(i).getName(), tables.get(i));
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        request.getRequestDispatcher("header.html").include(request, response);
        writer.println("<body>");
        writer.println("<div>");
        request.getRequestDispatcher("create_new_table.html").include(request, response);
        writer.println("</div>");
        writer.println("</body>");
        writer.println("</html>");

        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession(false);

        String name = request.getParameter("tableName");

        if (session != null) {
            int rows = Integer.parseInt(request.getParameter("tableRows"));
            int columns = Integer.parseInt(request.getParameter("tableColumns"));
            Table table = new Table(name, rows, columns, (String) session.getAttribute("login"));
            if (!check.containsKey(table.getName())) {
                tables.add(table);
                check.put(table.getName(), table);
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tableBase.txt"))) {
                oos.writeObject(tables);
                oos.flush();
            }
            response.sendRedirect("/table");
        } else {
            writer.println("Please sign in!");
        }
    }
}
