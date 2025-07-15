package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.ActivityCardController;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.IllegalSessionException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.Parent;

public class AllActivitiesController {
    private static final String ACTIVITY_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ActivityCard.fxml";
    
    @FXML private FlowPane activitiesCardContainer;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        loadActivities();
    }

    @FXML private void loadActivities() {
        try {
            hideEmptyListMessage();
            List<Attivita> attivitaList = homeService.getAttivitaUtente();

            for (Attivita attivita : attivitaList) {
                Parent card = createActivityCard(attivita);
                activitiesCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            showEmptyListMessage();
        } catch (Exception e) {
            FocusUtil.setFocusTo(activitiesCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private Parent createActivityCard(Attivita attivita) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ACTIVITY_CARD_PATH));
        Parent card = loader.load();
        ActivityCardController controller = loader.getController();
        controller.setData(attivita);
        return card;
    }

    @FXML private void showEmptyListMessage() {
        activitiesCardContainer.setVisible(false);
        activitiesCardContainer.setManaged(false);
        emptyMessageLabel.setVisible(true);
        emptyMessageLabel.setManaged(true);
    }
    @FXML private void hideEmptyListMessage() {
        activitiesCardContainer.setVisible(true);
        activitiesCardContainer.setManaged(true);
        emptyMessageLabel.setVisible(false);
        emptyMessageLabel.setManaged(false);
    }
}
