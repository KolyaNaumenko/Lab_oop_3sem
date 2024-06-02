package servlet;

import dao.UserDAO;
import filter.JwtUtil;
import model.User;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/login")
public class LoginServlet  extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder json = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        JSONObject jsonObject = new JSONObject(json.toString());
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        User user = userDAO.authenticateUser(email, password);
        if (user != null) {
            String token = JwtUtil.generateToken(user.getEmail(), user.getRole(), user.getName(), user.getMoney(), user.getUserId());
            response.setContentType("application/json");
            response.getWriter().write("{ \"token\": \"" + token + "\" }");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{ \"error\": \"Invalid email or password\" }");
        }
    }
}
