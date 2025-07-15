package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.gui.controller.home.widget.ProjectCardController;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.ErrorMessage;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;
import com.unina.biogarden.util.exception.DatabaseException;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllProjectsController extends BaseCardListController<Progetto> {
    private static final String PROJECT_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ProjectCard.fxml";
    
    @FXML private FlowPane projectsCardContainer;
    @FXML private VBox spazioFiltri;
    @FXML private TilePane projectsCheckboxContainer;
    @FXML private Label filterLabel;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;
    private List<Lotto> allPlots;
    private Map<String, Set<String>> progettoToLotti = new HashMap<>();
    private List<CheckBox> plotsCheckBoxes = new ArrayList<>();
    private Set<String> filtriAttivi = new LinkedHashSet<>(); // Hashset che mantiene l’ordine di inserimento
    private List<Progetto> allProjects;

    public void initialize() {
        homeService = HomeService.getInstance();

        caricaCheckBox();
        mappaProgettoSuLotti();
        caricaSezioneFiltriSeDisponibile();

        init(projectsCardContainer, emptyMessageLabel);
    }

    @Override
    protected String getCardFXMLPath() {
        return PROJECT_CARD_PATH;
    }
    @Override
    protected List<Progetto> caricaElementi() throws DatabaseException {
        allProjects = homeService.getProgettiUtente();
        return allProjects;
    }
    @Override
    protected void setupCardController(Object controller, Progetto progetto) {
        ((ProjectCardController) controller).setData(progetto);
    }
    @Override
    protected void mostraErrore() {
        Router.getInstance().showSnackbar(ErrorMessage.CARICAMENTO_PROGETTI.toString());
    }

    private void caricaCheckBox() {
        try {
            allPlots = homeService.getLottiUtente();
        } catch (Exception e) {
            FocusUtil.setFocusTo(projectsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private void mappaProgettoSuLotti() {
        for (Lotto lotto : allPlots) {
            progettoToLotti.computeIfAbsent(lotto.getIdProgetto(), _ -> new HashSet<>()).add(lotto.getIdLotto()); // Aggiunge l'id del lotto al set dei lotti associati a un progetto, creando automaticamente il set se non esiste ancora
        }
    }
    private void caricaSezioneFiltriSeDisponibile() {
        if (allPlots.isEmpty()) {
            nascondiSezioneFiltri();
        } else {
            mostraCheckbox();
        }
    }
    
    private void mostraCheckbox() {
        for (Lotto lotto : allPlots) {
            String idLotto = lotto.getIdLotto();

            CheckBox cb = new CheckBox("Lotto numero " + idLotto);
            cb.setUserData(idLotto);
            cb.selectedProperty().addListener((_, _, isNowSelected) -> onLottoCheckChanged(idLotto, isNowSelected));

            plotsCheckBoxes.add(cb);
            projectsCheckboxContainer.getChildren().add(cb);
        }
    }
    @FXML private void onLottoCheckChanged(String idLotto, boolean isNowSelected) {
        aggiornaFiltri(idLotto, isNowSelected);
        aggiornaVistaFiltrata();
    }
    private void aggiornaFiltri(String idLotto, boolean isSelezionato) {
        if (isSelezionato) {
            filtriAttivi.add(idLotto);
        } else {
            filtriAttivi.remove(idLotto);
        }
    }
    private void aggiornaVistaFiltrata() {
        aggiornaFiltroLabel();
        List<Progetto> progettiDaMostrare = filtriAttivi.isEmpty() ? allProjects : applicaFiltri();
        mostraSezione(progettiDaMostrare);
    }
    private void aggiornaFiltroLabel() {
        if (filtriAttivi.isEmpty()) {
            filterLabel.setText("Nessun filtro applicato.");
        } else {
            filterLabel.setText("Filtri attivi sui lotti: " + String.join(", ", filtriAttivi));
        }
    }
    
    private List<Progetto> applicaFiltri() { // Non va nel service siccome non si accede al DAO e non si usano filtri altrove
        List<Progetto> progettiFiltrati = new ArrayList<>();
        for (Progetto progetto : allProjects) {
            Set<String> lottiDelProgetto = progettoToLotti.getOrDefault(progetto.getIdProgetto(), Collections.emptySet()); // Prende i lotti associati al progetto, in caso non ce ne siano restituisce set vuoto (ma non nullo)
            if (lottiDelProgetto.containsAll(filtriAttivi)) { // Controllo che tutti i lotti per cui sto filtrando siano associati al progetto.
                progettiFiltrati.add(progetto);
            }
        }
        return progettiFiltrati;
    }

    private void nascondiSezioneFiltri() {
        spazioFiltri.setVisible(false);
        spazioFiltri.setManaged(false);
    }
}