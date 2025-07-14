package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.gui.controller.home.widget.ActivityCardController;
import com.unina.biogarden.gui.controller.home.widget.PlotCardController;
import com.unina.biogarden.gui.controller.home.widget.ProjectCardController;
import com.unina.biogarden.model.Attivita;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.IllegalSessionException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.Parent;

public class DashboardController {
    private static final String PROJECT_CARD_PATH  = "/com/unina/biogarden/gui/view/home/widget/ProjectCard.fxml";   
    private static final String ACTIVITY_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ActivityCard.fxml";
    private static final String PLOT_CARD_PATH     = "/com/unina/biogarden/gui/view/home/widget/PlotCard.fxml";

    @FXML private FlowPane activitiesCardContainer;
    @FXML private FlowPane projectsCardContainer;
    @FXML private FlowPane plotsCardContainer;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        loadProjects();
        loadActivities();
        loadPlots();
    }

    @FXML private void loadProjects() {
        try {
            List<Progetto> progettoList = homeService.get3ProgettiUtente();

            for (Progetto progetto : progettoList) {
                Parent card = createProjectCard(progetto);
                projectsCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            // TODO: handle exception
        } catch (Exception e) {
            FocusUtil.setFocusTo(plotsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void loadActivities() {
        try {
            List<Attivita> attivitaList = homeService.get3AttivitaUtente();

            for (Attivita attivita : attivitaList) {
                Parent card = createActivityCard(attivita);
                activitiesCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            // TODO: handle exception
        } catch (Exception e) {
            FocusUtil.setFocusTo(plotsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    @FXML private void loadPlots() {
        try {
            List<Lotto> lottoList = homeService.get3LottiUtente();

            for (Lotto lotto : lottoList) {
                Parent card = createPlotCard(lotto);
                plotsCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            // TODO: handle exception
        } catch (Exception e) {
            FocusUtil.setFocusTo(plotsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    private Parent createProjectCard(Progetto progetto) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJECT_CARD_PATH));
        Parent card = loader.load();
        ProjectCardController controller = loader.getController();
        controller.setData(progetto);
        return card;
    }
    private Parent createActivityCard(Attivita attivita) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ACTIVITY_CARD_PATH));
        Parent card = loader.load();
        ActivityCardController controller = loader.getController();
        controller.setData(attivita);
        return card;
    }
    private Parent createPlotCard(Lotto lotto) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PLOT_CARD_PATH));
        Parent card = loader.load();
        PlotCardController controller = loader.getController();
        controller.setData(lotto);
        return card;
    }
}