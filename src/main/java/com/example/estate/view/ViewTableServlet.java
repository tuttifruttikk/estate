package com.example.estate.view;
import com.example.estate.model.Table;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "view", value = "/view")
public class ViewTableServlet extends HttpServlet {
    ArrayList<Table> tables;
    Table table;
    int table_id;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tableBase.txt"))) {
            tables = (ArrayList<Table>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        String nameTable = request.getParameter("name");

        for (int i = 0; i < tables.size(); ++i) {
            if (tables.get(i).getName().equals(nameTable)) {
                table = tables.get(i);
                table_id = i;
            }
        }

        HttpSession session = request.getSession(false);

        PrintWriter writer = response.getWriter();
        RequestDispatcher requestDispatcher;
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        request.getRequestDispatcher("header.html").include(request, response);
        writer.println("<body>");
        writer.println("<div class=\"toMain\">");
        writer.println("<a href=\"/table\" class=\"menu_button\">To main</a>");
        writer.println("</div>");
        writer.println("<div class=\"header_name\">");
        writer.println(table.getName());
        writer.println("</div>");
        if (session != null && session.getAttribute("role").equals("teacher")) {
            writer.println(table.getTableTeacher());
        } else {
            writer.println(table.getTableStudent());
        }
        writer.println("</body>");
        writer.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String addRow = request.getParameter("addRow");
        String addColumn = request.getParameter("addColumn");

        String removeRow = request.getParameter("removeRow");
        String removeColumn = request.getParameter("removeColumn");

        String changeRow = request.getParameter("changeRow");
        String changeColumn = request.getParameter("changeColumn");
        String text = request.getParameter("text");

        if (addRow != null) {
            table.addRows(Integer.parseInt(addRow));
            tables.set(table_id, table);
            response.sendRedirect("/view?name=" + table.getName());
        } else if (addColumn != null) {
            table.addColumns(Integer.parseInt(addColumn));
            tables.set(table_id, table);
            response.sendRedirect("/view?name=" + table.getName());
        } else if (text != null) {
            int row = Integer.parseInt(changeRow);
            int column = Integer.parseInt(changeColumn);
            table.setTable(row, column, text);
            response.sendRedirect("/view?name=" + table.getName());
        } else if (removeColumn != null) {
            table.removeColumns(Integer.parseInt(removeColumn));
            tables.set(table_id, table);
            response.sendRedirect("/view?name=" + table.getName());
        } else if (removeRow != null) {
            table.removeRow(Integer.parseInt(removeRow));
            tables.set(table_id, table);
            response.sendRedirect("/view?name=" + table.getName());
        }

        String date = new Date().toString();
        table.setDateOfLastModification(date);
        tables.set(table_id, table);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tableBase.txt"))) {
            oos.writeObject(tables);
            oos.flush();
        }
    }
}
