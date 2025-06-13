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

    public static StackPane getRootStack() {
        return getInstance().rootStack;
    }

    public void navigateTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/unina/biogarden/gui/view/" + fxml + ".fxml"));
            Node newView = loader.load();

            if (rootStack.getChildren().isEmpty()) {
                rootStack.getChildren().setAll(newView);
            } else {
                Node oldView = rootStack.getChildren().get(0);
                fadeOut(oldView, () -> {
                    rootStack.getChildren().setAll(newView);
                    fadeIn(newView);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchBlocks(Node from, Node to) {
        fadeOut(from, () -> {
            from.setVisible(false);
            from.setManaged(false);

            to.setOpacity(0);
            to.setVisible(true);
            to.setManaged(true);

            fadeIn(to);
        });
    }

    private void fadeOut(Node node, Runnable onFinished) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setOnFinished(event -> onFinished.run());
        ft.play();
    }

    private void fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}
