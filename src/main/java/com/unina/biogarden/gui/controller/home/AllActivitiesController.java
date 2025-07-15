package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.ActivityCardController;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.DatabaseException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class AllActivitiesController extends BaseListController<Attivita> {
    private static final String ACTIVITY_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ActivityCard.fxml";

    @FXML private FlowPane activitiesCardContainer;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        init(activitiesCardContainer, emptyMessageLabel);
    }
    
        @Override
        protected String getCardFXMLPath() {
            return ACTIVITY_CARD_PATH;
        }

    @Override
    protected List<Attivita> caricaElementi() throws DatabaseException {
        return homeService.getAttivitaUtente();
    }

    @Override
    protected void setupCardController(Object controller, Attivita attivita) {
        ((ActivityCardController) controller).setData(attivita);
    }

    @Override
    protected void mostraErrore() {
        Router.getInstance().showSnackbar(ErrorMessage.CARICAMENTO_ATTIVITA.toString());
    }
}
