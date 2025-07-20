package com.unina.biogarden.gui.controller.home;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.unina.biogarden.gui.controller.home.widget.PlotReportCardController;
import com.unina.biogarden.model.ReportLotto;
import com.unina.biogarden.model.ReportVoceLotto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.DatabaseException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class AllPlotsReportController extends BaseCardListController<ReportLotto> {
    private static final String PLOT_REPORT_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/plotReportCard.fxml";

    @FXML private FlowPane plotReportContainer;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;

    public void initialize() {
        homeService = HomeService.getInstance();

        init(plotReportContainer, emptyMessageLabel);
    }

    @Override
    protected String getCardFXMLPath() {
        return PLOT_REPORT_CARD_PATH;
    }

    @Override
    protected List<ReportLotto> caricaElementi() throws DatabaseException {
        List<ReportVoceLotto> listaPiana = homeService.getListaReportLotti();

        Map<String, List<ReportVoceLotto>> mappa = listaPiana.stream() // Raggruppa per idLotto
            .collect(Collectors.groupingBy(ReportVoceLotto::getIdLotto));

        List<ReportLotto> raggruppamentoPerLotto = mappa.entrySet().stream() // Costruisci lista di gruppi con somma raccolte riuscite
            .map(e -> {
                String idLotto = e.getKey();
                List<ReportVoceLotto> voci = e.getValue();

                int numeroRaccolteSuccesso = voci.stream()
                    .mapToInt(ReportVoceLotto::getNumeroRaccolteSuccesso)
                    .sum();

                return new ReportLotto(idLotto, voci, numeroRaccolteSuccesso);
            })
            .collect(Collectors.toList());

        return raggruppamentoPerLotto;
    }

    @Override
    protected void setupCardController(Object controller, ReportLotto reportLotto) {
        ((PlotReportCardController) controller).setData(reportLotto);
    }

    @Override
    protected void mostraErrore() {
        Router.getInstance().showSnackbar(ErrorMessage.CARICAMENTO_LOTTI.toString());
    }
}
