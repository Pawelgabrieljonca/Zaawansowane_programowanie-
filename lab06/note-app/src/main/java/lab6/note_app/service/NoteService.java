package lab6.note_app.service;

import lab6.note_app.model.Note;
import java.util.List;

/**
 * Interfejs dla warstwy usług Notatek.
 * Definiuje operacje logiki biznesowej dla notatek.
 */
public interface NoteService {
    /**
     * Pobiera listę wszystkich notatek.
     */
    List<Note> listOfNotes();

    /**
     * Dodaje nową notatkę do systemu.
     */
    void addNote(Note note);
}
