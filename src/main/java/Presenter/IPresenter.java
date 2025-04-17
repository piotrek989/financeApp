package Presenter;

import Model.Expense;
import Model.Income;
import Model.User;

import java.util.ArrayList;

public interface IPresenter {
    User logIn (String email, String password);
    boolean createAccount(String email, String password, String login);
    boolean validateIfMailAndLoginNotExists(String mail, String login);


    ArrayList<Income> getTopValueItemsOfUser(int userid);//for incoms
    boolean addIncome(Income income);
    void deleteIncome(int incomeid);
    ArrayList<Income> getUserIncomes(int userid);
    boolean addIncoms(ArrayList<Income> incomes);


    ArrayList<Expense> getTopExpensesOfUser(int userid);
    boolean addExpense(Expense expense);
    void deleteExpense(int expid);
    ArrayList<Expense> getUserExpenses(int userid);
    boolean addExpenses(ArrayList<Expense> expenses);

}