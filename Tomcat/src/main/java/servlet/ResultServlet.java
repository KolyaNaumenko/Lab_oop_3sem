package servlet;

import dao.ResultDAO;
import model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/result")
public class ResultServlet extends HttpServlet {
    private ResultDAO resultDAO;

    public void init() {
        try {
            resultDAO = new ResultDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addResult(request, response);
        }
    }

    private void addResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(json.toString());
        int raceId = jsonObject.getInt("raceId");
        int horseId = jsonObject.getInt("horseId");
        int position = jsonObject.getInt("position");

        Result result = new Result();
        result.setRaceId(raceId);
        result.setHorseId(horseId);
        result.setPosition(position);

        resultDAO.addResult(result);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Result added successfully\"}");
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
