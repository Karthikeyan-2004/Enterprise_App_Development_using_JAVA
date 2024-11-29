/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.example;
import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
 @Inject
 private LoginBean loginBean;
 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse 
response)
 throws ServletException, IOException {
 String username = request.getParameter("username");
 String password = request.getParameter("password");
 if (loginBean.authenticate(username, password)) {
 HttpSession session = request.getSession();
 session.setAttribute("user", username);
 response.sendRedirect("welcome.jsp");
 } else {
 response.sendRedirect("login.jsp?error=1");
 }
 }
}
