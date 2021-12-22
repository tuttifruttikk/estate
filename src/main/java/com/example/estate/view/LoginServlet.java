package com.example.estate.view;

import com.example.estate.model.User;
import com.example.estate.model.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    private HashMap<String, User> users = null;
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

        if (password.equals(users.get(login).getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("login", login);
            session.setAttribute("role", users.get(login).getRole());
            response.sendRedirect("table");
        } else {
            writer.println("Incorrect login or password, please try again.");
        }
    }
}
