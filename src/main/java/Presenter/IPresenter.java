package Presenter;

import Model.Income;
import Model.User;

import java.util.ArrayList;

public interface IPresenter {
    User logIn (String email, String password);

    boolean createAccount(String email, String password, String login);

    ArrayList<Income> getTopValueItemsOfUser(int userid);

    boolean addIncome(Income income);
    ArrayList<Income> getUserIncomes(int userid);


}