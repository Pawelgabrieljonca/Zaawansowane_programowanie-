package lab6.note_app.service;

import lab6.note_app.model.Note;
import lab6.note_app.repository.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacja interfejsu NoteService.
 * Zawiera logikę biznesową do zarządzania notatkami.
 * Komunikuje się z NoteRepo w celu interakcji z bazą danych.
 */
@Service
public class NoteServiceImp implements NoteService {

    private final NoteRepo noteRepo;

    /**
     * Konstruktor do wstrzykiwania zależności NoteRepo.
     * Spring automatycznie wstrzyknie instancję NoteRepo.
     */
    @Autowired
    public NoteServiceImp(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    /**
     * Pobiera listę notatek, posortowanych malejąco według znacznika czasu.
     * Wykorzystuje niestandardową metodę zdefiniowaną w NoteRepo.
     */
    @Override
    public List<Note> listOfNotes() {
        return noteRepo.findByOrderByTimestampDesc();
    }

    /**
     * Dodaje nową notatkę do bazy danych.
     */
    @Override
    public void addNote(Note note) {
        noteRepo.save(note);
    }
}
