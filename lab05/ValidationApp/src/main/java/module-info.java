module lab06.validationapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab06.validationapp to javafx.fxml;
    exports lab06.validationapp;
}