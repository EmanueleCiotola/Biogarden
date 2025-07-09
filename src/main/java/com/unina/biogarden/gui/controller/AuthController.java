package com.unina.biogarden.gui.controller;

import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class AuthController {
    @FXML private StackPane authContentContainer;
    @FXML private StackPane authPage;

    public void initialize() {
        Router.getInstance().setContentContainer(authContentContainer);
        Router.getInstance().loadContent("loginBlock");

        FocusUtil.setupDefocusOnClick(authPage);
    }
}
