package com.unina.biogarden.gui.controller;

import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML private StackPane rootStack;

    public void initialize() {
        Router.getInstance().setRootStack(rootStack);
        Router.getInstance().navigateTo("auth/authPage");
    }
}
