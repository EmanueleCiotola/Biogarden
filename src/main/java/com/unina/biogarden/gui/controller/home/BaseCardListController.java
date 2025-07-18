package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.DatabaseException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.List;


public abstract class BaseCardListController<T> {
    protected Pane cardContainer;
    protected Label emptyMessageLabel;

    public void init(Pane cardContainer, Label emptyMessageLabel) {
        try {
            this.cardContainer = cardContainer;
            this.emptyMessageLabel = emptyMessageLabel;

            List<T> items = caricaElementi();
            mostraSezione(items);
        } catch (Exception e) {
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    protected void mostraSezione(List<T> elementi) {
        if (elementi.isEmpty()) {
            mostraMessaggioListaVuota();
        } else {
            nascondiMessaggioListaVuota();
            mostraElementi(elementi);
        }
    }

    private void mostraElementi(List<T> elementi) {
        try {
            cardContainer.getChildren().clear();

            for (T elemento : elementi) {
                Parent card = creaCard(elemento);
                cardContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            mostraErrore();
        }
    }

    private Parent creaCard(T elemento) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getCardFXMLPath()));
        Parent card = loader.load();
        Object controller = loader.getController();
        setupCardController(controller, elemento);
        
        return card;
    }

    private void mostraMessaggioListaVuota() {
        cardContainer.setVisible(false);
        cardContainer.setManaged(false);
        emptyMessageLabel.setVisible(true);
        emptyMessageLabel.setManaged(true);
    }
    private void nascondiMessaggioListaVuota() {
        cardContainer.setVisible(true);
        cardContainer.setManaged(true);
        emptyMessageLabel.setVisible(false);
        emptyMessageLabel.setManaged(false);
    }

    protected abstract String getCardFXMLPath();
    protected abstract List<T> caricaElementi() throws DatabaseException;
    protected abstract void setupCardController(Object controller, T elemento);
    protected abstract void mostraErrore();
}
