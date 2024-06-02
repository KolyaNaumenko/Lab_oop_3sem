package dao;

import model.Race;
import database.DbUtil;

import java.sql.*;

public class RaceDAO {
    private Connection connection;

    public RaceDAO() throws SQLException {
        connection = DbUtil.getConnection();
    }

    public void updateRace(Race race) {
        try {
            String query = "UPDATE public.race SET date=?, time=?, location=?, status=? WHERE race_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, race.getDate());
            preparedStatement.setTime(2, race.getTime());
            preparedStatement.setString(3, race.getLocation());
            preparedStatement.setString(4, race.getStatus());
            preparedStatement.setInt(5, race.getRaceId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Race getRaceById(int raceId) {
        Race race = new Race();
        try {
            String query = "SELECT * FROM public.race WHERE race_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, raceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                race.setRaceId(resultSet.getInt("race_id"));
                race.setDate(resultSet.getDate("date"));
                race.setTime(resultSet.getTime("time"));
                race.setLocation(resultSet.getString("location"));
                race.setStatus(resultSet.getString("status"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return race;
    }
}