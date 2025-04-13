package Presenter;

import Model.Income;
import Model.ModelFacade;
import Model.User;
import View.FinanceApplication;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

public class PresenterFacade implements IPresenter{
    @Override
    public User logIn(String login, String password) {
        ModelFacade facade = new ModelFacade();
        User user = facade.getUserByCredentials(login, password);
        return user;
    }

    @Override
    public boolean validateIfMailAndLoginNotExists(String mail, String login) {
        ModelFacade facade = new ModelFacade();
        return facade.validateIfEmailAndLoginNotExists(mail, login);
    }

    @Override
    public ArrayList<Income> getTopValueItemsOfUser(int userid) {
        ModelFacade facade = new ModelFacade();
        ArrayList<Income> incoms = facade.getUserTop3Incomes(userid);
        return incoms;
    }

    @Override
    public boolean createAccount(String email, String password, String login) {
        ModelFacade facade = new ModelFacade();
        User client = new User();
        client.email = email;
        client.password = password;
        client.login = login;

        boolean ifAddedUser = facade.addUser(client);//dodanie klienta do BD
        if(ifAddedUser){
            return true;
        } else return false;
    }

    @Override
    public boolean addIncome(Income income) {
        ModelFacade facade = new ModelFacade();
        boolean bool = facade.addIncome(income);//adding income to DB and taking it;s bool value

        if(!bool){
            return false;
        } return true;

    }

    @Override
    public ArrayList<Income> getUserIncomes(int userid) {
        ModelFacade facade = new ModelFacade();
        ArrayList<Income> incoms = facade.getUserIncomes(userid);
        return incoms;
    }

    @Override
    public void deleteIncome(int incomeid) {
        ModelFacade facade = new ModelFacade();
        facade.removeIncome(incomeid);
    }

    @Override
    public boolean addIncoms(ArrayList<Income> incomes){
        ModelFacade facade = new ModelFacade();
        boolean bool = facade.addIncomes(incomes);
        if(!bool){
            return false;
        } return true;
    }
}