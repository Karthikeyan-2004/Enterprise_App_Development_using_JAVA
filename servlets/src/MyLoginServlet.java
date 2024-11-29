import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple authentication check
        if (username.equals("karthi") && password.equals("17Nov")) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            out.println("Welcome, " + username + "!<br>");
            out.println("<a href='profile'>Go to Profile</a><br>");
            out.println("<a href='logout'>Logout</a>");
        } else {
            out.println("Invalid username or password!<br>");
            out.println("<a href='index.html'>Try again</a>");
        }
    }
}
