package com.unina.biogarden.gui.controller.home.widget;

import com.unina.biogarden.model.ReportLotto;
import com.unina.biogarden.model.ReportVoceLotto;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PlotReportCardController {
    @FXML private Label idLottoLabel;
    @FXML private Label nRaccolteLabel;
    @FXML private GridPane tabellaReport;
    @FXML private PieChart graficoReport;

    public void setData(ReportLotto report) {
        idLottoLabel.setText(report.getIdLotto());
        nRaccolteLabel.setText(String.valueOf(report.getNumRaccolte()));

        int rowIndex = 1;
        for (ReportVoceLotto voce : report.getVoci()) {
            Label tipoLabel = new Label(voce.getTipo());
            Label mediaLabel = new Label(voce.getMediaKg());
            Label minLabel = new Label(voce.getMinKg());
            Label maxLabel = new Label(voce.getMaxKg());
            tabellaReport.addRow(rowIndex++, tipoLabel, mediaLabel, minLabel, maxLabel);

            int raccolteSuccesso = voce.getNumeroRaccolteSuccesso();
            if (raccolteSuccesso > 0) {
                PieChart.Data slice = new PieChart.Data(voce.getTipo(), raccolteSuccesso);
                graficoReport.getData().add(slice);
            }
        }
    }
}
