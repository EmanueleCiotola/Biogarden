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
}