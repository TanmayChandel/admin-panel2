import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/searchUser")
public class SearchUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            out.println("<html><body style='font-family: Arial; padding: 20px;'>");

            if (rs.next()) {
                out.println("<h3>User Found:</h3>");
                out.println("<p><strong>Username:</strong> " + rs.getString("username") + "</p>");
                out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                out.println("<a href='search.html'>Search Again</a>");
            } else {
                out.println("<h3>User Not Found</h3>");
                out.println("<a href='search.html'>Try Again</a>");
            }

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace(out); // For debugging; remove in production
        }
    }
}
