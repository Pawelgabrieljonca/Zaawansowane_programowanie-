module pl.edu.reflectioneditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.edu.reflectioneditor to javafx.fxml;
    exports pl.edu.reflectioneditor;
}