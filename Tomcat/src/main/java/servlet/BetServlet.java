package servlet;

import dao.BetDAO;
import model.Bet;
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

@WebServlet("/api/bet")
public class BetServlet extends HttpServlet {
    private BetDAO betDAO;

    public void init() {
        try {
            betDAO = new BetDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addBet(request, response);
        } else if ("update".equals(action)) {
            updateBet(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            listBets(request, response);
        }
    }

    private void addBet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            int userId = jsonObject.getInt("userId");
            int betTypeId = jsonObject.getInt("betTypeId");
            double amount = jsonObject.getDouble("amount");
            String status = jsonObject.getString("status");
            double potentialWin = jsonObject.getDouble("potential_win");

            Bet bet = new Bet();
            bet.setUserId(userId);
            bet.setBetTypeId(betTypeId);
            bet.setAmount(amount);
            bet.setStatus(status);
            bet.setPotential_win(potentialWin);

            betDAO.addBet(bet);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{ \"message\": \"Bet added successfully\" }");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{ \"error\": \"Error processing request\" }");
        }
    }

    private void updateBet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(json.toString());
        int betId = Integer.parseInt(request.getParameter("bet_id"));
        Bet bet = betDAO.getBetById(betId);
        int userId = bet.getUserId();
        int betTypeId = bet.getBetTypeId();
        double amount = bet.getAmount();
        String status = jsonObject.getString("status");
        double potentialWin = bet.getPotential_win();

        bet = new Bet(betId, userId, betTypeId, amount, status, potentialWin);
        betDAO.updateBet(bet);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Bet updated successfully\"}");
    }

    private void listBets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Bet> bets = betDAO.getBetsByUserId(userId);
            JSONArray jsonArray = new JSONArray(bets);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonArray.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Unable to retrieve bets due to incorrect input or database error\"}");
        }
    }
}
