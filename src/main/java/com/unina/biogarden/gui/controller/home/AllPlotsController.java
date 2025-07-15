package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.PlotCardController;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.DatabaseException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class AllPlotsController extends BaseCardListController<Lotto> {
    private static final String PLOT_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/PlotCard.fxml";

    @FXML private FlowPane plotsCardContainer;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        init(plotsCardContainer, emptyMessageLabel);
    }

    @Override
    protected String getCardFXMLPath() {
        return PLOT_CARD_PATH;
    }

    @Override
    protected List<Lotto> caricaElementi() throws DatabaseException {
        return homeService.getLottiUtente();
    }

    @Override
    protected void setupCardController(Object controller, Lotto lotto) {
        ((PlotCardController) controller).setData(lotto);
    }

    @Override
    protected void mostraErrore() {
        Router.getInstance().showSnackbar(ErrorMessage.CARICAMENTO_LOTTI.toString());
    }
}
