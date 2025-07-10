package lab06.validationapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Field;

public class UserFormApp extends Application {
    private final UserData userData = new UserData();
    private Button submitButton;

    @Override
    public void start(Stage primaryStage) {
        Label headerLabel = new Label("Rejestracja użytkownika");
        headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Podaj imię:");

        ValidatedTextField nameField = new ValidatedTextField(new TextField());
        nameField.setMaxWidth(250);

        submitButton = new Button("Zapisz");
        submitButton.setDisable(true);
        submitButton.setOnAction(e -> {
            userData.setUsername(nameField.getText());
            System.out.println("Użytkownik zapisany: " + userData.getUsername());
        });

        initializeValidation(nameField, "username");

        nameField.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean isInputEmpty = newVal == null || newVal.isEmpty();
            submitButton.setDisable(isInputEmpty || !nameField.isValid());
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(headerLabel, nameLabel, nameField, submitButton);

        Scene scene = new Scene(layout, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Walidacja danych użytkownika");
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(200);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void initializeValidation(ValidatedTextField inputField, String fieldName) {
        try {
            Field field = UserData.class.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(ValidationPattern.class)) {
                ValidationPattern annotation = field.getAnnotation(ValidationPattern.class);
                FieldValidator validator = new RegexValidator(annotation.regex(), annotation.errorMessage());
                inputField.addValidator(validator);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
