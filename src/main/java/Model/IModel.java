package Model;

import java.util.ArrayList;

public interface IModel {
    void deleteUser(User user);
    boolean addIncome(Income income);
    void addExpense(Expense expense);
    void removeIncome(int incomeid);
    void removeExpense(Expense expense);
    User getUserByCredentials(String email, String password);
    User getUserById(int id);
    ArrayList<Income> getUserIncomes(int userId);
    ArrayList<Income> getUserTop3Incomes(int userid);
    ArrayList<Expense> getUserExpenses(int userId);
    boolean addUser(User user);
    boolean validateIfEmailAndLoginNotExists(String email, String login);
    boolean addIncomes(ArrayList<Income> incomes);

}

