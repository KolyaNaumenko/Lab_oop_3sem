package servlet;

import dao.UserDAO;
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

@WebServlet("/api/user")

public class UserServlet extends HttpServlet {
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
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addUser(request, response);
        } else if ("update".equals(action)) {
            updateUser(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("get".equals(action)) {
            getUser(request, response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{ \"error\": \"Error reading input\" }");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(json.toString());
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("user");
            user.setMoney(0);

            userDAO.addUser(user);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{ \"message\": \"User added successfully\" }");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{ \"error\": \"Error processing request\" }");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(json.toString());
        int id = Integer.parseInt(request.getParameter("id"));
        String role = request.getParameter("role");
        double money = Double.parseDouble(request.getParameter("money"));
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        User user = new User(id, name, password, email, role, money);
        userDAO.updateUser(user);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"message\": \"User updated successfully\" }");
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.getUserById(id);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("name", user.getName());
        jsonResponse.put("email", user.getEmail());
        jsonResponse.put("password", user.getPassword());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }
}
