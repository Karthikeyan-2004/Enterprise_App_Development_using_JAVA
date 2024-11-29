import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                out.println("Profile Page<br>");
                out.println("Welcome, " + username + "!<br>");
                out.println("<a href='logout'>Logout</a>");
            } else {
                response.sendRedirect("index.html");
            }
        } else {
            response.sendRedirect("index.html");
        }
    }
}
