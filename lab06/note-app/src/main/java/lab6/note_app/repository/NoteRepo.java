package lab6.note_app.repository;

import lab6.note_app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    /**
     * Pobiera wszystkie notatki posortowane malejąco według znacznika czasu.
     * Sygnatura tej metody jest automatycznie implementowana przez Spring Data JPA.
     */
    List<Note> findByOrderByTimestampDesc();
}
