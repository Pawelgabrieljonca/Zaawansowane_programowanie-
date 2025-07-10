package lab6.note_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Główna klasa aplikacji Spring Boot.
 * Konfiguruje skanowanie komponentów, repozytoria JPA i skanowanie encji.
 * Zapewnia odnalezienie wszystkich komponentów w pakiecie bazowym 'lab6.note_app' i jego podpakietach.
 */
@SpringBootApplication

// Jawnie skanuje pakiet model w poszukiwaniu encji JPA
@EntityScan("lab6.note_app.model")
// Jawnie włącza repozytoria JPA w pakiecie repository
@EnableJpaRepositories("lab6.note_app.repository")
public class NoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteAppApplication.class, args);
	}
}
