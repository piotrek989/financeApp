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

    private Connection getConnection() {
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
    public boolean addIncome(Income income) {
        String procedureCall = "INSERT INTO incoms (date, price, type, userId) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(procedureCall)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(income.date));
            preparedStatement.setFloat(2, income.price);
            preparedStatement.setString(3, income.type);
            preparedStatement.setInt(4, income.userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Added item succesfully.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error with adding user to DB: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
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
    public ArrayList<Income> getUserTop3Incomes(int userId) { // TOP 3 valuable items
        ArrayList<Income> incomes = new ArrayList<>(); // Initialize the list

        String sql = "SELECT * FROM incoms WHERE userid = ? ORDER BY price DESC LIMIT 3";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Income income = new Income(
                        rs.getInt("id"),
                        rs.getDate("date").toString(),
                        rs.getFloat("price"),
                        rs.getString("type"),
                        rs.getInt("userid")
                );
                System.out.println("Oto date: " + income.date);
                incomes.add(income);
            }

            if (incomes.isEmpty()) {
                System.out.println("Nie znaleziono dochodów dla użytkownika o ID: " + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomes; // Zwróć pełną listę wyników
    }

    @Override
    public ArrayList<Expense> getUserExpenses(int userId) {
        return new ArrayList<>(0);
    }

    @Override
    public boolean addUser(User user) {
        String procedureCall = "INSERT INTO users (email, password, login) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(procedureCall)) {

            preparedStatement.setString(1, user.email);
            preparedStatement.setString(2, user.password);
            preparedStatement.setString(3, user.login);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Użytkownik został pomyślnie dodany.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Błąd podczas dodawania użytkownika: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}