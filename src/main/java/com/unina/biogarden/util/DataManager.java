package com.unina.biogarden.util;

import java.util.ArrayList;
import java.util.List;

import com.unina.biogarden.model.Lotto;
import com.unina.biogarden.model.Progetto;

public class DataManager {
    private static DataManager instance = new DataManager();

    private List<Progetto> progetti = new ArrayList<>();
    private List<Lotto> lotti = new ArrayList<>();

    private DataManager() {}

    public static DataManager getInstance() {
        return instance;
    }

    public void setProgetti(List<Progetto> progetti) {
        this.progetti = progetti;
    }
    public List<Progetto> getProgetti() {
        return progetti;
    }

    public void setLotti(List<Lotto> lotti) {
        this.lotti = lotti;
    }
    public List<Lotto> getLotti() {
        return lotti;
    }

    public void clear() {
        progetti.clear();
        lotti.clear();
    }
}
