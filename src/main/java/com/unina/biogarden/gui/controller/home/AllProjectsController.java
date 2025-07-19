package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.gui.controller.home.widget.ProjectCardController;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllProjectsController extends BaseCardListController<Progetto> {
    private static final String PROJECT_CARD_PATH = "/com/unina/biogarden/gui/view/home/widget/ProjectCard.fxml";
    
    @FXML private FlowPane projectsCardContainer;
    @FXML private VBox spazioFiltri;
    @FXML private TilePane projectsCheckboxContainer;
    @FXML private Label filterLabel;
    @FXML private Label emptyMessageLabel;

    private HomeService homeService;
    private Map<String, Set<String>> progettoToLotti = new HashMap<>(); // Mappatura dei lotti per ogni progetto
    private Set<String> allPlots;
    private List<CheckBox> plotsCheckBoxes = new ArrayList<>();
    private Set<String> filtriAttivi = new LinkedHashSet<>(); // HashSet che mantiene l'ordine di inserimento
    private List<Progetto> allProjects;

    public void initialize() {
        homeService = HomeService.getInstance();

        caricaRelazioniProgettoLotto();
        allPlots = estraiLotti();
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

    private void caricaRelazioniProgettoLotto() {
        try {
            progettoToLotti = homeService.getRelazioniProgettoLotto();
        } catch (Exception e) {
            FocusUtil.setFocusTo(projectsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
 
    private Set<String> estraiLotti() {
        return progettoToLotti.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }
    
    private void caricaSezioneFiltriSeDisponibile() {
        if (allPlots.isEmpty()) {
            nascondiSezioneFiltri();
        } else {
            mostraCheckbox(allPlots);
        }
    }
    
    private void mostraCheckbox(Set<String> allPlots) {
        for (String idLotto : allPlots) {
            CheckBox cb = new CheckBox("Lotto numero " + idLotto);
            cb.setUserData(idLotto);
            cb.selectedProperty().addListener((_, _, isNowSelected) -> onLottoCheckChanged(idLotto, isNowSelected));

            plotsCheckBoxes.add(cb);
            projectsCheckboxContainer.getChildren().add(cb);
        }
    }
    
    @FXML 
    private void onLottoCheckChanged(String idLotto, boolean isNowSelected) {
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
    
    private List<Progetto> applicaFiltri() {
        List<Progetto> progettiFiltrati = new ArrayList<>();
        for (Progetto progetto : allProjects) {
            Set<String> lottiDelProgetto = progettoToLotti.getOrDefault(progetto.getIdProgetto().toString(), Collections.emptySet());
            if (lottiDelProgetto.containsAll(filtriAttivi)) {
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