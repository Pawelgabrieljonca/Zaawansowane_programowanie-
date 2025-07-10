package lab06.validationapp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Dostępność w czasie działania programu
@Target(ElementType.FIELD)          // Stosowana tylko do pól klas
public @interface ValidationPattern {
    String regex();         // Wyrażenie regularne dla walidacji
    String errorMessage();  // Komunikat błędu
}
