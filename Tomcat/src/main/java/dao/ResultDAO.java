package dao;

import model.Result;
import database.DbUtil;

import java.sql.*;

public class ResultDAO {
    private Connection connection;

    public ResultDAO() throws SQLException {
        connection = DbUtil.getConnection();
    }

    public void addResult(Result result) {
        try {
            String query = "INSERT INTO public.result (race_id, horse_id, position) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, result.getRaceId());
            preparedStatement.setInt(2, result.getHorseId());
            preparedStatement.setInt(3, result.getPosition());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
