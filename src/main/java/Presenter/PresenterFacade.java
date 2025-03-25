package Presenter;

import Model.Income;
import Model.ModelFacade;
import Model.User;

import java.util.ArrayList;

public class PresenterFacade implements IPresenter{
    @Override
    public User logIn(String login, String password) {
        ModelFacade facade = new ModelFacade();
        User user = facade.getUserByCredentials(login, password);
        return user;
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
}