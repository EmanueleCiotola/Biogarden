package com.unina.biogarden;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import com.unina.biogarden.router.Router;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/unina/biogarden/gui/view/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setMaximized(true);
        stage.setMinWidth(300);
        stage.setMinHeight(525);
        stage.setTitle("UNINA BioGarder");
        stage.setScene(scene);
        stage.show();

        Font.loadFont(getClass().getResourceAsStream("/com/unina/biogarden/gui/asset/font/IBMPlexSans-VariableFont_wdth,wght.ttf"), 14);
        scene.getStylesheets().add(getClass().getResource("/com/unina/biogarden/gui/style/style.css").toExternalForm());

        StackPane rootStack = (StackPane) scene.lookup("#rootStack");
        Router.getInstance().setRootStack(rootStack);
        Router.getInstance().navigateTo("loginPage");
    }

    public static void main(String[] args) {
        launch(args);
    }
}