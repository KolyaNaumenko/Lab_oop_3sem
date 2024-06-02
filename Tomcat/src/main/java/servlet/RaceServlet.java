package servlet;

import dao.RaceDAO;
import model.Race;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

@WebServlet("/api/race")
public class RaceServlet extends HttpServlet {
    private RaceDAO raceDAO;

    public void init() {
        try {
            raceDAO = new RaceDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateRace(request, response);
        }
    }

    private void updateRace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(json.toString());
        int raceId = Integer.parseInt(request.getParameter("race_id"));
        Race race = raceDAO.getRaceById(raceId);
        Date date = race.getDate();
        Time time = race.getTime();
        String location = race.getLocation();
        String status = jsonObject.getString("status");

        race = new Race(raceId, date, time, location, status);
        raceDAO.updateRace(race);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Bet updated successfully\"}");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
