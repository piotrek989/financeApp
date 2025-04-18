package View;

import Presenter.PresenterFacade;
import javafx.scene.control.Button;


public class ButtonWithId extends Button {
    public int id;

    public enum Type {
        INCOME, EXPENSE
    }

    public ButtonWithId(int id, Type type) {
        this.id = id;
        if (type == Type.INCOME) {
            this.setOnAction(event -> handleDeleteButtonIncome());
        } else {
            this.setOnAction(event -> handleDeleteButtonExpense());
        }
    }

    private void handleDeleteButtonIncome() {
        PresenterFacade presenterFacade = new PresenterFacade();
        presenterFacade.deleteIncome(this.id);
    }

    private void handleDeleteButtonExpense() {
        PresenterFacade presenterFacade = new PresenterFacade();
        presenterFacade.deleteExpense(this.id);
    }
}


