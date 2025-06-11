package com.unina.biogarden.gui.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

import com.unina.biogarden.router.Router;

public class SnackbarController {

    @FXML private Label snackBarLabel;
    @FXML private StackPane rootStack;

    public void setMessage(String message) {
        snackBarLabel.setText(message);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), snackBarLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), snackBarLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(1.5));

        fadeIn.setOnFinished(_ -> fadeOut.play());
        fadeOut.setOnFinished(_ -> Router.getRootStack().getChildren().remove(rootStack));

        fadeIn.play();
    }

    public static void show(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(SnackbarController.class.getResource("/com/unina/biogarden/gui/view/snackbar.fxml"));
            StackPane snackbarRoot = loader.load();
            snackbarRoot.setMouseTransparent(true);

            SnackbarController controller = loader.getController();
            controller.rootStack = snackbarRoot;
            Router.getRootStack().getChildren().add(snackbarRoot);
            StackPane.setAlignment(snackbarRoot, javafx.geometry.Pos.BOTTOM_CENTER);

            controller.setMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
