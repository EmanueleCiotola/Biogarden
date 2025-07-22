package com.unina.biogarden.util;

import com.unina.biogarden.gui.controller.SnackbarController;

import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;

public class Router {
    private static final String FXML_VIEW_PATH = "/com/unina/biogarden/gui/view/";
    private static final Duration FADE_DURATION = Duration.millis(250);
    private static final Duration SNACKBAR_DURATION = Duration.millis(2000);

    private static Router instance;
    private Pane rootStack;
    private Pane contentContainer;
    private Node currentSnackbar;

    private Router() {}

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
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
    private Node creaNuovaView(String fxmlName) throws Exception {
        String fxmlPath = FXML_VIEW_PATH + fxmlName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        
        Node newView = loader.load();
        newView.setOpacity(0);
        return newView;
    }
    private void animaPrimaView(Pane container, Node newView) throws Exception {
        container.getChildren().setAll(newView);
        Platform.runLater(() -> fadeIn(newView)); // Il runLater evita effetto scattino dovuto al rendering anticipato
    }
    private void animaView(Pane container, Node newView) throws Exception {
        Node oldView = container.getChildren().getFirst();
        container.getChildren().add(newView);

        fadeOut(oldView, () -> {
            container.getChildren().remove(oldView);
            fadeIn(newView);
        });
    }

    public Node showSnackbar(String messaggio) {
        try {
            if (currentSnackbar != null) {
                removeSnackbar(currentSnackbar); // Rimuovi snackbar ancora presenti
            }

            Node snackbar = creaSnackbar(messaggio);
            rootStack.getChildren().add(snackbar);
            currentSnackbar = snackbar;
            fadeIn(snackbar);

            rimozioneProgrammataSnackbar(snackbar);

            return snackbar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Node creaSnackbar(String messaggio) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_VIEW_PATH + "snackbar.fxml"));
        Node node = loader.load();
        SnackbarController controller = loader.getController();
        controller.setMessage(messaggio);

        return node;
    }
    private void rimozioneProgrammataSnackbar(Node snackbar) {
        PauseTransition pause = new PauseTransition(SNACKBAR_DURATION);
        pause.setOnFinished(_ -> removeSnackbar(snackbar));
        pause.play();
    }
    public void removeSnackbar(Node snackbar) {
        if (rootStack.getChildren().contains(snackbar)) {
            fadeOut(snackbar, () -> {
                rootStack.getChildren().remove(snackbar);
                if (snackbar == currentSnackbar) {
                    currentSnackbar = null;
                }
            });
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
        createFadeTransition(node, 1.0, 0.0, onFinished).play();
    }
    private void fadeIn(Node node) {
        createFadeTransition(node, 0.0, 1.0, null).play();
    }
    private FadeTransition createFadeTransition(Node node, double fromValue, double toValue, Runnable onFinished) {
        FadeTransition transition = new FadeTransition(FADE_DURATION, node);
        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        
        if (onFinished != null) {
            transition.setOnFinished(_ -> onFinished.run());
        }
        
        return transition;
    }
}