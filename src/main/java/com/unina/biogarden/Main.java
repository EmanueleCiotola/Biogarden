package com.unina.biogarden;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/unina/biogarden/gui/view/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setMaximized(true);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.setTitle("Biogarden");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/unina/biogarden/asset/icon/logo/logo_16.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/unina/biogarden/asset/icon/logo/logo_32.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/unina/biogarden/asset/icon/logo/logo_48.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/unina/biogarden/asset/icon/logo/logo_256.png")));
        scene.getStylesheets().add(getClass().getResource("/com/unina/biogarden/gui/style/style.css").toExternalForm());
        Font.loadFont(getClass().getResourceAsStream("/com/unina/biogarden/gui/asset/font/IBMPlexSans-VariableFont_wdth,wght.ttf"), 14);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}