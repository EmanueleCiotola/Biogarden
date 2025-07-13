package com.unina.biogarden.gui.controller.home;


import com.unina.biogarden.gui.controller.home.widget.ActivityCardController;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.IllegalSessionException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import java.util.*;
import javafx.scene.Parent;

public class AllActivitiesController {
    private static final String ACTIVITY_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ActivityCard.fxml";

    @FXML private FlowPane activitiesCardContainer;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        loadActivities();
    }

    @FXML private void loadActivities() {
        try {
            List<Attivita> attivitaList = homeService.getAttivitaUtente();

            for (Attivita attivita : attivitaList) {
                Parent card = createActivityCard(attivita);
                activitiesCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            // TODO: handle exception
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
}
