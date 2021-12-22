package com.example.estate.view;

import com.example.estate.model.User;
import com.example.estate.model.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private HashMap<String, User> users;
    private UserDAO dao = new UserDAO("users.txt");

    @Override
    public void init() throws ServletException {
        users = dao.loadFile();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String role = request.getParameter("role");


        if (!users.containsKey(login)) {
            users.put(login, new User(password, role));
            dao.saveToFile(users);
            HttpSession session = request.getSession();
            session.setAttribute("login", login);
            session.setAttribute("role", role);
            response.sendRedirect("table");
        } else {
            writer.println("Login already!");
        }
    }
}
