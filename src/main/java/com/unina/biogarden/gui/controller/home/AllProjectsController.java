package com.unina.biogarden.gui.controller.home;

import com.unina.biogarden.gui.controller.home.widget.ProjectCardController;
import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;
import com.unina.biogarden.service.HomeService;
import com.unina.biogarden.util.FocusUtil;
import com.unina.biogarden.util.Router;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class AllProjectsController {
    
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

        loadCheckboxes();
        loadFilterSection();

        loadProjects();
        renderProjects(allProjects);
    }

    private void loadCheckboxes() {
        try {
            allPlots = homeService.getLottiUtente();
            buildProgettoToLottiMap();
        } catch (Exception e) {
            FocusUtil.setFocusTo(projectsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }
    private void buildProgettoToLottiMap() {
        for (Lotto lotto : allPlots) {
            progettoToLotti.computeIfAbsent(lotto.getIdProgetto(), _ -> new HashSet<>()).add(lotto.getIdLotto()); // Aggiunge l'id del lotto al set dei lotti associati a un progetto, creando automaticamente il set se non esiste ancora
        }
    }
    private void loadFilterSection() {
        if (allPlots.isEmpty()) {
            hideFilters();
        } else {
            renderCheckboxes();
        }
    }
    private void loadProjects() {
        try {
            allProjects = homeService.getProgettiUtente();
        } catch (Exception e) {
            FocusUtil.setFocusTo(projectsCardContainer);
            Router.getInstance().showSnackbar(e.getMessage());
        }
    }

    @FXML private void renderCheckboxes() {
        for (Lotto lotto : allPlots) {
            String idLotto = lotto.getIdLotto();

            CheckBox cb = new CheckBox("Lotto numero " + idLotto);
            cb.setUserData(idLotto);
            cb.selectedProperty().addListener((_, _, isNowSelected) -> onLottoCheckChanged(idLotto, isNowSelected));

            plotsCheckBoxes.add(cb);
            projectsCheckboxContainer.getChildren().add(cb);
        }
    }
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
        List<Progetto> progettiDaMostrare = filtriAttivi.isEmpty() ? allProjects : applyFilters();
        mostraProgetti(progettiDaMostrare);
    }
    private void aggiornaFiltroLabel() {
        if (filtriAttivi.isEmpty()) {
            filterLabel.setText("Nessun filtro applicato.");
        } else {
            filterLabel.setText("Filtri attivi sui lotti: " + String.join(", ", filtriAttivi));
        }
    }
    
    private void mostraProgetti(List<Progetto> progettiDaMostrare) {
        if (progettiDaMostrare.isEmpty()) {
            showEmptyListMessage();
        } else {
            hideEmptyListMessage();
            renderProjects(progettiDaMostrare);
        }
    }
    private List<Progetto> applyFilters() {
        List<Progetto> progettiFiltrati = new ArrayList<>();
        for (Progetto progetto : allProjects) {
            Set<String> lottiDelProgetto = progettoToLotti.getOrDefault(progetto.getIdProgetto(), Collections.emptySet()); // Prende i lotti associati al progetto, in caso non ce ne siano restituisce set vuoto (ma non nullo)
            if (lottiDelProgetto.containsAll(filtriAttivi)) { // Controllo che tutti i lotti per cui sto filtrando siano associati al progetto.
                progettiFiltrati.add(progetto);
            }
        }
        return progettiFiltrati;
    }
    private void renderProjects(List<Progetto> progetti) {
        try {
            projectsCardContainer.getChildren().clear();
    
            for (Progetto progetto : progetti) {
                Parent card = createProjectCard(progetto);
                projectsCardContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            Router.getInstance().showSnackbar("Errore nel caricamento dei progetti. Riprova più tardi.");
        }
    }
    private Parent createProjectCard(Progetto progetto) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PROJECT_CARD_PATH));
        Parent card = loader.load();
        ProjectCardController controller = loader.getController();
        controller.setData(progetto);
        return card;
    }

    private void hideFilters() {
        spazioFiltri.setVisible(false);
        spazioFiltri.setManaged(false);
    }
    private void showEmptyListMessage() {
        projectsCardContainer.setVisible(false);
        projectsCardContainer.setManaged(false);
        emptyMessageLabel.setVisible(true);
        emptyMessageLabel.setManaged(true);
    }
    private void hideEmptyListMessage() {
        projectsCardContainer.setVisible(true);
        projectsCardContainer.setManaged(true);
        emptyMessageLabel.setVisible(false);
        emptyMessageLabel.setManaged(false);
    }
}