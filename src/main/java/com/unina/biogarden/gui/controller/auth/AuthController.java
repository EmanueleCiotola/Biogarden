package com.unina.biogarden.gui.controller.auth;

import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class AuthController {
    @FXML private StackPane authPage;
    @FXML private StackPane authContentContainer;

    public void initialize() {
        Router.getInstance().setContentContainer(authContentContainer);
        Router.getInstance().loadContent("auth/loginBlock");

        FocusUtil.setupDefocusOnClick(authPage);
    }
}
