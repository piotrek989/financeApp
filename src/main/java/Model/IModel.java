package Model;

import java.util.ArrayList;

public interface IModel {
    void deleteUser(User user);
    boolean addIncome(Income income);
    boolean addExpense(Expense expense);
    void removeIncome(int incomeid);
    User getUserByCredentials(String email, String password);
    User getUserById(int id);
    ArrayList<Income> getUserIncomes(int userId);
    ArrayList<Income> getUserTop3Incomes(int userid);
    ArrayList<Expense> getUserExpenses(int userId);
    boolean addUser(User user);
    boolean validateIfEmailAndLoginNotExists(String email, String login);
    boolean addIncomes(ArrayList<Income> incomes);


    void removeExpense(int expenseid);
    boolean addExpenses(ArrayList<Expense> expenses);
    ArrayList<Expense> getUserTop3Expenses(int userid);

}

