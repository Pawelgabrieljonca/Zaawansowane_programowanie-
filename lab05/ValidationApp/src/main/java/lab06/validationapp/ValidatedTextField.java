package lab06.validationapp;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ValidatedTextField extends HBox {
    private final TextInputControl inputControl;
    private final Label validationIndicator;
    private FieldValidator validator;
    private boolean valid;
    private boolean edited = false;

    public ValidatedTextField(TextInputControl inputControl) {
        this.inputControl = inputControl;
        this.valid = false;

        validationIndicator = new Label();
        validationIndicator.setPrefSize(15, 15);
        validationIndicator.setStyle("-fx-cursor: hand;");
        validationIndicator.setVisible(false);
        updateIndicator();

        setSpacing(5);
        getChildren().addAll(inputControl, validationIndicator);

        inputControl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!edited && !newValue.isEmpty()) {
                edited = true;
                validationIndicator.setVisible(true);
            }
            validateInput();
        });
    }

    public StringProperty textProperty() {
        return inputControl.textProperty();
    }

    public void addValidator(FieldValidator validator) {
        this.validator = validator;
        validateInput();
    }

    private void validateInput() {
        if (validator != null) {
            validator.check(inputControl.getText());
            valid = validator.isCorrect();
            Tooltip.uninstall(validationIndicator, null);
        }
        updateIndicator();
    }

    private void updateIndicator() {
        if (!edited) {
            validationIndicator.setVisible(false);
            return;
        }

        String iconPath = valid ? "/success.png" : "/error.png";
        try {
            Image icon = new Image(getClass().getResourceAsStream(iconPath));
            ImageView iconView = new ImageView(icon);
            iconView.setFitWidth(15);
            iconView.setFitHeight(15);
            validationIndicator.setGraphic(iconView);

            if (valid) {
                Tooltip validTooltip = new Tooltip("Dane poprawne");
                validTooltip.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
                Tooltip.install(validationIndicator, validTooltip);
            } else {
                String errorMsg = validator != null ? validator.getErrorMessage() : "Nieprawidłowe dane";
                Tooltip invalidTooltip = new Tooltip(errorMsg);
                invalidTooltip.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");
                Tooltip.install(validationIndicator, invalidTooltip);
            }
        } catch (Exception e) {
            validationIndicator.setText(valid ? "✓" : "✗");
        }
    }

    public boolean isValid() {
        return valid;
    }

    public boolean wasEdited() {
        return edited;
    }

    public String getText() {
        return inputControl.getText();
    }
}
