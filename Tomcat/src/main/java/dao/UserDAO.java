package dao;

import model.User;
import database.DbUtil;

import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO() throws SQLException {
        connection = DbUtil.getConnection();
    }

    public User authenticateUser(String email, String password) {
        try {
            String query = "SELECT * FROM public.user WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role"),
                        resultSet.getDouble("money")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(User user) {
        try {
            String query = "INSERT INTO public.user (name, password, email, role, money) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setDouble(5, user.getMoney());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try {
            String query = "UPDATE public.user SET name=?, password=?, email=?, role=?, money=? WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setDouble(5, user.getMoney());
            preparedStatement.setInt(6, user.getUserId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        User user = new User();
        try {
            String query = "SELECT * FROM public.user WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                user.setMoney(resultSet.getDouble("money"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}