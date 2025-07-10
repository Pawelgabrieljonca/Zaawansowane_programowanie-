package lab06.validationapp;

public interface FieldValidator {
    void check(String value); // Metoda walidująca wartość
    boolean isCorrect();      // Sprawdza poprawność wartości
    String getErrorMessage(); // Zwraca komunikat błędu
}
