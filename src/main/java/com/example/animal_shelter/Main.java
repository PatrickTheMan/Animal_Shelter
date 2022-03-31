package com.example.animal_shelter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scenehandler.setStage(stage);
        stage.show();
        Scenehandler.bookingScene();
        Scenehandler.setSceneLocation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}