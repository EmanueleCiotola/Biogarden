package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.PlotCardController;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;

public class AllPlotsController {
    private static final String PLOT_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/PlotCard.fxml";
    
    @FXML private FlowPane plotsCardContainer;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;
    List<Lotto> allPlots;

    public void initialize() {
        homeService = HomeService.getInstance();

        caricaLotti();
        caricaSezioneLottiSeDisponibile(allPlots);
    }

    private void caricaLotti() {
        try {
            allPlots = homeService.getLottiUtente();
        } catch (Exception e) {
            FocusUtil.setFocusTo(plotsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    private void caricaSezioneLottiSeDisponibile(List<Lotto> lottiDaMostrare) {
        if (lottiDaMostrare.isEmpty()) {
            mostraMessaggioListaVuota();
        } else {
            nascondiMessaggioListaVuota();
            mostraLotti(lottiDaMostrare);
        }
    }

    private void mostraLotti(List<Lotto> lotti) {
        try {    
            for (Lotto lotto : lotti) {
                Parent card = creaCardLotti(lotto);
                plotsCardContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            Router.getInstance().showSnackbar(ErrorMessage.CARICAMENTO_LOTTI.toString());
        }
    }

    private Parent creaCardLotti(Lotto lotto) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PLOT_CARD_PATH));
        Parent card = loader.load();
        PlotCardController controller = loader.getController();
        controller.setData(lotto);
        return card;
    }

    @FXML private void mostraMessaggioListaVuota() {
        plotsCardContainer.setVisible(false);
        plotsCardContainer.setManaged(false);
        emptyMessageLabel.setVisible(true);
        emptyMessageLabel.setManaged(true);
    }
    @FXML private void nascondiMessaggioListaVuota() {
        plotsCardContainer.setVisible(true);
        plotsCardContainer.setManaged(true);
        emptyMessageLabel.setVisible(false);
        emptyMessageLabel.setManaged(false);
    }
}
