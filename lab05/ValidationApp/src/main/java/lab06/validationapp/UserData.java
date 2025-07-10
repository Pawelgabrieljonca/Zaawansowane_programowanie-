package lab06.validationapp;

public class UserData {
    @ValidationPattern(
            regex = "^[A-Za-z]+$",
            errorMessage = "Imię może zawierać tylko litery (A-Z, a-z)"
    )
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
