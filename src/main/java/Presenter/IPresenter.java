package Presenter;

import Model.User;

public interface IPresenter {
    User logIn (String email, String password);

    boolean createAccount(String email, String password, String login);

}