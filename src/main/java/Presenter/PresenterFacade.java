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
    public boolean createAccount(int id, String email, String password, String name, String surname) {
        return false;
    }
}