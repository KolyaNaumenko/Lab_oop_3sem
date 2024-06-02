package servlet;

import dao.BetTypeDAO;
import model.BetType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/bet-type")
public class BetTypeServlet extends HttpServlet {
    private BetTypeDAO betTypeDAO;

    public void init() {
        try {
            betTypeDAO = new BetTypeDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateBetType(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("get".equals(action)) {
            getBetType(request, response);
        } else if ("list".equals(action)) {
            listBetTypes(request, response);
        }
    }

    private void updateBetType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(json.toString());
        int betTypeId = Integer.parseInt(request.getParameter("betType_id"));
        BetType betType = betTypeDAO.getBetTypeById(betTypeId);
        int raceId = betType.getRaceId();
        int horseId = betType.getHorseId();
        double odd = jsonObject.getDouble("odd");
        boolean is_first = betType.isIs_first();

        betType = new BetType(betTypeId, raceId, horseId, odd, is_first);
        betTypeDAO.updateBetType(betType);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Bet updated successfully\"}");
    }

    private void getBetType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("userId"));
        BetType betType = betTypeDAO.getBetTypeById(id);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("odd", betType.getOdd());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    private void listBetTypes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<BetType> betTypes = betTypeDAO.getAllBetTypes();
            JSONArray jsonArray = new JSONArray(betTypes);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonArray.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Unable to retrieve bets due to incorrect input or database error\"}");
        }
    }
}
