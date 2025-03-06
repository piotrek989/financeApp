package Model;

import java.util.ArrayList;

public interface IModel {
    void deleteUser(User user);
    void addIncome(Income income);
    void addExpense(Expense expense);
    void removeIncome(Income income);
    void removeExpense(Expense expense);
    User getUserByCredentials(String email, String password);
    User getUserById(int id);
    ArrayList<Income> getUserIncomes(int userId);
    ArrayList<Expense> getUserExpenses(int userId);

}

