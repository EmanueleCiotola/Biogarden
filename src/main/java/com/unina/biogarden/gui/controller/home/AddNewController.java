package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.service.AddAndUpdateService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AddNewController {
    @FXML private VBox addNewContainer;
    
    private AddAndUpdateService addService;

    public void initialize() {
        addService = AddAndUpdateService.getInstance();

        FocusUtil.setFocusTo(addNewContainer);
    }

    @FXML private void handleAddProject() {
        Router.getInstance().loadContent("home/addProjectBlock");
    }
    @FXML private void handleAddActivity() {
        try {
            addService.getNomiProgettiAttiviProprietario(); // Non permette di andare alla schermata voluta se non ci sono progetti disponibili
            addService.getNomiLottiProprietario();

            Router.getInstance().loadContent("home/addActivityBlock");
        } catch (Exception e) {
            FocusUtil.setFocusTo(addNewContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
}
