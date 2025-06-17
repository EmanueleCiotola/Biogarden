package com.unina.biogarden.util;

import javafx.application.Platform;
import javafx.scene.Node;

public class FocusUtil {
    public static void setFocusTo(Node node) {
        Platform.runLater(() -> node.requestFocus());

        node.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) {
                node.requestFocus();
            }
        });
    }
}
