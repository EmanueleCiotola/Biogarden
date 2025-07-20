package com.unina.biogarden.gui.controller.home;

import java.util.List;

import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.service.AddAndUpdateService;
import com.unina.biogarden.util.DataManager;
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
            List<Progetto> progetti = addService.getProgettiAttiviProprietario();
            List<Lotto> lotti = addService.getLottiProprietario();

            DataManager.getInstance().clear();
            DataManager.getInstance().setProgetti(progetti);
            DataManager.getInstance().setLotti(lotti);

            Router.getInstance().loadContent("home/addActivityBlock");
        } catch (Exception e) {
            FocusUtil.setFocusTo(addNewContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
}
