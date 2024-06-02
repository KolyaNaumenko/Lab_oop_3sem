package dao;

import model.BetType;
import database.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetTypeDAO {
    private Connection connection;

    public BetTypeDAO() throws SQLException {
        connection = DbUtil.getConnection();
    }

    public void updateBetType(BetType betType) {
        try {
            String query = "UPDATE public.bet_type SET race_id=?, horse_id=?, odd=?, is_first=? WHERE bet_type_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, betType.getRaceId());
            preparedStatement.setInt(2, betType.getHorseId());
            preparedStatement.setDouble(3, betType.getOdd());
            preparedStatement.setBoolean(4, betType.isIs_first());
            preparedStatement.setInt(5, betType.getBetTypeId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BetType> getAllBetTypes() {
        List<BetType> betTypes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.bet_type");
            while (resultSet.next()) {
                BetType betType = new BetType();
                betType.setBetTypeId(resultSet.getInt("bet_type_id"));
                betType.setRaceId(resultSet.getInt("race_id"));
                betType.setHorseId(resultSet.getInt("horse_id"));
                betType.setOdd(resultSet.getDouble("odd"));
                betType.setIs_first(resultSet.getBoolean("is_first"));
                betTypes.add(betType);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return betTypes;
    }

    public BetType getBetTypeById(int betTypeId) {
        BetType betType = new BetType();
        try {
            String query = "SELECT * FROM public.bet_type WHERE bet_type_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, betTypeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                betType.setBetTypeId(resultSet.getInt("bet_type_id"));
                betType.setRaceId(resultSet.getInt("race_id"));
                betType.setHorseId(resultSet.getInt("horse_id"));
                betType.setOdd(resultSet.getDouble("odd"));
                betType.setIs_first(resultSet.getBoolean("is_first"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return betType;
    }
}
