package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.PlotCardController;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.IllegalSessionException;

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

    public void initialize() {
        homeService = HomeService.getInstance();

        loadActivities();
    }

    @FXML private void loadActivities() {
        try {
            hideEmptyListMessage();
            List<Lotto> attivitaList = homeService.getLottiUtente();

            for (Lotto lotto : attivitaList) {
                Parent card = createPlotCard(lotto);
                plotsCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            showEmptyListMessage();
        } catch (Exception e) {
            FocusUtil.setFocusTo(plotsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private Parent createPlotCard(Lotto lotto) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PLOT_CARD_PATH));
        Parent card = loader.load();
        PlotCardController controller = loader.getController();
        controller.setData(lotto);
        return card;
    }

    @FXML private void showEmptyListMessage() {
        plotsCardContainer.setVisible(false);
        plotsCardContainer.setManaged(false);
        emptyMessageLabel.setVisible(true);
        emptyMessageLabel.setManaged(true);
    }
    @FXML private void hideEmptyListMessage() {
        plotsCardContainer.setVisible(true);
        plotsCardContainer.setManaged(true);
        emptyMessageLabel.setVisible(false);
        emptyMessageLabel.setManaged(false);
    }
}
