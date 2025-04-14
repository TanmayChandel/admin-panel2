
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                out.println("<h3>Database connection failed!</h3>");
                return;
            }

            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<h3>Registration Successful!</h3><a href='register.html'>Login</a>");
            } else {
                out.println("<h3>Registration Failed!</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();  // <-- You NEED this to see errors
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
