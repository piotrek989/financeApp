package Presenter;

import Model.ModelFacade;
import Model.User;

public class PresenterFacade implements IPresenter{
    @Override
    public User logIn(String login, String password) {
        ModelFacade facade = new ModelFacade();
        User user = facade.getUserByCredentials(login, password);
        return user;
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