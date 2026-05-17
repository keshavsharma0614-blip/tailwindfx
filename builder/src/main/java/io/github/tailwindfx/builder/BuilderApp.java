package io.github.tailwindfx.builder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BuilderApp extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(new Label("TailwindFX Builder"));

        StackPane canvas = new StackPane();
        canvas.getChildren().add(new Label("Canvas Area"));

        root.setTop(toolBar);
        root.setCenter(canvas);

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("TailwindFX Visual Builder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
