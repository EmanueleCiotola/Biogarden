package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.ActivityCardController;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

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
    List<Attivita> allActivities;

    public void initialize() {
        homeService = HomeService.getInstance();

        caricaAttivita();
        caricaSezioneAttivitaSeDisponibile(allActivities);
    }

    private void caricaAttivita() {
        try {
            allActivities = homeService.getAttivitaUtente();
        } catch (Exception e) {
            FocusUtil.setFocusTo(activitiesCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    private void caricaSezioneAttivitaSeDisponibile(List<Attivita> attivitaDaMostrare) {
        if (attivitaDaMostrare.isEmpty()) {
            mostraMessaggioListaVuota();
        } else {
            nascondiMessaggioListaVuota();
            mostraAttivita(attivitaDaMostrare);
        }
    }

    private void mostraAttivita(List<Attivita> attivita) {
        try {    
            for (Attivita s_attivita : attivita) {
                Parent card = creaCardAttivita(s_attivita);
                activitiesCardContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            Router.getInstance().showSnackbar(ErrorMessage.CARICAMENTO_ATTIVITA.toString());
        }
    }

    private Parent creaCardAttivita(Attivita attivita) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ACTIVITY_CARD_PATH));
        Parent card = loader.load();
        ActivityCardController controller = loader.getController();
        controller.setData(attivita);
        return card;
    }

    @FXML private void mostraMessaggioListaVuota() {
        activitiesCardContainer.setVisible(false);
        activitiesCardContainer.setManaged(false);
        emptyMessageLabel.setVisible(true);
        emptyMessageLabel.setManaged(true);
    }
    @FXML private void nascondiMessaggioListaVuota() {
        activitiesCardContainer.setVisible(true);
        activitiesCardContainer.setManaged(true);
        emptyMessageLabel.setVisible(false);
        emptyMessageLabel.setManaged(false);
    }
}
