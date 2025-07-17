package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;

public class AddNewController {
    @FXML private void handleAddProject() {
        Router.getInstance().loadContent("home/addProjectBlock");
    }
    @FXML private void handleAddActivity() {
        // TODO
    }
}
