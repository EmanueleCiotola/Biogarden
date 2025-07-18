package com.unina.biogarden.gui.controller.home;

import java.time.LocalDate;

import com.unina.biogarden.service.AddAndUpdateService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

public class AddProjectController {
    @FXML private VBox addProjectContainer;
    @FXML private TextField projectNameField;
    @FXML private DatePicker projectStartDatePicker;
    @FXML private DatePicker projectEndDatePicker;

    AddAndUpdateService addNewService;

    public void initialize() {
        addNewService = AddAndUpdateService.getInstance();

        projectEndDatePicker.setDayCellFactory(_ -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true); // Disabilita le date precedenti a oggi
                }
            }
        });
    }


    @FXML private void handleAddNewProject() {
        try {
            String name = projectNameField.getText();
            LocalDate startDate = projectStartDatePicker.getValue();
            LocalDate endDate = projectEndDatePicker.getValue();

            addNewService.addNewProject(name, startDate, endDate);

            Router.getInstance().showSnackbar("Progetto aggiunto con successo.");
            Router.getInstance().loadContent("home/addNewBlock");
        } catch (Exception e) {
            FocusUtil.setFocusTo(addProjectContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    
    @FXML private void handleGoBackToAddNewBlock() {
        Router.getInstance().loadContent("home/addNewBlock");
    }
}
