package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.model.Utente;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class HomeController {    
    @FXML private BorderPane homePage;
    @FXML private StackPane homeContentContainer;
    @FXML private Label usernameLabel;
    @FXML private Label fullNameLabel;
    @FXML private Label fiscalCodeLabel;
    @FXML private Hyperlink allProjectsLink;
    @FXML private Hyperlink allActivitiesLink;
    @FXML private Hyperlink allPlotsLink;
    @FXML private Hyperlink plotReportLink;
    @FXML private Hyperlink addNewLink;

    private Hyperlink currentActiveLink;
    private HomeService homeService;

    public void initialize() {
        Router.getInstance().setContentContainer(homeContentContainer);
        goToAllProjects();
        setActiveLink(allProjectsLink);

        FocusUtil.setupDefocusOnClick(homePage);

        homeService = HomeService.getInstance();
        setUserInfo();
    }

    private void setUserInfo() {
        try {
            Utente user = homeService.getCurrentUser();
            
            usernameLabel.setText(user.getUsername());
            fullNameLabel.setText(user.getNome() + " " + user.getCognome());
            fiscalCodeLabel.setText(user.getCodiceFiscale());
        } catch (Exception e) {
             Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    private void setActiveLink(Hyperlink link) {
        if (currentActiveLink != null) {
            currentActiveLink.setDisable(false);
        }

        FocusUtil.setFocusTo(homePage);

        link.setDisable(true);
        currentActiveLink = link;
    }
    @FXML private void goToAllProjects() {
        Router.getInstance().loadContent("home/allProjectsBlock");
        setActiveLink(allProjectsLink);
    }
    @FXML private void goToAllActivities() {
        Router.getInstance().loadContent("home/allActivitiesBlock");
        setActiveLink(allActivitiesLink);
    }
    @FXML private void goToAllPlots() {
        Router.getInstance().loadContent("home/allPlotsBlock");
        setActiveLink(allPlotsLink);
    }
    @FXML private void goToPlotReport() {
        Router.getInstance().loadContent("home/plotReportBlock");
        setActiveLink(plotReportLink);
    }
    @FXML private void goToAddNew() {
        Router.getInstance().loadContent("home/addNewBlock");
        setActiveLink(addNewLink);
    }
    @FXML private void handleLogout() {
        try {
            homeService.logout();
            Router.getInstance().navigateTo("auth/authPage");
        } catch (Exception e) {
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
}
