module com.appvillage.unismart {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;
    requires java.sql;


    opens com.appvillage.unismart to javafx.fxml;
    exports com.appvillage.unismart;
}