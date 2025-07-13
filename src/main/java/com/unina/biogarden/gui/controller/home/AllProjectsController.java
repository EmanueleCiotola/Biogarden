package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.gui.controller.home.widget.ProjectCardController;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.IllegalSessionException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.Parent;

import java.util.List;

public class AllProjectsController {
    private static final String PROJECT_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ProjectCard.fxml";

    @FXML private FlowPane projectsCardContainer;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        loadProjects();
    }

    @FXML
    private void loadProjects() {
        try {
            List<Progetto> progettiList = homeService.getProgettiUtente();

            for (Progetto progetto : progettiList) {
                Parent card = createProjectCard(progetto);
                projectsCardContainer.getChildren().add(card);
            }
        } catch (IllegalSessionException e) {
            // TODO: handle exception
        } catch (Exception e) {
            FocusUtil.setFocusTo(projectsCardContainer);
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
}
