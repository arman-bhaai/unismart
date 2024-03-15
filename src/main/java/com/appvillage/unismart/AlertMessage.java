package com.appvillage.unismart;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.OptionalInt;

public class AlertMessage {
    Alert alert;
    public void successMessage(String txt){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(txt);
        alert.show();
    }
    public void errorMessage(String txt){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(txt);
        alert.show();
    }

    public boolean confirmMessage(String txt){
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText(txt);
        Optional<ButtonType> option = alert.showAndWait();
        return (option.isPresent() && option.get().equals(ButtonType.OK));
    }
    // vid p1 48 min
    // -fx-background-color: linear-gradient(to bottom right, #0f7b21, #00b919);
    // -fx-border-color: linear-gradient(to bottom right, #44eef4, #068fff);
}
