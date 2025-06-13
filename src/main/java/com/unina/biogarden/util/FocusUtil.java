package com.unina.biogarden.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class FocusUtil {
    public static void removeInitialFocus(Region root) {
        Platform.runLater(() -> root.requestFocus());

        root.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            if (!target.isFocusTraversable()) {
                root.requestFocus();
            }
        });
    }
}
