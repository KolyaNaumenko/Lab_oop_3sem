package dao;

import model.Bet;
import database.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetDAO {
    private Connection connection;

    public BetDAO() throws SQLException {
        connection = DbUtil.getConnection();
    }

    public void addBet(Bet bet) {
        try {
            String query = "INSERT INTO public.bet (user_id, bet_type_id, amount, status, potential_win) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bet.getUserId());
            preparedStatement.setInt(2, bet.getBetTypeId());
            preparedStatement.setDouble(3, bet.getAmount());
            preparedStatement.setString(4, bet.getStatus());
            preparedStatement.setDouble(5, bet.getPotential_win());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBet(Bet bet) {
        try {
            String query = "UPDATE public.bet SET user_id=?, bet_type_id=?, amount=?, status=?, potential_win=? WHERE bet_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bet.getUserId());
            preparedStatement.setInt(2, bet.getBetTypeId());
            preparedStatement.setDouble(3, bet.getAmount());
            preparedStatement.setString(4, bet.getStatus());
            preparedStatement.setDouble(5, bet.getPotential_win());
            preparedStatement.setInt(6, bet.getBetId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bet> getBetsByUserId(int userId) {
        List<Bet> bets = new ArrayList<>();
        try {
            String query = "SELECT * FROM public.bet WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bet bet = new Bet();
                bet.setBetId(resultSet.getInt("bet_id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setBetTypeId(resultSet.getInt("bet_type_id"));
                bet.setAmount(resultSet.getDouble("amount"));
                bet.setStatus(resultSet.getString("status"));
                bet.setPotential_win(resultSet.getDouble("potential_win"));
                bets.add(bet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bets;
    }


    public Bet getBetById(int betId) {
        Bet bet = new Bet();
        try {
            String query = "SELECT * FROM public.bet WHERE bet_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, betId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bet.setBetId(resultSet.getInt("bet_id"));
                bet.setUserId(resultSet.getInt("user_id"));
                bet.setBetTypeId(resultSet.getInt("bet_type_id"));
                bet.setAmount(resultSet.getDouble("amount"));
                bet.setStatus(resultSet.getString("status"));
                bet.setPotential_win(resultSet.getDouble("potential_win"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bet;
    }
}
