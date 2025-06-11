package com.unina.biogarden.router;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Router {
    private static Router instance;
    private StackPane rootStack;

    private Router() {}

    public static Router getInstance() {
        if (instance == null) instance = new Router();
        return instance;
    }

    public void setRootStack(StackPane rootStack) {
        this.rootStack = rootStack;
    }

    // per snackbar
    public static StackPane getRootStack() {
        return getInstance().rootStack;
    }

    public void navigateTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/unina/biogarden/gui/view/" + fxml + ".fxml"));
            Node view = loader.load();

            if (rootStack.getChildren().isEmpty()) {
                rootStack.getChildren().setAll(view);
            } else {
                Node oldView = rootStack.getChildren().get(0);
                FadeTransition fadeOut = new FadeTransition(Duration.millis(250), oldView);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                fadeOut.setOnFinished(_ -> {
                    view.setOpacity(0);
                    rootStack.getChildren().setAll(view);

                    FadeTransition fadeIn = new FadeTransition(Duration.millis(250), view);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                });

                fadeOut.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}