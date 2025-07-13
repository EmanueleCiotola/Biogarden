module com.unina.biogarden {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires transitive javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.base;

    opens com.unina.biogarden to javafx.fxml;
    exports com.unina.biogarden;
    opens com.unina.biogarden.gui.controller to javafx.fxml;
    exports com.unina.biogarden.gui.controller;
    opens com.unina.biogarden.gui.controller.auth to javafx.fxml;
    exports com.unina.biogarden.gui.controller.auth;
    opens com.unina.biogarden.gui.controller.home to javafx.fxml;  
    exports com.unina.biogarden.gui.controller.home;
    opens com.unina.biogarden.gui.controller.home.widget to javafx.fxml;
    exports com.unina.biogarden.gui.controller.home.widget;
    opens com.unina.biogarden.model to javafx.base;
    exports com.unina.biogarden.model;
}