module com.appvillage.unismart {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.appvillage.unismart to javafx.fxml;
    exports com.appvillage.unismart;
}