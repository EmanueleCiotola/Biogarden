package com.unina.biogarden.gui.controller.home.widget;

import com.unina.biogarden.model.Lotto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlotCardController {
    @FXML private Label idLottoLabel;
    @FXML private Label mqLabel;

    public void setData(Lotto lotto) {
        idLottoLabel.setText(lotto.getIdLotto());
        mqLabel.setText(lotto.getMq().toString());
    }
}
