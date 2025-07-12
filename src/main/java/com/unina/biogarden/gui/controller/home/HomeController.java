package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.Sessione;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class HomeController {
    @FXML private BorderPane homePage;
    @FXML private StackPane homeContentContainer;

    public void initialize() {
        Router.getInstance().setContentContainer(homeContentContainer);
        goToDashboard();

        FocusUtil.setupDefocusOnClick(homePage);
    }

    @FXML private void goToDashboard() {
        Router.getInstance().loadContent("home/dashboardBlock");
    }
    @FXML private void goToPlotReport() {
        Router.getInstance().loadContent("home/plotReportBlock");
    }
    @FXML private void goToAllProjects() {
        Router.getInstance().loadContent("home/allProjectsBlock");
    }
    @FXML private void goToAllActivities() {
        Router.getInstance().loadContent("home/allActivitiesBlock");
    }
    @FXML private void goToAllPlots() {
        Router.getInstance().loadContent("home/allPlotsBlock");
    }
    @FXML private void handleLogout() {
        Sessione.getInstance().logout();
        Router.getInstance().navigateTo("auth/authPage");
    }
}
