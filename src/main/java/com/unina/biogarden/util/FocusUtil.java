package com.unina.biogarden.util;

import javafx.application.Platform;
import javafx.scene.Node;

public class FocusUtil {
    public static void setFocusTo(Node focusTo) {
        Platform.runLater(() -> focusTo.requestFocus());
    }
    
    public static void setupDefocusOnClick(Node container) {
        container.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) {
                container.requestFocus();
            }
        });
    }
}
