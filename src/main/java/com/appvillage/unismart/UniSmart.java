package com.appvillage.unismart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UniSmart extends Application {
    @Override
    public void start(Stage stg) throws IOException {
        stg.setTitle("UniSmart");
        FXMLLoader root = new FXMLLoader(UniSmart.class.getResource("unismart.fxml"));
        stg.setScene(new Scene(root.load()));
        stg.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
