package com.unina.biogarden.gui.controller;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AuthContainerController {
    @FXML private StackPane authContainer;

    
    @FXML
    public void initialize() {
        // Carica il form di login di default all'avvio
        switchForm("/com/unina/biogarden/gui/view/loginForm.fxml");
    }   

public void switchForm(String fxmlPath) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent newForm = loader.load();

        if (!authContainer.getChildren().isEmpty()) {
            Node oldForm = authContainer.getChildren().get(0);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), oldForm);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                authContainer.getChildren().clear();
                newForm.setOpacity(0.0);
                authContainer.getChildren().add(newForm);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newForm);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        } else {
            newForm.setOpacity(0.0);
            authContainer.getChildren().add(newForm);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newForm);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
