package com.unina.biogarden.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class Router {
    private static Router instance;
    private Pane rootStack;
    private Pane contentContainer;

    private Router() {}

    public static Router getInstance() {
        if (instance == null) instance = new Router();
        return instance;
    }

    public void setRootStack(Pane rootStack) {
        this.rootStack = rootStack;
    }
    public void setContentContainer(Pane container) {
        this.contentContainer = container;
    }

    public static Pane getRootStack() {
        return getInstance().rootStack;
    }
    public static Pane getContentContainer() {
        return getInstance().contentContainer;
    }

    public void navigateTo(String fxmlName) {
        loadIntoContainer(rootStack, fxmlName);
    }
    public void loadContent(String fxmlName) {
        loadIntoContainer(contentContainer, fxmlName);
    }
    private void loadIntoContainer(Pane container, String fxmlName) {
        try {
            Node newView = creaNuovaView(fxmlName);
            if (container.getChildren().isEmpty()) {
                animaPrimaView(container, newView);
            } else {
                animaView(container, newView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Node creaNuovaView(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/unina/biogarden/gui/view/" + fxml + ".fxml"));
        Node newView = loader.load();
        newView.setOpacity(0);
        return newView;
    }
    private void animaPrimaView(Pane container, Node newView) throws Exception {
        container.getChildren().setAll(newView);
        fadeIn(newView);
    }
    private void animaView(Pane container, Node newView) throws Exception {
        Node oldView = container.getChildren().get(0);
        container.getChildren().add(newView);

        fadeOut(oldView, () -> {
            container.getChildren().remove(oldView);
            fadeIn(newView);
        });
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
        ft.setOnFinished(_ -> onFinished.run());
        ft.play();
    }

    private void fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(250), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}