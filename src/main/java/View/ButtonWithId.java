package View;

import Presenter.PresenterFacade;
import javafx.scene.control.Button;

public class ButtonWithId extends Button {
    public int id;

    public ButtonWithId(int id) {
        this.id = id;
        this.setOnAction(event -> handleDeleteButton());
    }

    private void handleDeleteButton() {
        PresenterFacade presenterFacade = new PresenterFacade();
        presenterFacade.deleteIncome(this.id);
    }
}

