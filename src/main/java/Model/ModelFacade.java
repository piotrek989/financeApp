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
    public void removeIncome(int idIncome) {
        String sql = "DELETE FROM incoms WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idIncome);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                System.out.println("User not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public ArrayList<Income> getUserIncomes(int userId) {
        ArrayList<Income> incomes = new ArrayList<>(); // Initialize the list

        String sql = "SELECT * FROM incoms WHERE userid = ? ORDER BY date";

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
                incomes.add(income);
            }

            if (incomes.isEmpty()) {
                System.out.println("Not found incoms for user with id" + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomes; // Zwróć pełną listę wyników
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
                incomes.add(income);
            }

            if (incomes.isEmpty()) {
                System.out.println("Incoms not found for user with id " + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomes; // Zwróć pełną listę wyników
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
                System.out.println("User added correctly.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error when adding user " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validateIfEmailAndLoginNotExists(String email, String login) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ? OR login = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return (count == 0);
                }
            }

        } catch (SQLException e) {
            System.err.println("Login or Email already exists " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addIncomes(ArrayList<Income> incomes) {
        String procedureCall = "INSERT INTO incoms (date, price, type, userId) VALUES (?, ?, ?, ?)";
        boolean allSuccess = true;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(procedureCall)) {

            for (Income income : incomes) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(income.date));
                preparedStatement.setFloat(2, income.price);
                preparedStatement.setString(3, income.type);
                preparedStatement.setInt(4, income.userId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("Failed to insert income: " + income);
                    allSuccess = false;
                }
            }

            if (allSuccess) {
                System.out.println("All incomes added successfully.");
            } else {
                System.err.println("Some incomes failed to add.");
            }

            return allSuccess;

        } catch (SQLException e) {
            System.err.println("Error adding incomes to DB: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeExpense(int expenseid) {
        String sql = "DELETE FROM expenses WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, expenseid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addExpenses(ArrayList<Expense> expenses) {
        String procedureCall = "INSERT INTO expenses (date, price, type, userId) VALUES (?, ?, ?, ?)";
        boolean allSuccess = true;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(procedureCall)) {

            for (Expense expense : expenses) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(expense.date));
                preparedStatement.setFloat(2, expense.price);
                preparedStatement.setString(3, expense.type);
                preparedStatement.setInt(4, expense.userId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("Failed to insert expense: " + expenses);
                    allSuccess = false;
                }
            }

            if (allSuccess) {
                System.out.println("All expenses added successfully.");
            } else {
                System.err.println("Some expenses failed to add.");
            }

            return allSuccess;

        } catch (SQLException e) {
            System.err.println("Error adding expenses to DB: " + e.getMessage());
            e.printStackTrace();
            return false;
        }    }

    @Override
    public ArrayList<Expense> getUserTop3Expenses(int userid) {
        ArrayList<Expense> expenses = new ArrayList<>(); // Initialize the list

        String sql = "SELECT * FROM expenses WHERE userid = ? ORDER BY price DESC LIMIT 3";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getDate("date").toString(),
                        rs.getFloat("price"),
                        rs.getString("type"),
                        rs.getInt("userid")
                );
                expenses.add(expense);
            }

            if (expenses.isEmpty()) {
                System.out.println("Expenses not found for user with id " + userid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses; // Zwróć pełną listę wyników
    }
//    @Override
//    public void removeExpense(Expense expense) {
//
//    }

    @Override
    public boolean addExpense(Expense expense) {
        String procedureCall = "INSERT INTO expenses (date, price, type, userId) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(procedureCall)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(expense.date));
            preparedStatement.setFloat(2, expense.price);
            preparedStatement.setString(3, expense.type);
            preparedStatement.setInt(4, expense.userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Added item succesfully.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error with adding expense to DB: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Expense> getUserExpenses(int userId) {
        ArrayList<Expense> expenses = new ArrayList<>(); // Initialize the list

        String sql = "SELECT * FROM expenses WHERE userid = ? ORDER BY date";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getDate("date").toString(),
                        rs.getFloat("price"),
                        rs.getString("type"),
                        rs.getInt("userid")
                );
                expenses.add(expense);
            }

            if (expenses.isEmpty()) {
                System.out.println("Not found expenses for user with id" + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses; // Zwróć pełną listę wyników
    }
}