package Presenter;

import Model.User;

public interface IPresenter {
    User logIn (String email, String password);

    boolean createAccount(int id, String email, String password, String name, String surname);

}