package lab06.validationapp;

import java.util.regex.Pattern;

public class RegexValidator implements FieldValidator {
    private final Pattern regexPattern; // Skompilowane wyrażenie regularne
    private final String errorMessage;  // Komunikat błędu
    private boolean valid;              // Status walidacji

    public RegexValidator(String regex, String errorMessage) {
        this.regexPattern = Pattern.compile(regex); // Kompilacja wyrażenia regularnego
        this.errorMessage = errorMessage;
        this.valid = false;
    }

    @Override
    public void check(String value) {
        // Sprawdzenie zgodności wartości z wzorcem
        valid = regexPattern.matcher(value).matches();
    }

    @Override
    public boolean isCorrect() {
        return valid;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
