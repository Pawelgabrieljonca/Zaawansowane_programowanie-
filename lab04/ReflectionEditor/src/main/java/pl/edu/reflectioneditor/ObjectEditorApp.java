package pl.edu.reflectioneditor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.lang.reflect.*;
import java.util.*;

public class ObjectEditorApp extends Application {
    private VBox attributesBox = new VBox(10);
    private Object activeObject;
    private Map<String, Control> inputFields = new HashMap<>();
    private TextArea logArea = new TextArea();

    @Override
    public void start(Stage window) {
        TextField classInputField = new TextField("pl.edu.reflectioneditor.SampleBean");
        classInputField.setPromptText("Podaj pełną nazwę klasy");

        Button createInstanceButton = new Button("Utwórz obiekt");
        createInstanceButton.setOnAction(event -> {
            String className = classInputField.getText();
            try {
                Class<?> loadedClass = Class.forName(className);
                activeObject = loadedClass.getDeclaredConstructor().newInstance();
                showAttributes(loadedClass);
                logArea.appendText("Obiekt klasy " + className + " został utworzony.\n");
            } catch (Exception ex) {
                logArea.appendText("Błąd przy tworzeniu obiektu: " + ex.getMessage() + "\n");
            }
        });

        Button saveButton = new Button("Zapisz zmiany");
        saveButton.setOnAction(event -> saveUpdates());

        logArea.setEditable(false);
        logArea.setPrefRowCount(8);

        VBox layout = new VBox(10, classInputField, createInstanceButton, attributesBox, saveButton,
                new Label("Logi:"), logArea);
        layout.setPadding(new Insets(10));

        window.setScene(new Scene(layout, 600, 600));
        window.setTitle("Object Editor");
        window.show();
    }

    private void showAttributes(Class<?> clazz) {
        attributesBox.getChildren().clear();
        inputFields.clear();
        for (Field field : clazz.getDeclaredFields()) {
            String attributeName = field.getName();
            String capitalizedName = Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);
            String getterMethodName = "get" + capitalizedName;

            try {
                Method getterMethod = clazz.getMethod(getterMethodName);
                Object fieldValue = getterMethod.invoke(activeObject);

                Label attributeLabel = new Label(attributeName + ":");
                Control inputControl;
                if (attributeName.toLowerCase().contains("text")) {
                    TextArea textArea = new TextArea(fieldValue != null ? fieldValue.toString() : "");
                    textArea.setPrefRowCount(3);
                    inputControl = textArea;
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(fieldValue != null && Boolean.parseBoolean(fieldValue.toString()));
                    inputControl = checkBox;
                } else {
                    TextField textField = new TextField(fieldValue != null ? fieldValue.toString() : "");
                    inputControl = textField;
                }
                inputFields.put(attributeName, inputControl);
                attributesBox.getChildren().add(new VBox(attributeLabel, inputControl));
            } catch (Exception e) {
                logArea.appendText("Błąd odczytu pola " + attributeName + ": " + e.getMessage() + "\n");
            }
        }
    }

    private void saveUpdates() {
        Class<?> objectClass = activeObject.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            String attributeName = field.getName();
            String capitalizedName = Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);
            String setterMethodName = "set" + capitalizedName;
            Control inputControl = inputFields.get(attributeName);
            if (inputControl == null) continue;

            try {
                Method setterMethod = objectClass.getMethod(setterMethodName, field.getType());
                Object parsedValue = convertInput(inputControl, field.getType());
                setterMethod.invoke(activeObject, parsedValue);
                logArea.appendText("Pole " + attributeName + " zmienione na " + parsedValue + "\n");
            } catch (Exception e) {
                logArea.appendText("Błąd zapisu pola " + attributeName + ": " + e.getMessage() + "\n");
            }
        }
    }

    private Object convertInput(Control control, Class<?> targetType) {
        try {
            String inputValue;
            if (control instanceof TextArea) {
                inputValue = ((TextArea) control).getText();
            } else if (control instanceof TextField) {
                inputValue = ((TextField) control).getText();
            } else if (control instanceof CheckBox) {
                return ((CheckBox) control).isSelected();
            } else {
                return null;
            }

            if (targetType == String.class) return inputValue;
            if (targetType == int.class || targetType == Integer.class) return Integer.parseInt(inputValue);
            if (targetType == float.class || targetType == Float.class) return Float.parseFloat(inputValue);
            if (targetType == double.class || targetType == Double.class) return Double.parseDouble(inputValue);
            if (targetType == long.class || targetType == Long.class) return Long.parseLong(inputValue);
            if (targetType == short.class || targetType == Short.class) return Short.parseShort(inputValue);
            if (targetType == byte.class || targetType == Byte.class) return Byte.parseByte(inputValue);
            if (targetType == char.class || targetType == Character.class) return inputValue.charAt(0);
            if (targetType == boolean.class || targetType == Boolean.class) return Boolean.parseBoolean(inputValue);
        } catch (Exception e) {
            logArea.appendText("Błąd konwersji: " + e.getMessage() + "\n");
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
