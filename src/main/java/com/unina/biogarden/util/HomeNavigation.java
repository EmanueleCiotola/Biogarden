package com.unina.biogarden.util;

public class HomeNavigation {
    private static final HomeNavigation instance = new HomeNavigation();

    private HomeNavigation() {}
    public static HomeNavigation getInstance() {
        return instance;
    }
    
    
}
