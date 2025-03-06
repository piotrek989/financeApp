package Model;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ModelFacade implements IModel {

    final String connectionString = "jdbc:postgresql://localhost:5432/fianance";
    private Properties props;


    public ModelFacade() {
        props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "admin");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionString, props);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void addIncome(Income income) {

    }

    @Override
    public void addExpense(Expense expense) {

    }

    @Override
    public void removeIncome(Income income) {

    }

    @Override
    public void removeExpense(Expense expense) {

    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public User getUserByCredentials(String login, String password) {
        User user = null;//teoretycznie walidacja danych
        String sql = "SELECT * FROM users WHERE login = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("login")

                );
                System.out.println(user.password);
            } else{
                System.out.println("Nie znaleziono uzytkownika!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public ArrayList<Income> getUserIncomes(int userId) {
        return new ArrayList<>(0);
    }

    @Override
    public ArrayList<Expense> getUserExpenses(int userId) {
        return new ArrayList<>(0);
    }

}