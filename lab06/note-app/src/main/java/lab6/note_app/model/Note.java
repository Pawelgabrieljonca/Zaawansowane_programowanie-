package lab6.note_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

/**
 * Reprezentuje pojedynczy wpis notatki w aplikacji.
 * Klasa ta jest Encją, mapowaną na tabelę w bazie danych.
 */
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unikalny identyfikator notatki

    private String title;   // Tytuł notatki
    private String content; // Treść notatki

    @Enumerated(EnumType.STRING) // Przechowuje nazwę enuma (np. "URGENT") jako String w bazie danych
    private Importance importance; // Poziom ważności notatki (URGENT, STANDARD, OPTIONAL)

    @Column(name = "timestamp") // Jawne zdefiniowanie nazwy kolumny
    private LocalDateTime timestamp; // Czas dodania notatki

    /**
     * Domyślny konstruktor wymagany przez JPA.
     */
    public Note() {
        // Konstruktor bezargumentowy
    }

    /**
     * Konstruktor do tworzenia nowej instancji Notatki ze wszystkimi właściwościami.
     * Czas dodania jest zazwyczaj ustawiany automatycznie przy utrwalaniu.
     */
    public Note(Long id, String title, String content, Importance importance) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.importance = importance;
        // timestamp zostanie ustawiony przez @PrePersist
    }

    /**
     * Ustawia znacznik czasu automatycznie przed utrwaleniem encji.
     */
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // --- Metody Getter ---

    /**
     * Pobiera ID notatki.
     */
    public Long getId() {
        return id;
    }

    /**
     * Pobiera tytuł notatki.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Pobiera treść notatki.
     */
    public String getContent() {
        return content;
    }

    /**
     * Pobiera poziom ważności notatki.
     */
    public Importance getImportance() {
        return importance;
    }

    /**
     * Pobiera znacznik czasu notatki.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // --- Metody Setter ---

    /**
     * Ustawia ID notatki.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ustawia tytuł notatki.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Ustawia treść notatki.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Ustawia poziom ważności notatki.
     */
    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    /**
     * Ustawia znacznik czasu notatki.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Nadpisuje metodę toString dla lepszego logowania/debugowania.
     */
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", importance=" + importance +
                ", timestamp=" + timestamp +
                '}';
    }
}
